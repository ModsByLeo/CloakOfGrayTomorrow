package adudecalledleo.graytomorrow.mixin;

import adudecalledleo.graytomorrow.GrayTomorrow;
import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
	private void blockThatBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand,
			BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
		if (player.hasStatusEffect(GrayTomorrowStatusEffects.TRUE_BLINDNESS)) {
			player.sendMessage(
					Text.translatable("message." + GrayTomorrow.NAMESPACE + ".no_blocks"), true);
			cir.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
	private void blockThatItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand,
			CallbackInfoReturnable<ActionResult> cir) {
		if (player.hasStatusEffect(GrayTomorrowStatusEffects.TRUE_BLINDNESS)) {
			player.sendMessage(
					Text.translatable("message." + GrayTomorrow.NAMESPACE + ".no_blocks"), true);
			cir.setReturnValue(ActionResult.FAIL);
		}
	}
}
