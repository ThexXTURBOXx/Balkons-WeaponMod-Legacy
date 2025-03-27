package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemBlowgunDart extends WMItem implements WMDispenserExtension {

    public static final String ID_PREFIX = "dart";
    public static final Map<DartType, ItemBlowgunDart> ITEMS =
            Arrays.stream(DartType.dartTypes).filter(Objects::nonNull)
                    .map(t -> new Pair<>(
                            t, WMItemBuilder.createStandardBlowgunDart(t, BalkonsWeaponMod.id(t.typeName))))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

    @NotNull
    private final DartType dartType;

    public ItemBlowgunDart(@NotNull DartType dartType, @NotNull ResourceLocation id) {
        super(id);
        this.dartType = dartType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay,
                                Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        PotionContents.addPotionTooltip(Collections.singleton(dartType.potionEffect),
                consumer, 1.0f, context.tickRate());
    }

    @NotNull
    public DartType getDartType() {
        return dartType;
    }

    @NotNull
    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        EntityBlowgunDart dart = new EntityBlowgunDart(level, pos.x(), pos.y(), pos.z(), null);
        Item item = stack.getItem();
        if (item instanceof ItemBlowgunDart)
            dart.setDartEffectType(((ItemBlowgunDart) item).getDartType());
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
