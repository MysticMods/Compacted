package noobanidus.mods.compacted.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class HammerItemProperties extends Item.Properties {
  private int width;
  private IItemTier tier;
  private int attackDamage;
  private float attackSpeed;

  public HammerItemProperties() {
    super();
  }

  public int getWidth() {
    return width;
  }

  public HammerItemProperties width(int width) {
    this.width = width;
    return this;
  }

  public IItemTier getTier() {
    return tier;
  }

  public HammerItemProperties tier(IItemTier tier) {
    this.tier = tier;
    return this;
  }

  public int getAttackDamage() {
    return attackDamage;
  }

  public HammerItemProperties attackDamage(int attackDamage) {
    this.attackDamage = attackDamage;
    return this;
  }

  public float getAttackSpeed() {
    return attackSpeed;
  }

  public HammerItemProperties attackSpeed(float attackSpeed) {
    this.attackSpeed = attackSpeed;
    return this;
  }

  public HammerItemProperties setGroup (ItemGroup group) {
    super.group(group);
    return this;
  }
}
