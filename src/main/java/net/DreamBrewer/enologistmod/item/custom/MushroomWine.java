package net.DreamBrewer.enologistmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class MushroomWine extends Item{
    public MushroomWine(Item.Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(3)
                .saturationMod(3.0f)
                .alwaysEat()
                .build()));
    }
    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0)); // 急迫

            // 收集所有有害效果
            List<MobEffectInstance> harmfulEffects = entity.getActiveEffects().stream()
                  .filter(effectInstance -> effectInstance.getEffect().getCategory() == net.minecraft.world.effect.MobEffectCategory.HARMFUL)
                  .collect(Collectors.toList());

            // 统一移除有害效果
            harmfulEffects.forEach(effectInstance -> entity.removeEffect(effectInstance.getEffect()));
        }
        // 如果是玩家且不是创造模式，给予空瓶
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
        }
        return result;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // 与药水一致的饮用时间
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.DRINK; // 使用饮用动画
    }
}
