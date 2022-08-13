package adudecalledleo.graytomorrow.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	// TODO figure out how to exclude Chameleon & True Blindness from being cleared by milk
}
