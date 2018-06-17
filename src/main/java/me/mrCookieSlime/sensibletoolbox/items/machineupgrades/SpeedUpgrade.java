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
        return "����������� �� " + ChatColor.GOLD + "����װ��";
    }

    @Override
    public String[] getLore() {
        return new String[]{
			"TID:505",
        	"��������Ϸ���һ̨STB������������",
        	"�������ٶ�: ����1.4��",
        	"��Դʹ����: ����1.6��"
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
