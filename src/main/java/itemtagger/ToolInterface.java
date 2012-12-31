package itemtagger;

import java.util.List;

public abstract interface ToolInterface {
	public abstract String getName();

	public abstract void setName(String paramString);

	public abstract Integer getRepairCost();

	public abstract void setRepairCost(Integer paramInteger);

	public abstract void setLore(List<String> paramList);

	public abstract String[] getLore();

	public abstract void setLore(String paramString);

	public abstract List<String> getLoreList();
}