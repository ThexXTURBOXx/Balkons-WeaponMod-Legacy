package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void leftClickEntity(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getMainHandItem();
        boolean cont = stack.isEmpty() ||
                       (stack.getItem() instanceof IItemWeapon weapon &&
                        !weapon.leftClickEntity(stack, player, target));
        if (!cont) ci.cancel();
    }

}
