package itemtagger;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class SpecialItem extends CraftItemStack implements ToolInterface {
	private NBTTagCompound tag;

	public SpecialItem(Material mat) {
		super(mat, 1);
		net.minecraft.server.ItemStack mitem = getHandle();
		if (mitem == null)
			return;
		if (mitem.tag == null) {
			mitem.tag = new NBTTagCompound();
		}
		this.tag = mitem.tag;
	}

	public SpecialItem(net.minecraft.server.ItemStack item) {
		super(item);
		net.minecraft.server.ItemStack mitem = getHandle();
		if (mitem == null)
			return;
		if (mitem.tag == null) {
			mitem.tag = new NBTTagCompound();
		}
		this.tag = mitem.tag;
	}

	public SpecialItem(org.bukkit.inventory.ItemStack source) {
		super(((CraftItemStack) source).getHandle());
		net.minecraft.server.ItemStack mitem = getHandle();
		if (mitem == null) {
			return;
		}
		if (mitem.tag == null) {
			mitem.tag = new NBTTagCompound();
		}
		this.tag = mitem.tag;
	}

	public String getName() {
		NBTTagCompound nc = this.tag.getCompound("display");
		if (nc != null) {
			String s = nc.getString("Name");
			if (s != null)
				return s;
		}
		return null;
	}

	public void setName(String name) {
		NBTTagCompound ntag = new NBTTagCompound();
		NBTTagString p = new NBTTagString(name);
		p.setName(name);
		p.data = name;
		ntag.set("Name", p);
		ntag.setString("Name", name);
		this.tag.setCompound("display", ntag);
	}

	public Integer getRepairCost() {
		return Integer.valueOf(this.tag.getInt("RepairCost"));
	}

	public void setRepairCost(Integer i) {
		this.tag.setInt("RepairCost", i.intValue());
	}

	public void setLore(List<String> lore) {
		NBTTagCompound ntag = this.tag.getCompound("display");
		if (ntag == null)
			ntag = new NBTTagCompound("display");
		NBTTagList p = new NBTTagList("Lore");
		for (String s : lore) {
			p.add(new NBTTagString("", s));
		}
		ntag.set("Lore", p);
		this.tag.setCompound("display", ntag);
	}

	public String[] getLore() {
		NBTTagList list = this.tag.getCompound("display").getList("Lore");
		ArrayList<String> strings = new ArrayList<String>();
		String[] lores = new String[0];
		for (int i = 0; i < list.size(); i++)
			strings.add(((NBTTagString) list.get(i)).data);
		strings.toArray(lores);
		return lores;
	}

	public List<String> getLoreList() {
		ArrayList<String> strings = new ArrayList<String>();
		if (this.tag == null)
			return strings;
		if (this.tag.getCompound("display") == null)
			return strings;
		if (this.tag.getCompound("display").getList("Lore") == null)
			return strings;

		NBTTagList list = this.tag.getCompound("display").getList("Lore");
		for (int i = 0; i < list.size(); i++) {
			NBTTagString n = (NBTTagString) list.get(i);
			if (n != null) {
				if ((n.data != null) && (n.data.length() >= 1)) {
					strings.add(n.data);
				}
			}
		}
		return strings;
	}

	public void setLore(String string) {
		NBTTagCompound ntag = this.tag.getCompound("display");
		if (ntag == null)
			ntag = new NBTTagCompound("display");
		NBTTagList p = new NBTTagList("Lore");
		p.add(new NBTTagString("", string));
		ntag.set("Lore", p);
		this.tag.setCompound("display", ntag);
	}

	public void addLore(String string) {
		NBTTagCompound ntag = this.tag.getCompound("display");
		if (ntag == null)
			ntag = new NBTTagCompound("display");
		NBTTagList p = ntag.getList("Lore");
		if (p == null)
			p = new NBTTagList("Lore");
		p.add(new NBTTagString("", string));
		ntag.set("Lore", p);
		this.tag.setCompound("display", ntag);
	}

	public void setHeadName(String s) {
		this.tag.remove("skullowner");
		this.tag.setString("SkullOwner", s);
	}

	public void setFrom(String s) {
		this.tag.remove("From");
		this.tag.setString("From", s);
	}

	public void setTo(String s) {
		this.tag.remove("To");
		this.tag.setString("To", s);
	}

	public String getFrom() {
		return this.tag.getString("From");
	}

	public String getTo() {
		return this.tag.getString("To");
	}

	void setSubtext(String msg) {
		this.tag.setString("subtext", msg);
	}

	public String getSubtext() {
		return this.tag.getString("subtext");
	}
}