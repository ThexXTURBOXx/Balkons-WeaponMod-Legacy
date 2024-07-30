package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.EntityDummy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDummy extends WMItem {
    public ItemDummy(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemDummy(String modId, String id) {
        super(modId, id);
        maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos pos,
                             EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return false;
        float f = 1.0f;
        float f2 =
                entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f3 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
        double d2 =
                entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + entityplayer.getEyeHeight();
        double d3 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);
        Vec3 vec3d = new Vec3(d, d2, d3);
        float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        float f7 = MathHelper.sin(-f2 * 0.01745329f);
        float f8 = f5 * f6;
        float f10 = f4 * f6;
        double d4 = 5.0;
        Vec3 vec3d2 = vec3d.addVector(f8 * d4, f7 * d4, f10 * d4);
        MovingObjectPosition raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            return false;
        }
        Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        BlockPos blockpos = raytraceresult.getBlockPos();
        boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
        BlockPos blockpos2 = flag ? blockpos : blockpos.offset(raytraceresult.sideHit);
        boolean flag2 = block == Blocks.snow;
        EntityDummy entitydummy = new EntityDummy(world, blockpos2.getX() + 0.5,
                blockpos2.getY() - (flag2 ? 0.12 : 0), blockpos2.getZ() + 0.5);
        entitydummy.rotationYaw = entityplayer.rotationYaw;
        if (!world.getCollisionBoxes(entitydummy.getEntityBoundingBox().expand(-0.1, -0.1, -0.1)).isEmpty()) {
            return false;
        }
        world.spawnEntityInWorld(entitydummy);
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.splitStack(1);
            if (itemstack.stackSize <= 0) {
                deleteStack(entityplayer.inventory, itemstack);
            }
        }
        entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return true;
    }
}
