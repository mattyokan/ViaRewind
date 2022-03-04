package de.gerrygames.viarewind;

import com.google.inject.Inject;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import com.viaversion.viaversion.util.VersionInfo;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "viarewind",
		name = "ViaRewind",
		version = VersionInfo.VERSION,
		authors = {"Gerrygames"},
		dependencies = {
			@Dependency(id = "viaversion"),
			@Dependency(id = "viabackwards", optional = true)
		},
		url = "https://viaversion.com/rewind"
)
public class SpongePlugin implements ViaRewindPlatform {
	private Logger logger;
	@Inject
	private org.apache.logging.log4j.Logger loggerLog4j;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	@Listener(order = Order.LATE)
	public void onGameStart(GameInitializationEvent e) {
		// Setup Logger
		this.logger = new LoggerWrapper(loggerLog4j);
		// Init!
		ViaRewindConfigImpl conf = new ViaRewindConfigImpl(configDir.resolve("config.yml").toFile());
		conf.reloadConfig();
		this.init(conf);
	}

	public Logger getLogger() {
		return this.logger;
	}
}
