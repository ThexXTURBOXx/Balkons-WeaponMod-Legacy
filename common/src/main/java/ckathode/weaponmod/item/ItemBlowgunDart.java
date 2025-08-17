package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemBlowgunDart extends WMItem implements WMDispenserExtension {

    public static final String ID_PREFIX = "dart";
    public static final Map<DartType, ItemBlowgunDart> ITEMS = DartType.DART_TYPES.stream()
            .map(t -> new Pair<>(t, WMItemBuilder.createStandardBlowgunDart(t)))
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

    @NotNull
    private final DartType dartType;

    public ItemBlowgunDart(@NotNull DartType dartType) {
        super(getBaseProperties(null)
                .component(DataComponents.POTION_CONTENTS, dartType.potionContents())
        );
        this.dartType = dartType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
                                TooltipFlag tooltipFlag) {
        PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null)
            potionContents.addPotionTooltip(tooltipComponents::add, 1.0f, context.tickRate());
    }

    @NotNull
    public DartType getDartType() {
        return dartType;
    }

    @NotNull
    private static DartType getDartType(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ItemBlowgunDart dart ? dart.getDartType() : DartType.DAMAGE;
    }

    public static Iterable<MobEffectInstance> getEffects(@NotNull ItemStack stack) {
        if (stack.isEmpty()) return Collections.emptyList();

        PotionContents effects = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        return effects.getAllEffects();
    }

    public static float[] getColor(ItemStack stack) {
        if (stack.isEmpty()) return DartType.DAMAGE.color();
        return getDartType(stack).color();
    }

    @NotNull
    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        EntityBlowgunDart dart = new EntityBlowgunDart(level, pos.x(), pos.y(), pos.z(), null);
        dart.setThrownItemStack(stack);
        return dart;
    }

    @NotNull
    @Override
    public DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder().power(3.0F).uncertainty(2.0F).build();
    }

    @Override
    public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
        blockSource.level().playSound(null, blockSource.pos(), SoundEvents.ARROW_SHOOT,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }

}
