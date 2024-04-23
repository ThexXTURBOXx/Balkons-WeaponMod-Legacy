package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityCannon;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCannon extends WMItem {
    private static final Predicate<Entity> PREDICATE = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);

    public ItemCannon(String id) {
        super(id, new Properties().maxStackSize(1));
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entityplayer, Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        RayTraceResult raytraceresult = rayTrace(world, entityplayer, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            Vec3d lookVec = entityplayer.getLook(1.0F);
            double f = 5.0;
            List<Entity> entities = world.getEntitiesInAABBexcluding(entityplayer,
                    entityplayer.getBoundingBox().expand(lookVec.scale(f)).grow(1.0), PREDICATE);
            if (!entities.isEmpty()) {
                Vec3d eyePos = entityplayer.getEyePosition(1.0F);
                for (Entity e : entities) {
                    AxisAlignedBB aabb = e.getBoundingBox().grow(e.getCollisionBorderSize());
                    if (aabb.contains(eyePos)) {
                        return new ActionResult<>(ActionResultType.PASS, itemstack);
                    }
                }
            }

            if (raytraceresult instanceof BlockRayTraceResult) {
                BlockRayTraceResult brtr = (BlockRayTraceResult) raytraceresult;
                Block block = world.getBlockState(brtr.getPos()).getBlock();
                boolean flag1 = block == Blocks.SNOW;
                EntityCannon entitycannon = new EntityCannon(world, raytraceresult.getHitVec().x + 0.5,
                        raytraceresult.getHitVec().y + (flag1 ? 0.38 : 1.0), raytraceresult.getHitVec().z + 0.5);
                entitycannon.rotationYaw = entityplayer.rotationYaw;
                if (!world.hasNoCollisions(entitycannon, entitycannon.getBoundingBox().grow(-0.1))) {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                } else {
                    if (!world.isRemote) {
                        world.addEntity(entitycannon);
                    }

                    if (!entityplayer.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    entityplayer.addStat(Stats.ITEM_USED.get(this));
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                }
            } else {
                return new ActionResult<>(ActionResultType.PASS, itemstack);
            }
        }
    }

}
