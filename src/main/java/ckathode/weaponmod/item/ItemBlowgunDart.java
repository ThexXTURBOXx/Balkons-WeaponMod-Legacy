package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemBlowgunDart extends WMItem {
    @Nonnull
    private final DartType dartType;

    public ItemBlowgunDart(String id, @Nonnull DartType dartType) {
        this(BalkonsWeaponMod.MOD_ID, id, dartType);
    }

    public ItemBlowgunDart(String modId, String id, @Nonnull DartType dartType) {
        super(modId, id);
        this.dartType = dartType;
    }

    @Override
    public void addInformation(@Nonnull ItemStack itemstack, @Nullable World worldIn,
                               @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag) {
        EffectInstance potioneffect = dartType.potionEffect;
        Effect potion = potioneffect.getPotion();
        ITextComponent s = new TranslationTextComponent(potioneffect.getEffectName());
        if (potioneffect.getAmplifier() > 0) {
            s = s.appendSibling(new StringTextComponent(" "))
                    .appendSibling(new TranslationTextComponent("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = s.appendSibling(new StringTextComponent(" ("))
                    .appendSibling(new StringTextComponent(EffectUtils.getPotionDurationString(potioneffect, 1.0f)))
                    .appendSibling(new StringTextComponent(")"));
        }
        s = s.setStyle(new Style().setColor(potion.getEffectType().getColor()));
        list.add(s);
    }

    @Nonnull
    public DartType getDartType() {
        return dartType;
    }

}
