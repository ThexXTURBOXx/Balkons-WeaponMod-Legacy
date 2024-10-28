package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.WMCommonEventHandler;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;defineSynchedData"
                                                + "(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"))
    public void constructEntity(EntityType<?> entityType, Level level, CallbackInfo ci,
                                @Local SynchedEntityData.Builder synchedentitydata$builder) {
        WMCommonEventHandler.constructEntity((Entity) (Object) this, synchedentitydata$builder);
    }

}
