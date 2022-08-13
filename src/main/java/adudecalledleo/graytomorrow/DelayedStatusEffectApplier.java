package adudecalledleo.graytomorrow;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import org.quiltmc.qsl.lifecycle.api.event.ServerWorldTickEvents;

public final class DelayedStatusEffectApplier implements ServerWorldTickEvents.End {
	private static final Multimap<LivingEntity, StatusEffectInstance> EFFECTS = MultimapBuilder
			.hashKeys()
			.arrayListValues()
			.build();

	public static void add(LivingEntity target, StatusEffectInstance effect) {
		EFFECTS.put(target, effect);
	}

	@Override
	public void endWorldTick(MinecraftServer server, ServerWorld world) {
		for (var entry : EFFECTS.asMap().entrySet()) {
			var target = entry.getKey();
			for (var effect : entry.getValue()) {
				target.addStatusEffect(effect);
			}
		}
	}
}
