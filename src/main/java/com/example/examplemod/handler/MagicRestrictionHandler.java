package com.example.examplemod.handler;

import com.example.examplemod.util.TagUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Обработчик событий для ограничения использования магии из Iron's Spells 'n Spellbooks
 * Проверяет наличие тега класса у игрока перед использованием заклинаний
 */
@EventBusSubscriber
public class MagicRestrictionHandler {

    // Пространство имен и путь тега для класса, который может использовать магию
    // Замените на актуальные значения из NeoOrigins
    private static final String MAGIC_CLASS_TAG_NAMESPACE = "neoorigins";
    private static final String MAGIC_CLASS_TAG_PATH = "mage_class";

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
     * Событие тика игрока - проверка возможности использования магии
     * Здесь можно добавлять дополнительную логику проверки
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        // Пример логирования для отладки
        if (!canPlayerUseMagic(player)) {
            // Игрок не имеет нужного тега для использования магии
            // Можно добавить сообщение или другую логику
        }
    }
}