package itemtagger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemTagger extends JavaPlugin {
	public ChatColor color;
	boolean hasRunOnce = false;
	boolean usePermissions = false;
	private FileManager fm;

	public void onEnable() {
		if (!this.hasRunOnce) {
			getCommand("itemtagger").setExecutor(new ItemTaggerCommand(this));
			getCommand("sign").setExecutor(new SignCommand(this));
			this.fm = new FileManager(this);

			this.hasRunOnce = true;
		}
		this.fm.load();
	}

	public void onDisable() {
	}
}
