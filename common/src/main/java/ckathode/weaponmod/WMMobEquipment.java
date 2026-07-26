package ckathode.weaponmod;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class WMMobEquipment {

    private static final Map<ItemStack, Collection<String>> ZOMBIE_WEAPONS = new HashMap<>();

    public static void equipZombies(LivingEntity living, LevelAccessor level) {
        if (!WeaponModConfig.get().zombiesSpawnWithWeapons) return;
        if (level.isClientSide()) return;
        if (!(living instanceof Zombie entity)) return;

        if (entity.getRandom().nextFloat() < (level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
            ZOMBIE_WEAPONS.entrySet()
                    .stream()
                    .skip(entity.getRandom().nextInt(Math.max(1, ZOMBIE_WEAPONS.size())))
                    .findFirst()
                    .ifPresent(e -> {
                        if (e.getValue().stream().anyMatch(cfg ->
                                !WeaponModConfig.get().isEnabled(cfg))) return;
                        entity.setItemSlot(EquipmentSlot.MAINHAND, e.getKey());
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
