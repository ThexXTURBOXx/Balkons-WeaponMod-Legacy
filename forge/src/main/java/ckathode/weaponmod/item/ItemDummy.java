package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityDummy;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ItemDummy extends WMItem {
    private static final Predicate<Entity> PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public ItemDummy(String id) {
        super(id, new Properties().stacksTo(1));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer, InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        HitResult raytraceresult = getPlayerPOVHitResult(world, entityplayer, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        } else {
            Vec3 lookVec = entityplayer.getViewVector(1.0F);
            double f = 5.0;
            List<Entity> entities = world.getEntities(entityplayer,
                    entityplayer.getBoundingBox().expandTowards(lookVec.scale(f)).inflate(1.0), PREDICATE);
            if (!entities.isEmpty()) {
                Vec3 eyePos = entityplayer.getEyePosition(1.0F);
                for (Entity e : entities) {
                    AABB aabb = e.getBoundingBox().inflate(e.getPickRadius());
                    if (aabb.contains(eyePos)) {
                        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
                    }
                }
            }

            if (raytraceresult instanceof BlockHitResult) {
                BlockHitResult brtr = (BlockHitResult) raytraceresult;
                Block block = world.getBlockState(brtr.getBlockPos()).getBlock();
                boolean flag1 = block == Blocks.SNOW;
                EntityDummy entitydummy = new EntityDummy(world, raytraceresult.getLocation().x + 0.5,
                        raytraceresult.getLocation().y + (flag1 ? 0.38 : 1.0), raytraceresult.getLocation().z + 0.5);
                entitydummy.yRot = entityplayer.yRot;
                if (!world.noCollision(entitydummy, entitydummy.getBoundingBox().inflate(-0.1))) {
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
                } else {
                    if (!world.isClientSide) {
                        world.addFreshEntity(entitydummy);
                    }

                    if (!entityplayer.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    entityplayer.awardStat(Stats.ITEM_USED.get(this));
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
                }
            } else {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
            }
        }
    }

}
