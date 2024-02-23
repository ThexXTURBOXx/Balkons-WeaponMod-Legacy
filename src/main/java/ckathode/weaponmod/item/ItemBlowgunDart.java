package ckathode.weaponmod.item;

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
    public ItemBlowgunDart(final String id) {
        super(id);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull final CreativeTabs tab, @Nonnull final NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < DartType.dartTypes.length; ++j) {
                if (DartType.dartTypes[j] != null) {
                    list.add(new ItemStack(this, 1, j));
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull final ItemStack itemstack, @Nullable final World worldIn,
                               @Nonnull final List<String> list, @Nonnull final ITooltipFlag flag) {
        final DartType type = DartType.getDartTypeFromStack(itemstack);
        if (type == null) {
            return;
        }
        final PotionEffect potioneffect = type.potionEffect;
        final Potion potion = potioneffect.getPotion();
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
