package ckathode.weaponmod.item;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemBlowgunDart extends WMItem {
    private final DartType dartType;

    public ItemBlowgunDart(String id, @Nonnull DartType dartType) {
        super(id);
        this.dartType = dartType;
    }

    @Override
    public void addInformation(@Nonnull ItemStack itemstack, @Nullable World worldIn,
                               @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag) {
        PotionEffect potioneffect = dartType.potionEffect;
        Potion potion = potioneffect.getPotion();
        ITextComponent s = new TextComponentTranslation(potioneffect.getEffectName());
        if (potioneffect.getAmplifier() > 0) {
            s = s.appendSibling(new TextComponentString(" "))
                    .appendSibling(new TextComponentTranslation("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = s.appendSibling(new TextComponentString(" ("))
                    .appendSibling(new TextComponentString(PotionUtil.getPotionDurationString(potioneffect, 1.0f)))
                    .appendSibling(new TextComponentString(")"));
        }
        if (potion.isBadEffect()) {
            s = s.setStyle(new Style().setColor(TextFormatting.RED));
        } else {
            s = s.setStyle(new Style().setColor(TextFormatting.GRAY));
        }
        list.add(s);
    }

    public DartType getDartType() {
        return dartType;
    }

}
