package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityDummy;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemDummy extends WMItem {
    public ItemDummy(final String id) {
        super(id, new Properties().maxStackSize(1));
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
            return EnumActionResult.FAIL;
        }
        final EntityPlayer entityplayer = context.getPlayer();
        if (entityplayer == null) return EnumActionResult.FAIL;
        final ItemStack itemstack = context.getItem();
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
        final RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, RayTraceFluidMode.ALWAYS);
        if (raytraceresult == null || raytraceresult.type != RayTraceResult.Type.BLOCK) {
            return EnumActionResult.FAIL;
        }
        final Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        final BlockPos blockpos = raytraceresult.getBlockPos();
        final boolean flag = world.getBlockState(blockpos).isReplaceable(new BlockItemUseContext(context));
        final BlockPos blockpos2 = flag ? blockpos : blockpos.offset(raytraceresult.sideHit);
        final boolean flag2 = block == Blocks.SNOW;
        final EntityDummy entitydummy = new EntityDummy(world, blockpos2.getX() + 0.5, flag2 ?
                (blockpos2.getY() - 0.12) : blockpos2.getY(), blockpos2.getZ() + 0.5);
        entitydummy.rotationYaw = entityplayer.rotationYaw;
        if (world.getCollisionBoxes(entitydummy, entitydummy.getBoundingBox().grow(-0.1)).findAny().isPresent()) {
            return EnumActionResult.FAIL;
        }
        world.spawnEntity(entitydummy);
        if (!entityplayer.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        entityplayer.addStat(StatList.ITEM_USED.get(this));
        return EnumActionResult.SUCCESS;
    }
}
