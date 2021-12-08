/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package co.t19.dynmapgraveyard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

@ExtendWith(MockitoExtension.class)
class GraveServiceTest {

  static final String PLAYER_ID = "e678b909-58ce-4fff-aa00-bcb50ab95a51";
  static final String PLAYER_LIST_NAME = "player";
  static final String WORLD_NAME = "world";

  @Mock
  private MarkerAPI markerService;
  @Mock
  private MarkerIcon markerIcon;
  @Mock
  private MarkerSet markerSet;
  @Mock
  private Marker marker;

  @Mock
  private Player player;
  private Location playerLocation;
  @Mock
  private World playerWorld;

  void setupPlayer() {
    when(playerWorld.getName()).thenReturn(WORLD_NAME);
    playerLocation = new Location(playerWorld, 50, 40, 30);
    when(player.getLocation()).thenReturn(playerLocation);
    when(player.getUniqueId()).thenReturn(UUID.fromString(PLAYER_ID));
  }

  @Test
  void itCreatesNewSetIfNotAvailableOnConstruction() {
    when(markerService.getMarkerIcon("icon")).thenReturn(markerIcon);
    when(markerService.getMarkerSet("set")).thenReturn(null);
    when(markerService.createMarkerSet("set", "set name", null, false)).thenReturn(markerSet);
    final var service = new GraveService(markerService, "icon", "set", "set name");
    assertEquals(markerIcon, service.getIcon());
    assertEquals(markerSet, service.getMarkerSet());
  }

  @Test
  void itReusesSetIfAvailableOnConstruction() {
    when(markerService.getMarkerIcon("icon")).thenReturn(markerIcon);
    when(markerService.getMarkerSet("set")).thenReturn(markerSet);
    final var service = new GraveService(markerService, "icon", "set", "set name");
    assertEquals(markerIcon, service.getIcon());
    assertEquals(markerSet, service.getMarkerSet());
  }

  @Test
  void itUsesIconAndSetIfDirectlySupplied() {
    final var service = new GraveService(markerIcon, markerSet);
    assertEquals(markerIcon, service.getIcon());
    assertEquals(markerSet, service.getMarkerSet());
  }

  @Test
  void itCreatesNewMarkerOnFirstDeath() {
    setupPlayer();
    when(player.getPlayerListName()).thenReturn(PLAYER_LIST_NAME);
    when(markerSet.findMarker(PLAYER_ID)).thenReturn(null);

    final var service = new GraveService(markerIcon, markerSet);
    service.updateGrave(player);
    verify(markerSet).createMarker(PLAYER_ID, "player's grave", WORLD_NAME, 50, 40, 30, markerIcon, false);
  }

  @Test
  void itUpdatesMarkerOnSubsequentDeath() {
    setupPlayer();
    when(markerSet.findMarker(PLAYER_ID)).thenReturn(marker);

    final var service = new GraveService(markerIcon, markerSet);
    service.updateGrave(player);
    verify(marker).setLocation(WORLD_NAME, 50, 40, 30);
  }

}
