package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.item.ExtendedReachHelper;
import ckathode.weaponmod.item.IExtendedReachItem;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WMClientEventHandler {
    @SubscribeEvent
    public void onMouseClick(final MouseEvent e) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null || !player.worldObj.isRemote || Minecraft.getMinecraft().currentScreen != null) {
            return;
        }
        if (e.getButton() == 0 && e.isButtonstate()) {
            ItemStack itemstack = player.getHeldItemMainhand();
            if (itemstack != null) {
                IExtendedReachItem ieri = getExtendedReachItem(itemstack);
                if (ieri != null) {
                    float reach = ieri.getExtendedReach(player.worldObj, player, itemstack);
                    RayTraceResult raytraceResult = ExtendedReachHelper.getMouseOver(0.0f, reach);
                    if (raytraceResult != null && raytraceResult.entityHit != null && raytraceResult.entityHit != player && raytraceResult.entityHit.hurtResistantTime == 0) {
                        Minecraft.getMinecraft().playerController.attackEntity(player,
                                raytraceResult.entityHit);
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
    public void onPlayerTick(final TickEvent.PlayerTickEvent e) {
        if (!e.player.worldObj.isRemote) {
            return;
        }
        if (e.phase == TickEvent.Phase.START && e.player instanceof EntityPlayerSP) {
            final EntityPlayerSP entity = (EntityPlayerSP) e.player;
            if (entity.movementInput.jump && entity.getRidingEntity() instanceof EntityCannon && ((EntityCannon) entity.getRidingEntity()).isLoaded()) {
                final MsgCannonFire msg = new MsgCannonFire((EntityCannon) entity.getRidingEntity());
                BalkonsWeaponMod.instance.messagePipeline.sendToServer(msg);
            }
        }
    }

    @SubscribeEvent
    public void onFOVUpdateEvent(final FOVUpdateEvent e) {
        if (e.getEntity().isHandActive() && e.getEntity().getActiveItemStack().getItem() instanceof IItemWeapon) {
            final RangedComponent rc =
                    ((IItemWeapon) e.getEntity().getActiveItemStack().getItem()).getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(e.getEntity().getActiveItemStack())) {
                e.setNewfov(e.getFov() * rc.getFOVMultiplier(e.getEntity().getItemInUseMaxCount()));
            }
        }
    }
}
