package me.mrCookieSlime.sensibletoolbox.items.machineupgrades;

import me.mrCookieSlime.sensibletoolbox.items.components.IntegratedCircuit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class ThoroughnessUpgrade extends MachineUpgrade {
    private static final MaterialData md = new MaterialData(Material.ENCHANTED_BOOK);

    public static final int BONUS_OUTPUT_CHANCE = 8; // percent

    public ThoroughnessUpgrade() {
    }

    public ThoroughnessUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "����������� �� " + ChatColor.RED + "ȫ����������";
    }

    @Override
    public String[] getLore() {
        return new String[] {
			    "TID:506",
        	    "��������Ϸ���һ̨STB������������",
                "�ٶ�����: + x0.7��",
                "��Դʹ����: + x1.6��",
                "�������: +" + BONUS_OUTPUT_CHANCE + "%"
        };
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("   ", " E ", "   ");
        IntegratedCircuit ic = new IntegratedCircuit();
        registerCustomIngredients(ic);
        recipe.setIngredient('E', Material.BARRIER);
        return recipe;*/
		return null;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
