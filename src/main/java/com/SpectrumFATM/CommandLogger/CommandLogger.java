package com.SpectrumFATM.CommandLogger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalTime;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("cmdlogger")
public class CommandLogger
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public CommandLogger() throws IOException {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("[Command Logger] Mod setup complete!");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts/
        LOGGER.info("Logging has started.");
    }

    @SubscribeEvent
    public void onCommandEvent(CommandEvent commandEvent) throws IOException {
        String command = commandEvent.getParseResults().getReader().getRead();
        String player = commandEvent.getParseResults().getContext().getSource().getDisplayName().getString();

        if (!command.equals("/")) {
            //File writer
            FileWriter writer = new FileWriter("CommandLog.txt", true);
            writer.append("\r\n[" + LocalTime.now() + "]" + "[" + player + "] " + command);
            writer.close();
        }
    }
}

