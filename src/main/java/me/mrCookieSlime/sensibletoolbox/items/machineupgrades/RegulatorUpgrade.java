package me.mrCookieSlime.sensibletoolbox.items.machineupgrades;

import me.mrCookieSlime.sensibletoolbox.items.components.SimpleCircuit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class RegulatorUpgrade extends MachineUpgrade {
    private static final MaterialData md = new MaterialData(Material.ENCHANTED_BOOK);

    public RegulatorUpgrade() {
    }

    public RegulatorUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "机器升级组件 ·" + ChatColor.GOLD + " 智能化装置";
    }

    @Override
    public String[] getLore() {
        return new String[] {
			"TID:504",
            "增加机器的自动化影响",
            "以提高资源利用率。",
            "* 效果因机器而异"
        };
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        recipe.shape("   ", "S  ", "   ");
        recipe.setIngredient('S', Material.BARRIER);
        return recipe;*/
		return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
