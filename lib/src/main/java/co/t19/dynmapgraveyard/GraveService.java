package co.t19.dynmapgraveyard;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

public class GraveService {

  private final MarkerIcon icon;
  private final MarkerSet set;

  public GraveService(MarkerIcon icon, MarkerSet set) {
    this.icon = icon;
    this.set = set;
  }

  public MarkerIcon getIcon() {
    return icon;
  }

  public MarkerSet getMarkerSet() {
    return set;
  }

  public GraveService(MarkerAPI markerService, String iconName, String setId, String setName) {
    this(
        markerService.getMarkerIcon(iconName),
        Optional.ofNullable(markerService.getMarkerSet(setId))
            .orElse(markerService.createMarkerSet(setId, setName, null, false)));
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
