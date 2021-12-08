package co.t19.dynmapgraveyard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public final class DynmapGraveyard extends JavaPlugin {

  private static final String DYNMAP_PLUGIN_NAME = "dynmap";
  private static final String MARKER_ICON_NAME = "skull";
  private static final String MARKER_SET_ID = "dynmapgraveyard";
  private static final String MARKER_SET_NAME = "Graveyard";

  @Override
  public void onEnable() {
    final var pluginService = Bukkit.getPluginManager();
    final var dynmap = (DynmapAPI) pluginService.getPlugin(DYNMAP_PLUGIN_NAME);
    if (dynmap == null) {
      getLogger().severe("Expected dynmap plugin");
      pluginService.disablePlugin(this);
      return;
    }

    final var markerService = dynmap.getMarkerAPI();
    final var graveyardService = new GraveService(markerService, MARKER_ICON_NAME, MARKER_SET_ID, MARKER_SET_NAME);
    final var deathHandler = new PlayerDeathHandler(graveyardService);
    pluginService.registerEvents(deathHandler, this);
  }

}
