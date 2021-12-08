package co.t19.dynmapgraveyard;

import org.bukkit.entity.Player;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

public class GraveService {

  private final MarkerIcon icon;
  private final MarkerSet set;

  public GraveService(MarkerIcon icon, MarkerSet set) {
    this.icon = icon;
    this.set = set;
  }

  public void updateGrave(Player player) {
    final var playerId = player.getUniqueId().toString();
    final var location = player.getLocation();
    final var worldName = location.getWorld().getName();

    final var existingMarker = set.findMarker(playerId);
    if (existingMarker != null) {
      existingMarker.setLocation(worldName, location.getX(), location.getY(), location.getZ());
    } else {
      final var label = player.getPlayerListName() + "'s grave";
      set.createMarker(playerId, label, worldName, location.getX(), location.getY(), location.getZ(), icon, false);
    }
  }

}
