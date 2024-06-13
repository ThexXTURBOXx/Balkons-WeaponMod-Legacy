package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.WMRegistries;
import com.mojang.serialization.Codec;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemMusket extends ItemShooter {

    public static final String ID = "musket";
    public static final ItemMusket ITEM = new ItemMusket(new MeleeCompNone(null), null);

    public static final String WOOD_ID = "musketbayonet.wood";
    public static final ItemMusket WOOD_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.WOOD), MeleeCompKnife.WOOD_ITEM);

    public static final String STONE_ID = "musketbayonet.stone";
    public static final ItemMusket STONE_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.STONE), MeleeCompKnife.STONE_ITEM);

    public static final String IRON_ID = "musketbayonet.iron";
    public static final ItemMusket IRON_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.IRON), MeleeCompKnife.IRON_ITEM);

    public static final String GOLD_ID = "musketbayonet.gold";
    public static final ItemMusket GOLD_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.GOLD), MeleeCompKnife.GOLD_ITEM);

    public static final String DIAMOND_ID = "musketbayonet.diamond";
    public static final ItemMusket DIAMOND_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.DIAMOND), MeleeCompKnife.DIAMOND_ITEM);

    public static final String NETHERITE_ID = "musketbayonet.netherite";
    public static final ItemMusket NETHERITE_ITEM = new ItemMusket(
            new MeleeCompKnife(Tiers.NETHERITE), MeleeCompKnife.NETHERITE_ITEM);

    public static final String BAYONET_DAMAGE_TYPE_ID = "bayonet-damage";
    public static final DataComponentType<Short> BAYONET_DAMAGE_TYPE =
            DataComponentType.<Short>builder().persistent(Codec.SHORT).networkSynchronized(ByteBufCodecs.SHORT).build();

    @Nullable
    protected final Item bayonetItem;
    private final int bayonetDurability;

    public ItemMusket(MeleeComponent meleecomponent, @Nullable Item bayonetitem) {
        super(new RangedCompMusket(), meleecomponent);
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
    public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                             @NotNull LivingEntity attacker) {
        if (hasBayonet()) {
            if (entityliving.invulnerableTime == entityliving.invulnerableDuration) {
                float kb = meleeComponent.getKnockBack(itemstack, entityliving, attacker);
                PhysHelper.knockBack(entityliving, attacker, kb);
                entityliving.invulnerableTime -= (int) (2.0f / meleeComponent.meleeSpecs.attackDelay);
            }
            if (attacker instanceof Player && !((Player) attacker).isCreative()) {
                bayonetDamage(itemstack, (Player) attacker, 1);
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull BlockState block, @NotNull BlockPos pos,
                             @NotNull LivingEntity entityliving) {
        if (hasBayonet()) {
            boolean flag = block.is(BlockTags.SWORD_EFFICIENT) || block.is(Blocks.COBWEB);
            if (entityliving instanceof Player && !((Player) entityliving).isCreative() && !flag) {
                bayonetDamage(itemstack, (Player) entityliving, 2);
            }
        }
        return true;
    }

    public void bayonetDamage(ItemStack itemstack, Player entityplayer, int damage) {
        if (!itemstack.has(BAYONET_DAMAGE_TYPE)) {
            itemstack.set(BAYONET_DAMAGE_TYPE, (short) 0);
        }
        int bayonetdamage = Objects.requireNonNull(itemstack.get(BAYONET_DAMAGE_TYPE)) + damage;
        if (bayonetdamage > bayonetDurability) {
            entityplayer.onEquippedItemBroken(this, EquipmentSlot.MAINHAND);
            entityplayer.awardStat(Stats.ITEM_BROKEN.get(this));
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(WMRegistries.ITEM_MUSKET.get(), 1);
            itemstack2.setDamageValue(itemstack.getDamageValue());
            entityplayer.setItemSlot(EquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.has(ReloadHelper.ReloadState.TYPE)) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.set(BAYONET_DAMAGE_TYPE, (short) bayonetdamage);
    }

}
