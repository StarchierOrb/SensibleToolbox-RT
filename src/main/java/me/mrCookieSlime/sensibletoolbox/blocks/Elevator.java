package me.mrCookieSlime.sensibletoolbox.blocks;

import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBBlock;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;

import org.apache.commons.lang.Validate;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;

public class Elevator extends BaseSTBBlock implements Colorable {
    private DyeColor color;

    public Elevator() {
        color = DyeColor.WHITE;
    }

    public Elevator(ConfigurationSection conf) {
        super(conf);
        color = DyeColor.valueOf(conf.getString("color"));
    }

    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("color", color.toString());
        return conf;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
        update(true);
    }

    @Override
    public MaterialData getMaterialData() {
        return STBUtil.makeColouredMaterial(Material.STAINED_CLAY, color);
    }

    @Override
    public String getItemName() {
        return "电梯方块";
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "§d在垂直的方向上",
                " §d连接其他电梯方块",
                "跳跃键： §a向上传送",
                "潜行键： §b向下传送"
        };
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("WWW", "WPW", "WWW");
        recipe.setIngredient('W', Material.WOOL);
        recipe.setIngredient('P', Material.ENDER_PEARL);
        return recipe;*/
		return null;
    }

    public Elevator findOtherElevator(BlockFace direction) {
        Validate.isTrue(direction == BlockFace.UP || direction == BlockFace.DOWN, "电梯连接方向必须是竖直的");

        Block b = getLocation().getBlock();
        Elevator res = null;
        while (b.getY() > 0 && b.getY() < b.getWorld().getMaxHeight()) {
            b = b.getRelative(direction);
            if (b.getType().isSolid()) {
                res = SensibleToolbox.getBlockAt(b.getLocation(), Elevator.class, false);
                break;
            }
        }
        return (res != null && res.getColor() == getColor()) ? res : null;
    }
}
