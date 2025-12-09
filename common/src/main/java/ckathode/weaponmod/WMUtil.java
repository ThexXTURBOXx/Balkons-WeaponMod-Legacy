package ckathode.weaponmod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WMUtil {

    public static final RandomSource RANDOM = RandomSource.createNewThreadLocalInstance();

    private static Method entityInside1_21_9;

    @SuppressWarnings("deprecation")
    public static void hurt(Entity entity, DamageSource damageSource, float amount) {
        entity.hurt(damageSource, amount);
    }

    @SuppressWarnings("deprecation")
    public static boolean hurtOrSimulate(Entity entity, DamageSource damageSource, float amount) {
        return entity.hurtOrSimulate(damageSource, amount);
    }

    public static void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity,
                                    InsideBlockEffectApplier insideBlockEffectApplier, boolean bl) {
        if (entityInside1_21_9 == null) {
            try {
                blockState.entityInside(level, blockPos, entity, insideBlockEffectApplier, bl);
                return; // if everything worked fine, we are most likely on 1.21.10 and just return
            } catch (Throwable ignored) {
            }
        }

        try {
            // otherwise, let's try the dirty 1.21.9 compat hack...
            if (entityInside1_21_9 == null) {
                entityInside1_21_9 = BlockState.class.getMethod("method_26178",
                        Level.class, BlockPos.class, Entity.class, InsideBlockEffectApplier.class);
            }
            entityInside1_21_9.invoke(blockState, level, blockPos, entity, insideBlockEffectApplier);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Client {

        public static Player getLocalPlayer() {
            return Minecraft.getInstance().player;
        }

        public static Level getLocalLevel() {
            return Minecraft.getInstance().level;
        }

    }

}
