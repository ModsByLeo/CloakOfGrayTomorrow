package adudecalledleo.graytomorrow;

import java.util.List;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public final class CloakItem extends TrinketItem {
	public static final Item INSTANCE = new CloakItem(new Item.Settings().group(ItemGroup.COMBAT));

	public CloakItem(Settings settings) {
		super(settings);
	}

	public static boolean isEquipped(LivingEntity entity) {
		return TrinketsApi.getTrinketComponent(entity).map(c -> c.isEquipped(INSTANCE)).orElse(false);
	}

	public static CloakState getState(LivingEntity entity) {
		if (isEquipped(entity)) {
			return CloakHoodComponent.KEY.maybeGet(entity).map(CloakHoodComponent::isHoodUp).orElse(false)
					? CloakState.HOOD_UP : CloakState.HOOD_DOWN;
		} else {
			return CloakState.NOT_EQUIPPED;
		}
	}

	public static void applyChameleon(LivingEntity entity) {
		if (!entity.hasStatusEffect(CloakStatusEffects.TRUE_BLINDNESS)) {
			entity.addStatusEffect(new StatusEffectInstance(CloakStatusEffects.CHAMELEON,
					CloakStatusEffects.CHAMELEON_LENGTH, 0, false, false, true));
		}
	}

	public static StatusEffectInstance createTrueBlindness(@Nullable StatusEffectInstance chameleonInstance) {
		int duration = CloakStatusEffects.CHAMELEON_LENGTH;
		if (chameleonInstance != null) {
			duration -= chameleonInstance.getDuration();
		}
		return new StatusEffectInstance(CloakStatusEffects.TRUE_BLINDNESS, duration, 0, false, false, true);
	}

	public static void removeChameleonAndApplyTrueBlindness(LivingEntity entity) {
		entity.addStatusEffect(createTrueBlindness(entity.getStatusEffect(CloakStatusEffects.CHAMELEON)));
		entity.removeStatusEffect(CloakStatusEffects.CHAMELEON);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		String key = this.getTranslationKey(stack) + ".tooltip";
		tooltip.add(Text.translatable(key + "[0]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[1]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[2]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[3]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[4]",
						Texts.bracketed(Text.keyBind(CloakOfGrayTomorrow.KEY_TOGGLE_HOOD_ID)
								.styled(style -> style.withColor(Formatting.WHITE))))
				.styled(style -> style.withColor(Formatting.GRAY)));
	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		CloakHoodComponent.KEY.get(entity).setHoodUp(false);
	}
}
