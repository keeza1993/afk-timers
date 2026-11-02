package com.afktimers;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
public class AfkTimersLauncher {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(AfkTimersPlugin.class);
        RuneLite.main(args);
    }
}
