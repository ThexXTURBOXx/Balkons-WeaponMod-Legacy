package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.EntityCannon;
import java.util.List;
import java.util.function.Predicate;
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
import org.jetbrains.annotations.NotNull;

public class ItemCannon extends WMItem {

    public static final String ID = "cannon";
    public static final ItemCannon ITEM = new ItemCannon();

    private static final Predicate<Entity> PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public ItemCannon() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        BlockHitResult raytraceresult = getPlayerPOVHitResult(world, entityplayer, ClipContext.Fluid.ANY);
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

            Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
            boolean flag1 = block == Blocks.SNOW;
            EntityCannon entitycannon = new EntityCannon(world, raytraceresult.getLocation().x + 0.5,
                    raytraceresult.getLocation().y + (flag1 ? 0.38 : 1.0), raytraceresult.getLocation().z + 0.5);
            entitycannon.setYRot(entityplayer.getYRot());
            if (!world.noCollision(entitycannon, entitycannon.getBoundingBox().inflate(-0.1))) {
                return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
            } else {
                if (!world.isClientSide) {
                    world.addFreshEntity(entitycannon);
                }

                if (!entityplayer.isCreative()) {
                    itemstack.shrink(1);
                }

                entityplayer.awardStat(Stats.ITEM_USED.get(this));
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
            }
        }
    }

}
