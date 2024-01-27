package me.zircta.bnjd.listener;

import me.zircta.bnjd.event.StepEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class JumpListener {
    public int stepCount;
    public int stepTicks;
    public int jumpTicks;

    @SubscribeEvent
    public void onStep(StepEvent ev) {
        if (nullcheck()) {
            if (ev.height > 0) {
                if (ev.state == StepEvent.State.ON_STEP) return;
                if (getBlockBelow(0, 1, 0) instanceof BlockStairs) {
                    if (stepCount <= 3) {
                        stepCount += 1;
                        jumpTicks = 0;
                    } else {
                        jumpTicks = 2;
                        stepCount = 0;
                    }
                } else if (getBlockBelow(0 , 1, 0) instanceof BlockSlab) {
                    jumpTicks = 1;
                }

                stepTicks = 0;
            } else {
                jumpTicks = 3;
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.Pre ev) {
        if (nullcheck()) {
            if (jumpTicks > 0) {
                jumpTicks -= 1;
            }

            stepTicks += 1;
            if (stepTicks > 3) {
                stepCount = 0;
            }

            if (stepCount <= 3) {
                Minecraft.getMinecraft().thePlayer.jumpTicks = jumpTicks;
            }
        }
    }

    public boolean nullcheck() {
        return Minecraft.getMinecraft() != null &&
                Minecraft.getMinecraft().thePlayer != null &&
                Minecraft.getMinecraft().theWorld != null;
    }

    public Block getBlockBelow(double x, double y, double z) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer).add(x, y, z)).getBlock();
    }
}
