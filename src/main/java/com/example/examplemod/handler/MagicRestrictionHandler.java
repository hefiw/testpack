package com.example.examplemod.handler;

import com.example.examplemod.util.TagUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Обработчик событий для ограничения использования магии из Iron's Spells 'n Spellbooks
 * Проверяет наличие тега класса у игрока перед использованием заклинаний
 */
@EventBusSubscriber
public class MagicRestrictionHandler {

    // Пространство имен и путь тега для класса, который может использовать магию
    // Замените на актуальные значения из NeoOrigins
    private static final String MAGIC_CLASS_TAG_NAMESPACE = "";
    private static final String MAGIC_CLASS_TAG_PATH = "irons_spellcaster";

    /**
     * Проверяет может ли игрок использовать магию
     * @param player игрок
     * @return true если использование магии разрешено, false если запрещено
     */
    public static boolean canPlayerUseMagic(Player player) {
        return TagUtil.hasTag(player, MAGIC_CLASS_TAG_NAMESPACE, MAGIC_CLASS_TAG_PATH);
    }

    /**
     * Устанавливает тег для проверки магии
     * @param namespace пространство имен тега
     * @param path путь тега
     */
    public static void setMagicClassTag(String namespace, String path) {
        // Метод для изменения тега в runtime (если потребуется)
    }

    /**
     * Проверяет является ли предмет магическим предметом из Iron's Spells 'n Spellbooks
     * @param stack предмет для проверки
     * @return true если предмет из мода Iron's Spells
     */
    public static boolean isIronSpellItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        String itemNamespace = stack.getItem().toString().split(":")[0];
        return "irons_spellbooks".equals(itemNamespace);
    }

    /**
     * Событие тика игрока - проверка возможности использования магии
     * Здесь можно добавлять дополнительную логику проверки
     */
    /**@SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        // Пример логирования для отладки
        if (!canPlayerUseMagic(player)) {
            player.sendSystemMessage(Component.literal("У вас нет доступа к магии!"));
            return;
        }
    }*/

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        // Проверяем только предметы из Iron's Spells 'n Spellbooks
        if (!isIronSpellItem(stack)) {
            return;
        }

        // Если у игрока нет тега для использования магии - отменяем действие
        if (!canPlayerUseMagic(player)) {
            event.setCanceled(true);
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("§cУ вас нет доступа к магии!"), true);
            System.out.println("[MagicBlock] Магия заблокирована для игрока: " + player.getName().getString());
            return;
        }
    }
}