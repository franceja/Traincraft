/*
 * Traincraft
 * Copyright (c) 2011-2020.
 *
 * This file ("BlockWindMill.java") is part of the Traincraft mod for Minecraft.
 * It is created by all people that are listed with @author below.
 * It is distributed under LGPL-v3.0.
 * You can find the source code at https://github.com/Traincraft/Traincraft
 */

package traincraft.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import traincraft.Traincraft;
import traincraft.items.ItemBlockGeneratorWindMill;
import traincraft.tile.TileWindMill;

public class BlockWindMill extends BaseContainerBlock {
    
    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
    
    public BlockWindMill(){
        super(Material.WOOD, TileWindMill.class);
        this.setRegistryName(Traincraft.MOD_ID, "wind_mill");
        
        this.setCreativeTab(Traincraft.TAB);
        this.setTickRandomly(true);
        this.setHardness(1.7F);
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
        
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public ItemBlock getItemBlock(){
        return new ItemBlockGeneratorWindMill();
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state){
        return false;
    }
    
    // state: ABCD => CD = facing
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(meta & 0b0011));
    }
    
    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

	/* todo water mill particle code
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		if (tile != null && tile instanceof TileWindMill && ((TileWindMill) tile).windClient > 0) {
			if (par5Random.nextInt(20) == 0) {
				par1World.playSound(par2, par3, par4, "minecart.inside", par5Random.nextFloat() * 0.25F + 0.1F, par5Random.nextFloat() * 1F - 0.6F, true);
			}
		}
	}
	 */
    
    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDING_BOX;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }
}
