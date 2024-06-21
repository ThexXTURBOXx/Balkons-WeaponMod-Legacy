package ckathode.weaponmod.item;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlowgunDart extends WMItem {
    public ItemBlowgunDart(String id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull Item itemIn, @Nonnull CreativeTabs tab, @Nonnull List<ItemStack> subItems) {
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
        String string = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
        Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
        if (potioneffect.getAmplifier() > 0) {
            string += " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
        }
        if (potioneffect.getDuration() > 20) {
            string += " (" + Potion.getDurationString(potioneffect) + ")";
        }
        if (potion.isBadEffect()) {
            string = EnumChatFormatting.RED + string;
        } else {
            string = EnumChatFormatting.GRAY + string;
        }
        tooltip.add(string);
    }

}
