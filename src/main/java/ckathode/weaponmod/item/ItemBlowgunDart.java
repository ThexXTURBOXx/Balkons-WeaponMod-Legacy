package ckathode.weaponmod.item;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlowgunDart extends WMItem {
    public ItemBlowgunDart(String id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull Item itemIn, @Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        for (int j = 0; j < DartType.dartTypes.length; ++j) {
            if (DartType.dartTypes[j] != null) {
                subItems.add(new ItemStack(this, 1, j));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn,
                               @Nonnull List<String> tooltip, boolean advanced) {
        DartType type = DartType.getDartTypeFromStack(stack);
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
        tooltip.add(s.getFormattedText());
    }

}
