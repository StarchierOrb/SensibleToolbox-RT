package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class EnergizedGoldIngot extends BaseSTBItem {
    public static final MaterialData md = new MaterialData(Material.GOLD_INGOT);

    public EnergizedGoldIngot() {
    }

    public EnergizedGoldIngot(ConfigurationSection conf) {

    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "����ɵĽ�";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:657", "�����⸽�ϵ�ɵĽ𶧣�", "�������ǳ����...." };
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
