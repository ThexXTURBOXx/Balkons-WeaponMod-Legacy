package ckathode.weaponmod;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;

public class WMMobEquipment {

    private static final Map<ItemStack, Collection<String>> ZOMBIE_WEAPONS = new HashMap<>();

    public static void equipZombies(LivingEntity living, IWorld world) {
        if (!BalkonsWeaponMod.instance.modConfig.zombiesSpawnWithWeapons.get()) return;
        if (world.isRemote()) return;
        if (!(living instanceof ZombieEntity)) return;

        ZombieEntity entity = (ZombieEntity) living;
        if (entity.rand.nextFloat() < (world.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
            ZOMBIE_WEAPONS.entrySet()
                    .stream()
                    .skip(entity.rand.nextInt(Math.max(1, ZOMBIE_WEAPONS.size())))
                    .findFirst()
                    .ifPresent(e -> {
                        if (e.getValue().stream().anyMatch(cfg ->
                                !BalkonsWeaponMod.instance.modConfig.isEnabled(cfg))) return;
                        entity.setItemStackToSlot(EquipmentSlotType.MAINHAND, e.getKey());
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
