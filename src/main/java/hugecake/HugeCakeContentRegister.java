package hugecake;

import cpw.mods.fml.common.registry.GameRegistry;
import hugecake.block.BlockHugeCake;
import hugecake.block.ItemBlockHugeCake;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public final class HugeCakeContentRegister
{
	public static Block blockHugeCake;

	public static void registerBlocks ()
	{
		blockHugeCake = new BlockHugeCake();
		GameRegistry.registerBlock(blockHugeCake, ItemBlockHugeCake.class, "blockHugeCake");
	}
	
	private static final int LEVEL_LIMIT = 8;
	public static void registerRecipes ()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(blockHugeCake, 1, (1 << 4 | 1)),
				new Object[] {
						"BAB", "AEA", " C ",
						'A', Items.milk_bucket,
						'B', Items.sugar,
						'C', Blocks.hay_block,
						'E', Items.egg,
				});

		for (int h = 1; h <= LEVEL_LIMIT; h++)
		{
			for (int w = 1; w <= LEVEL_LIMIT; w++)
			{
				if (w < LEVEL_LIMIT)	registerCakeExtensionRecipeW(w, h);
				if (h < LEVEL_LIMIT)	registerCakeExtensionRecipeH(w, h);
			}
		}
	}

	private static void registerCakeExtensionRecipeW (int resWidthStep, int resHeightStep)
	{
		ItemStack input = new ItemStack(blockHugeCake, 1, (resHeightStep << 4) | resWidthStep);
		ItemStack output = new ItemStack(blockHugeCake, 1, (resHeightStep << 4) | (resWidthStep + 1));

		CraftingManager.getInstance().addRecipe(output, new Object[] {"II", "II", 'I', input});
	}

	private static void registerCakeExtensionRecipeH (int resWidthStep, int resHeightStep)
	{
		ItemStack input = new ItemStack(blockHugeCake, 1, (resHeightStep << 4) | resWidthStep);
		ItemStack output = new ItemStack(blockHugeCake, 1, ((resHeightStep + 1) << 4) | resWidthStep);

		CraftingManager.getInstance().addRecipe(output, new Object[] {"I", "I", 'I', input});
	}
}