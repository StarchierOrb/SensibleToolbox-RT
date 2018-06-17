package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import me.desht.dhutils.Debugger;

public class ReceiverModule extends ItemRouterModule {
    //private static final Dye md = makeDye(DyeColor.ORANGE);
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public ReceiverModule() {
    }

    public ReceiverModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "I.R. 模块: 接收器";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:09",
                "可以安装到物品路由器中的",
                "被动模块; 可以从",
                "正对的发送器或者",
                "已连接的高级发射器接收物品"
        };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.TRAP_DOOR);
        return recipe;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    public int receiveItem(ItemStack item, UUID senderUUID) {
        int received = getItemRouter().insertItems(item, BlockFace.SELF, false, senderUUID);
        if (received > 0) {
            Debugger.getInstance().debug(2, "receiver in " + getItemRouter() + " received " + received + " of " + item +
                    ", now has " + getItemRouter().getBufferItem());
        }
        return received;
    }
}
