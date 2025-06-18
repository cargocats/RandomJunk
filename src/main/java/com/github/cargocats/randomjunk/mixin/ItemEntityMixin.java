package com.github.cargocats.randomjunk.mixin;

import com.github.cargocats.randomjunk.registry.RJItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getStack();

    @Shadow
    public abstract @Nullable Entity getOwner();

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ItemStack itemStack = this.getStack();
        Entity entity = this.getOwner();

        if (itemStack.isOf(RJItems.PIPE_BOMB) && entity != null && !entity.getWorld().isClient) {
            itemStack.getItem().inventoryTick(itemStack, (ServerWorld) entity.getWorld(), entity, null);
        }
    }
}
