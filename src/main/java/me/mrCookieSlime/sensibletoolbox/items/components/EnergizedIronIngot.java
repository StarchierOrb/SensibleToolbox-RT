package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class EnergizedIronIngot extends BaseSTBItem {
    public static final MaterialData md = new MaterialData(Material.IRON_INGOT);

    public EnergizedIronIngot() {
    }

    public EnergizedIronIngot(ConfigurationSection conf) {

    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "带电铁锭";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:660", "诡异的发光..." };
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
