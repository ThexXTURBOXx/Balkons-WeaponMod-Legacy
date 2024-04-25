package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

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
            if (attacker instanceof Player && !((Player) attacker).abilities.instabuild) {
                bayonetDamage(itemstack, attacker, 1);
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack itemstack, @Nonnull Level world,
                             @Nonnull BlockState block, @Nonnull BlockPos pos,
                             @Nonnull LivingEntity entityliving) {
        if (hasBayonet()) {
            Material material = block.getMaterial();
            boolean flag =
                    material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && material != Material.LEAVES && material != Material.VEGETABLE;
            if (entityliving instanceof Player && !((Player) entityliving).abilities.instabuild && flag) {
                bayonetDamage(itemstack, entityliving, 2);
            }
        }
        return true;
    }

    public void bayonetDamage(ItemStack itemstack, LivingEntity entityliving, int damage) {
        Player entityplayer = (Player) entityliving;
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundTag());
        }
        int bayonetdamage = itemstack.getTag().getShort("bayonetDamage") + damage;
        if (bayonetdamage > bayonetDurability) {
            entityplayer.breakItem(itemstack);
            int id = Item.getId(this);
            if (id != 0) {
                BalkonsWeaponModForge.modLog.debug("Musket Item (" + this + ") ID = " + id);
                entityplayer.awardStat(Stats.ITEM_BROKEN.get(this));
            }
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(BalkonsWeaponModForge.musket, 1);
            itemstack2.setDamageValue(itemstack.getDamageValue());
            entityplayer.setItemSlot(EquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.getTag().contains("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTag().putShort("bayonetDamage", (short) bayonetdamage);
    }
}
