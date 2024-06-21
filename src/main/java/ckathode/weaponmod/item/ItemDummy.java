package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityDummy;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemDummy extends WMItem {
    public ItemDummy(String id) {
        super(id);
        maxStackSize = 1;
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemStack itemstack, @Nonnull EntityPlayer entityplayer,
                                      @Nonnull World world, @Nonnull BlockPos pos,
                                      @Nonnull EnumHand hand, @Nonnull EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.FAIL;
        float f = 1.0f;
        float f2 =
                entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f3 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
        double d2 =
                entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + entityplayer.getEyeHeight();
        double d3 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);
        Vec3d vec3d = new Vec3d(d, d2, d3);
        float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        float f7 = MathHelper.sin(-f2 * 0.01745329f);
        float f8 = f5 * f6;
        float f10 = f4 * f6;
        double d4 = 5.0;
        Vec3d vec3d2 = vec3d.addVector(f8 * d4, f7 * d4, f10 * d4);
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return EnumActionResult.FAIL;
        }
        Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        BlockPos blockpos = raytraceresult.getBlockPos();
        boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
        BlockPos blockpos2 = flag ? blockpos : blockpos.offset(raytraceresult.sideHit);
        boolean flag2 = block == Blocks.SNOW;
        EntityDummy entitydummy = new EntityDummy(world, blockpos2.getX() + 0.5,
                blockpos2.getY() - (flag2 ? 0.12 : 0), blockpos2.getZ() + 0.5);
        entitydummy.rotationYaw = entityplayer.rotationYaw;
        if (!world.getCollisionBoxes(entitydummy, entitydummy.getEntityBoundingBox().expandXyz(-0.1)).isEmpty()) {
            return EnumActionResult.FAIL;
        }
        world.spawnEntityInWorld(entitydummy);
        if (!entityplayer.isCreative()) {
            itemstack.splitStack(1);
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.deleteStack(itemstack);
            }
        }
        entityplayer.addStat(StatList.getObjectUseStats(this));
        return EnumActionResult.SUCCESS;
    }
}
