package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class QuartzDust extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.SUGAR);

    public QuartzDust() {
    }

    public QuartzDust(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "石英粉";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:666", "通过冶炼厂来冶炼", "可以制作高纯度硅"};
    }

    @Override
    public Recipe getRecipe() {
        // no vanilla recipe - made in a masher
        return null;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new SiliconWafer().toItemStack();
    }
}
