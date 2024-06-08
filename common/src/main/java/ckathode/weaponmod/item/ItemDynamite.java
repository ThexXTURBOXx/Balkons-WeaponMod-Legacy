package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDynamite extends WMItem implements WMDispenserExtension {

    public static final String ID = "dynamite";
    public static final ItemDynamite ITEM = new ItemDynamite();

    public ItemDynamite() {
        super(WMItem.getBaseProperties(null).stacksTo(64));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!entityplayer.isCreative()) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.TNT_PRIMED,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + entityplayer.getRandom().nextInt(10));
            entitydynamite.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.7f, 4.0f);
            world.addFreshEntity(entitydynamite);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @NotNull
    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new EntityDynamite(level, pos.x(), pos.y(), pos.z());
    }

    @Override
    public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
        blockSource.level().playSound(null, blockSource.pos(), SoundEvents.TNT_PRIMED,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }

}
