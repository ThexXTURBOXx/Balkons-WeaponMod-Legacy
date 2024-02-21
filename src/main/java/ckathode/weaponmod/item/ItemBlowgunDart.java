package ckathode.weaponmod.item;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.potion.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemBlowgunDart extends WMItem
{
    public ItemBlowgunDart(final String id) {
        super(id);
        this.setHasSubtypes(true);
    }
    
    public void getSubItems(final CreativeTabs tab, final NonNullList list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < DartType.dartTypes.length; ++j) {
                if (DartType.dartTypes[j] != null) {
                    list.add(new ItemStack(this, 1, j));
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, @Nullable final World worldIn, final List list, final ITooltipFlag flag) {
        final DartType type = DartType.getDartTypeFromStack(itemstack);
        if (type == null) {
            return;
        }
        final PotionEffect potioneffect = type.potionEffect;
        final Potion potion = potioneffect.getPotion();
        String s = I18n.translateToLocal(potioneffect.getEffectName()).trim();
        if (potioneffect.getAmplifier() > 0) {
            s = s + " " + I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
        }
        if (potioneffect.getDuration() > 20) {
            s = s + " (" + Potion.getPotionDurationString(potioneffect, 1.0f) + ")";
        }
        if (potion.isBadEffect()) {
            list.add(TextFormatting.RED + s);
        }
        else {
            list.add(TextFormatting.GRAY + s);
        }
    }
}
