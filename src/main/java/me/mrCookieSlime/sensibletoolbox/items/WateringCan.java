package me.mrCookieSlime.sensibletoolbox.items;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.ChatColor;

import me.desht.dhutils.MiscUtil;
import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.BlockProtection;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.api.util.SoilSaturation;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

public class WateringCan extends BaseSTBItem {
	
    private static final MaterialData md = new MaterialData(Material.POTION);
    private static final MaterialData md2 = new MaterialData(Material.GLASS_BOTTLE);

    private static final int GROW_CHANCE = 10;
    private static final int MAX_LEVEL = 200;
    private static final int FIRE_EXTINGUISH_AMOUNT = 50;
    public static final int SATURATION_RATE = 5;
    private int waterLevel;
    private boolean floodWarning;

    public WateringCan() {
        super();
        waterLevel = 0;
    }

    public WateringCan(ConfigurationSection conf) {
        super(conf);
        setWaterLevel(conf.getInt("level"));
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int level) {
        this.waterLevel = level;
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration res = super.freeze();
        res.set("level", waterLevel);
        return res;
    }

    @Override
    public MaterialData getMaterialData() {
        return getWaterLevel() == 0 ? md2 : md;
    }

    @Override
    public String getItemName() {
        return "喷壶";
    }

    @Override
    public String getDisplaySuffix() {
        return getWaterLevel() / 2 + "%";
    }

    @Override
    public String[] getLore() {
        return new String[]{"TID:634", "右键点击 来灌溉庄稼.", "持续右键点击 水来装满喷壶", ChatColor.GRAY + UnicodeSymbol.COLOR_CODE.toUnicode() + "l" + "(" + ChatColor.GOLD + UnicodeSymbol.COLOR_CODE.toUnicode() + "l" + "!" + ChatColor.GRAY + UnicodeSymbol.COLOR_CODE.toUnicode() + "l" + ")" + " 请勿对同一片农田过度使用!"};
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        Dye d = new Dye();
        d.setColor(DyeColor.WHITE);
        recipe.shape("   ", "  S", "   ");
        recipe.setIngredient('S', Material.STONE);
        //recipe.setIngredient('M', d);
        //recipe.setIngredient('B', Material.BOWL);
        return recipe;*/
		return null;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onInteractItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack newStack = null;
        floodWarning = false;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Block neighbour = block.getRelative(event.getBlockFace());
            boolean buildok = SensibleToolbox.getBlockProtection().playerCanBuild(player, block, BlockProtection.Operation.PLACE);
            if ((neighbour.getType() == Material.STATIONARY_WATER || neighbour.getType() == Material.WATER) && neighbour.getData() == 0) {
                // attempt to refill the watering can
				if (buildok) {
					player.playSound(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 1.0f, 0.8f);
					neighbour.setType(Material.AIR);
					setWaterLevel(MAX_LEVEL);
					newStack = toItemStack();
				}
            } else if (STBUtil.isCrop(block.getType())) {
				if (buildok) {
					// attempt to grow the crops in a 3x3 area, and use some water from the can
					waterCrops(player, block);
					irrigateSoil(player, block.getRelative(BlockFace.DOWN));
					newStack = toItemStack();
				}
            } else if (block.getType() == Material.SOIL) {
                if (STBUtil.isCrop(block.getRelative(BlockFace.UP).getType())) {
					if (buildok) {
						waterCrops(player, block.getRelative(BlockFace.UP));
						irrigateSoil(player, block);
						newStack = toItemStack();
					}
                } else {
					if (buildok) {
						// make the soil wetter if possible
						waterSoil(player, block);
						irrigateSoil(player, block);
						newStack = toItemStack();
					}
                }
            } else if (block.getType() == Material.COBBLESTONE && getWaterLevel() >= 10) {
				if (buildok) {
					if (new Random().nextBoolean()) {
						block.setType(Material.MOSSY_COBBLESTONE);
					}
					useSomeWater(player, block, 10);
					newStack = toItemStack();
				}
            } else if (block.getType() == Material.SMOOTH_BRICK && block.getData() != 1 && getWaterLevel() >= 10) {
				if (buildok) {
					if (new Random().nextBoolean()) {
						block.setData((byte) 1);
					}
					useSomeWater(player, block, 10);
					newStack = toItemStack();
				}
            } else if (block.getType() == Material.DIRT) {
				if (buildok) {
					if (maybeGrowGrass(block)) {
						useSomeWater(player, block, 1);
						newStack = toItemStack();
					}
				}
            } else if (block.getType() == Material.CACTUS || block.getType() == Material.SUGAR_CANE_BLOCK) {
				if (buildok) {
					maybeGrowTallCrop(player, block);
					useSomeWater(player, block, 2);
					newStack = toItemStack();
				}
            }
		//this
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            Block b = player.getEyeLocation().getBlock();
            if (b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {
                // attempt to refill the watering can
				b.setType(Material.AIR);
				player.playSound(player.getLocation(), Sound.BLOCK_WATER_AMBIENT, 1.0f, 0.8f);
				setWaterLevel(MAX_LEVEL);
				newStack = toItemStack();
            }
        }
        event.setCancelled(true);
        if (newStack != null) {
            player.setItemInHand(newStack);
//			player.updateInventory();
        }
        if (floodWarning) {
            MiscUtil.alertMessage(player, "这片农田变得太湿润了！!");
            floodWarning = false;
        }
    }

    @SuppressWarnings("deprecation")
	private void maybeGrowTallCrop(Player player, Block b) {
        // check if we can make this cactus or sugar cane grow

        if (getWaterLevel() < 2) {
            STBUtil.complain(player);
            return;
        }

        // find the bottom block of this tall plant
        Material mat = b.getType();
        Block b0 = b;
        while (b0.getRelative(BlockFace.DOWN).getType() == mat) {
            b0 = b0.getRelative(BlockFace.DOWN);
        }

        checkForFlooding(b0.getRelative(BlockFace.DOWN));

        Block candidate = null;
        if (b0.getRelative(BlockFace.UP).getType() == Material.AIR) candidate = b0;
        else if (b0.getRelative(BlockFace.UP, 2).getType() == Material.AIR) candidate = b0.getRelative(BlockFace.UP);

        if (candidate != null && CSCoreLib.randomizer().nextInt(100) < 50) {
            if (candidate.getData() == 15) candidate.getRelative(BlockFace.UP).setTypeIdAndData(mat.getId(), (byte) 0, true);
            else candidate.setData((byte) (candidate.getData() + 1));
        }
    }

    private boolean maybeGrowGrass(Block b) {
        for (BlockFace face : STBUtil.allHorizontalFaces) {
            Block b1 = b.getRelative(face);
            if (b1.getType() == Material.GRASS) {
                b.setType(Material.GRASS);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
	private void irrigateSoil(Player player, Block b) {
        b.setData((byte) 8);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (player.getFireTicks() > 0 && getWaterLevel() >= FIRE_EXTINGUISH_AMOUNT) {
            player.setFireTicks(0);
            setWaterLevel(getWaterLevel() - FIRE_EXTINGUISH_AMOUNT);
            MiscUtil.alertMessage(player, "火灭了!");
        }
        player.setItemInHand(toItemStack());
        player.updateInventory();
        event.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
	private void waterSoil(Player player, Block b) {
        for (Block b1 : STBUtil.getSurroundingBlocks(b)) {
            if (getWaterLevel() <= 0) {
                STBUtil.complain(player);
                break;
            }
            if (b1.getType() == Material.SOIL) {
                if (b1.getData() < 8) b1.setData((byte) (b1.getData() + 1));
                checkForFlooding(b1);
                useSomeWater(player, b, 1);
            }
            if (player.isSneaking()) {
                break; // only water one block if sneaking
            }
        }
    }

    private void waterCrops(Player player, Block b) {
        for (Block b1 : STBUtil.getSurroundingBlocks(b)) {
            if (getWaterLevel() <= 0) {
                STBUtil.complain(player);
                break;
            }
            maybeGrowCrop(player, b1);
            if (player.isSneaking()) {
                break; // only water one block if sneaking
            }
        }
    }

    @SuppressWarnings("deprecation")
	private void maybeGrowCrop(Player player, Block b) {
        if (!STBUtil.isCrop(b.getType()) || !SensibleToolbox.getBlockProtection().playerCanBuild(player, b, BlockProtection.Operation.PLACE)) {
            return;
        }
        if (CSCoreLib.randomizer().nextInt(100) < GROW_CHANCE) {
            if (b.getData() < 7) b.setData((byte) (b.getData() + 1));
        }
        checkForFlooding(b.getRelative(BlockFace.DOWN));
        useSomeWater(player, b, 1);
    }

    @SuppressWarnings("deprecation")
	private void checkForFlooding(Block soil) {
        int saturation = SoilSaturation.getSaturationLevel(soil);
        long now = System.currentTimeMillis();
        long delta = (now - SoilSaturation.getLastWatered(soil)) / 1000;
        saturation = Math.max(0, saturation + SATURATION_RATE - (int) delta);
        if (saturation > SoilSaturation.MAX_SATURATION && new Random().nextBoolean()) {
            soil.breakNaturally();
            soil.setTypeIdAndData(Material.WATER.getId(), (byte) 0, true);
            SoilSaturation.clear(soil);
        } else {
            SoilSaturation.setLastWatered(soil, System.currentTimeMillis());
            SoilSaturation.setSaturationLevel(soil, saturation);
        }
        if (saturation > SoilSaturation.MAX_SATURATION - 10) {
            floodWarning = true;
        }
    }

    private void useSomeWater(Player p, Block b, int amount) {
        setWaterLevel(Math.max(0, getWaterLevel() - amount));
        p.playSound(p.getLocation(), Sound.BLOCK_WATER_AMBIENT, 1.0f, 2.0f);
        p.playEffect(b.getLocation(), Effect.STEP_SOUND, Material.WATER);
    }
}
