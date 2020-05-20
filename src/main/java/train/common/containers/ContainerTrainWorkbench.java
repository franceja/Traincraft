package train.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import train.common.inventory.TrainCraftingManager;
import train.common.slots.SlotCrafterTier;
import train.common.slots.SlotTrainCrafting;

import javax.annotation.Nonnull;

public class ContainerTrainWorkbench extends Container {

	public IInventory craftMatrix;
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;

	public ContainerTrainWorkbench(InventoryPlayer invPlayer, World world, IInventory te) {
		this.worldObj = world;
		craftMatrix = te;

		this.addSlotToContainer(new SlotTrainCrafting(invPlayer.player, craftMatrix, craftResult, 0, 124, 35));
		int var6;
		int var7;

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 3; ++var7) {
				addSlotToContainer(new SlotCrafterTier(invPlayer.player, craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				addSlotToContainer(new Slot(invPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			addSlotToContainer(new Slot(invPlayer, var6, 8 + var6 * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		super.onCraftMatrixChanged(inv);
		craftResult.setInventorySlotContents(0, TrainCraftingManager.getInstance().func_82787_a(craftMatrix, worldObj));
	}

	/*
	 * @Override public void updateCraftingResults() { super.updateCraftingResults(); craftResult.setInventorySlotContents(0, TrainCraftingManager.getInstance().func_82787_a(craftMatrix, worldObj)); } */

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		craftResult.setInventorySlotContents(0, TrainCraftingManager.getInstance().func_82787_a(craftMatrix, worldObj));
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player) {
		return craftMatrix.isUsableByPlayer(player);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(@Nonnull EntityPlayer player, int par1) {
		ItemStack var2 = ItemStack.EMPTY;
		Slot var3 = this.inventorySlots.get(par1);

		if (var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();

			if (par1 == 0) {
				if (!this.mergeItemStack(var4, 10, 46, true)) {
					return ItemStack.EMPTY;
				}
				var3.onSlotChange(var4, var2);
			}
			else if (par1 >= 10 && par1 < 37) {
				if (!this.mergeItemStack(var4, 37, 46, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (par1 >= 37 && par1 < 46) {
				if (!this.mergeItemStack(var4, 10, 37, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var4, 10, 46, false)) {
				return ItemStack.EMPTY;
			}

			if (var4.getCount() == 0) {
				var3.putStack(ItemStack.EMPTY);
			}
			else {
				var3.onSlotChanged();
			}

			if (var4.getCount() == var2.getCount()) {
				return ItemStack.EMPTY;
			}
			var3.onTake(player, var4);
		}
		return var2;
	}
}
