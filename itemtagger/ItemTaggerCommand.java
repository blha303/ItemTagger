package itemtagger;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ItemTaggerCommand
  implements CommandExecutor
{
  private final ItemTagger plugin;

  public ItemTaggerCommand(ItemTagger aThis)
  {
    this.plugin = aThis;
  }

  public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args)
  {
    if (!(cs instanceof Player)) {
      printHelp(cs);
      cs.sendMessage(ChatColor.getByChar("c") + "Only players can use these commands.");
      return true;
    }
    Player p = (Player)cs;
    if (p.getItemInHand() == null) {
      printHelp(cs);
      cs.sendMessage(ChatColor.getByChar("c") + "You need to be holding an item to use these commands.");
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

    SpecialItem gift = new SpecialItem(p.getItemInHand());
    if (args[0].equalsIgnoreCase("set")) {
      if (args[1].equalsIgnoreCase("name")) {
        if (!csHasPermission(cs, "itemtagger.name")) {
          return true;
        }
        List<String> temp = gift.getLoreList();
        gift.setName(msg);
        gift.setLore(temp);
        cs.sendMessage("success.");
        p.setItemInHand(gift);
        return true;
      }if ((args[1].equalsIgnoreCase("lore")) || (args[1].equalsIgnoreCase("subtext"))) {
        if (!csHasPermission(cs, "itemtagger.lore")) {
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
        p.setItemInHand(gift);
        return true;
      }if (args[1].equalsIgnoreCase("author")) {
        if (!csHasPermission(cs, "itemtagger.book")) {
          return true;
        }
        if (gift.getTypeId() == 387) {
          SpecialBookItem book = new SpecialBookItem(gift);
          book.setAuthor(msg);
          p.setItemInHand(book);
          cs.sendMessage("success.");
          return true;
        }
        cs.sendMessage(ChatColor.getByChar("c") + "That only works on signed books, person.");
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
        cs.sendMessage(ChatColor.getByChar("c") + "That only works on signed books, person.");
        return true;
      }
      if ((args[1].equalsIgnoreCase("skullowner")) || (args[1].equalsIgnoreCase("head"))) {
        if (!csHasPermission(cs, "itemtagger.heads")) {
          return true;
        }
        if (gift.getTypeId() == 397) {
          NBTTagCompound headNBT = new NBTTagCompound();
          headNBT.setString("SkullOwner", args[2]);
          gift.getHandle().tag = headNBT;
          p.setItemInHand(gift);
          cs.sendMessage("success.");
          return true;
        }
        cs.sendMessage(ChatColor.getByChar("c") + "That only works on heads, person.");
        return true;
      }
    }
    else if (args[0].equalsIgnoreCase("add")) {
      if ((args[1].equalsIgnoreCase("lore")) || (args[1].equalsIgnoreCase("subtext"))) {
        if (!csHasPermission(cs, "itemtagger.lore")) {
          return true;
        }
        ArrayList<String> neoLores = new ArrayList<String>();
        for (String s : gift.getLoreList()) {
          if ((!s.contains("From : ")) && (!s.contains("To : ")))
          {
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
      gift.setFrom(args[1]);
      gift.setTo(args[2]);
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
    }

    return true;
  }
  public void printHelp(CommandSender cs) {
              ChatColor colorthree = ChatColor.getByChar("3");
              ChatColor colorf = ChatColor.getByChar("f");
              ChatColor colorseven = ChatColor.getByChar("7");
    cs.sendMessage(colorthree + "==========================");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt set name <name>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt set <lore/subtext> <subtext message>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt add <lore/subtext> <subtext message>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt set head <player's name>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt set title <new book titke>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt set author <author>");
    cs.sendMessage(colorthree + "=" + colorf + "/nbt sign <from> <to>");
    cs.sendMessage(colorthree + "=" + colorf + "/sign <from> <to>");
    cs.sendMessage(colorthree + "=" + colorseven + "instead of itag you can also use 'gift','it','tag',and 'itemtag'.");
    cs.sendMessage(colorthree + "==========================");
  }

  private boolean csHasPermission(CommandSender cs, String node)
  {
    if (!this.plugin.usePermissions) return true;
    if (cs.isOp())
      return true;
    if (cs.hasPermission(node))
      return true;
    if (cs.hasPermission("itemtagger.*")) {
      return true;
    }
    cs.sendMessage(ChatColor.getByChar("c") + "You need the '" + node + "' permission node to use this command.");
    return false;
  }
}