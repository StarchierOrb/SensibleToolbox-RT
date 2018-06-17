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
        return "地狱之尘";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:999", "运气好的时候可以从烈焰人身上得到.", "对于抢夺伏魔可能对此有帮助.", "可以将它与金粉或者铁粉结合."};
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
