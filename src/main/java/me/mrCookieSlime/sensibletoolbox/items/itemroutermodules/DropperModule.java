package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import me.desht.dhutils.Debugger;

public class DropperModule extends DirectionalItemRouterModule {
    //private static final MaterialData md = makeDye(DyeColor.GRAY);
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public DropperModule() {
    }

    public DropperModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "物品路由器模块: 掉落器";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore(
				"TIDX:06",
        		"将此模块安装到物品路由器中", 
        		"可以将物品以掉落的形式", 
        		"掉落在设定的方向"
        );
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.DROPPER);
        return recipe;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public boolean execute(Location loc) {
        if (getItemRouter() != null && getItemRouter().getBufferItem() != null) {
            if (getFilter() != null && !getFilter().shouldPass(getItemRouter().getBufferItem())) {
                return false;
            }
            int toDrop = getItemRouter().getStackSize();
            ItemStack stack = getItemRouter().extractItems(BlockFace.SELF, null, toDrop, null);
            if (stack != null) {
                Location targetLoc = getTargetLocation(loc).add(0.5, 0.5, 0.5);
                Item item = targetLoc.getWorld().dropItem(targetLoc, stack);
                item.setVelocity(new Vector(0, 0, 0));
                Debugger.getInstance().debug(2, "dropper dropped " + stack + " from " + getItemRouter());
            }
            return true;
        }
        return false;
    }
}
