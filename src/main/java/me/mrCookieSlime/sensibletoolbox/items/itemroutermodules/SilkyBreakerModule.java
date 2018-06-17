package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class SilkyBreakerModule extends BreakerModule {
    private static final ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    static {
        pick.addEnchantment(Enchantment.SILK_TOUCH, 1);
    }

    public SilkyBreakerModule() {
        super();
    }

    public SilkyBreakerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "I.R. 模块: 精准破坏器";
    }

    @Override
    public String[] getLore() {
        return new String[] {
				"TIDX:11",
                "可以安装到物品路由器中",
                "向设置的方向",
                " 精准破坏指定方块",
                " 并将物品推入路由器.",
                "注意: 必须使用精准采集",
                "  附魔书来制造."
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        BreakerModule b = new BreakerModule();
        registerCustomIngredients(b);
        recipe.addIngredient(b.getMaterialData());
        recipe.addIngredient(Material.ENCHANTED_BOOK);
        return recipe;
    }

    @Override
    public boolean validateCrafting(CraftingInventory inventory) {
        for (ItemStack stack : inventory.getMatrix()) {
            if (stack != null && stack.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getItemMeta();
                if (meta.getStoredEnchantLevel(Enchantment.SILK_TOUCH) < 1) {
                    return false;
                }
            }
        }
        return true;
    }

    protected ItemStack getBreakerTool() {
        return pick;
    }
}
