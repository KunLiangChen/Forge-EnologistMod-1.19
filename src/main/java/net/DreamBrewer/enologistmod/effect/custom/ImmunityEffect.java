package net.DreamBrewer.enologistmod.effect.custom;

//import net.jadenxgamer.netherexp.config.JNEConfigs;
//import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

/**
 * 提供对指定药水效果的动态免疫能力
 *
 * <p>当实体同时携带本效果与目标效果时，将触发免疫协议：
 * 1. 立即清除目标效果
 * 2. 根据配置重新计算免疫持续时间
 * 3. 播放清除特效音效
 *
 * @author JadenXGamer
 * @version 1.2.0
 * @since NetherExpansion
 *
 * @see IncurableEffect 继承不可治愈特性

 *
 * @implSpec
 * 本实现保证每游戏刻(tick)执行一次效果验证，实时性误差不超过50ms
 *
 * @implNote
 * 持续时间计算公式：newDuration = originalDuration - (600 ticks × targetAmplifier)
 * 当计算结果≤0时免疫效果将自动过期
 */
public class ImmunityEffect extends IncurableEffect {
    // 存储目标药水效果的资源路径（如中毒、虚弱等）
    private final ResourceLocation effectResourceLocation;

    /**
     * 构造函数
     * @param category  效果分类（有益/有害）
     * @param color     效果在UI显示的颜色
     * @param mobEffect 需要免疫的目标药水效果的资源路径
     */
    public ImmunityEffect(MobEffectCategory category, int color, ResourceLocation mobEffect) {
        super(category, color);
        this.effectResourceLocation = mobEffect;
    }

    /**
     * 每个游戏tick触发的效果逻辑
     * @param entity 应用效果的实体
     * @param i      效果强度（未使用）
     */
    @Override
    public void applyEffectTick(LivingEntity entity, int i) {
        super.applyEffectTick(entity, i);
        // 从注册表获取目标效果实例
        MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(this.effectResourceLocation);

        if (mobEffect != null && entity.hasEffect(mobEffect)) {
            // 获取当前免疫效果的持续时间
            int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();

            /* 根据配置决定是否保留原效果等级：
             * 如果启用 AMPLIFIER_IMMUNITY_REDUCTION，则等级设为0
             * 否则保留原效果等级 */
            //目前启用reduction，会根据清除效果的等级减少时间
            int amplifier = Objects.requireNonNull(entity.getEffect(mobEffect)).getAmplifier();

            // 播放清除音效
//            entity.playSound(JNESoundEvents.ANTIDOTE_NEGATE.get(), 1, 1);

            // 移除目标效果和当前免疫效果
            entity.removeEffect(mobEffect);
            entity.removeEffect(this);

            /* 重新应用免疫效果：
             * 新持续时间 = 原持续时间 - (30秒 * 原效果等级)
             * 强制设置效果等级为0 */
            entity.addEffect(new MobEffectInstance(
                    this,
                    (duration - (600 * amplifier)), // 600 ticks = 30秒
                    0 // 新效果等级固定为0
            ));
        }
    }

    /**
     * 控制效果是否持续激活
     * @return 始终返回true，表示每个tick都会触发applyEffectTick
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}