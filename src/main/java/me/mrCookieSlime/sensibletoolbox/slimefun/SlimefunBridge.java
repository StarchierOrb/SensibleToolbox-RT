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
		Category items = new Category(new CustomItem(new MaterialData(Material.BREAD), "&7STB��ҵ�Ƽ� - ���ߺ���Ʒ", "&fTID:501", "&a> �����"));
		//Category items = new Category(new MenuItem(Material.SHEARS, "&7STB��ҵ�Ƽ� - ���ߺ���Ʒ", 0, "�����"));
		Category blocks = new Category(new CustomItem(new MaterialData(Material.DIAMOND), "&7STB��ҵ�Ƽ� - ���������", "&fTID:502", "&a> �����"));
		//Category blocks = new Category(new CustomItem(new MaterialData(Material.STAINED_GLASS, (byte) 10), "&7STB��ҵ�Ƽ� - ���������", "", "&a> �����"));
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
					//recipeType = new RecipeType(new CustomItem(Material.DIAMOND_BLOCK, "&6ͨ������ ����̨ �����̵깺�򡢻���", 1));
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
					//recipeType = new RecipeType(new CustomItem(Material.DIAMOND_BLOCK, "&6ͨ������ ����̨ �����̵깺�򡢻���", 1));
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
		
		patch("INFERNALDUST", RecipeType.MOB_DROP, new CustomItem(Material.MONSTER_EGG, "&a&o������", 61));
		patch("ENERGIZEDGOLDINGOT", RecipeType.FURNACE, SlimefunItem.getByName("ENERGIZEDGOLDDUST").getItem());
		patch("QUARTZDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.QUARTZ));
		patch("ENERGIZEDIRONINGOT", RecipeType.FURNACE, SlimefunItem.getByName("ENERGIZEDIRONDUST").getItem());
		patch("SILICONWAFER", RecipeType.FURNACE, SlimefunItem.getByName("QUARTZDUST").getItem());
		patch("IRONDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.IRON_INGOT));
		patch("GOLDDUST", new RecipeType(SlimefunItem.getByName("MASHER").getItem()), new ItemStack(Material.GOLD_INGOT));
		patch("FISHBAIT", new RecipeType(SlimefunItem.getByName("FERMENTER").getItem()), new ItemStack(Material.ROTTEN_FLESH));
		//patch to new recipe
		//MODEL��new CustomItem(new MaterialData(Material.***), "NAME", "LORE1", "LORE2", "LORE...")
		patch("WOODCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("GOLDCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("PVCELL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("MULTIBUILDER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		
		//donate to get
		patch("ENDERLEASH", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2005", "&6�� �����̳ǹ���", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6��������"));
		patch("ENDERTUNER", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2005", "&6�� �����̳ǹ���", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ENDERBAG", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2005", "&6�� �����̳ǹ���", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("WATERINGCAN", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2005", "&6�� �����̳ǹ���", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("DIAMONDCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2005", "&6�� �����̳ǹ���", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		
		patch("RECIPEBOOK", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("CIRCUITBOARD", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("BLOCKUPDATEDETECTOR", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e�޷��ϳ�", "&fTID:372")), new CustomItem(new MaterialData(Material.BOOK), "&6???"));
		patch("REDSTONECLOCK", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e�޷��ϳ�", "&fTID:372")), new CustomItem(new MaterialData(Material.BOOK), "&6???"));
		patch("ITEMROUTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ELEVATOR", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ANGELICBLOCK", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e�޷��ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ENDERBOX", new RecipeType(new CustomItem(new MaterialData(Material.ENCHANTED_BOOK), "&e�޷��ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ADVANCEDFARM", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("AUTOBUILDER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("AUTOFORESTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("BASICSOLARCELL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("BIGSTORAGEUNIT", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("BIOENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("DENSESOLAR", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("ELECTRICALENERGIZER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("FERMENTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("FISHINGNET", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("HEATENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("HYPERSTORAGEUNIT", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("INFERNALFARM", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("MAGMATICENGINE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("MASHER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("PUMP", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("SAWMILL", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("SMELTER", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("PAINTCAN", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("EJECTORUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("REGULATORUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("SPEEDUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		patch("THOROUGHNESSUPGRADE", new RecipeType(new CustomItem(new MaterialData(Material.WORKBENCH), "&e����ϳɣ�")), new CustomItem(new MaterialData(Material.BOOK), "&6ͨ������ ����̨ ����õ���"));
		
		//��ѫ�̳Ƕһ�
		//patch("IRONCOMBINEHOE", new RecipeType(new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JkYjhmMzc5NjE0MmFhYTNlYjg5Y2NiZWM3MTcwNWQ0YTIxNjkyZWI3Yzg0NjZkMmE3OWIyMWFlMTRiMzI2In19fQ=="), "&aͨ����ѫ�̳Ƕһ�")), null);
		patch("IRONCOMBINEHOE", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2004", "&6�� ��ѫ�̳Ƕһ�", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6��ȡ;����", "&a�� ��ѫ�̳�", "&6�� �齱���"));
		patch("AUTOFARM", new RecipeType(new CustomItem(new MaterialData(Material.PRISMARINE_CRYSTALS), "&e��ȡ;����", "&7TID:2004", "&6�� ��ѫ�̳Ƕһ�", "&b�� ��������齱���")), new CustomItem(new MaterialData(Material.BOOK), "&6��ȡ;����", "&a�� ��ѫ�̳�", "&6�� �齱���"));
		
		//custom recipe
		customp("MACHINEFRAME", RecipeType.ENHANCED_CRAFTING_TABLE, getItem("COMBINE_ITEM"), getItem("REINFORCED_ALLOY_INGOT"), getItem("COMBINE_ITEM"), getItem("SILICON"), new ItemStack(Material.GOLD_BLOCK), getItem("SILICON"), getItem("CIRCUITBOARD"), new ItemStack(Material.REDSTONE), getItem("CIRCUITBOARD"));
		
		Slimefun.addDescription("REACTOR_COOLANT_PORT", "&e1: ��������ڷ�Ӧ�ѵĵײ�", "&e2: ����ȴҺ������", "&3: ��ȷ���ṩ�������ȴ�����", "&e* ��Ϊ��������ʱ�䱻���ĵ�");
		//custom deprecation
		Slimefun.addDescription("AUTOFARM", "&e1: ���������ǵĶ���̨��������");
		Slimefun.addDescription("MACHINEFRAME", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("ADVANCEDFARM", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("THOROUGHNESSUPGRADE", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("SPEEDUPGRADE", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("REGULATORUPGRADE", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("EJECTORUPGRADE", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("PAINTCAN", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("PUMP", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("MASHER", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
		Slimefun.addDescription("BASICSOLARCELL", "&e1: ���������ǵĶ���̨��������", "&e2: ͨ�� &d��ѫ�̳� &e�һ�");
	}

}
