package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

public class IronDust extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public IronDust() {
        super();
    }

    public IronDust(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "����";
    }

    @Override
    public String[] getLore() {
        return new String[]{"TID:1006", "�����ŵ�ұ����", " ������¯ȼ�����������"};
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
        return new ItemStack(Material.IRON_INGOT);
    }
}
