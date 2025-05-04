package com.github.cargocats.randomjunk.entity;

import com.github.cargocats.randomjunk.RandomJunk;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class JobAppEntity extends HostileEntity {
    public JobAppEntity(EntityType<? extends JobAppEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, (double)1.0F, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 64.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(2, new RevengeGoal(this));
    }
}
