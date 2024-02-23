package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityCannon;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCannon extends WMItem {
    public ItemCannon(final String id) {
        super(id);
        this.maxStackSize = 1;
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        final float f = 1.0f;
        final float f2 =
                entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        final float f3 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        final double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
        final double d2 =
                entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + entityplayer.getEyeHeight();
        final double d3 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);
        final Vec3d vec3d = new Vec3d(d, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        final float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        final float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        final float f7 = MathHelper.sin(-f2 * 0.01745329f);
        final float f8 = f5 * f6;
        final float f10 = f4 * f6;
        final double d4 = 5.0;
        final Vec3d vec3d2 = vec3d.add(f8 * d4, f7 * d4, f10 * d4);
        final RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK || raytraceresult.sideHit != EnumFacing.UP) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        final Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        final BlockPos blockpos = raytraceresult.getBlockPos();
        final boolean flag1 = block == Blocks.SNOW;
        final EntityCannon entitycannon = new EntityCannon(world, blockpos.getX() + 0.5, flag1 ?
                (blockpos.getY() + 0.38) : (blockpos.getY() + 1.0), blockpos.getZ() + 0.5);
        if (!world.getCollisionBoxes(entitycannon, entitycannon.getEntityBoundingBox().grow(-0.1)).isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (!world.isRemote) {
            world.spawnEntity(entitycannon);
        }
        if (!entityplayer.capabilities.isCreativeMode || !world.isRemote) {
            itemstack.shrink(1);
        }
        entityplayer.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
