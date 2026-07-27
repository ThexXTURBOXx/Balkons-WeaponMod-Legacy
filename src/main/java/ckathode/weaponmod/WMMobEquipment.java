package ckathode.weaponmod;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;

public class WMMobEquipment {

    private static final Map<ItemStack, Collection<String>> ZOMBIE_WEAPONS = new HashMap<>();

    public static void equipZombies(EntityLivingBase living, IWorld world) {
        if (!BalkonsWeaponMod.instance.modConfig.zombiesSpawnWithWeapons.get()) return;
        if (world.isRemote()) return;
        if (!(living instanceof EntityZombie)) return;

        EntityZombie entity = (EntityZombie) living;
        if (entity.rand.nextFloat() < (world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            ZOMBIE_WEAPONS.entrySet()
                    .stream()
                    .skip(entity.rand.nextInt(Math.max(1, ZOMBIE_WEAPONS.size())))
                    .findFirst()
                    .ifPresent(e -> {
                        if (e.getValue().stream().anyMatch(cfg ->
                                !BalkonsWeaponMod.instance.modConfig.isEnabled(cfg))) return;
                        entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, e.getKey());
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
