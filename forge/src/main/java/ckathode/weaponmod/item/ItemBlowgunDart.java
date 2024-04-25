package ckathode.weaponmod.item;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemBlowgunDart extends WMItem {
    @Nonnull
    private final DartType dartType;

    public ItemBlowgunDart(String id, @Nonnull DartType dartType) {
        super(id);
        this.dartType = dartType;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack itemstack, @Nullable Level worldIn,
                                @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        MobEffectInstance potioneffect = dartType.potionEffect;
        MobEffect potion = potioneffect.getEffect();
        MutableComponent s = new TranslatableComponent(potioneffect.getDescriptionId());
        if (potioneffect.getAmplifier() > 0) {
            s = new TranslatableComponent("potion.withAmplifier",
                    s, new TranslatableComponent("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = new TranslatableComponent("potion.withDuration", s, MobEffectUtil.formatDuration(potioneffect, 1.0f));
        }
        s = s.withStyle(potion.getCategory().getTooltipFormatting());
        list.add(s);
    }

    @Nonnull
    public DartType getDartType() {
        return dartType;
    }

}
