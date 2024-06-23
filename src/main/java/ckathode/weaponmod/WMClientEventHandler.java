package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.item.ExtendedReachHelper;
import ckathode.weaponmod.item.IExtendedReachItem;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.network.MsgCannonFire;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;

@SideOnly(Side.CLIENT)
public class WMClientEventHandler {
    @SubscribeEvent
    public void onMouseClick(final MouseEvent e) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null || !player.worldObj.isRemote || Minecraft.getMinecraft().currentScreen != null) {
            return;
        }
        if (e.button == 0 && e.buttonstate) {
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null) {
                IExtendedReachItem ieri = getExtendedReachItem(itemstack);
                if (ieri != null) {
                    float reach = ieri.getExtendedReach(player.worldObj, player, itemstack);
                    MovingObjectPosition raytraceResult = ExtendedReachHelper.getMouseOver(0.0f, reach);
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
            if (entity.movementInput.jump && entity.ridingEntity instanceof EntityCannon && ((EntityCannon) entity.ridingEntity).isLoaded()) {
                final MsgCannonFire msg = new MsgCannonFire((EntityCannon) entity.ridingEntity);
                BalkonsWeaponMod.instance.messagePipeline.sendToServer(msg);
            }
        }
    }

    @SubscribeEvent
    public void onFOVUpdateEvent(final FOVUpdateEvent e) {
        if (e.entity.isUsingItem() && e.entity.getItemInUse().getItem() instanceof IItemWeapon) {
            final RangedComponent rc =
                    ((IItemWeapon) e.entity.getItemInUse().getItem()).getRangedComponent();
            if (rc != null && RangedComponent.isReadyToFire(e.entity.getItemInUse())) {
                e.newfov = e.fov * rc.getFOVMultiplier(e.entity.getItemInUseDuration());
            }
        }
    }
}
