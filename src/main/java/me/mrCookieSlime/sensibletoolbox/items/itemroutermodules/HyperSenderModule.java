package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import me.mrCookieSlime.sensibletoolbox.items.components.SubspaceTransponder;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class HyperSenderModule extends AdvancedSenderModule {
    //private static final Dye md = makeDye(DyeColor.CYAN);
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public HyperSenderModule() {
        super();
    }

    public HyperSenderModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "I.R. 模块: 跨维发送器";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:07",
                "可以安装到物品路由器中.",
                "在任何一个世界向一个",
                "已连接的接收器发送物品",
                "左键点击 已安装接收器模块",
                "  的路由器: " + ChatColor.RESET + " 连接跨维发送器",
                UnicodeSymbol.ARROW_UP.toUnicode() + " + 左键点击: " + ChatColor.RESET + " 断开发射器连接"
        };
    }

    @Override
    public Recipe getRecipe() {
        SenderModule sm = new SenderModule();
        SubspaceTransponder st = new SubspaceTransponder();
        registerCustomIngredients(sm, st);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(sm.getMaterialData());
        recipe.addIngredient(st.getMaterialData());
        return recipe;
    }

    protected boolean inRange(Location ourLoc) {
        return ourLoc != null;
    }
}
