package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class InfernalDust extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.BLAZE_POWDER);

    public InfernalDust() {
    }

    public InfernalDust(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "����֮��";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:999", "�����õ�ʱ����Դ����������ϵõ�.", "���������ħ���ܶԴ��а���.", "���Խ������ۻ������۽��."};
    }

    @Override
    public Recipe getRecipe() {
        // no vanilla recipe to make infernal dust, but a custom recipe will be added
        return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
