package com.github.cargocats.randomjunk.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class JobAppEntity extends HostileEntity {
    public JobAppEntity(EntityType<? extends JobAppEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer createJobAppAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5f)
                .add(EntityAttributes.ATTACK_DAMAGE, 20.0f)
                .add(EntityAttributes.STEP_HEIGHT, 16.0f)
                .add(EntityAttributes.MAX_HEALTH, 100.0f)
                .add(EntityAttributes.FOLLOW_RANGE, 128.0f)
                .build();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 128.0f));
        this.goalSelector.add(2, new LookAroundGoal(this));

        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, NerdEntity.class, false));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }
}
