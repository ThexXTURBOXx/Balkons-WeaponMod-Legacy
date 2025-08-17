package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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
        for (PotionEffect pe : getEffects(itemstack)) {
            Potion potion = pe.getPotion();
            ITextComponent s = new TextComponentTranslation(pe.getEffectName());
            if (pe.getAmplifier() > 0)
                s = s.appendSibling(new TextComponentString(" "))
                        .appendSibling(new TextComponentTranslation("potion.potency." + pe.getAmplifier()));
            if (pe.getDuration() > 20)
                s = s.appendSibling(new TextComponentString(" ("))
                        .appendSibling(new TextComponentString(PotionUtil.getPotionDurationString(pe, 1.0f)))
                        .appendSibling(new TextComponentString(")"));
            s = s.setStyle(new Style().setColor(potion.isBadEffect() ? TextFormatting.RED : TextFormatting.GRAY));
            list.add(s);
        }
    }

    @Nonnull
    public DartType getDartType() {
        return dartType;
    }

    @Nonnull
    private static DartType getDartType(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemBlowgunDart
                ? ((ItemBlowgunDart) stack.getItem()).getDartType()
                : DartType.DAMAGE;
    }

    public static List<PotionEffect> getEffects(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) return Collections.emptyList();

        List<PotionEffect> effects = new ArrayList<>(getDartType(stack).potionEffects);
        PotionUtils.addCustomPotionEffectToList(stack.getTag(), effects);
        return effects;
    }

    public static float[] getColor(ItemStack stack) {
        if (stack.isEmpty()) return DartType.DAMAGE.color;
        return getDartType(stack).color;
    }

}
