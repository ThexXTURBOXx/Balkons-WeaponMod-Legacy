package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.item.ExtendedReachHelper;
import ckathode.weaponmod.item.IExtendedReachItem;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import ckathode.weaponmod.network.WMMessagePipeline;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class WMClientEventHandler {

    @Nullable
    private static IExtendedReachItem getExtendedReachItem(ItemStack itemstack) {
        if (itemstack.getItem() instanceof IExtendedReachItem) {
            return (IExtendedReachItem) itemstack.getItem();
        } else if (itemstack.getItem() instanceof IItemWeapon &&
                   ((IItemWeapon) itemstack.getItem()).getMeleeComponent() instanceof IExtendedReachItem) {
            return (IExtendedReachItem) ((IItemWeapon) itemstack.getItem()).getMeleeComponent();
        }
        return null;
    }

    public static EventResult onMouseClick(Minecraft client, int button, int action, int mods) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.level().isClientSide || Minecraft.getInstance().screen != null) {
            return EventResult.pass();
        }
        if (button == 0 && action == GLFW.GLFW_PRESS) {
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.isEmpty()) {
                IExtendedReachItem ieri = getExtendedReachItem(itemstack);
                if (ieri != null) {
                    float reach = ieri.getExtendedReach(player.level(), player, itemstack);
                    HitResult raytraceResult = ExtendedReachHelper.getMouseOver(0.0f, reach);
                    if (!(raytraceResult instanceof EntityHitResult ertr)) return EventResult.pass();
                    Entity entity = ertr.getEntity();
                    if (entity != null && entity != player && entity.invulnerableTime == 0) {
                        Minecraft.getInstance().gameMode.attack(player, entity);
                        return EventResult.interruptTrue();
                    }
                }
            }
        }
        return EventResult.pass();
    }

    public static void onPlayerTick(Player player) {
        if (!player.level().isClientSide) {
            return;
        }
        if (player instanceof LocalPlayer entity) {
            if (entity.input.jumping && entity.getVehicle() instanceof EntityCannon && ((EntityCannon) entity.getVehicle()).isLoaded()) {
                MsgCannonFire msg = new MsgCannonFire((EntityCannon) entity.getVehicle());
                WMMessagePipeline.sendToServer(msg);
            }
        }
    }

    public static float getNewFOV(LivingEntity entity, float fov, float newFov) {
        if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof IItemWeapon) {
            RangedComponent rc = ((IItemWeapon) entity.getUseItem().getItem()).getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(entity.getUseItem())) {
                return fov * rc.getFOVMultiplier(entity.getTicksUsingItem());
            }
        }
        return newFov;
    }

    public static void init() {
        if (Platform.getEnvironment() != Env.CLIENT) return;

        TickEvent.PLAYER_PRE.register(WMClientEventHandler::onPlayerTick);
        ClientRawInputEvent.MOUSE_CLICKED_PRE.register(WMClientEventHandler::onMouseClick);
    }

}
