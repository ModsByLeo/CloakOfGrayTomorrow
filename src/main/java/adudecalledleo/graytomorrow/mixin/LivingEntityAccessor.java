package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor
	Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

	@Invoker
	void callOnStatusEffectRemoved(StatusEffectInstance instance);
}
