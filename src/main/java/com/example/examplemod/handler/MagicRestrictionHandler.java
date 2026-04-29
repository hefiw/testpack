package com.example.examplemod.handler;

import com.example.examplemod.util.TagUtil;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN;
import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;

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
        return player.getTags().contains(MAGIC_CLASS_TAG_PATH);
        //return TagUtil.hasTag(player, MAGIC_CLASS_TAG_NAMESPACE, MAGIC_CLASS_TAG_PATH);
    }

    @SubscribeEvent
    public static void onSpellPreCast(SpellPreCastEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer)) return;

        // Если у игрока нет тега для использования магии - отменяем действие
        if (!canPlayerUseMagic(player)) {
            event.setCanceled(true);
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("§cУ вас нет доступа к магии!"), true);
        }
    }
}