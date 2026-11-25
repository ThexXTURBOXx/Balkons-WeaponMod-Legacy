package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompKnife extends MeleeComponent {

    private final boolean bayonet;

    public MeleeCompKnife(Item.ToolMaterial toolmaterial) {
        this(toolmaterial, false);
    }

    public MeleeCompKnife(Item.ToolMaterial toolmaterial, boolean bayonet) {
        super(MeleeSpecs.KNIFE, toolmaterial);
        this.bayonet = bayonet;
    }

    @Override
    public boolean isRepairableInAnvil(ItemStack toRepair, ItemStack repairMaterial) {
        return !bayonet && super.isRepairableInAnvil(toRepair, repairMaterial);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    EntityPlayer entityplayer, EnumHand hand) {
        if (!BalkonsWeaponMod.instance.modConfig.canThrowKnife) {
            return super.onItemRightClick(itemstack, world, entityplayer, hand);
        }
        if (!world.isRemote) {
            EntityKnife entityknife = new EntityKnife(world, entityplayer, itemstack.copy());
            entityknife.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            applyProjectileEnchantments(entityknife, itemstack);
            world.spawnEntity(entityknife);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack = itemstack.copy();
            itemstack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowKnife ? EnumAction.NONE : super.getItemUseAction(itemstack);
    }

}
