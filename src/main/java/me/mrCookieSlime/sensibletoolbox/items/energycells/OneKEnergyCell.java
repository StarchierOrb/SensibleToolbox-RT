package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedIronIngot;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class OneKEnergyCell extends EnergyCell {
    public OneKEnergyCell() {
        super();
    }

    public OneKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 1000;
    }

    @Override
    public int getChargeRate() {
        return 30;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }
*/
    @Override
    public String getItemName() {
        return "1K ÄÜÁ¿µç³Ø";
    }
	
    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:655"
        };
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

}
