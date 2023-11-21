//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelvoiding.sophisticatedcore.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.util.ItemStackHelper;


/**
 * This mixin injects into any backpack advanced filter and allows to match
 * jewel items by their single affix.
 */
@Mixin(value = ItemStackHelper.class, remap = false)
public class MixinItemStackHelper
{
    /**
     * This method injects code at the start of areItemStackTagsEqualIgnoreDurability to check if
     * jewel items has the same affix.
     * @param stackA The first stack item.
     * @param stackB The second stack item.
     * @param callbackInfoReturnable The callback info returnable.
     */
    @Inject(method = "areItemStackTagsEqualIgnoreDurability", at = @At("HEAD"), cancellable = true)
    private static void areJewelItemSame(ItemStack stackA, ItemStack stackB, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (stackA.getItem() instanceof JewelItem && stackB.getItem() instanceof JewelItem)
        {
            VaultGearData dataA = VaultGearData.read(stackA);
            VaultGearData dataB = VaultGearData.read(stackB);

            List<VaultGearModifier<?>> leftAffixes = new ArrayList<>();
            leftAffixes.addAll(dataA.getModifiers(VaultGearModifier.AffixType.PREFIX));
            leftAffixes.addAll(dataA.getModifiers(VaultGearModifier.AffixType.SUFFIX));

            // Get all affixes for right item.
            List<VaultGearModifier<?>> rightAffixes = new ArrayList<>();
            rightAffixes.addAll(dataB.getModifiers(VaultGearModifier.AffixType.PREFIX));
            rightAffixes.addAll(dataB.getModifiers(VaultGearModifier.AffixType.SUFFIX));

            // Get compared affix.
            VaultGearAttribute<?> leftAttribute = leftAffixes.size() == 1 ? leftAffixes.get(0).getAttribute() : null;
            VaultGearAttribute<?> rightAttribute = rightAffixes.size() == 1 ? rightAffixes.get(0).getAttribute() : null;

            if (leftAttribute == rightAttribute && leftAttribute != null)
            {
                // Can it be optimized more? Yes. But I am lazy.
                callbackInfoReturnable.setReturnValue(true);
                callbackInfoReturnable.cancel();
            }
            else if (rightAttribute == null)
            {
                Optional<Integer> sizeA = dataA.getFirstValue(ModGearAttributes.JEWEL_SIZE);
                Optional<Integer> sizeB = dataB.getFirstValue(ModGearAttributes.JEWEL_SIZE);

                if (sizeA.isPresent() && sizeB.isPresent() && sizeB.get() < sizeA.get())
                {
                    // Arbitrary size matching... ugly but works
                    callbackInfoReturnable.setReturnValue(true);
                    callbackInfoReturnable.cancel();
                }
            }
        }
    }
}
