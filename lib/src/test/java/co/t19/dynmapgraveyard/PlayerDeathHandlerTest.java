package co.t19.dynmapgraveyard;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerDeathHandlerTest {

  @Mock
  private GraveService graveService;
  @Mock
  private PlayerDeathEvent event;
  @Mock
  private Player player;

  @Test
  void itUpdatesGraveOnPlayerDeath() {
    when(event.getEntity()).thenReturn(player);
    final var handler = new PlayerDeathHandler(graveService);
    handler.handlePlayerDeath(event);
    verify(graveService).updateGrave(player);
  }

}
