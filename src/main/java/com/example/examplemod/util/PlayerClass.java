package com.example.examplemod.util;

import net.minecraft.world.entity.player.Player;

public class PlayerClass {
    ClassesUtil params;
    Player player;

    public PlayerClass(ClassesUtil cu, Player player) {
        this.player = player;
        this.params = cu;
    }
}
