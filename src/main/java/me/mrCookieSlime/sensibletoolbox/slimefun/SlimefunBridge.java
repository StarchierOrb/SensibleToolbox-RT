package me.mrCookieSlime.sensibletoolbox.slimefun;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.PluginUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomPotion;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.CSCoreLibSetup.CSCoreLibLoader;
import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ExcludedBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ExcludedGadget;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.recipes.STBFurnaceRecipe;
import me.mrCookieSlime.sensibletoolbox.api.recipes.SimpleCustomRecipe;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.BioEngine;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.HeatEngine;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.MagmaticEngine;
import me.mrCookieSlime.sensibletoolbox.items.RecipeBook;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class SlimefunBridge {
	private static ItemStack getItem(String id) {
		SlimefunItem item = SlimefunItem.getByName(id);
		return item != null ? item.getItem(): null;
	}
	
	private static void patch(String id, RecipeType recipeType, ItemStack recipe) {
		SlimefunItem item = SlimefunItem.getByName(id);;
		if (item != null) {
			item.setRecipe(new ItemStack[] {null, null, null, null, recipe, null, null, null, null});
			//item.setRecipe(new ItemStack[] {null, null, null, null, recipe, null, null, null, null});
			item.setRecipeType(recipeType);
		}
	}
	//hook
	private static void customp(String id, RecipeType recipeType, ItemStack recipe1, ItemStack recipe2, ItemStack recipe3, ItemStack recipe4, ItemStack recipe, ItemStack recipe6, ItemStack recipe7, ItemStack recipe8, ItemStack recipe9) {
		SlimefunItem item = SlimefunItem.getByName(id);;
		if (item != null) {
			item.setRecipe(new ItemStack[] {recipe1, recipe2, recipe3, recipe4, recipe, recipe6, recipe7, recipe8, recipe9});
			//item.setRecipe(new ItemStack[] {null, null, null, null, recipe, null, null, null, null});
			item.setRecipeType(recipeType);
		}
	}

	@SuppressWarnings("deprecation")
	public static void initiate() {
		Category items = new Category(new CustomItem(new MaterialData(Material.BREAD), "&7STB工业科技 - 道具和物品", "&fTID:501", "&a> 点击打开"));
		//Category items = new Category(new MenuItem(Material.SHEARS, "&7STB工业科技 - 道具和物品", 0, "点击打开"));
		Category blocks = new Category(new CustomItem(new MaterialData(Material.DIAMOND), "&7STB工业科技 - 方块与机器", "&fTID:502", "&a> 点击打开"));
		//Category blocks = new Category(new CustomItem(new MaterialData(Material.STAINED_GLASS, (byte) 10), "&7STB工业科技 - 方块与机器", "", "&a> 点击打开"));
		for (String id: SensibleToolboxPlugin.getInstance().getItemRegistry().getItemIds()) {
			BaseSTBItem item = SensibleToolboxPlugin.getInstance().getItemRegistry().getItemById(id);
			Category category = item.toItemStack().getType().isBlock() ? blocks: items;
			List<ItemStack> recipe = new ArrayList<ItemStack>();
			RecipeType recipeType = null;
			Recipe r = item.getRecipe();
			if (r != null) {
				if (r instanceof SimpleCustomRecipe) {
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(((SimpleCustomRecipe) r).getIngredient());
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
				}
				else if (r instanceof STBFurnaceRecipe) {
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(((STBFurnaceRecipe) r).getIngredient());
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
					recipe.add(null);
				}
				else if (item.getRecipe() instanceof ShapelessRecipe) {
					recipeType = RecipeType.SHAPELESS_RECIPE;
					//recipeType = new RecipeType(new CustomItem(Material.DIAMOND_BLOCK, "&6通过主城 锻造台 或者商店购买、活动获得", 1));
					for (ItemStack input: ((ShapelessRecipe) item.getRecipe()).getIngredientList()) {
						if (input == null) recipe.add(null);
						else recipe.add(RecipeBook.getIngredient(item, input));
					}
					for (int i = recipe.size(); i < 9; i++) {
						recipe.add(null);
					}
				}
				else if (item.getRecipe() instanceof ShapedRecipe) {
					recipeType = RecipeType.SHAPED_RECIPE;
					//recipeType = new RecipeType(new CustomItem(Material.DIAMOND_BLOCK, "&6通过主城 锻造台 或者商店购买、活动获得", 1));
					for (String row : ((ShapedRecipe) item.getRecipe()).getShape()) {
				        for (int i = 0; i < 3; i++) {
				        	try {
				        		recipe.add(RecipeBook.getIngredient(item, ((ShapedRecipe) item.getRecipe()).getIngredientMap().get(Character.valueOf(row.charAt(i)))));
				        	} catch(StringIndexOutOfBoundsException x) {
				        		recipe.add(null);
				        	}
				        }
				    }
					for (int i = recipe.size(); i < 9; i++) {
						recipe.add(null);
					}
				}
			}
			
			SlimefunItem sfItem = null;
			
			if (id.equalsIgnoreCase("bioengine")) {
				Set<ItemStack> fuels = ((BioEngine) item).getFuelInformation();
				if (fuels.size() % 2 != 0) fuels.add(null);
				sfItem = new ExcludedGadget(category, item.toItemStack(), id.toUpperCase(), null, null, fuels.toArray(new ItemStack[fuels.size()]));
			}
			else if (id.equalsIgnoreCase("magmaticengine")) {
				Set<ItemStack> fuels = ((MagmaticEngine) item).getFuelInformation();
				if (fuels.size() % 2 != 0) fuels.add(null);
				sfItem = new ExcludedGadget(category, item.toItemStack(), id.toUpperCase(), null, null, fuels.toArray(new ItemStack[fuels.size()]));
			}
			else if (id.equalsIgnoreCase("heatengine")) {
				Set<ItemStack> fuels = ((HeatEngine) item).getFuelInformation();
				if (fuels.size() % 2 != 0) fuels.add(null);
				sfItem = new ExcludedGadget(category, item.toItemStack(), id.toUpperCase(), null, null, fuels.toArray(new ItemStack[fuels.size()]));
			}
			else sfItem = new ExcludedBlock(category, item.toItemStack(), id.toUpperCase(), null, null);
			
			sfItem.setReplacing(true);
			sfItem.setRecipeType(recipeType);
			sfItem.setRecipe(recipe.toArray(new ItemStack[recipe.size()]));
			if (r != null) sfItem.setRecipeOutput(r.getResult());
			sfItem.register();
		}
		
		patch("INFERNALDUST", RecipeType.MOB_DROP, new CustomItem(Material.MONSTER_EGG, "&a&o烈焰人", 61));
		patch("ENERGIZEDGOLDINGOT", RecipeType.FURNACE, SlimefunItem.getByName("ENERGIZEDGOLDDUST").getItem());
		patch("QUARTZDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.QUARTZ));
		patch("ENERGIZEDIRONINGOT", RecipeType.FURNACE, SlimefunItem.getByName("ENERGIZEDIRONDUST").getItem());
		patch("SILICONWAFER", RecipeType.FURNACE, SlimefunItem.getByName("QUARTZDUST").getItem());
		patch("IRONDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.IRON_INGOT));
		patch("GOLDDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.GOLD_INGOT));
		patch("FISHBAIT", new RecipeType(SlimefunItem.getByName("FERMENTER").getItem()), new ItemStack(Material.ROTTEN_FLESH));
		//patch to new recipe
		//MODEL：new CustomItem(new MaterialData(Material.***), "NAME", "LORE1", "LORE2", "LORE...")
		patch("WOODCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("GOLDCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("PVCELL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("MULTIBUILDER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		
		//donate to get
		patch("ENDERLEASH", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2005", "&6· 赞助商城购买", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6？？？！"));
		patch("ENDERTUNER", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2005", "&6· 赞助商城购买", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ENDERBAG", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2005", "&6· 赞助商城购买", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("WATERINGCAN", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2005", "&6· 赞助商城购买", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("DIAMONDCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2005", "&6· 赞助商城购买", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		
		patch("RECIPEBOOK", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("CIRCUITBOARD", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("BLOCKUPDATEDETECTOR", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e无法合成", "&fTID:372")), new CustomItem(new MaterialData(Material.BOOK), "&6???"));
		patch("REDSTONECLOCK", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e无法合成", "&fTID:372")), new CustomItem(new MaterialData(Material.BOOK), "&6???"));
		patch("ITEMROUTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ELEVATOR", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ANGELICBLOCK", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e无法合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ENDERBOX", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e无法合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ADVANCEDFARM", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("AUTOBUILDER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("AUTOFORESTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("BASICSOLARCELL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("BIGSTORAGEUNIT", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("BIOENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("DENSESOLAR", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("ELECTRICALENERGIZER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("FERMENTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("FISHINGNET", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("HEATENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("HYPERSTORAGEUNIT", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("INFERNALFARM", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("MAGMATICENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("MASHER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("PUMP", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("SAWMILL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("SMELTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("PAINTCAN", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("EJECTORUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("REGULATORUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("SPEEDUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		patch("THOROUGHNESSUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e特殊合成！")), new CustomItem(new MaterialData(Material.BOOK), "&6通过主城 锻造台 制造得到！"));
		
		//功勋商城兑换
		//patch("IRONCOMBINEHOE", new RecipeType(new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JkYjhmMzc5NjE0MmFhYTNlYjg5Y2NiZWM3MTcwNWQ0YTIxNjkyZWI3Yzg0NjZkMmE3OWIyMWFlMTRiMzI2In19fQ=="), "&a通过功勋商城兑换")), null);
		patch("IRONCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2004", "&6· 功勋商城兑换", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6获取途径：", "&a· 功勋商城", "&6· 抽奖获得"));
		patch("AUTOFARM", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e获取途径：", "&7TID:2004", "&6· 功勋商城兑换", "&b· 开启宝箱抽奖获得")), new CustomItem(new MaterialData(Material.BOOK), "&6获取途径：", "&a· 功勋商城", "&6· 抽奖获得"));
		
		//custom recipe
		customp("MACHINEFRAME", RecipeType.ENHANCED_CRAFTING_TABLE, getItem("COMBINE_ITEM"), getItem("REINFORCED_ALLOY_INGOT"), getItem("COMBINE_ITEM"), getItem("SILICON"), new ItemStack(Material.GOLD_BLOCK), getItem("SILICON"), getItem("CIRCUITBOARD"), new ItemStack(Material.REDSTONE), getItem("CIRCUITBOARD"));
		
		Slimefun.addDescription("REACTOR_COOLANT_PORT", "&e1: 把这个放在反应堆的底部", "&e2: 用冷却液充满它", "&3: 请确保提供更多的冷却剂电池", "&e* 因为它们随着时间被消耗掉");
		//custom deprecation
		Slimefun.addDescription("AUTOFARM", "&e1: 可以在主城的锻造台制造它！");
		Slimefun.addDescription("MACHINEFRAME", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("ADVANCEDFARM", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("THOROUGHNESSUPGRADE", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("SPEEDUPGRADE", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("REGULATORUPGRADE", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("EJECTORUPGRADE", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("PAINTCAN", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("PUMP", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("MASHER", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
		Slimefun.addDescription("BASICSOLARCELL", "&e1: 可以在主城的锻造台制造它！", "&e2: 通过 &d功勋商城 &e兑换");
	}

}
