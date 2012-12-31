package itemtagger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;

class SignCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private final ItemTagger plugin;

	public SignCommand(ItemTagger s) {
		this.plugin = s;
	}

	public boolean onCommand(CommandSender cs, Command cmnd, String string,
			String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("only players can use this command.");
			return true;
		}
		Player p = (Player) cs;
		if (args.length == 2) {
			ItemStack is = p.getItemInHand();
			if ((null == is) || (is.getType().equals(Material.AIR))) {
				cs.sendMessage(ChatColor.getByChar("c") + "cHold something in your hand before attempting this command.");
				return true;
			}
			is = sign(is.getItemMeta().getLore(), args[0], args[1], is);
			p.setItemInHand(is);
		} else {
			cs.sendMessage("/sign <from> <to>");
		}
		return true;
	}
	
	public ItemStack sign(List<String> lore, String from, String to, ItemStack item) {
		ItemMeta im = item.getItemMeta();
		ArrayList<String> neoLores = new ArrayList<String>();
		for (String s : lore) {
			if ((!s.contains("From : ")) && (!s.contains("To : "))) {
				neoLores.add(s);
			}
		}
		neoLores.add("From : " + from);
		neoLores.add("To : " + to);
		im.setLore(neoLores);
		item.setItemMeta(im);
		return item;
	}
}