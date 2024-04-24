package ckathode.weaponmod;

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WMClientProxy extends WMCommonProxy {

    private static final ResourceLocation RELOAD_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reload");
    private static final IItemPropertyGetter RELOAD_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !RangedComponent.isReloaded(stack)) ? 1.0f : 0.0f;

    private static final ResourceLocation RELOADED_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reloaded");
    private static final IItemPropertyGetter RELOADED_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) ->
                    RangedComponent.isReloaded(stack) ? 1.0f : 0.0f;

    private static final ResourceLocation BOOMERANG_READY_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "boomerang-ready");
    private static final IItemPropertyGetter BOOMERANG_READY_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0f : 0.0f;

    private static final ResourceLocation FLAIL_THROWN_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "flail-thrown");
    private static final IItemPropertyGetter FLAIL_THROWN_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) ->
                    entity instanceof PlayerEntity && entity.getMainHandItem() == stack && PlayerWeaponData.isFlailThrown((PlayerEntity) entity) ? 1.0f : 0.0f;

    private static final ResourceLocation HALBERD_STATE_GETTER_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "halberd-state");
    private static final IItemPropertyGetter HALBERD_STATE_GETTER =
            (@Nonnull ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) ->
                    MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        WMClientEventHandler eventhandler = new WMClientEventHandler();
        MinecraftForge.EVENT_BUS.register(eventhandler);
        if (BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded.get()) {
            MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded(Minecraft.getInstance()));
        }
    }

    @Override
    public void registerRenderersEntity() {
        super.registerRenderersEntity();
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityKnife, RenderKnife::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entitySpear, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityJavelin, RenderJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityMusketBullet, RenderMusketBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCrossbowBolt, RenderCrossbowBolt::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBlowgunDart, RenderBlowgunDart::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityDynamite, RenderDynamite::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityFlail, RenderFlail::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCannon, RenderCannon::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCannonBall, RenderCannonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBlunderShot, RenderBlunderShot::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityDummy, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBoomerang, RenderBoomerang::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityMortarShell, RenderMortarShell::new);
    }

    @Override
    public void registerItemModelProperties() {
        super.registerItemModelProperties();

        ItemModelsProperties.register(BalkonsWeaponMod.blowgun,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.blunderbuss,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.crossbow,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.mortar,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.musket,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.bayonetWood,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.bayonetStone,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.bayonetSteel,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.bayonetDiamond,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.bayonetGold,
                RELOAD_GETTER_ID, RELOAD_GETTER);

        ItemModelsProperties.register(BalkonsWeaponMod.crossbow,
                RELOADED_GETTER_ID, RELOADED_GETTER);

        ItemModelsProperties.register(BalkonsWeaponMod.boomerangWood,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.boomerangStone,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.boomerangSteel,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.boomerangDiamond,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.boomerangGold,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);

        ItemModelsProperties.register(BalkonsWeaponMod.flailWood,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.flailStone,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.flailSteel,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.flailDiamond,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.flailGold,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);

        ItemModelsProperties.register(BalkonsWeaponMod.halberdWood,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.halberdStone,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.halberdSteel,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.halberdDiamond,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemModelsProperties.register(BalkonsWeaponMod.halberdGold,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
    }
}
