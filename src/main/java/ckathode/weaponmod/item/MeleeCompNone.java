package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MeleeCompNone extends MeleeComponent {
    public MeleeCompNone(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.NONE, toolmaterial);
    }

    @Override
    public float getEntityDamageMaterialPart() {
        return 0.0f;
    }

    @Override
    public float getEntityDamage() {
        return 1.0f;
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, Block block) {
        return 1.0f;
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos pos,
                                    EntityLivingBase entityliving) {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving,
                             EntityLivingBase attacker) {
        return true;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving,
                              EntityLivingBase attacker) {
        return 0.0f;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public ItemStack onItemRightClick(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        return itemstack;
    }
}
