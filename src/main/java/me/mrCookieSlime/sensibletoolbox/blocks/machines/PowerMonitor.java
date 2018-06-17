package me.mrCookieSlime.sensibletoolbox.blocks.machines;

import me.mrCookieSlime.CSCoreLibPlugin.general.Math.DoubleHandler;
import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.energy.EnergyNet;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBBlock;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.energycells.TenKEnergyCell;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class PowerMonitor extends BaseSTBBlock {
	
    private static final MaterialData md = STBUtil.makeColouredMaterial(Material.STAINED_GLASS, DyeColor.ORANGE);

    public PowerMonitor() {
    }

    public PowerMonitor(ConfigurationSection conf) {
        super(conf);
    }
    
	@Override
	public MaterialData getMaterialData() {
		return md;
	}

	@Override
	public String getItemName() {
		return "��Դ������";
	}

	@Override
	public String[] getLore() {
		return new String[] {
				"��һ�����ӵĸ�ʾ����",
				"��ʾ��Դ�������Դ ����/����"
		};
	}

	@Override
	public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("GGG", "RCR", "GGG");
        TenKEnergyCell cell = new TenKEnergyCell();
        cell.setCharge(0.0);
        registerCustomIngredients(cell);
        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('C', STBUtil.makeWildCardMaterialData(cell));
        recipe.setIngredient('R', Material.REDSTONE);
        return recipe;
	}
	
	@Override
	public int getTickRate() {
        return 100;
    }
	
	@Override
	public void onServerTick() {
        updateAttachedLabelSigns();
		super.onServerTick();
    }
	
	@Override
    protected String[] getSignLabel(BlockFace face) {
        String[] label = super.getSignLabel(face);
        for (BlockFace f: STBUtil.mainHorizontalFaces) {
            EnergyNet net = SensibleToolbox.getEnergyNet(getRelativeLocation(f).getBlock());
            if (net != null) {
            	double stat = net.getSupply() - net.getDemand();
            	String prefix;
            	if (stat > 0) prefix = "&a&l+";
            	else prefix = "&4&l-";
            	label[2] = prefix + " &8" + DoubleHandler.getFancyDouble(Double.valueOf(String.valueOf(stat).replace("-", ""))) + " SCU/t";
            	break;
            }
        }
        return label;
    }
}