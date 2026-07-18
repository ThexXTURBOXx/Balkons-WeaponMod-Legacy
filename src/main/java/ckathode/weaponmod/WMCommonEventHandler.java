package ckathode.weaponmod;

import ckathode.weaponmod.item.MeleeCompFirerod;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCommonEventHandler {

    private static final Map<ItemStack, Collection<String>> ZOMBIE_WEAPONS = new HashMap<>();

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.entity;
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        Entity source = event.source.getSourceOfDamage();
        if (!(source instanceof EntityZombie)) return;

        EntityZombie zombie = (EntityZombie) source;
        ItemStack stack = zombie.getEquipmentInSlot(0);
        if (stack == null) return;
        Item item = stack.getItem();
        if (item == BalkonsWeaponMod.fireRod) {
            ((MeleeCompFirerod) BalkonsWeaponMod.fireRod.meleeComponent).applyFire(event.entityLiving, stack);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (!BalkonsWeaponMod.instance.modConfig.zombiesSpawnWithWeapons) return;
        if (event.world.isRemote) return;
        if (!(event.entityLiving instanceof EntityZombie)) return;

        EntityZombie entity = (EntityZombie) event.entityLiving;
        if (entity.rand.nextFloat() < (event.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            ZOMBIE_WEAPONS.entrySet()
                    .stream()
                    .skip(entity.rand.nextInt(ZOMBIE_WEAPONS.size()))
                    .findFirst()
                    .ifPresent(e -> {
                        if (e.getValue().stream().anyMatch(cfg ->
                                !BalkonsWeaponMod.instance.modConfig.isEnabled(cfg))) return;
                        entity.setCurrentItemOrArmor(0, e.getKey());
                    });
        }
    }

    public static void registerZombieWeapon(ItemStack stack, String... configs) {
        ZOMBIE_WEAPONS.put(stack, new HashSet<>(Arrays.asList(configs)));
    }

    public static void registerZombieWeapon(Item item, String... configs) {
        registerZombieWeapon(new ItemStack(item), configs);
    }

}
