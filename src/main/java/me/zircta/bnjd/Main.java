package me.zircta.bnjd;

import me.zircta.bnjd.listener.JumpListener;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.StartGameEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class Main implements ModInitializer {
    @Override
    public void preInit() {
        EventBus.subscribe(this);
    }

    @SubscribeEvent
    public void onStart(StartGameEvent.Post ev) {
        EventBus.subscribe(new JumpListener());
    }
}