package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class TenKEnergyCell extends EnergyCell {
    public TenKEnergyCell() {
        super();
    }

    public TenKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 10000;
    }

    @Override
    public int getChargeRate() {
        return 100;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }
*/
    @Override
    public String getItemName() {
        return "10K ÄÜÁ¿µç³Ø";
    }
	
    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:657"
        };
    }
	
    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("WWW", "W W", "   ");
        recipe.setIngredient('W', STBUtil.makeWildCardMaterialData(Material.WOOD));
        return recipe;
    }

}
