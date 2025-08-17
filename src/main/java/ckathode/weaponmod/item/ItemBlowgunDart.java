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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlowgunDart extends WMItem {
    public ItemBlowgunDart(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemBlowgunDart(String modId, String id) {
        super(modId, id);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull Item itemIn, @Nonnull CreativeTabs tab, @Nonnull List<ItemStack> subItems) {
        short[] keys = DartType.DART_TYPES.keys();
        IntStream.range(0, keys.length)
                .map(i -> keys[i]).sorted()
                .forEach(k -> subItems.add(new ItemStack(this, 1, k)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn,
                               @Nonnull List<String> tooltip, boolean advanced) {
        for (PotionEffect pe : getEffects(stack)) {
            String string = StatCollector.translateToLocal(pe.getEffectName()).trim();
            Potion potion = Potion.potionTypes[pe.getPotionID()];
            if (pe.getAmplifier() > 0)
                string += " " + StatCollector.translateToLocal("potion.potency." + pe.getAmplifier()).trim();
            if (pe.getDuration() > 20)
                string += " (" + Potion.getDurationString(pe) + ")";
            string = (potion.isBadEffect() ? EnumChatFormatting.RED : EnumChatFormatting.GRAY) + string;
            tooltip.add(string);
        }
    }

    @Nullable
    private static DartType getDartType(ItemStack stack) {
        return stack == null ? null : getDartType(stack.getItemDamage());
    }

    @Nonnull
    private static DartType getDartType(int damage) {
        return damage >= 0 && damage <= Short.MAX_VALUE && DartType.DART_TYPES.containsKey((short) damage)
                ? DartType.DART_TYPES.get((short) damage)
                : DartType.DAMAGE;
    }

    public static List<PotionEffect> getEffects(ItemStack stack) {
        if (stack == null) return Collections.emptyList();

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
            List<PotionEffect> customEffects = new ArrayList<>();
            NBTTagList tagList = stack.getTagCompound().getTagList("CustomPotionEffects", 10);

            for (int i = 0; i < tagList.tagCount(); ++i) {
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                PotionEffect pe = PotionEffect.readCustomPotionEffectFromNBT(tag);
                if (pe != null) customEffects.add(pe);
            }

            return customEffects;
        }

        return getDartType(stack).potionEffects;
    }

    public static float[] getColor(ItemStack stack) {
        if (stack == null) return DartType.DAMAGE.color;
        return getDartType(stack).color;
    }

}
