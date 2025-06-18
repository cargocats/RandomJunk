package com.github.cargocats.randomjunk.client.screen;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.network.packet.AttemptOpenSafeC2S;
import com.github.cargocats.randomjunk.screen.PasswordScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PasswordScreen extends HandledScreen<PasswordScreenHandler> {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/container/generic_54.png");

    private final PasswordScreenHandler handler;
    private TextFieldWidget passwordField;
    private ButtonWidget confirmButton;

    public PasswordScreen(PasswordScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;

        this.playerInventoryTitleY = 30;
    }

    @Override
    protected void init() {
        super.init();
        this.playerInventoryTitleY = 80 + 6;

        passwordField = new TextFieldWidget(textRenderer, x + 10, y + 20, 156, 20, Text.literal("Password").formatted(Formatting.WHITE));
        passwordField.setMaxLength(32);
        passwordField.setEditable(true);
        passwordField.setTextShadow(true);


        addSelectableChild(passwordField);
        setInitialFocus(passwordField);

        confirmButton = ButtonWidget.builder(Text.literal("Confirm").formatted(Formatting.WHITE), b -> {
            String input = passwordField.getText();
            // Send password to server
            RandomJunk.LOG.info("I sent a password from the client with {}", input);

            ClientPlayNetworking.send(new AttemptOpenSafeC2S(this.handler.getBlockPos(), input));
            // Send packet to server with the block position this.handler.getBlockPos();
        }).dimensions(x + 10, y + 50, 156, 20).build();

        addDrawableChild(confirmButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, i, j + 80, 0.0F, 0.0F, this.backgroundWidth, 20, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURE, i, j + 3 * 18 + 30, 0.0F, 126.0F, this.backgroundWidth, 96, 256, 256);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        var hasPassword = this.handler.hasPassword();
        context.drawText(this.textRenderer, hasPassword ? Text.literal("Enter password") : Text.literal("Set password"), this.titleX, this.titleY, Colors.WHITE, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, Colors.DARK_GRAY, false);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext, mouseX, mouseY, delta);
        super.render(drawContext, mouseX, mouseY, delta);

        passwordField.render(drawContext, mouseX, mouseY, delta);
    }
}