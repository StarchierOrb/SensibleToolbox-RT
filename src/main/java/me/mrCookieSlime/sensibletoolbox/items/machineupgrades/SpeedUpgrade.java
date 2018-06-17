package me.mrCookieSlime.sensibletoolbox.items.machineupgrades;

import me.mrCookieSlime.sensibletoolbox.items.components.SimpleCircuit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class SpeedUpgrade extends MachineUpgrade {
    private static final MaterialData md = new MaterialData(Material.ENCHANTED_BOOK);

    public SpeedUpgrade() {
    }

    public SpeedUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public String getItemName() {
        return "机器升级组件 · " + ChatColor.GOLD + "提速装置";
    }

    @Override
    public String[] getLore() {
        return new String[]{
			"TID:505",
        	"将这个材料放入一台STB机器中来升级",
        	"升级后速度: 提升1.4倍",
        	"能源使用量: 提升1.6倍"
        };
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        recipe.shape("   ", " S ", "   ");
        recipe.setIngredient('S', Material.BARRIER);
        return recipe;*/
		return null;
    }
}
