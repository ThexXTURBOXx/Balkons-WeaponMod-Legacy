package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import ckathode.weaponmod.network.WMMessagePipeline;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class WMClientEventHandler {

    public static void onPlayerTick(Player player) {
        if (!player.level().isClientSide()) {
            return;
        }
        if (player instanceof LocalPlayer entity) {
            if (entity.input.keyPresses.jump() && entity.getVehicle() instanceof EntityCannon cannon && cannon.isLoaded()) {
                MsgCannonFire msg = new MsgCannonFire(cannon);
                WMMessagePipeline.sendToServer(msg);
            }
        }

        propagateClientTick(player.getMainHandItem(), player, EquipmentSlot.MAINHAND);
        propagateClientTick(player.getOffhandItem(), player, EquipmentSlot.OFFHAND);
    }

    private static void propagateClientTick(ItemStack stack, Player player, EquipmentSlot slot) {
        if (!stack.isEmpty() && stack.getItem() instanceof IItemWeapon iiw) {
            iiw.inventoryTickClient(stack, player.level(), player, slot);
        }
    }

    public static float getNewFOV(LivingEntity entity, float fov, float newFov) {
        if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof IItemWeapon iiw) {
            RangedComponent rc = iiw.getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(entity.getUseItem())) {
                return fov * rc.getFOVMultiplier(entity.getTicksUsingItem());
            }
        }
        return newFov;
    }

    public static void onTooltip(ItemStack itemStack, List<Component> list,
                                 TooltipContext tooltipContext, TooltipFlag tooltipFlag) {
        if (itemStack.getItem() instanceof IItemWeapon weapon) {
            weapon.modifyTooltip(itemStack, list, tooltipContext, tooltipFlag);
        }
    }

    public static void init() {
        if (Platform.getEnvironment() != Env.CLIENT) return;

        TickEvent.PLAYER_PRE.register(WMClientEventHandler::onPlayerTick);
        ClientTooltipEvent.ITEM.register(WMClientEventHandler::onTooltip);
    }

}
