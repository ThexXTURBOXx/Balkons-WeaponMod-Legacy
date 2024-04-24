package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityDummy;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemDummy extends WMItem {
    private static final Predicate<Entity> PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);

    public ItemDummy(String id) {
        super(id, new Properties().stacksTo(1));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer, Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(world, entityplayer, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            Vector3d lookVec = entityplayer.getViewVector(1.0F);
            double f = 5.0;
            List<Entity> entities = world.getEntities(entityplayer,
                    entityplayer.getBoundingBox().expandTowards(lookVec.scale(f)).inflate(1.0), PREDICATE);
            if (!entities.isEmpty()) {
                Vector3d eyePos = entityplayer.getEyePosition(1.0F);
                for (Entity e : entities) {
                    AxisAlignedBB aabb = e.getBoundingBox().inflate(e.getPickRadius());
                    if (aabb.contains(eyePos)) {
                        return new ActionResult<>(ActionResultType.PASS, itemstack);
                    }
                }
            }

            if (raytraceresult instanceof BlockRayTraceResult) {
                BlockRayTraceResult brtr = (BlockRayTraceResult) raytraceresult;
                Block block = world.getBlockState(brtr.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.SNOW;
                EntityDummy entitydummy = new EntityDummy(world, raytraceresult.getLocation().x + 0.5,
                        raytraceresult.getLocation().y + (flag1 ? 0.38 : 1.0), raytraceresult.getLocation().z + 0.5);
                entitydummy.yRot = entityplayer.yRot;
                if (!world.noCollision(entitydummy, entitydummy.getBoundingBox().inflate(-0.1))) {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                } else {
                    if (!world.isClientSide) {
                        world.addFreshEntity(entitydummy);
                    }

                    if (!entityplayer.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    entityplayer.awardStat(Stats.ITEM_USED.get(this));
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                }
            } else {
                return new ActionResult<>(ActionResultType.PASS, itemstack);
            }
        }
    }

}
