package com.github.cargocats.randomjunk.mixin;

import com.github.cargocats.randomjunk.init.RJItems;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin({
        ChestBlockEntity.class,
        BarrelBlockEntity.class
})
public abstract class ChestAndBarrelBlockEntityMixin {
    @Shadow(aliases = {"getHeldStacks", "method_11282"}, remap = false)
    protected abstract DefaultedList<ItemStack> getHeldStacks();

    @Shadow(aliases = {"setHeldStacks", "method_11281"}, remap = false)
    protected abstract void setHeldStacks(DefaultedList<ItemStack> inventory);

    @Inject(method = "onOpen", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/entity/ViewerCountManager;openContainer(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            shift = At.Shift.AFTER
    ))

    public void onOpen(PlayerEntity player, CallbackInfo ci) {
        for (ItemStack itemStack : this.getHeldStacks()) {
            if (itemStack.isOf(RJItems.PIPE_BOMB)) {

                DefaultedList<ItemStack> stacks = this.getHeldStacks();
                DefaultedList<ItemStack> filtered = DefaultedList.ofSize(stacks.size(), ItemStack.EMPTY);

                for (int i = 0; i < stacks.size(); i++) {
                    ItemStack stack = stacks.get(i);
                    if (stack.getItem() != RJItems.PIPE_BOMB) {
                        filtered.set(i, stack);
                    }
                }
                this.setHeldStacks(filtered);

                World world = player.getWorld();
                world.createExplosion(null, Explosion.createDamageSource(world, player), new ExplosionBehavior(), player.getPos(), 4.0f, false, World.ExplosionSourceType.TNT);
            }
        }
    }
}
