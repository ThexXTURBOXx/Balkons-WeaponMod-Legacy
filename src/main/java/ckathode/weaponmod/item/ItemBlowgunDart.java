package ckathode.weaponmod.item;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemBlowgunDart extends WMItem {
    @Nonnull
    private final DartType dartType;

    public ItemBlowgunDart(String id, @Nonnull DartType dartType) {
        super(id);
        this.dartType = dartType;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack itemstack, @Nullable World worldIn,
                                @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag) {
        EffectInstance potioneffect = dartType.potionEffect;
        Effect potion = potioneffect.getEffect();
        IFormattableTextComponent s = new TranslationTextComponent(potioneffect.getDescriptionId());
        if (potioneffect.getAmplifier() > 0) {
            s = new TranslationTextComponent("potion.withAmplifier",
                    s, new TranslationTextComponent("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = new TranslationTextComponent("potion.withDuration", s, EffectUtils.formatDuration(potioneffect, 1.0f));
        }
        s = s.withStyle(potion.getCategory().getTooltipFormatting());
        list.add(s);
    }

    @Nonnull
    public DartType getDartType() {
        return dartType;
    }

}
