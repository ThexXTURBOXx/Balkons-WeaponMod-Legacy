package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public abstract class WMItemProjectile extends WMItem implements WMDispenserExtension {

    public static final String BULLET_MUSKET_ID = "bullet";
    public static final WMItemProjectile BULLET_MUSKET_ITEM = new WMItemProjectile() {

        private final Random rand = new Random();

        @NotNull
        @Override
        public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
            return new EntityMusketBullet(level, pos.x(), pos.y(), pos.z());
        }

        @NotNull
        @Override
        public ProjectileItem.DispenseConfig createDispenseConfig() {
            return ProjectileItem.DispenseConfig.builder().power(350F).uncertainty(3.0F).build();
        }

        @Override
        public double getYVel(@NotNull BiFunction<BlockSource, ItemStack, Double> origFn,
                              @NotNull BlockSource blockSource,
                              @NotNull ItemStack itemStack) {
            return 0.0;
        }

        @Override
        public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
            blockSource.level().playSound(null, blockSource.pos(), SoundEvents.GENERIC_EXPLODE.value(),
                    SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.7f));
            blockSource.level().playSound(null, blockSource.pos(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.4f));
        }

        @Override
        public void playAnimation(@NotNull BiConsumer<BlockSource, Direction> origFn, @NotNull BlockSource blockSource,
                                  @NotNull Direction direction) {
            super.playAnimation(origFn, blockSource, direction);
            Position pos = DispenserBlock.getDispensePosition(blockSource);
            blockSource.level().addParticle(ParticleTypes.FLAME, pos.x() + direction.getStepX(),
                    pos.y() + direction.getStepY(), pos.z() + direction.getStepZ(), 0.0, 0.2, 0.0);
        }

    };

    public static final String CROSSBOW_BOLT_ID = "bolt";
    public static final WMItemProjectile CROSSBOW_BOLT_ITEM = new WMItemProjectile() {

        @NotNull
        @Override
        public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
            return new EntityCrossbowBolt(level, pos.x(), pos.y(), pos.z());
        }

        @NotNull
        @Override
        public ProjectileItem.DispenseConfig createDispenseConfig() {
            return ProjectileItem.DispenseConfig.builder().power(3.0F).uncertainty(2.0F).build();
        }

        @Override
        public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
            blockSource.level().playSound(null, blockSource.pos(), SoundEvents.ARROW_SHOOT,
                    SoundSource.NEUTRAL, 1.0f, 1.2f);
        }

    };

    public static final String MORTAR_SHELL_ID = "shell";
    public static final WMItemProjectile MORTAR_SHELL_ITEM = new WMItemProjectile() {

        private final Random rand = new Random();

        @NotNull
        @Override
        public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
            return new EntityMortarShell(level, pos.x(), pos.y(), pos.z());
        }

        @Override
        public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
            blockSource.level().playSound(null, blockSource.pos(), SoundEvents.GENERIC_EXPLODE.value(),
                    SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.2f + 0.2f));
        }

        @Override
        public void playAnimation(@NotNull BiConsumer<BlockSource, Direction> origFn,
                                  @NotNull BlockSource blockSource,
                                  @NotNull Direction direction) {
            super.playAnimation(origFn, blockSource, direction);
            Position pos = DispenserBlock.getDispensePosition(blockSource);
            blockSource.level().addParticle(ParticleTypes.FLAME, pos.x() + direction.getStepX(),
                    pos.y() + direction.getStepY(), pos.z() + direction.getStepZ(), 0.0, 0.0, 0.0);
        }

    };

    public WMItemProjectile() {
        this(new Properties());
    }

    public WMItemProjectile(Properties properties) {
        super(properties);
    }

}
