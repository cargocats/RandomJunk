package com.github.cargocats.randomjunk.mixin;

import com.github.cargocats.randomjunk.init.RJItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack itemStack = itemEntity.getStack();

        if (itemStack.isOf(RJItems.PIPE_BOMB) && !itemEntity.getWorld().isClient) {
            itemStack.getItem().inventoryTick(itemStack, (ServerWorld) itemEntity.getWorld(), itemEntity, null);
        }
    }
}
