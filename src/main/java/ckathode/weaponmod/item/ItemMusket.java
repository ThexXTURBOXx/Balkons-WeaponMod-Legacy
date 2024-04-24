package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMusket extends ItemShooter {
    @Nullable
    protected final Item bayonetItem;
    private final int bayonetDurability;

    public ItemMusket(String id, MeleeComponent meleecomponent, @Nullable Item bayonetitem) {
        super(id, new RangedCompMusket(), meleecomponent);
        bayonetItem = bayonetitem;
        bayonetDurability =
                meleecomponent.meleeSpecs != MeleeComponent.MeleeSpecs.NONE && meleecomponent.weaponMaterial != null
                        ? meleecomponent.meleeSpecs.durabilityBase +
                          (int) (meleecomponent.weaponMaterial.getUses() * meleecomponent.meleeSpecs.durabilityMult)
                        : 0;
    }

    public boolean hasBayonet() {
        return bayonetItem != null;
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityliving,
                             @Nonnull LivingEntity attacker) {
        if (hasBayonet()) {
            if (entityliving.invulnerableTime == entityliving.invulnerableDuration) {
                float kb = meleeComponent.getKnockBack(itemstack, entityliving, attacker);
                PhysHelper.knockBack(entityliving, attacker, kb);
                entityliving.invulnerableTime -= (int) (2.0f / meleeComponent.meleeSpecs.attackDelay);
            }
            if (attacker instanceof PlayerEntity && !((PlayerEntity) attacker).abilities.instabuild) {
                bayonetDamage(itemstack, attacker, 1);
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack itemstack, @Nonnull World world,
                             @Nonnull BlockState block, @Nonnull BlockPos pos,
                             @Nonnull LivingEntity entityliving) {
        if (hasBayonet()) {
            Material material = block.getMaterial();
            boolean flag =
                    material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && material != Material.LEAVES && material != Material.VEGETABLE;
            if (entityliving instanceof PlayerEntity && !((PlayerEntity) entityliving).abilities.instabuild && flag) {
                bayonetDamage(itemstack, entityliving, 2);
            }
        }
        return true;
    }

    public void bayonetDamage(ItemStack itemstack, LivingEntity entityliving, int damage) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundNBT());
        }
        int bayonetdamage = itemstack.getTag().getShort("bayonetDamage") + damage;
        if (bayonetdamage > bayonetDurability) {
            entityplayer.breakItem(itemstack);
            int id = Item.getId(this);
            if (id != 0) {
                BalkonsWeaponMod.modLog.debug("Musket Item (" + this + ") ID = " + id);
                entityplayer.awardStat(Stats.ITEM_BROKEN.get(this));
            }
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(BalkonsWeaponMod.musket, 1);
            itemstack2.setDamageValue(itemstack.getDamageValue());
            entityplayer.setItemSlot(EquipmentSlotType.MAINHAND, itemstack2);
            if (itemstack.getTag().contains("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTag().putShort("bayonetDamage", (short) bayonetdamage);
    }
}
