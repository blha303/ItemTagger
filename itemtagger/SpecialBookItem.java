package itemtagger;

import net.minecraft.server.NBTTagCompound;

public class SpecialBookItem extends SpecialItem {
	private NBTTagCompound tag;

	public SpecialBookItem(org.bukkit.inventory.ItemStack is) {
		super(is);
		net.minecraft.server.ItemStack mitem = getHandle();
		if (mitem == null)
			return;
		if (mitem.tag == null) {
			mitem.tag = new NBTTagCompound();
		}
		this.tag = mitem.tag;
	}

	public void setAuthor(String author) {
		this.tag.remove("author");
		this.tag.setString("author", author);
	}

	public void setTitle(String title) {
		this.tag.remove("title");
		this.tag.setString("title", title);
	}
}