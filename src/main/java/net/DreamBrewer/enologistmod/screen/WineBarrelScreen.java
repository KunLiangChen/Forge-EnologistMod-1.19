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
        int waterHeight = menu.getWaterProgress();
        int wineHeight = menu.getWineProgress();
        int maxWaterHeight = 66; // 水位指示器的最大高度
        int maxWineHeight = 66;

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
                    0,
                    menu.getScaledProgress(),
                    9
            );
            // 如果泡泡效果需要额外的渲染元素，可以在这里添加
            // 例如，可能需要渲染多个泡泡或泡泡的背景
        }
        if(menu.isBrewing())
        {
            int wineType = menu.getWineType();
            switch (wineType) {
                case 1:
                    // 渲染配方1的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            195,
                            16 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 2:
                    // 渲染配方2的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            214,
                            16 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 3:
                    // 渲染配方3的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            233,
                            16 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 4:
                    // 渲染配方4的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            176,
                            82 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 5:
                    // 渲染配方5的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            214,
                            82 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 6:
                    // 渲染配方6的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            233,
                            82 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 7:
                    // 渲染配方7的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            195,
                            148 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 8:
                    // 渲染配方8的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            195,
                            82 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                case 9:
                    // 渲染配方9的酿造进度条
                    blit(
                            pPoseStack,
                            x + 108,
                            y + 9 + (maxWineHeight - wineHeight), // 调整Y坐标起始位置
                            176,
                            148 + (maxWineHeight - wineHeight), // 调整纹理Y坐标
                            19,
                            wineHeight
                    );
                    break;
                default:
                    break;
            }
        }
    }

    @Override//基础方法，不用动
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
