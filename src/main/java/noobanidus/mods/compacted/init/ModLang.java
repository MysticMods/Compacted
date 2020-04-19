package noobanidus.mods.compacted.init;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModLang {
  static {
    REGISTRATE.addRawLang("tooltip.compacted.pocket_compacter.desc1", "Compacts cobblestone in your inventory. %s");
    REGISTRATE.addRawLang("tooltip.compacted.pocket_compacter.desc2", "Functions more rapidly when enchanted with Efficiency.");
    REGISTRATE.addRawLang("tooltip.compacted.pocket_impacter.desc1", "Contains %s cobblestone. %s");
    REGISTRATE.addRawLang("tooltip.compacted.pocket_impacter.desc2", "Consumes cobblestone from your inventory. Can be crafted shapelessly to extract cobblestone.");
    REGISTRATE.addRawLang("tooltip.compacted.pocket_impacter.desc3", "Can also be used to place cobblestone from its reserves, and will instantly break cobblestone.");
    REGISTRATE.addRawLang("tooltip.compacted.impacter_active", "Active.");
    REGISTRATE.addRawLang("tooltip.compacted.impacter_inactive", "Inactive.");
    REGISTRATE.addRawLang("tooltip.compacted.hold_shift", "[Hold shift for more]");
    REGISTRATE.addRawLang("tooltip.compacted.sneak_right_click", "Sneak-right-click empty air to toggle active/inactive.");
    REGISTRATE.addRawLang("subtitles.compacted.item.toggled", "Pocket Impacter toggled");
  }

  public static void load () {

  }
}
