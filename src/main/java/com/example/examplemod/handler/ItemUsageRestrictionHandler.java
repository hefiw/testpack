package com.example.examplemod.handler;

import com.example.examplemod.util.TagUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;
import java.util.HashSet;

import static com.example.examplemod.handler.MagicRestrictionHandler.isIronSpellItem;

/**
 * Обработчик событий для ограничения использования предметов
 * Запрещает использование предметов определённых ID игрокам без соответствующего тега класса
 */
@EventBusSubscriber
public class ItemUsageRestrictionHandler {

    // Список запрещённых предметов (ResourceLocation)
    private static final Set<ResourceLocation> RESTRICTED_ITEMS = new HashSet<>();

    // Маппинг: предмет -> требуемый тег класса
    private static final Set<ItemRestriction> ITEM_RESTRICTIONS = new HashSet<>();

    /**
     * Класс для хранения ограничений предмета
     */
    public static class ItemRestriction {
        public final ResourceLocation itemId;
        public final String tagNamespace;
        public final String tagPath;

        public ItemRestriction(String namespace, String path, String tagNamespace, String tagPath) {
            this.itemId = ResourceLocation.fromNamespaceAndPath(namespace, path);
            this.tagNamespace = tagNamespace;
            this.tagPath = tagPath;
        }

        public ItemRestriction(ResourceLocation itemId, String tagNamespace, String tagPath) {
            this.itemId = itemId;
            this.tagNamespace = tagNamespace;
            this.tagPath = tagPath;
        }
    }

    /**
     * Добавляет предмет в список запрещённых
     * @param namespace пространство имен предмета
     * @param path путь предмета
     */
    public static void addRestrictedItem(String namespace, String path) {
        RESTRICTED_ITEMS.add(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    /**
     * Добавляет ограничение на предмет с требуемым тегом
     * @param namespace пространство имен предмета
     * @param path путь предмета
     * @param tagNamespace пространство имен требуемого тега
     * @param tagPath путь требуемого тега
     */
    public static void addItemRestriction(String namespace, String path, String tagNamespace, String tagPath) {
        ITEM_RESTRICTIONS.add(new ItemRestriction(namespace, path, tagNamespace, tagPath));
    }

    /**
     * Добавляет ограничение на предмет с требуемым тегом
     * @param itemId ID предмета
     * @param tagNamespace пространство имен требуемого тега
     * @param tagPath путь требуемого тега
     */
    public static void addItemRestriction(ResourceLocation itemId, String tagNamespace, String tagPath) {
        ITEM_RESTRICTIONS.add(new ItemRestriction(itemId, tagNamespace, tagPath));
    }

    /**
     * Проверяет может ли игрок использовать предмет
     * @param player игрок
     * @param itemStack предмет
     * @return true если использование разрешено, false если запрещено
     */
    public static boolean canPlayerUseItem(Player player, ItemStack itemStack) {
        ResourceLocation itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(itemStack.getItem());

        // Проверка на полностью запрещённые предметы
        if (RESTRICTED_ITEMS.contains(itemId)) {
            return false;
        }

        // Проверка на предметы с ограничениями по тегу класса
        for (ItemRestriction restriction : ITEM_RESTRICTIONS) {
            if (restriction.itemId.equals(itemId)) {
                if (!TagUtil.hasTag(player, restriction.tagNamespace, restriction.tagPath)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Событие взаимодействия игрока с предметом (использование предмета правой кнопкой мыши)
     */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled()) return;

        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        if (isIronSpellItem(itemStack)) {
            return;
        }

        if (!canPlayerUseItem(player, itemStack)) {
            event.setCanceled(true);

            // Отправляем сообщение игроку
            ResourceLocation itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            player.displayClientMessage(
                net.minecraft.network.chat.Component.translatable(
                    "message.examplemod.cannot_use_item",
                    itemId.toString()
                ),
                true
            );
        }
    }

    /**
     * Событие взаимодействия игрока с блоком (может включать использование предметов)
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) return;
        
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        if (isIronSpellItem(itemStack)) {
            return;
        }

        if (!canPlayerUseItem(player, itemStack)) {
            event.setCanceled(true);

            ResourceLocation itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            player.displayClientMessage(
                net.minecraft.network.chat.Component.translatable(
                    "message.examplemod.cannot_use_item",
                    itemId.toString()
                ),
                true
            );
        }
    }
}