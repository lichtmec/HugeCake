package hugecake.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockHugeCake extends BlockContainer
{
	private IIcon[] iconUpperBase;
	private IIcon[] iconSide;
	private IIcon[] iconBottomBase;

	public BlockHugeCake ()
	{
		super(Material.cake);

		this.setCreativeTab(CreativeTabs.tabFood);
		this.setBlockName("blockHugeCake");
		this.setHardness(0.5F);
		this.setStepSound(soundTypeCloth);

		iconUpperBase = new IIcon[IconIndex_Base.values().length];
		iconSide = new IIcon[IconIndex_Side.values().length];
		iconBottomBase = new IIcon[IconIndex_Base.values().length];
	}

	@Override
	public void setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileEntityHugeCake)
		{
			AxisAlignedBB aabb = ((TileEntityHugeCake)tile).getPartBB().convertAABB();

			this.setBlockBounds((float)aabb.minX, (float)aabb.minY, (float)aabb.minZ, (float)aabb.maxX, (float)aabb.maxY, (float)aabb.maxZ);
		}
		else
		{
			super.setBlockBoundsBasedOnState(world, x, y, z);
		}
	}

	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.875F, 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
	{
		AxisAlignedBB aabb;
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileEntityHugeCake)
		{
			aabb = ((TileEntityHugeCake)tile).getPartBB().convertAABB(x, y, z);
		}
		else
		{
			aabb = super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}

		return aabb;
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool (World world, int x, int y, int z)
	{
		AxisAlignedBB aabb;
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileEntityHugeCake)
		{
			aabb = ((TileEntityHugeCake)tile).getPartBB().convertAABB(x, y, z);
		}
		else
		{
			aabb = super.getSelectedBoundingBoxFromPool(world, x, y, z);
		}

		return aabb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		IIcon ret;

		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileEntityHugeCake)
		{
			HugeCakePartBB cakeBB = ((TileEntityHugeCake)tile).getPartBB();

			switch (side)
			{
				case 0 :	// bottom
					ret = iconBottomBase[IconIndex_Base.getIconType(cakeBB).ordinal()];
					break;

				case 1 :	// top
					ret = iconUpperBase[IconIndex_Base.getIconType(cakeBB).ordinal()];
					break;

				case 2 :	// north
				case 3 :	// south
				case 4 :	// west
				case 5 :	// east
					ret = iconSide[IconIndex_Side.getIconType(cakeBB, side).ordinal()];
					break;

				default :
					ret = super.getIcon(world, x, y, z, side);
			}
		}
		else
		{
			ret = super.getIcon(world, x, y, z, side);
		}

		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon (int side, int metadata)
	{
		IIcon ret;
		switch (side)
		{
			case 0 :
				ret = iconBottomBase[IconIndex_Base.ALL.ordinal()];
				break;

			case 1 :
				ret = iconUpperBase[IconIndex_Base.ALL.ordinal()];
				break;

			case 2 :
			case 3 :
			case 4 :
			case 5 :
				ret = iconSide[IconIndex_Side.OUTSIDE.ordinal()];
				break;

			default :
				ret = super.getIcon(side, metadata);
		}

		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons (IIconRegister iconRegister)
	{
		iconUpperBase[IconIndex_Base.INNER.ordinal()] = iconRegister.registerIcon("hugecake:cake_top");
		iconUpperBase[IconIndex_Base.NORTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_n");
		iconUpperBase[IconIndex_Base.NORTH_EAST.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_ne");
		iconUpperBase[IconIndex_Base.EAST.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_e");
		iconUpperBase[IconIndex_Base.EAST_SOUTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_es");
		iconUpperBase[IconIndex_Base.SOUTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_s");
		iconUpperBase[IconIndex_Base.SOUTH_WEST.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_sw");
		iconUpperBase[IconIndex_Base.WEST.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_w");
		iconUpperBase[IconIndex_Base.WEST_NORTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_wn");
		iconUpperBase[IconIndex_Base.ALL.ordinal()] = iconRegister.registerIcon("hugecake:cake_top_nesw");

		iconSide[IconIndex_Side.INNER.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_inner");
		iconSide[IconIndex_Side.LEFT.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_inner_left");
		iconSide[IconIndex_Side.RIGHT.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_inner_right");
		iconSide[IconIndex_Side.ALL.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_inner_lr");
		iconSide[IconIndex_Side.OUTSIDE.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_outer");
		iconSide[IconIndex_Side.OUTSIDE_BOTTOM.ordinal()] = iconRegister.registerIcon("hugecake:cake_side_outer_b");

		iconBottomBase[IconIndex_Base.INNER.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom");
		iconBottomBase[IconIndex_Base.NORTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_n");
		iconBottomBase[IconIndex_Base.NORTH_EAST.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_ne");
		iconBottomBase[IconIndex_Base.EAST.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_e");
		iconBottomBase[IconIndex_Base.EAST_SOUTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_es");
		iconBottomBase[IconIndex_Base.SOUTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_s");
		iconBottomBase[IconIndex_Base.SOUTH_WEST.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_sw");
		iconBottomBase[IconIndex_Base.WEST.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_w");
		iconBottomBase[IconIndex_Base.WEST_NORTH.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_wn");
		iconBottomBase[IconIndex_Base.ALL.ordinal()] = iconRegister.registerIcon("hugecake:cake_bottom_nesw");
	}

	@Override
	public boolean renderAsNormalBlock ()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube ()
	{
		return false;
	}

	private void eatCake (EntityPlayer player, ForgeDirection face, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (player.canEat(false) && tile instanceof TileEntityHugeCake)
		{
			HugeCakePartBB hugeCakeBB = ((TileEntityHugeCake)tile).getPartBB();
			int eatWidth;
			switch (face)
			{
				case NORTH:
					hugeCakeBB.chippedNorth += 2;
					eatWidth = 16 - (hugeCakeBB.chippedEast + hugeCakeBB.chippedWest);
					break;

				case EAST:
					hugeCakeBB.chippedEast += 2;
					eatWidth = 16 - (hugeCakeBB.chippedNorth + hugeCakeBB.chippedSouth);
					break;

				case SOUTH:
					hugeCakeBB.chippedSouth += 2;
					eatWidth = 16 - (hugeCakeBB.chippedEast + hugeCakeBB.chippedWest);
					break;

				case WEST:
					hugeCakeBB.chippedWest += 2;
					eatWidth = 16 - (hugeCakeBB.chippedNorth + hugeCakeBB.chippedSouth);
					break;

				default:
					hugeCakeBB.chippedEast += 2;
					eatWidth = 16 - (hugeCakeBB.chippedNorth + hugeCakeBB.chippedSouth);
					break;
			}

			if (eatWidth > 8)
			{
				player.getFoodStats().addStats(2, 0.1F);
			}
			else
			{
				player.getFoodStats().addStats(1, 0.1F);
			}

			if ((hugeCakeBB.chippedNorth + hugeCakeBB.chippedSouth >= 16) ||
				(hugeCakeBB.chippedEast + hugeCakeBB.chippedWest >= 16))
			{
				world.removeTileEntity(x, y, z);
				world.setBlockToAir(x, y, z);
			}
			else
			{
				tile.markDirty();
			}

			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int face, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		ForgeDirection eatFace;
		if (face == 0 || face == 1)
		{
			eatFace = ForgeDirection.EAST;
		}
		else
		{
			eatFace = ForgeDirection.getOrientation(face);
		}

		eatCake(player, eatFace, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		eatCake(player, ForgeDirection.EAST, world, x, y, z);
	}

	@Override
	public int quantityDropped (Random rand)
	{
		return 0;
	}

	@Override
	public Item getItemDropped (int p_149650_1_, Random rand, int p_149650_3_)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void getSubBlocks (Item item, CreativeTabs creativeTab, List tabItemList)
	{
		tabItemList.add(createCakeStack(1, 1));
		tabItemList.add(createCakeStack(2, 1));
		tabItemList.add(createCakeStack(3, 2));
		tabItemList.add(createCakeStack(4, 2));
		tabItemList.add(createCakeStack(5, 3));
		tabItemList.add(createCakeStack(6, 3));
		tabItemList.add(createCakeStack(7, 4));
	}
	
	public ItemStack createCakeStack (int widthLevel, int heightLevel)
	{
		if (widthLevel >= 16 || heightLevel >= 16)
		{
			throw new IllegalArgumentException();
		}
		
		return new ItemStack(this, 1, (heightLevel << 4) | widthLevel);
	}

	@Override
	public TileEntity createNewTileEntity (World world, int metadata)
	{
		return new TileEntityHugeCake();
	}

	private enum IconIndex_Base
	{
		INNER,
		NORTH,
		NORTH_EAST,
		EAST,
		EAST_SOUTH,
		SOUTH,
		SOUTH_WEST,
		WEST,
		WEST_NORTH,
		ALL;

		public static IconIndex_Base getIconType (HugeCakePartBB cakeBB)
		{
			IconIndex_Base ret;

			if (!cakeBB.connectNorth && !cakeBB.connectEast && !cakeBB.connectSouth && !cakeBB.connectWest)
			{
				ret = ALL;
			}
			else if (!cakeBB.connectNorth)
			{
				if (!cakeBB.connectEast)
				{
					ret = NORTH_EAST;
				}
				else if (!cakeBB.connectWest)
				{
					ret = WEST_NORTH;
				}
				else
				{
					ret = NORTH;
				}
			}
			else if (!cakeBB.connectEast)
			{
				if (!cakeBB.connectSouth)
				{
					ret = EAST_SOUTH;
				}
				else
				{
					ret = EAST;
				}
			}
			else if (!cakeBB.connectSouth)
			{
				if (!cakeBB.connectWest)
				{
					ret = SOUTH_WEST;
				}
				else
				{
					ret = SOUTH;
				}
			}
			else if (!cakeBB.connectWest)
			{
				ret = WEST;
			}
			else
			{
				ret = INNER;
			}

			return ret;
		}
	}

	private enum IconIndex_Side
	{
		INNER,
		LEFT,
		RIGHT,
		ALL,
		OUTSIDE,
		OUTSIDE_BOTTOM;

		public static IconIndex_Side getIconType (HugeCakePartBB cakeBB, int face)
		{
			IconIndex_Side ret;

			switch (face)
			{
				case 2 :	// north
					if (!cakeBB.connectNorth && cakeBB.chippedNorth == 0)
					{
						ret = OUTSIDE;
					}
					else if (!cakeBB.connectSouth && cakeBB.chippedNorth >= 14)
					{
						ret = OUTSIDE;
					}
					else
					{
						if (!cakeBB.connectEast && !cakeBB.connectWest)
						{
							ret = ALL;
						}
						else if (!cakeBB.connectEast)
						{
							ret = LEFT;
						}
						else if (!cakeBB.connectWest)
						{
							ret = RIGHT;
						}
						else
						{
							ret = INNER;
						}
					}
					break;

				case 3 :	// south
					if (!cakeBB.connectSouth && cakeBB.chippedSouth == 0)
					{
						ret = OUTSIDE;
					}
					else if (!cakeBB.connectNorth && cakeBB.chippedSouth >= 14)
					{
						ret = OUTSIDE;
					}
					else
					{
						if (!cakeBB.connectWest && !cakeBB.connectEast)
						{
							ret = ALL;
						}
						else if (!cakeBB.connectWest)
						{
							ret = LEFT;
						}
						else if (!cakeBB.connectEast)
						{
							ret = RIGHT;
						}
						else
						{
							ret = INNER;
						}
					}
					break;

				case 4 :	// west
					if (!cakeBB.connectWest && cakeBB.chippedWest == 0)
					{
						ret = OUTSIDE;
					}
					else if (!cakeBB.connectEast && cakeBB.chippedWest >= 14)
					{
						ret = OUTSIDE;
					}
					else
					{
						if (!cakeBB.connectSouth && !cakeBB.connectNorth)
						{
							ret = ALL;
						}
						else if (!cakeBB.connectNorth)
						{
							ret = LEFT;
						}
						else if (!cakeBB.connectSouth)
						{
							ret = RIGHT;
						}
						else
						{
							ret = INNER;
						}
					}
					break;

				case 5 :	// east
					if (!cakeBB.connectEast && cakeBB.chippedEast == 0)
					{
						ret = OUTSIDE;
					}
					else if (!cakeBB.connectWest && cakeBB.chippedEast >= 14)
					{
						ret = OUTSIDE;
					}
					else
					{
						if (!cakeBB.connectNorth && !cakeBB.connectSouth)
						{
							ret = ALL;
						}
						else if (!cakeBB.connectSouth)
						{
							ret = LEFT;
						}
						else if (!cakeBB.connectNorth)
						{
							ret = RIGHT;
						}
						else
						{
							ret = INNER;
						}
					}
					break;

				default:
					ret = OUTSIDE;
			}
			
			if (ret == OUTSIDE && cakeBB.isBottomPart)
			{
				ret = OUTSIDE_BOTTOM;
			}

			return ret;
		}
	}
}