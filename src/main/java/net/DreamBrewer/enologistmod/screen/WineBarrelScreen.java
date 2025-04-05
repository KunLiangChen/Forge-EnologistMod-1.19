package net.DreamBrewer.enologistmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.DreamBrewer.enologistmod.EnologistMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WineBarrelScreen extends AbstractContainerScreen<WineBarrelMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(EnologistMod.MOD_ID,"textures/gui/wine_barrel_gui.png");

    public WineBarrelScreen(WineBarrelMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F,  1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pPoseStack, x, y);
    }
    //进度箭头渲染位置
    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        // 修改为从下往上渲染水位
        int waterHeight = menu.getWaterProgress();
        int maxWaterHeight = 66; // 水位指示器的最大高度
        
        blit(
                pPoseStack,
                x + 108,
                y + 9 + (maxWaterHeight - waterHeight), // 调整Y坐标起始位置
                176,
                16 + (maxWaterHeight - waterHeight), // 调整纹理Y坐标
                19,
                waterHeight
        );

        if(menu.isCrafting()) {
            // 修改渲染位置和尺寸以匹配GUI中央的泡泡效果
            // 假设泡泡纹理位于材质的(176, 0)位置
            // 调整x坐标到GUI中央区域，大约在3x3网格和右侧槽位之间
            // 调整y坐标到适当的垂直位置
            blit(
                    pPoseStack,
                    x + 77,
                    y + 36,
                    176,
                    0, menu.getScaledProgress(),
                    31
            );
            // 如果泡泡效果需要额外的渲染元素，可以在这里添加
            // 例如，可能需要渲染多个泡泡或泡泡的背景
        }
    }

    @Override//基础方法，不用动
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
