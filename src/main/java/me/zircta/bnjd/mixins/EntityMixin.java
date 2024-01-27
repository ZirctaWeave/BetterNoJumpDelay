package me.zircta.bnjd.mixins;

import me.zircta.bnjd.event.StepEvent;
import net.minecraft.entity.Entity;
import net.weavemc.loader.api.event.EventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class)
public class EntityMixin {
    @Shadow public float stepHeight;

    @Inject(
            method = "moveEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setEntityBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V",
                    ordinal = 4
            )
    )
    private void callStepPre(double x, double y, double z, CallbackInfo ci) {
        EventBus.callEvent(new StepEvent(stepHeight, (Entity)(Object) this, StepEvent.State.PRE));
    }

    @Inject(
            method = "moveEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setEntityBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V",
                    ordinal = 7,
                    shift = At.Shift.BEFORE
            )
    )
    private void callStepOnStep(double x, double y, double z, CallbackInfo ci) {
        EventBus.callEvent(new StepEvent(stepHeight, (Entity)(Object) this, StepEvent.State.ON_STEP));
    }

    @Inject(
            method = "moveEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setEntityBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V",
                    ordinal = 7,
                    shift = At.Shift.AFTER
            )
    )
    private void callStepPost(double x, double y, double z, CallbackInfo ci) {
        EventBus.callEvent(new StepEvent(stepHeight, (Entity)(Object) this, StepEvent.State.POST));
    }
}
