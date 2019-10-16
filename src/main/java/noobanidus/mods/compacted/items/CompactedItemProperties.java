package noobanidus.mods.compacted.items;

import net.minecraft.item.Item;

public class CompactedItemProperties extends Item.Properties {
  private int width;

  public CompactedItemProperties() {
    super();
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }
}
