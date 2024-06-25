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
import ckathode.weaponmod.render.ModelCannonBarrel;
import ckathode.weaponmod.render.ModelCannonStandard;
import ckathode.weaponmod.render.ModelDummy;
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
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
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
            EntityModelLayerRegistry.register(
                    ModelCannonBarrel.CANNON_BARREL_LAYER, ModelCannonBarrel::createLayer);
            EntityModelLayerRegistry.register(
                    ModelCannonStandard.CANNON_STANDARD_LAYER, ModelCannonStandard::createLayer);
            EntityModelLayerRegistry.register(
                    ModelDummy.MAIN_LAYER, ModelDummy::createLayer);

            EntityRendererRegistry.register(() -> EntityJavelin.TYPE, RenderJavelin::new);
            EntityRendererRegistry.register(() -> EntitySpear.TYPE, RenderSpear::new);
            EntityRendererRegistry.register(() -> EntityKnife.TYPE, RenderKnife::new);
            EntityRendererRegistry.register(() -> EntityMusketBullet.TYPE, RenderMusketBullet::new);
            EntityRendererRegistry.register(() -> EntityBlowgunDart.TYPE, RenderBlowgunDart::new);
            EntityRendererRegistry.register(() -> EntityDynamite.TYPE, RenderDynamite::new);
            EntityRendererRegistry.register(() -> EntityFlail.TYPE, RenderFlail::new);
            EntityRendererRegistry.register(() -> EntityCannon.TYPE, RenderCannon::new);
            EntityRendererRegistry.register(() -> EntityCannonBall.TYPE, RenderCannonBall::new);
            EntityRendererRegistry.register(() -> EntityDummy.TYPE, RenderDummy::new);
            EntityRendererRegistry.register(() -> EntityBoomerang.TYPE, RenderBoomerang::new);
            EntityRendererRegistry.register(() -> EntityCrossbowBolt.TYPE, RenderCrossbowBolt::new);
            EntityRendererRegistry.register(() -> EntityBlunderShot.TYPE, RenderBlunderShot::new);
            EntityRendererRegistry.register(() -> EntityMortarShell.TYPE, RenderMortarShell::new);
        }

        @Environment(EnvType.CLIENT)
        public static void initializeClient() {
            registerRenderers();
            WMClientEventHandler.init();
            WMItemProperties.init();
            WeaponModConfigGui.init();
        }

    }

}
