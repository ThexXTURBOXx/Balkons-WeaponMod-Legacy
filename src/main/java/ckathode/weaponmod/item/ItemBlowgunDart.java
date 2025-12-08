package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ItemBlowgunDart extends WMItem {
    public ItemBlowgunDart(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemBlowgunDart(String modId, String id) {
        super(modId, id);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull Item itemIn, @Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        short[] keys = DartType.DART_TYPES.keys();
        IntStream.range(0, keys.length)
                .map(i -> keys[i]).sorted()
                .forEach(k -> subItems.add(new ItemStack(this, 1, k)));
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn,
                               @Nonnull List<String> tooltip, boolean advanced) {
        for (PotionEffect pe : getEffects(stack)) {
            Potion potion = pe.getPotion();
            ITextComponent s = new TextComponentTranslation(pe.getEffectName());
            if (pe.getAmplifier() > 0)
                s = s.appendSibling(new TextComponentString(" "))
                        .appendSibling(new TextComponentTranslation("potion.potency." + pe.getAmplifier()));
            if (pe.getDuration() > 20)
                s = s.appendSibling(new TextComponentString(" ("))
                        .appendSibling(new TextComponentString(Potion.getPotionDurationString(pe, 1.0f)))
                        .appendSibling(new TextComponentString(")"));
            s = s.setStyle(new Style().setColor(potion.isBadEffect() ? TextFormatting.RED : TextFormatting.GRAY));
            tooltip.add(s.getFormattedText());
        }
    }

    @Nullable
    private static DartType getDartType(@Nonnull ItemStack stack) {
        return stack.isEmpty() ? null : getDartType(stack.getItemDamage());
    }

    @Nonnull
    private static DartType getDartType(int damage) {
        return damage >= 0 && damage <= Short.MAX_VALUE && DartType.DART_TYPES.containsKey((short) damage)
                ? DartType.DART_TYPES.get((short) damage)
                : DartType.DAMAGE;
    }

    public static List<PotionEffect> getEffects(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) return Collections.emptyList();

        List<PotionEffect> effects = new ArrayList<>(getDartType(stack).potionEffects);
        PotionUtils.addCustomPotionEffectToList(stack.getTagCompound(), effects);
        return effects;
    }

    public static float[] getColor(ItemStack stack) {
        if (stack.isEmpty()) return DartType.DAMAGE.color;
        return getDartType(stack).color;
    }

}
