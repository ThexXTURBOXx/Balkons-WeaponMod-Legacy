package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.WMCommonEventHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void constructEntity(EntityType<?> type, Level level, CallbackInfo ci) {
        WMCommonEventHandler.constructEntity((Entity) (Object) this);
    }

}
