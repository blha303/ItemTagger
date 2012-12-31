package itemtagger;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.ChatColor;

public class ItemTaggerCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private final ItemTagger plugin;

	public ItemTaggerCommand(ItemTagger aThis) {
		this.plugin = aThis;
	}

	public boolean onCommand(CommandSender cs, Command cmnd, String string,
			String[] args) {
		if (!(cs instanceof Player)) {
			printHelp(cs);
			cs.sendMessage(ChatColor.getByChar("c")
					+ "Only players can use these commands.");
			return true;
		}
		Player p = (Player) cs;
		if (p.getItemInHand() == null) {
			printHelp(cs);
			cs.sendMessage(ChatColor.getByChar("c")
					+ "You need to be holding an item to use these commands.");
			return true;
		}
		if (args.length < 2) {
			printHelp(cs);
			return true;
		}

		String msg = "";
		msg = ChatColor.translateAlternateColorCodes('&', msg);

		ItemStack item = p.getItemInHand();
		if (args[0].equalsIgnoreCase("set")) {
			for (int i = 2; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}
			if (args[1].equalsIgnoreCase("name")) {
				if (!cs.hasPermission("itemtagger.name")) {
					cs.sendMessage("You can't use this command.");
					return true;
				}
				ItemMeta temp = item.getItemMeta();
				temp.setDisplayName(msg);
				item.setItemMeta(temp);
				p.setItemInHand(item);
				return true;
			} else if (args[1].equalsIgnoreCase("lore")
					|| args[1].equalsIgnoreCase("subtext")) {
				if (!cs.hasPermission("itemtagger.lore")) {
					cs.sendMessage("You can't use this command.");
					return true;
				}
				List<String> lore = new ArrayList<String>();
				lore.add(msg);
				item = addLore(lore, item);
				p.setItemInHand(item);
				return true;
			} else if (args[1].equalsIgnoreCase("author")) {
				if (!cs.hasPermission("itemtagger.book")) {
					return true;
				}
				if (item.getTypeId() == 387) {
					BookMeta book = (BookMeta) item.getItemMeta();
					book.setAuthor(msg);
					item.setItemMeta(book);
					p.setItemInHand(item);
					return true;
				}
				cs.sendMessage(ChatColor.getByChar("c")
						+ "That only works on signed books, "
						+ p.getDisplayName() + ".");
				return true;
			} else if (args[1].equalsIgnoreCase("title")) {
				if (!cs.hasPermission("itemtagger.book")) {
					return true;
				}
				if (item.getTypeId() == 387) {
					BookMeta book = (BookMeta) item.getItemMeta();
					book.setTitle(msg);
					item.setItemMeta(book);
					p.setItemInHand(item);
					return true;
				}
				cs.sendMessage(ChatColor.getByChar("c")
						+ "That only works on signed books, "
						+ p.getDisplayName() + ".");
				return true;
			} else if (args[1].equalsIgnoreCase("head")) {
				if (!cs.hasPermission("itemtagger.heads")) {
					return true;
				}
				if (item.getTypeId() == 397) {
					SkullMeta im = (SkullMeta) item.getItemMeta();
					im.setOwner(args[2]);
					item.setItemMeta(im);
					p.setItemInHand(item);
					return true;
				}
				cs.sendMessage(ChatColor.getByChar("c")
						+ "That only works on heads, " + p.getDisplayName()
						+ ".");
				return true;
			} else {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("add")) {
			for (int i = 1; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}
			if (!cs.hasPermission("itemtagger.lore")) {
				return true;
			}
			List<String> lore = new ArrayList<String>();
			for (String s : item.getItemMeta().getLore()) {
				lore.add(s);
			}
			lore.add(msg);
			item = addLore(lore, item);
			p.setItemInHand(item);
			return true;
		} else {
			return false;
		}
	}

	public ItemStack addLore(List<String> lore, ItemStack item) {
		ItemMeta im = item.getItemMeta();
		ArrayList<String> neoLores = new ArrayList<String>();
		for (String s : lore) {
			neoLores.add(s);
		}
		im.setLore(neoLores);
		item.setItemMeta(im);
		return item;
	}

	public void printHelp(CommandSender cs) {
		List<String> msg = new ArrayList<String>();
		msg.add("&cItemTagger, by Pangamma and blha303");
		msg.add("&3===================================");
		msg.add("&3=&f/tag set name <name>");
		msg.add("&3=&f/tag set lore <subtext message>");
		msg.add("&3=&f/tag add <subtext message>");
		msg.add("&3=&f/tag set head <player's name>");
		msg.add("&3=&f/tag set title <new book title>");
		msg.add("&3=&f/tag set author <author>");
		msg.add("&3=&7Aliases: it, itag, gift, itemtag, itemtagger");
		msg.add("&3===================================");
		for (String s : msg) {
			cs.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
}