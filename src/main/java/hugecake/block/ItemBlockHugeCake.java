package hugecake.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBlockHugeCake extends ItemBlock
{
	public ItemBlockHugeCake (Block block)
	{
		super(block);

		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	private boolean placeCakePart (ItemStack stack, EntityPlayer player, World world, int x, int y, int z)
	{
		boolean ret = true;

		if (world.getBlock(x, y, z) != Blocks.air)
		{
			ret = false;
		}
		else if (!world.setBlock(x, y, z, this.field_150939_a, 0, 3))
		{
			ret = false;
		}
		else if (world.getBlock(x, y, z) == this.field_150939_a)
		{
			this.field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			this.field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

			if (world.getBlock(x, y - 1, z) == this.field_150939_a)
			{
				TileEntity tile = world.getTileEntity(x, y - 1, z);
				if (tile instanceof TileEntityHugeCake)
				{
					((TileEntityHugeCake)tile).getPartBB().isTopPart = false;
				}
			}
		}

		return ret;
	}

	private boolean placeCake (World world, EntityPlayer player, int baseX, int baseY, int baseZ, int cakeWidth, int cakeHeight, ItemStack stack)
	{
		boolean ret = false;

		ForgeDirection placeDir;
		{
			int l = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
			placeDir = ForgeDirection.getOrientation((new int[] {2, 5, 3, 4})[l]);
		}

		int loop_limitCount = 16777217;

		ForgeDirection placeLeft = getLeftDirection(placeDir).getOpposite();
		ForgeDirection placeDirX;
		ForgeDirection placeDirZ;
		{
			if (placeDir.offsetX != 0)
			{
				placeDirX = placeDir;
				placeDirZ = placeLeft.getOpposite();
			}
			else
			{
				placeDirX = placeLeft;
				placeDirZ = placeDir.getOpposite();
			}
		}

		int startX = baseX + (cakeWidth / 2 * placeLeft.offsetX);
		int startZ = baseZ + (cakeWidth / 2 * placeLeft.offsetZ);
		int placeOffsetX = 0;
		int placeOffsetY = 0;
		int placeOffsetZ = 0;
		while (placeOffsetY < cakeHeight)
		{
			if (placeCakePart(stack, player, world, startX + placeOffsetX, baseY + placeOffsetY, startZ + placeOffsetZ))
			{
				ret = true;

				TileEntity tile = world.getTileEntity(startX + placeOffsetX, baseY + placeOffsetY, startZ + placeOffsetZ);

				if (tile instanceof TileEntityHugeCake)
				{
					HugeCakePartBB partBB = ((TileEntityHugeCake)tile).getPartBB();
					
					if (placeOffsetY == 0)						// If: bottom part
					{
						partBB.isBottomPart = true;
					}
					else if (placeOffsetY == cakeHeight - 1)	// If: top part
					{
						partBB.isTopPart = true;
					}

					// If: Outside part
					if (placeOffsetX != 0)
					{
						if (placeDirX.offsetX < 0)
						{
							partBB.connectWest = true;
						}
						else
						{
							partBB.connectEast = true;
						}
					}
					if (Math.abs(placeOffsetX) < cakeWidth - 1)
					{
						if (placeDirX.offsetX < 0)
						{
							partBB.connectEast = true;
						}
						else
						{
							partBB.connectWest = true;
						}
					}
					if (placeOffsetZ != 0)
					{
						if (placeDirZ.offsetZ > 0)
						{
							partBB.connectNorth = true;
						}
						else
						{
							partBB.connectSouth = true;
						}
					}
					if (Math.abs(placeOffsetZ) < cakeWidth - 1)
					{
						if (placeDirZ.offsetZ > 0)
						{
							partBB.connectSouth = true;
						}
						else
						{
							partBB.connectNorth = true;
						}
					}
				}
				else
				{
					System.out.println("> tile is not hugecake");
				}
			}

			if (placeLeft.offsetX != 0)
			{
				placeOffsetX += placeLeft.getOpposite().offsetX;
				if (Math.abs(placeOffsetX) >= cakeWidth)
				{
					placeOffsetX = 0;
					placeOffsetZ += placeDir.getOpposite().offsetZ;
					if (Math.abs(placeOffsetZ) >= cakeWidth)
					{
						placeOffsetZ = 0;
						placeOffsetY++;
					}
				}
			}
			else if (placeLeft.offsetZ != 0)
			{
				placeOffsetZ += placeLeft.getOpposite().offsetZ;
				if (Math.abs(placeOffsetZ) >= cakeWidth)
				{
					placeOffsetZ = 0;
					placeOffsetX += placeDir.getOpposite().offsetX;
					if (Math.abs(placeOffsetX) >= cakeWidth)
					{
						placeOffsetX = 0;
						placeOffsetY++;
					}
				}
			}
			else
			{
				System.out.println("cake direction fail.");
				break;
			}

			loop_limitCount--;
			if (loop_limitCount <= 0)
			{
				System.out.println("loop limit.");

				String info = "dir:" + placeDir.name() + " dir-left:" + placeLeft.name() + " size[" + cakeWidth + ", " + cakeHeight + "] offset[" + placeOffsetX + ", " + placeOffsetY + ", " + placeOffsetZ + "]";
				System.out.println("> " + info);
				break;
			}
		}

		return ret;
	}

	@Override
	public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int clickFace, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		boolean ret = true;

		switch (clickFace)
		{
			case 0:	y--;	break;
			case 1:	y++;	break;
			case 2:	z--;	break;
			case 3:	z++;	break;
			case 4:	x--;	break;
			case 5:	x++;	break;
		}

		if (stack.stackSize == 0 || !player.canPlayerEdit(x, y, z, clickFace, stack) ||
			(y >= 255 && this.field_150939_a.getMaterial().isSolid()))
		{
			ret = false;
		}
		else if (world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, clickFace, player, stack))
		{
			int width = getCakeWidth(stack.getItemDamage());
			int height = getCakeHeight(stack.getItemDamage());

			if (placeCake(world, player, x, y, z, width, height, stack))
			{
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
				stack.stackSize--;
			}
		}
		else
		{
			ret = false;
		}

		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName (ItemStack stack)
	{
		int width = getCakeWidth(stack.getItemDamage());
		int height = getCakeHeight(stack.getItemDamage());

		return super.getItemStackDisplayName(stack) + " (" + width + "x" + height + "x" + width + ")";
	}

	public static int getCakeWidth (int metadata)
	{
		return (int)(Math.round(Math.pow((double)2, (double)(metadata & 0xF)) / 2D));
	}

	public static int getCakeHeight (int metadata)
	{
		return (int)(Math.round(Math.pow((double)2, (double)((metadata >> 4) & 0xF)) / 2D));
	}

	private static ForgeDirection getLeftDirection (ForgeDirection dir)
	{
		ForgeDirection ret;

		switch (dir)
		{
			case NORTH:
				ret = ForgeDirection.WEST;
				break;

			case EAST:
				ret = ForgeDirection.NORTH;
				break;

			case SOUTH:
				ret = ForgeDirection.EAST;
				break;

			case WEST:
				ret = ForgeDirection.SOUTH;
				break;

			default :
				ret = ForgeDirection.NORTH;
		}

		return ret;
	}
}