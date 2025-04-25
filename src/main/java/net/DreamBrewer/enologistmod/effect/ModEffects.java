package net.DreamBrewer.enologistmod.effect;

import net.DreamBrewer.enologistmod.EnologistMod;
import net.DreamBrewer.enologistmod.effect.custom.ImmunityEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EnologistMod.MOD_ID);

    public static final RegistryObject<MobEffect> WITHER_IMMUNE = MOB_EFFECTS.register("wither_immune",
            () -> new ImmunityEffect(MobEffectCategory.BENEFICIAL,7561558,new ResourceLocation("minecraft", "wither")));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
