package ckathode.weaponmod.neoforge.mixin;

import ckathode.weaponmod.WMCommonEventHandler;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;defineSynchedData"
                                                + "(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void constructEntity(EntityType<?> entityType, Level level, CallbackInfo ci,
                                SynchedEntityData.Builder synchedentitydata$builder) {
        WMCommonEventHandler.constructEntity((Entity) (Object) this, synchedentitydata$builder);
    }

}
