package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.registry.RJSounds;
import com.github.cargocats.randomjunk.registry.RJStatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class NarcanItem extends Item {
    public NarcanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user.hasStatusEffect(RJStatusEffects.OVERDOSE)) {
            user.playSound(RJSounds.SPRAY_NARCAN, 1.0f, 1.0f);
        }

        if (!world.isClient) {
            ItemStack item = user.getStackInHand(hand);

            if (user.hasStatusEffect(RJStatusEffects.OVERDOSE)) {
                user.sendMessage(Text.literal("You feel your overdose slipping away."), true);
                user.removeStatusEffect(RJStatusEffects.OVERDOSE);
                user.getItemCooldownManager().set(item, 20 * 60 * 20);
                item.decrementUnlessCreative(1, user);
            } else {
                user.sendMessage(Text.literal("You're not overdosing right now."), true);
            }
        }
        return ActionResult.FAIL;
    }
}
