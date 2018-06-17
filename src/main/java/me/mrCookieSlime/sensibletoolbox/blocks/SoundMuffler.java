package me.mrCookieSlime.sensibletoolbox.blocks;

import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.gui.GUIUtil;
import me.mrCookieSlime.sensibletoolbox.api.gui.InventoryGUI;
import me.mrCookieSlime.sensibletoolbox.api.gui.NumericGadget;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBBlock;

import org.apache.commons.lang.math.IntRange;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class SoundMuffler extends BaseSTBBlock {
	
    private static final MaterialData md = new Wool(DyeColor.WHITE);
    public static final int DISTANCE = 8;
    private int volume; // 0-100

    public SoundMuffler() {
        volume = 10;
        createGUI();
    }

    public SoundMuffler(ConfigurationSection conf) {
        super(conf);
        volume = conf.getInt("volume");
        createGUI();
    }

    @Override
    protected InventoryGUI createGUI() {
        InventoryGUI gui = GUIUtil.createGUI(this, 9, ChatColor.DARK_AQUA + getItemName());
        gui.addGadget(new NumericGadget(gui, 0, "音量", new IntRange(0, 100), getVolume(), 10, 1, new NumericGadget.NumericListener() {
            @Override
            public boolean run(int newValue) {
                setVolume(newValue);
                return true;
            }
        }));
        return gui;
    }

    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("volume", volume);
        return conf;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
        update(false);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "消声器";
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "能够屏蔽所有声音的装置",
                "作用范围 " + DISTANCE + "-个方块 半径",
                "右键点击: " + ChatColor.RESET + " 进入设置界面"
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("WWW", "WNW", "WWW");
        recipe.setIngredient('W', Material.WOOL);
        recipe.setIngredient('N', Material.NOTE_BLOCK);
        return recipe;
    }

    @Override
    public void onBlockRegistered(Location loc, boolean isPlacing) {
        ((SensibleToolboxPlugin) getProviderPlugin()).getSoundMufflerListener().registerMuffler(this);

        super.onBlockRegistered(loc, isPlacing);
    }

    @Override
    public void onBlockUnregistered(Location loc) {
        ((SensibleToolboxPlugin) getProviderPlugin()).getSoundMufflerListener().unregisterMuffler(this);

        super.onBlockUnregistered(loc);
    }

    @Override
    public void onInteractBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
            getGUI().show(event.getPlayer());
        }
        super.onInteractBlock(event);
    }

    @Override
    public int getTickRate() {
        return 40;
    }

    @Override
    public String[] getSignLabel(BlockFace face) {
        String[] label = super.getSignLabel(face);
        label[1] = ChatColor.DARK_RED + "音量 " + ChatColor.RESET + getVolume();
        return label;
    }
}
