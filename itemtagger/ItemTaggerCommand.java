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
		if (args.length < 3) {
			printHelp(cs);
			return true;
		}

		String msg = "";
		for (int i = 2; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);

		ItemStack item = p.getItemInHand();
		if (args[0].equalsIgnoreCase("set")) {
			if (args[1].equalsIgnoreCase("name")) {
				if (!csHasPermission(cs, "itemtagger.name")) {
					cs.sendMessage("You can't use this command.");
					return true;
				}
				ItemMeta temp = item.getItemMeta();
				temp.setDisplayName(msg);
				item.setItemMeta(temp);
				p.setItemInHand(item);
				return true;
			}
			if (args[1].equalsIgnoreCase("lore") || args[1].equalsIgnoreCase("subtext")) {
				if (!csHasPermission(cs, "itemtagger.lore")) {
					cs.sendMessage("You can't use this command.");
					return true;
				}
				ArrayList<String> neoLores = new ArrayList<String>();
				neoLores.add(msg);
				if ((gift.getFrom() != null) && (!gift.getFrom().equals(""))) {
					neoLores.add("From : " + gift.getFrom());
					neoLores.add("To : " + gift.getTo());
				}
				gift.setLore(neoLores);
				cs.sendMessage("success.");
				p.setItemInHand(item);
				return true;
			}
			if (args[1].equalsIgnoreCase("author")) {
				if (!csHasPermission(cs, "itemtagger.book")) {
					return true;
				}
				if (item.getTypeId() == 387) {
					BookMeta book = (BookMeta) item.getItemMeta();
					book.setAuthor(msg);
					item.setItemMeta(book);
					p.setItemInHand(item);
					cs.sendMessage("success.");
					return true;
				}
				cs.sendMessage(ChatColor.getByChar("c")
						+ "That only works on signed books, person.");
				return true;
			}
			if (args[1].equalsIgnoreCase("title")) {
				if (!csHasPermission(cs, "itemtagger.book")) {
					return true;
				}
				if (gift.getTypeId() == 387) {
					SpecialBookItem book = new SpecialBookItem(gift);
					book.setTitle(msg);
					p.setItemInHand(book);
					cs.sendMessage("success.");
					return true;
				}
				cs.sendMessage(ChatColor.getByChar("c")
						+ "That only works on signed books, person.");
				return true;
			}
			if ((args[1].equalsIgnoreCase("skullowner"))
					|| (args[1].equalsIgnoreCase("head"))) {
				if (!csHasPermission(cs, "itemtagger.heads")) {
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
						+ "That only works on heads, person.");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("add")) {
			if ((args[1].equalsIgnoreCase("lore"))
					|| (args[1].equalsIgnoreCase("subtext"))) {
				if (!csHasPermission(cs, "itemtagger.lore")) {
					return true;
				}
				ArrayList<String> neoLores = new ArrayList<String>();
				for (String s : gift.getLoreList()) {
					if ((!s.contains("From : ")) && (!s.contains("To : "))) {
						neoLores.add(s);
					}
				}
				neoLores.add(msg);
				neoLores.add("From : " + gift.getFrom());
				neoLores.add("To : " + gift.getTo());
				gift.setLore(neoLores);
				p.setItemInHand(gift);
				cs.sendMessage("success.");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("sign")) {
			if (!csHasPermission(cs, "itemtagger.lore")) {
				return true;
			}
			item = sign(item.getItemMeta().getLore(), args[1], args[2], item);
			p.setItemInHand(item);
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

	public void printHelp(CommandSender cs) {
		ChatColor colort = ChatColor.getByChar("3");
		ChatColor colorf = ChatColor.getByChar("f");
		ChatColor colors = ChatColor.getByChar("7");
		cs.sendMessage(colort + "==========================");
		cs.sendMessage(colort + "=" + colorf + "/nbt set name <name>");
		cs.sendMessage(colort + "=" + colorf
				+ "/nbt set <lore/subtext> <subtext message>");
		cs.sendMessage(colort + "=" + colorf
				+ "/nbt add <lore/subtext> <subtext message>");
		cs.sendMessage(colort + "=" + colorf
				+ "/nbt set head <player's name>");
		cs.sendMessage(colort + "=" + colorf
				+ "/nbt set title <new book titke>");
		cs.sendMessage(colort + "=" + colorf + "/nbt set author <author>");
		cs.sendMessage(colort + "=" + colorf + "/nbt sign <from> <to>");
		cs.sendMessage(colort + "=" + colorf + "/sign <from> <to>");
		cs.sendMessage(colort
				+ "="
				+ colors
				+ "instead of itag you can also use 'gift','it','tag',and 'itemtag'.");
		cs.sendMessage(colort + "==========================");
	}

	private boolean csHasPermission(CommandSender cs, String node) {
		if (!this.plugin.usePermissions)
			return true;
		if (cs.isOp())
			return true;
		if (cs.hasPermission(node))
			return true;
		if (cs.hasPermission("itemtagger.*")) {
			return true;
		}
		cs.sendMessage(ChatColor.getByChar("c") + "You need the '" + node
				+ "' permission node to use this command.");
		return false;
	}
}