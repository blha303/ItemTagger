package itemtagger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	private final ItemTagger plugin;
	private FileConfiguration config;
	private File file;
	private boolean enableSnakes = true;
	private boolean enableCacti = true;
	private boolean limitToSpecificWorlds = false;
	private List<String> worldsList = new ArrayList<String>();

	FileManager(ItemTagger aThis) {
		this.plugin = aThis;
	}

	public void load() {
		String filename = "config.yml";
		this.file = new File(Bukkit.getServer().getPluginManager()
				.getPlugin(this.plugin.getName()).getDataFolder(), filename);

		if (this.file.exists())
			this.config = YamlConfiguration.loadConfiguration(this.file);
		else
			try {
				Bukkit.getServer().getPluginManager()
						.getPlugin(this.plugin.getName()).getDataFolder()
						.mkdir();
				InputStream jarURL = FileManager.class.getResourceAsStream("/"
						+ filename);
				copyFile(jarURL, this.file);
				this.config = YamlConfiguration.loadConfiguration(this.file);
			} catch (Exception e) {
			}
		String node = "usePermissions";
		if (this.config.contains(node))
			this.plugin.usePermissions = this.config.getBoolean(node);
		else
			this.plugin.usePermissions = false;
		try {
			this.config.save(this.file);
		} catch (IOException ex) {
			Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private static void copyFile(InputStream in, File out) throws Exception {
		InputStream fis = in;
		FileOutputStream fos = new FileOutputStream(out);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1)
				fos.write(buf, 0, i);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null)
				fos.close();
		}
	}

	public boolean isLimitToSpecificWorlds() {
		return this.limitToSpecificWorlds;
	}

	public List<String> getWorldsList() {
		return this.worldsList;
	}

	public boolean isEnableCacti() {
		return this.enableCacti;
	}

	public boolean isEnableSnakes() {
		return this.enableSnakes;
	}
}