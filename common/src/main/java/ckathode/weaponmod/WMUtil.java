package ckathode.weaponmod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;

public class WMUtil {

    public static final RandomSource RANDOM = RandomSource.createThreadLocalInstance();

    public static ItemStackTemplate templateFromStack(ItemStack itemStack) {
        return new ItemStackTemplate(itemStack.typeHolder(), itemStack.getCount(), itemStack.getComponentsPatch());
    }

    @SuppressWarnings("deprecation")
    public static void hurt(Entity entity, DamageSource damageSource, float amount) {
        entity.hurt(damageSource, amount);
    }

    @SuppressWarnings("deprecation")
    public static boolean hurtOrSimulate(Entity entity, DamageSource damageSource, float amount) {
        return entity.hurtOrSimulate(damageSource, amount);
    }

    public static class Client {

        public static Player getLocalPlayer() {
            return Minecraft.getInstance().player;
        }

        public static Level getLocalLevel() {
            return Minecraft.getInstance().level;
        }

    }

}
