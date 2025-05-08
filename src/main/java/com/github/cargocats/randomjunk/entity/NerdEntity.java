package com.github.cargocats.randomjunk.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NerdEntity extends PathAwareEntity {
    private int idlingAnimationCooldown = 0;
    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState yesAnimationState = new AnimationState();

    public NerdEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
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
        this.goalSelector.add(1, new FleeEntityGoal<>(this, JobAppEntity.class, 32.0f, 1.0, 2));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 32.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0f));
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
        this.playSound(state.getSoundGroup().getStepSound(), 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            this.updateAnimations();
        }
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
