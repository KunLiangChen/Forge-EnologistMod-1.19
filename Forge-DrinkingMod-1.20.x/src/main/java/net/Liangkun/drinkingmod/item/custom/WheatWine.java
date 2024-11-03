package net.Liangkun.drinkingmod.item.custom;
import net.Liangkun.drinkingmod.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class WheatWine extends Item {

    public WheatWine(Properties properties) {
		super(properties);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player $$3 = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        if ($$3 instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)$$3, pStack);
        }

        if (!pLevel.isClientSide) {
            List<MobEffectInstance> $$4 = PotionUtils.getMobEffects(pStack);
            Iterator var6 = $$4.iterator();

            while(var6.hasNext()) {
                MobEffectInstance $$5 = (MobEffectInstance)var6.next();
                if ($$5.getEffect().isInstantenous()) {
                    $$5.getEffect().applyInstantenousEffect($$3, $$3, pEntityLiving, $$5.getAmplifier(), 1.0);
                } else {
                    pEntityLiving.addEffect(new MobEffectInstance($$5));
                }
            }
        }

        if ($$3 != null) {
            $$3.awardStat(Stats.ITEM_USED.get(this));
                // 移除玩家已有的力量效果
            MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 2);
                // 添加新的力量效果
                $$3.addEffect(strengthEffect);


            MobEffectInstance confusionEffect = new MobEffectInstance(MobEffects.CONFUSION, 300, 1);
            // 添加新的恶心效果
                $$3.addEffect(confusionEffect);
            if (!$$3.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if ($$3 == null || !$$3.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                return new ItemStack(ModItems.CUP.get());
            }

            if ($$3 != null) {
                $$3.getInventory().add(new ItemStack(ModItems.CUP.get()));
            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level $$1 = pContext.getLevel();
        BlockPos $$2 = pContext.getClickedPos();
        Player $$3 = pContext.getPlayer();
        ItemStack $$4 = pContext.getItemInHand();
        BlockState $$5 = $$1.getBlockState($$2);
        if (pContext.getClickedFace() != Direction.DOWN && $$5.is(BlockTags.CONVERTABLE_TO_MUD) && PotionUtils.getPotion($$4) == Potions.WATER) {
            $$1.playSound((Player)null, $$2, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
            $$3.setItemInHand(pContext.getHand(), ItemUtils.createFilledResult($$4, $$3, new ItemStack(Items.GLASS_BOTTLE)));
            $$3.awardStat(Stats.ITEM_USED.get($$4.getItem()));
            if (!$$1.isClientSide) {
                ServerLevel $$6 = (ServerLevel)$$1;

                for(int $$7 = 0; $$7 < 5; ++$$7) {
                    $$6.sendParticles(ParticleTypes.SPLASH, (double)$$2.getX() + $$1.random.nextDouble(), (double)($$2.getY() + 1), (double)$$2.getZ() + $$1.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }

            $$1.playSound((Player)null, $$2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            $$1.gameEvent((Entity)null, GameEvent.FLUID_PLACE, $$2);
            $$1.setBlockAndUpdate($$2, Blocks.MUD.defaultBlockState());
            return InteractionResult.sidedSuccess($$1.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

//    public String getDescriptionId(ItemStack pStack) {
//        return PotionUtils.getPotion(pStack).getName(this.getDescriptionId() + ".effect.");
//    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.drinkingmod.wheat_wine"));
        PotionUtils.addPotionTooltip(pStack, pTooltip, 1.0F);
    }

}
