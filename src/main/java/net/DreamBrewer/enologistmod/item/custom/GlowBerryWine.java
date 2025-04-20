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

public class GlowBerryWine extends Item{
    public GlowBerryWine(Item.Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(1.6f)
                .alwaysEat()
                .build()));
    }
    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 3600, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 3600, 0));
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
