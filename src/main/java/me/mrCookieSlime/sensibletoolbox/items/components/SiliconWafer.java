package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class SiliconWafer extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.FIREWORK_CHARGE);

    public SiliconWafer() {
    }

    public SiliconWafer(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "�轺Ƭ";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:667", "����������Ƚ��ĵ��Ӽ��ɵ�·" };
    }

    @Override
    public Recipe getRecipe() {
        // made in a smelter
        return null;
    }
}
