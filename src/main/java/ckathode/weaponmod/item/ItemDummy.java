package ckathode.weaponmod.item;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import ckathode.weaponmod.entity.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class ItemDummy extends WMItem
{
    public ItemDummy(final String id) {
        super(id);
        this.maxStackSize = 1;
    }

    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (world.isRemote) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        final float f = 1.0f;
        final float f2 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        final float f3 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        final double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * 1.0;
        final double d2 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * 1.0 + entityplayer.getEyeHeight();
        final double d3 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * 1.0;
        final Vec3d vec3d = new Vec3d(d, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        final float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        final float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        final float f7 = MathHelper.sin(-f2 * 0.01745329f);
        final float f8 = f5 * f6;
        final float f9 = f7;
        final float f10 = f4 * f6;
        final double d4 = 5.0;
        final Vec3d vec3d2 = vec3d.add(f8 * d4, f9 * d4, f10 * d4);
        final RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        final Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        final BlockPos blockpos = raytraceresult.getBlockPos();
        final boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
        final BlockPos blockpos2 = flag ? blockpos : blockpos.offset(raytraceresult.sideHit);
        final boolean flag2 = block == Blocks.SNOW;
        final EntityDummy entitydummy = new EntityDummy(world, blockpos2.getX() + 0.5, flag2 ? (blockpos2.getY() - 0.12) : blockpos2.getY(), blockpos2.getZ() + 0.5);
        entitydummy.rotationYaw = entityplayer.rotationYaw;
        if (!world.getCollisionBoxes(entitydummy, entitydummy.getEntityBoundingBox().grow(-0.1)).isEmpty()) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        world.spawnEntity(entitydummy);
        if (!entityplayer.capabilities.isCreativeMode || !world.isRemote) {
            itemstack.shrink(1);
        }
        entityplayer.addStat(StatList.getObjectUseStats(this));
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
}
