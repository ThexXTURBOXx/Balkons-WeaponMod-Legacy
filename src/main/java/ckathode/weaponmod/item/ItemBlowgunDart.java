package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)) {
            for (int j = 0; j < DartType.dartTypes.length; ++j) {
                if (DartType.dartTypes[j] != null) {
                    list.add(new ItemStack(this, 1, j));
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemstack, @Nullable World worldIn,
                               @Nonnull List<String> list, @Nonnull ITooltipFlag flag) {
        DartType type = DartType.getDartTypeFromStack(itemstack);
        if (type == null) {
            return;
        }
        PotionEffect potioneffect = type.potionEffect;
        Potion potion = potioneffect.getPotion();
        ITextComponent s = new TextComponentTranslation(potioneffect.getEffectName());
        if (potioneffect.getAmplifier() > 0) {
            s = s.appendSibling(new TextComponentString(" "))
                    .appendSibling(new TextComponentTranslation("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = s.appendSibling(new TextComponentString(" ("))
                    .appendSibling(new TextComponentString(Potion.getPotionDurationString(potioneffect, 1.0f)))
                    .appendSibling(new TextComponentString(")"));
        }
        if (potion.isBadEffect()) {
            s = s.setStyle(new Style().setColor(TextFormatting.RED));
        } else {
            s = s.setStyle(new Style().setColor(TextFormatting.GRAY));
        }
        list.add(s.getFormattedText());
    }

}
