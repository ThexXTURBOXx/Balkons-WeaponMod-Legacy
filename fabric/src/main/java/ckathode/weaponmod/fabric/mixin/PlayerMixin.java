package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.WMUtil;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.WMItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "hurtCurrentlyUsedShield", at = @At(value = "HEAD"))
    public void isShield(float damageAmount, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack useItemStack = player.getUseItem();
        Item useItem = useItemStack.getItem();

        if (useItem instanceof ItemMelee || useItem instanceof ItemShooter || useItem instanceof WMItem) {
            if (!player.level.isClientSide) {
                player.awardStat(Stats.ITEM_USED.get(useItem));
            }

            if (damageAmount >= 3.0F) {
                int i = 1 + Mth.floor(damageAmount);
                InteractionHand interactionHand = player.getUsedItemHand();
                useItemStack.hurtAndBreak(i, player, p -> p.broadcastBreakEvent(interactionHand));
                if (useItemStack.isEmpty()) {
                    if (interactionHand == InteractionHand.MAIN_HAND) {
                        player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }

                    player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + WMUtil.RANDOM.nextFloat() * 0.4F);
                }
            }
        }
    }

}
