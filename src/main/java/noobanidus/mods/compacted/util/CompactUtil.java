package noobanidus.mods.compacted.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class CompactUtil {
  private static Int2ObjectOpenHashMap<ItemStack> compact_recipes = new Int2ObjectOpenHashMap<>();
  private static Int2ObjectOpenHashMap<ITextComponent> compact_items = new Int2ObjectOpenHashMap<>();

  static {
    compact_recipes.defaultReturnValue(ItemStack.EMPTY);
  }

  public static void setResultForItem(ItemStack stack, ItemStack stack2) {
    compact_recipes.put(RecipeItemHelper.pack(stack), stack2);
  }

  public static ItemStack getResultForItem(World world, ItemStack stack) {
    int packed = RecipeItemHelper.pack(stack);
    ItemStack type = compact_recipes.get(packed);
    if (!type.isEmpty()) {
      type = type.copy();
      type.setCount(1);
      return type;
    }

    FakeInventory.TWO_BY_TWO.setStack(stack);
    FakeInventory.THREE_BY_THREE.setStack(stack);

    RecipeManager manager = world.getRecipeManager();

    ICraftingRecipe recipe = manager.getRecipe(IRecipeType.CRAFTING, FakeInventory.TWO_BY_TWO, world).orElse(null);
    if (recipe != null) {
      ItemStack result = recipe.getRecipeOutput().copy();
      result.setCount(4);
      setResultForItem(stack, result);
      FakeInventory.TWO_BY_TWO.clear();
      FakeInventory.THREE_BY_THREE.clear();
      return result;
    }

    recipe = manager.getRecipe(IRecipeType.CRAFTING, FakeInventory.THREE_BY_THREE, world).orElse(null);
    if (recipe != null) {
      ItemStack result = recipe.getRecipeOutput().copy();
      result.setCount(9);
      setResultForItem(stack, result);
      FakeInventory.TWO_BY_TWO.clear();
      FakeInventory.THREE_BY_THREE.clear();
      return result;
    }
    FakeInventory.TWO_BY_TWO.clear();
    FakeInventory.THREE_BY_THREE.clear();
    return ItemStack.EMPTY;
  }

  @SuppressWarnings("NullableProblems")
  public static class FakeInventory extends CraftingInventory {
    public static FakeInventory TWO_BY_TWO = new FakeInventory(2);
    public static FakeInventory THREE_BY_THREE = new FakeInventory(3);

    private ItemStack stack;

    public FakeInventory(int width) {
      super(null, width, width);
    }

    public void setStack(ItemStack stack) {
      this.stack = stack.copy();
      stack.setCount(1);
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
      return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
      return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
      return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
      return true;
    }

    @Override
    public void clear() {
      super.clear();
      this.stack = ItemStack.EMPTY;
    }
  }

  public enum CompactType {
    UNKNOWN("unknown", 0),
    NONE("none", 0),
    TWO("two", 2),
    THREE("three", 3);

    private String name;
    private int count;

    CompactType(String name, int count) {
      this.name = name;
      this.count = count;
    }
  }
}
