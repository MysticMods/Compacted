package noobanidus.mods.compacted.events;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.init.ModItems;

public class Mappings {
  public static void onItemMappings (RegistryEvent.MissingMappings<Item> event) {
    for (RegistryEvent.MissingMappings.Mapping<Item> map : event.getAllMappings()) {
      if (map.key.getNamespace().equals(Compacted.MODID) && map.key.getPath().equals("pocket_impacter")) {
        map.remap(ModItems.COBBLESTONE_IMPACTER.get());
      }
      if (map.key.getNamespace().equals(Compacted.MODID) && map.key.getPath().equals("pocket_compacter")) {
        map.remap(ModItems.COBBLESTONE_COMPACTER.get());
      }
    }
  }
}
