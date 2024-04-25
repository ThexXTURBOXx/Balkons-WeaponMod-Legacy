package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import ckathode.weaponmod.item.ExtendedReachHelper;
import ckathode.weaponmod.item.IExtendedReachItem;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class WMClientEventHandler {
    @SubscribeEvent
    public void onMouseClick(InputEvent.MouseInputEvent e) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.level.isClientSide || Minecraft.getInstance().screen != null) {
            return;
        }
        if (e.getButton() == 0 && e.getAction() == GLFW.GLFW_PRESS) {
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.isEmpty()) {
                IExtendedReachItem ieri = getExtendedReachItem(itemstack);
                if (ieri != null) {
                    float reach = ieri.getExtendedReach(player.level, player, itemstack);
                    HitResult raytraceResult = ExtendedReachHelper.getMouseOver(0.0f, reach);
                    if (!(raytraceResult instanceof EntityHitResult)) return;
                    EntityHitResult ertr = (EntityHitResult) raytraceResult;
                    Entity entity = ertr.getEntity();
                    if (entity != null && entity != player && entity.invulnerableTime == 0) {
                        Minecraft.getInstance().gameMode.attack(player, entity);
                    }
                }
            }
        }
    }

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

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!e.player.level.isClientSide) {
            return;
        }
        if (e.phase == TickEvent.Phase.START && e.player instanceof LocalPlayer) {
            LocalPlayer entity = (LocalPlayer) e.player;
            if (entity.input.jumping && entity.getVehicle() instanceof EntityCannon && ((EntityCannon) entity.getVehicle()).isLoaded()) {
                MsgCannonFire msg = new MsgCannonFire((EntityCannon) entity.getVehicle());
                BalkonsWeaponModForge.instance.messagePipeline.sendToServer(msg);
            }
        }
    }

    @SubscribeEvent
    public void onFOVUpdateEvent(FOVUpdateEvent e) {
        if (e.getEntity().isUsingItem() && e.getEntity().getUseItem().getItem() instanceof IItemWeapon) {
            RangedComponent rc =
                    ((IItemWeapon) e.getEntity().getUseItem().getItem()).getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(e.getEntity().getUseItem())) {
                e.setNewfov(e.getFov() * rc.getFOVMultiplier(e.getEntity().getTicksUsingItem()));
            }
        }
    }
}
