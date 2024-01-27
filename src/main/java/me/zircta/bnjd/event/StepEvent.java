package me.zircta.bnjd.event;

import net.minecraft.entity.Entity;
import net.weavemc.loader.api.event.Event;

public class StepEvent extends Event {
    public final double height;
    public final Entity entity;
    public final State state;

    public StepEvent(double height, Entity entity, State state) {
        this.height = height;
        this.entity = entity;
        this.state = state;
    }

    public enum State {
        PRE, ON_STEP, POST
    }
}
