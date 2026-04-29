package com.example.examplemod.util;

import net.minecraft.world.entity.player.Player;

public class ClassesUtil {
    private final String class_id;
    private final String class_name;
    private double max_mana;
    private double mana_regeneration;
    private int mana_value;

    public ClassesUtil(String class_id, String class_name, double max_mana, double mana_regeneration, int mana_value) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.max_mana = max_mana;
        this.mana_regeneration = mana_regeneration;
        this.mana_value = mana_value;
    }

    public String getClassID() {
        return this.class_id;
    }

    public String getClassName() {
        return this.class_name;
    }

    public double getMaxMana() {
        return this.max_mana;
    }

    public double getManaRegeneration() {
        return this.mana_regeneration;
    }

    public int getManaValue() {
        return this.mana_value;
    }

    public void setMaxMana(double newValue) {
        this.max_mana = newValue;
    }

    public void setManaRegeneration(double newValue) {
        this.mana_regeneration = newValue;
    }

    public void setManaValue(int newValue) {
        this.mana_value = newValue;
    }
}
