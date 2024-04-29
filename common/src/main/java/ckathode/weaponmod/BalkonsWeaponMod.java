package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.entity.EntityDummy;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import ckathode.weaponmod.item.WMItemProperties;
import ckathode.weaponmod.network.WMMessagePipeline;
import ckathode.weaponmod.render.RenderBlowgunDart;
import ckathode.weaponmod.render.RenderBlunderShot;
import ckathode.weaponmod.render.RenderBoomerang;
import ckathode.weaponmod.render.RenderCannon;
import ckathode.weaponmod.render.RenderCannonBall;
import ckathode.weaponmod.render.RenderCrossbowBolt;
import ckathode.weaponmod.render.RenderDummy;
import ckathode.weaponmod.render.RenderDynamite;
import ckathode.weaponmod.render.RenderFlail;
import ckathode.weaponmod.render.RenderJavelin;
import ckathode.weaponmod.render.RenderKnife;
import ckathode.weaponmod.render.RenderMortarShell;
import ckathode.weaponmod.render.RenderMusketBullet;
import ckathode.weaponmod.render.RenderSpear;
import me.shedaniel.architectury.registry.entity.EntityRenderers;
import me.shedaniel.architectury.utils.Env;
import me.shedaniel.architectury.utils.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BalkonsWeaponMod {

    public static final String MOD_ID = "weaponmod";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        WeaponModConfig.init();
        WMCommonEventHandler.init();
        WMRegistries.init();
        WMMessagePipeline.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> Client::initializeClient);
    }

    @Environment(EnvType.CLIENT)
    public static class Client {

        public static void registerRenderers() {
            EntityRenderers.register(EntityJavelin.TYPE, RenderJavelin::new);
            EntityRenderers.register(EntitySpear.TYPE, RenderSpear::new);
            EntityRenderers.register(EntityKnife.TYPE, RenderKnife::new);
            EntityRenderers.register(EntityMusketBullet.TYPE, RenderMusketBullet::new);
            EntityRenderers.register(EntityBlowgunDart.TYPE, RenderBlowgunDart::new);
            EntityRenderers.register(EntityDynamite.TYPE, RenderDynamite::new);
            EntityRenderers.register(EntityFlail.TYPE, RenderFlail::new);
            EntityRenderers.register(EntityCannon.TYPE, RenderCannon::new);
            EntityRenderers.register(EntityCannonBall.TYPE, RenderCannonBall::new);
            EntityRenderers.register(EntityDummy.TYPE, RenderDummy::new);
            EntityRenderers.register(EntityBoomerang.TYPE, RenderBoomerang::new);
            EntityRenderers.register(EntityCrossbowBolt.TYPE, RenderCrossbowBolt::new);
            EntityRenderers.register(EntityBlunderShot.TYPE, RenderBlunderShot::new);
            EntityRenderers.register(EntityMortarShell.TYPE, RenderMortarShell::new);
        }

        @Environment(EnvType.CLIENT)
        public static void initializeClient() {
            registerRenderers();
            WMClientEventHandler.init();
            WMItemProperties.init();
        }

    }

}
