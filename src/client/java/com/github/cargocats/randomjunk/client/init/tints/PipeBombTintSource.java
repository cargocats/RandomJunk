package com.github.cargocats.randomjunk.client.init.tints;

import com.github.cargocats.randomjunk.component.CountdownTimestampComponent;
import com.github.cargocats.randomjunk.init.RJComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

public record PipeBombTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<PipeBombTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(PipeBombTintSource::defaultColor)).apply(instance, PipeBombTintSource::new)
    );

    public PipeBombTintSource() {
        this(ColorHelper.getArgb(0, 255, 0, 0));
    }

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        CountdownTimestampComponent countdownTimerComponent = stack.get(RJComponents.COUNTDOWN_TIMER);

        if (countdownTimerComponent != null && world != null) {
            long timeStart = countdownTimerComponent.start();
            long timeEnd = countdownTimerComponent.end();

            float progress = (float)(world.getTime() - timeStart) / (float)(timeEnd - timeStart);
            progress = Math.clamp(progress, 0, 1);

            return ColorHelper.getArgb((int) (255 * progress), 255, 0,0);
        }

        return defaultColor;
    }

    @Override
    public MapCodec<PipeBombTintSource> getCodec() {
        return CODEC;
    }
}
