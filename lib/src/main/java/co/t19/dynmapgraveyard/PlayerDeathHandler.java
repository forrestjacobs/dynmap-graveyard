package co.t19.dynmapgraveyard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathHandler implements Listener {

  private final GraveService graveService;

  public PlayerDeathHandler(GraveService graveService) {
    this.graveService = graveService;
  }

  @EventHandler
  public void handlePlayerDeath(PlayerDeathEvent event) {
    graveService.updateGrave(event.getEntity());
  }

}
