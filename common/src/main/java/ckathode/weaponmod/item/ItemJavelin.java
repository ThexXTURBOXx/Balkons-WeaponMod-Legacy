package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemJavelin extends WMItem implements WMDispenserExtension {

    public static final String ID = "javelin";
    public static final ItemJavelin ITEM = WMItemBuilder.createStandardJavelin();

    public ItemJavelin() {
        super(WMItem.getBaseProperties(null).stacksTo(16));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull LivingEntity entityLiving, int i) {
        int j = getUseDuration(itemstack, entityLiving) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = !entityLiving.onGround() && !entityLiving.isInWater();
        if (!world.isClientSide) {
            EntityJavelin entityjavelin = new EntityJavelin(world, entityLiving, itemstack);
            entityjavelin.shootFromRotation(entityLiving, entityLiving.getXRot(), entityLiving.getYRot(),
                    0.0f, f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setCritArrow(crit);
            world.addFreshEntity(entityjavelin);
        }
        world.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityLiving.getRandom().nextFloat() * 0.4f + 0.8f));
        if (entityLiving instanceof Player player && !player.isCreative()) {
            WMItem.decrStackSize(itemstack, 1, entityLiving);
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack, LivingEntity livingEntity) {
        return 72000;
    }

    @NotNull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.BOW;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        entityplayer.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, entityplayer.getItemInHand(hand));
    }

    @NotNull
    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new EntityJavelin(level, pos.x(), pos.y(), pos.z(), null);
    }

    @NotNull
    @Override
    public DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder().power(1.1F).uncertainty(4.0F).build();
    }

    @Override
    public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
        blockSource.level().playSound(null, blockSource.pos(), SoundEvents.ARROW_SHOOT,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }

}
