package de.gerrygames.viarewind;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import com.viaversion.viaversion.util.VersionInfo;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import org.apache.logging.log4j.LogManager;

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
public class VelocityPlugin implements ViaRewindPlatform {
    private Logger logger;

	@Inject
	@DataDirectory
	private Path configDir;

	@Subscribe(order = PostOrder.LATE)
	public void onProxyStart(ProxyInitializeEvent e) {
		// Setup Logger
		this.logger = new LoggerWrapper(LogManager.getLogger());
		// Init!
		ViaRewindConfigImpl conf = new ViaRewindConfigImpl(configDir.resolve("config.yml").toFile());
		conf.reloadConfig();
		this.init(conf);
	}

	public Logger getLogger() {
		return this.logger;
	}
}
