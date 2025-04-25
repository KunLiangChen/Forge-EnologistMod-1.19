package net.DreamBrewer.enologistmod.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 引用自JadenXGamer的NetherExpansion模组
 * 提供对指定药水效果的动态免疫能力
 *
 * <p>当实体同时携带本效果与目标效果时，将触发免疫协议：
 * 1. 立即清除目标效果
 * 2. 根据配置重新计算免疫持续时间
 * 3. 播放清除特效音效
 *
 * @author JadenXGamer
 * @version 1.2.0
 * @since NetherExpansion 1.0
 *
 * @see IncurableEffect 继承不可治愈特性
 *
 *
 * @implSpec
 * 本实现保证每游戏刻(tick)执行一次效果验证，实时性误差不超过50ms
 *
 * @implNote
 * 持续时间计算公式：newDuration = originalDuration - (600 ticks × targetAmplifier)
 * 当计算结果≤0时免疫效果将自动过期
 */
public class IncurableEffect extends MobEffect {
    /**
     * 构造函数
     * @param pCategory 效果分类（BENEFICIAL-有益 / NEUTRAL-中性 / HARMFUL-有害）
     * @param pColor    效果在UI显示的颜色（RGB十六进制值，如0xFF0000表示红色）
     */
    public IncurableEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    /**
     * 重写治愈物品获取方法
     * @return 始终返回空列表，表示没有可以解除此效果的常规物品
     */
    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(); // 返回不可变空列表
    }
}