package adudecalledleo.graytomorrow;

import java.util.List;

import adudecalledleo.graytomorrow.util.BooleanComponent;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public final class CloakItem extends TrinketItem {
	public CloakItem(Settings settings) {
		super(settings);
	}

	public static boolean isEquipped(LivingEntity entity) {
		return TrinketsApi.getTrinketComponent(entity).map(c -> c.isEquipped(GrayTomorrow.CLOAK_ITEM)).orElse(false);
	}

	public static CloakState getState(LivingEntity entity) {
		if (isEquipped(entity)) {
			return BooleanComponent.get(GrayTomorrowComponents.HOOD_UP, entity) ? CloakState.HOOD_UP : CloakState.HOOD_DOWN;
		} else {
			return CloakState.NOT_EQUIPPED;
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		String key = this.getTranslationKey(stack) + ".tooltip";
		tooltip.add(Text.translatable(key + "[0]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[1]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[2]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[3]").styled(style -> style.withColor(Formatting.GRAY)));
		tooltip.add(Text.translatable(key + "[4]",
						Texts.bracketed(Text.keyBind(GrayTomorrow.KEY_TOGGLE_HOOD_ID)
								.styled(style -> style.withColor(Formatting.WHITE))))
				.styled(style -> style.withColor(Formatting.GRAY)));
	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!entity.world.isClient) {
			BooleanComponent.set(GrayTomorrowComponents.HOOD_UP, entity, false);
		}
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!entity.world.isClient) {
			entity.removeStatusEffect(GrayTomorrowStatusEffects.CHAMELEON);
		}
	}
}
