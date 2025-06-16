package com.github.cargocats.randomjunk.entity;

import com.github.cargocats.randomjunk.registry.RJItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class NerdEntity extends PathAwareEntity {
    private int idlingAnimationCooldown = 0;
    private int eatingCooldown = 20;

    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState yesAnimationState = new AnimationState();

    public NerdEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    public static DefaultAttributeContainer createNerdAttributes() {
        return MobEntity.createLivingAttributes().add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.MAX_HEALTH, 20.0f)
                .add(EntityAttributes.FOLLOW_RANGE, 32)
                .build();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, JobAppEntity.class, 32.0f, 1.0, 1.5));
        this.goalSelector.add(2, new PickupSpecificItemGoal(this, RJItems.CRANBERRY));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 32.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0f));
    }

    public static class PickupSpecificItemGoal extends Goal {
        private final MobEntity mob;
        private final Item targetItem;
        private ItemEntity targetItemEntity;

        public PickupSpecificItemGoal(MobEntity mob, Item targetItem) {
            this.mob = mob;
            this.targetItem = targetItem;
        }

        @Override
        public boolean canStart() {
            List<ItemEntity> nearbyItems = mob.getWorld().getEntitiesByClass(ItemEntity.class, mob.getBoundingBox().expand(8.0), item -> item.isAlive() && item.getStack().getItem() == targetItem);

            if (!nearbyItems.isEmpty() && !mob.isHolding(targetItem)) {
                targetItemEntity = nearbyItems.getFirst();
                return true;
            }

            return false;
        }

        @Override
        public void start() {
            mob.getNavigation().startMovingTo(targetItemEntity, 1.0);
        }

        @Override
        public boolean shouldContinue() {
            return targetItemEntity != null && targetItemEntity.isAlive() && targetItemEntity.squaredDistanceTo(targetItemEntity) > 1.0 && !mob.isHolding(targetItem);
        }

        @Override
        public void stop() {
            targetItemEntity = null;
        }

        @Override
        public void tick() {
            if (targetItemEntity != null && !mob.isHolding(targetItem)) {
                mob.getNavigation().startMovingTo(targetItemEntity, 1.0);
            }
        }
    }

    @Override
    public boolean canPickupItem(ItemStack item) {
        return item.isOf(RJItems.CRANBERRY);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        //this.playSound(state.getSoundGroup().getStepSound(), 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            this.updateAnimations();
        } else {
            if (this.getMainHandStack().isOf(RJItems.CRANBERRY)) {
                if (eatingCooldown <= 0) {
                    eatingCooldown = 100;
                    eatCranberry();
                } else {
                    eatingCooldown--;
                }
            } else {
                // Not holding cranberry
                eatingCooldown = 20;
            }
        }
    }

    private void eatCranberry() {
        ItemStack stack = this.getMainHandStack();
        stack.decrement(1);
        this.getWorld().playSound(this, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_EAT, this.getSoundCategory(), 1.0F, 1.0F);
        this.swingHand(this.getActiveHand());

        ((ServerWorld) (this.getWorld())).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, RJItems.CRANBERRY.getDefaultStack()),
                this.getX(), this.getY() + 1.0f, this.getZ(), 5, 0.2, 0.2, 0.2, 0.05);
    }

    private void updateAnimations() {
        if (this.idlingAnimationCooldown <= 0) {
            this.idlingAnimationCooldown = 20;
            this.idlingAnimationState.start(this.age);
        } else {
            this.idlingAnimationCooldown--;
        }
    }
}
