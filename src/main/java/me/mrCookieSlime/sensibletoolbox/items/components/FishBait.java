package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class FishBait extends BaseSTBItem {
	
    private static final MaterialData md = new MaterialData(Material.PRISMARINE_CRYSTALS);

    public FishBait() {
    }

    public FishBait(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "鱼饵";
    }

    @Override
    public String[] getLore() {
        return new String[]{"TID:663", "用于自动钓鱼网络", "来捕抓水里的鱼"};
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
