package noobanidus.mods.compacted.init;

import com.tterrag.registrate.util.RegistryEntry;
import noobanidus.mods.compacted.recipes.ImpacterRecipe;
import noobanidus.mods.compacted.recipes.RepairRecipe;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModRecipes {


  public static final RegistryEntry<ImpacterRecipe.ImpacterSerializer> IMAPCTER_SERIALIZER = REGISTRATE.recipeSerializer("impacter_recipe", ImpacterRecipe.ImpacterSerializer::new).register();
  public static final RegistryEntry<RepairRecipe.RepairSerializer> REPAIR_SERIALIZER = REGISTRATE.recipeSerializer("repair_recipe", RepairRecipe.RepairSerializer::new).register();

  public static void load () {

  }
}
