package com.example.examplemod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

/**
 * Утилита для проверки наличия тегов у игрока
 * Используется для проверки классов из NeoOrigins и других модов
 */
public class TagUtil {

    /**
     * Проверяет наличие тега у игрока
     * @param player игрок для проверки
     * @param tagNamespace пространство имен тега (например "neoorigins")
     * @param tagPath путь тега (например "mage_class")
     * @return true если тег присутствует, false если отсутствует
     */
    public static boolean hasTag(Player player, String tagNamespace, String tagPath) {
        ResourceLocation tagId = ResourceLocation.fromNamespaceAndPath(tagNamespace, tagPath);
        if(tagNamespace != "") {
            return player.getTags().contains(tagId.toString());
        }
        return player.getTags().contains(tagPath);
    }

    /**
     * Создаёт TagKey для игрока
     *
     * @param namespace пространство имен
     * @param path      путь тега
     * @return созданный TagKey
     */
    public static TagKey<EntityType<?>> createPlayerTag(String namespace, String path) {
        ResourceLocation tagId = ResourceLocation.fromNamespaceAndPath(namespace, path);
        return TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, tagId);
    }
}