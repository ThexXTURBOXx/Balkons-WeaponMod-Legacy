package ckathode.weaponmod;

import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import ckathode.weaponmod.item.MeleeCompHalberd;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.render.GuiOverlayReloaded;
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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.shedaniel.architectury.registry.ItemPropertiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WMClientProxy extends WMCommonProxy {

    private static final ResourceLocation RELOAD_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reload");
    private static final ItemPropertyFunction RELOAD_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !RangedComponent.isReloaded(stack)) ? 1.0f : 0.0f;

    private static final ResourceLocation RELOADED_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reloaded");
    private static final ItemPropertyFunction RELOADED_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) ->
                    RangedComponent.isReloaded(stack) ? 1.0f : 0.0f;

    private static final ResourceLocation BOOMERANG_READY_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "boomerang-ready");
    private static final ItemPropertyFunction BOOMERANG_READY_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0f : 0.0f;

    private static final ResourceLocation FLAIL_THROWN_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "flail-thrown");
    private static final ItemPropertyFunction FLAIL_THROWN_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) ->
                    entity instanceof Player && entity.getMainHandItem() == stack && PlayerWeaponData.isFlailThrown((Player) entity) ? 1.0f : 0.0f;

    private static final ResourceLocation HALBERD_STATE_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "halberd-state");
    private static final ItemPropertyFunction HALBERD_STATE_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) ->
                    MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        WMClientEventHandler eventhandler = new WMClientEventHandler();
        MinecraftForge.EVENT_BUS.register(eventhandler);
        if (BalkonsWeaponModForge.instance.modConfig.guiOverlayReloaded.get()) {
            MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded(Minecraft.getInstance()));
        }
    }

    @Override
    public void registerRenderersEntity() {
        super.registerRenderersEntity();
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityKnife, RenderKnife::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entitySpear, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityJavelin, RenderJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityMusketBullet,
                RenderMusketBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityCrossbowBolt,
                RenderCrossbowBolt::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityBlowgunDart,
                RenderBlowgunDart::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityDynamite, RenderDynamite::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityFlail, RenderFlail::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityCannon, RenderCannon::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityCannonBall, RenderCannonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityBlunderShot,
                RenderBlunderShot::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityDummy, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityBoomerang, RenderBoomerang::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponModForge.entityMortarShell,
                RenderMortarShell::new);
    }

    @Override
    public void registerItemModelProperties() {
        super.registerItemModelProperties();

        ItemPropertiesRegistry.register(BalkonsWeaponModForge.blowgun,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.blunderbuss,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.crossbow,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.mortar,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.musket,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.bayonetWood,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.bayonetStone,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.bayonetSteel,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.bayonetDiamond,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.bayonetGold,
                RELOAD_GETTER_ID, RELOAD_GETTER);

        ItemPropertiesRegistry.register(BalkonsWeaponModForge.crossbow,
                RELOADED_GETTER_ID, RELOADED_GETTER);

        ItemPropertiesRegistry.register(BalkonsWeaponModForge.boomerangWood,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.boomerangStone,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.boomerangSteel,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.boomerangDiamond,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.boomerangGold,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);

        ItemPropertiesRegistry.register(BalkonsWeaponModForge.flailWood,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.flailStone,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.flailSteel,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.flailDiamond,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.flailGold,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);

        ItemPropertiesRegistry.register(BalkonsWeaponModForge.halberdWood,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.halberdStone,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.halberdSteel,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.halberdDiamond,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(BalkonsWeaponModForge.halberdGold,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
    }
}
