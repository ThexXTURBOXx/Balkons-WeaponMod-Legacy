package ckathode.weaponmod.item;

import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompNone extends MeleeComponent {

    public MeleeCompNone() {
        this(null);
    }

    public MeleeCompNone(ToolMaterial itemTier) {
        super(MeleeSpecs.NONE, itemTier);
    }

    @Override
    public float getDamage() {
        return 0.0f;
    }

    @Override
    public float getEntityDamage() {
        return 1.0f;
    }

    @Override
    public Tool getToolComponent() {
        return new Tool(List.of(), 1.0F, meleeSpecs.dmgFromBlock, false);
    }

    @Override
    public Weapon getWeaponComponent() {
        return null;
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                          @NotNull LivingEntity attacker) {
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        return 0.0f;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public ItemAttributeModifiers.Builder setAttributes(ItemAttributeModifiers.Builder attributeBuilder) {
        return ItemAttributeModifiers.builder();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity) {
        return false;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public @NotNull InteractionResult use(ItemStack itemstack, Level world,
                                          Player entityplayer, InteractionHand hand) {
        return InteractionResult.PASS;
    }

}
