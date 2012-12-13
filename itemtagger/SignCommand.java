package itemtagger;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
			SpecialItem gift = new SpecialItem(is);
			gift.setFrom(args[0]);
			gift.setTo(args[1]);
			ArrayList<String> neoLores = new ArrayList<String>();
			for (String s : gift.getLoreList()) {
				if ((!s.contains("From : ")) && (!s.contains("To : "))) {
					neoLores.add(s);
				}
			}
			neoLores.add("From : " + gift.getFrom());
			neoLores.add("To : " + gift.getTo());
			gift.setLore(neoLores);
			p.setItemInHand(gift);
			cs.sendMessage("success.");
		} else {
			cs.sendMessage("/sign <from> <to>");
		}
		return true;
	}
}