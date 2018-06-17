package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class GoldDust extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.GLOWSTONE_DUST);

    public GoldDust() {
        super();
    }

    public GoldDust(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "金粉";
    }

    @Override
    public String[] getLore() {
        return new String[]{"TID:662", "将它放进电力冶炼厂", "或者通过熔炉燃烧获得.."};
    }

    @Override
    public Recipe getRecipe() {
        return null;  // Only made by the Masher
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new ItemStack(Material.GOLD_INGOT);
    }
}
