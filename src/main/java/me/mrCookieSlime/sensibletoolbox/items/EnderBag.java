package me.mrCookieSlime.sensibletoolbox.items;

import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.enderstorage.EnderStorage;
import me.mrCookieSlime.sensibletoolbox.api.enderstorage.EnderTunable;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.blocks.EnderBox;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class EnderBag extends BaseSTBItem implements EnderTunable {
    private static final MaterialData md = new MaterialData(Material.PRISMARINE_CRYSTALS);

    public static final String BAG_SAVE_DIR = "bagofholding";
    //public static final int BAG_SIZE = 54;
	//size manager path:sensibletoolbox\core\enderstorage\EnderStorageManager.java
    public static final int BAG_SIZE = 18;

    private int frequency;
    private boolean global;

    public EnderBag() {
        super();
        frequency = 1;
        global = false;
    }

    public EnderBag(ConfigurationSection conf) {
        super(conf);
        frequency = conf.getInt("frequency", 1);
        global = conf.getBoolean("global", false);
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("frequency", getEnderFrequency());
        conf.set("global", isGlobal());
        return conf;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "ĩӰ����";
    }

    @Override
    public String getDisplaySuffix() {
        return (isGlobal() ? "ȫ��ͬ��" : "˽�˱���") + " " + UnicodeSymbol.NUMBER.toUnicode() + getEnderFrequency();
    }

    @Override
    public String[] getLore() {
        return new String[]{
			    "TID:657",
				"��dע����ĩӰ�����ı�����",
				"��dʹ��������Ӵ�Ԫ������",
				"",
        		"�Ҽ����: ��a�򿪱���",
        		UnicodeSymbol.ARROW_UP.toUnicode() + " + �һ� ĩӰͬ������: ��bͬ������ " + UnicodeSymbol.NUMBER.toUnicode()
        };
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("WDW", "GCG", "WGW");
        recipe.setIngredient('W', Material.WOOL);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('C', Material.ENDER_CHEST);
        return recipe;*/
		return null;
    }

    public int getEnderFrequency() {
        return frequency;
    }

    public void setEnderFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean isGlobal() {
        return global;
    }

    @Override
    public void setGlobal(boolean global) {
        this.global = global;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onInteractItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clicked = event.getClickedBlock();
            Player player = event.getPlayer();

            if (clicked != null) {
                // shift-right-click an ender bag against an ender box to copy its frequency
                EnderBox box = SensibleToolbox.getBlockAt(clicked.getLocation(), EnderBox.class, true);
                if (box != null && player.isSneaking()) {
                    if (getEnderFrequency() != box.getEnderFrequency()) {
                        setEnderFrequency(box.getEnderFrequency());
                        setGlobal(box.isGlobal());
                        player.setItemInHand(toItemStack(player.getItemInHand().getAmount()));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 2.0f);
                    }
                    player.updateInventory();
                    event.setCancelled(true);
                    return;
                } else if (STBUtil.isInteractive(clicked.getType())) {
                    return;
                }
            }

            if (EnderStorage.isCreativeAccessBlocked(player)) {
                STBUtil.complain(player, "û�д���ģʽȨ��������!");
            } else {
                Inventory inv = isGlobal() ?
                        EnderStorage.getEnderInventory(getEnderFrequency()) :
                        EnderStorage.getEnderInventory(player, getEnderFrequency());
                player.openInventory(inv);
            }
            event.setCancelled(true);
        }
    }
}
