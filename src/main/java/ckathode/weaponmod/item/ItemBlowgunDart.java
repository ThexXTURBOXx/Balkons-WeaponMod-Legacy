package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemVariants;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemBlowgunDart extends WMItem {
    public ItemBlowgunDart(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemBlowgunDart(String modId, String id) {
        super(modId, id);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        for (int j = 0; j < DartType.dartTypes.length; ++j) {
            if (DartType.dartTypes[j] != null) {
                subItems.add(new ItemStack(this, 1, j));
            }
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return (damage >= 0 && damage < DartType.dartTypes.length && DartType.dartTypes[damage] != null) ?
                DartType.dartTypes[damage].itemIcon : itemIcon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(getIconString());
        for (DartType type : DartType.dartTypes) {
            if (type != null) {
                String variant = type.getIconVariantName();
                type.itemIcon = WMItemVariants.registerItemVariants(register, this, variant).get(0);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
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
