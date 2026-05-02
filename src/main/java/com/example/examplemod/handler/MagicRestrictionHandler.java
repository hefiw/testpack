package com.example.examplemod.handler;

import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Обработчик событий для ограничения использования магии из Iron's Spells 'n Spellbooks
 * Проверяет наличие тега класса у игрока перед использованием заклинаний
 */
@EventBusSubscriber
public class MagicRestrictionHandler {

    // Пространство имен и путь тега для класса, который может использовать магию
    // Замените на актуальные значения из NeoOrigins
    private static final String MAGIC_CLASS_TAG = "irons_spellcaster";

    /**
     * Проверяет может ли игрок использовать магию
     * @param player игрок
     * @return true если использование магии разрешено, false если запрещено
     */
    public static boolean canPlayerUseMagic(Player player) {
        return player.getTags().contains(MAGIC_CLASS_TAG);
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

    /**
     * Событие входа игрока на сервер - применяем атрибуты расы и класса
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Дополнительная проверка при входе на сервер
        // Атрибуты применяются через RaceClassCapabilityProvider
    }
}