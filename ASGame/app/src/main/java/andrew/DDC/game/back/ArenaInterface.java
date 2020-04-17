package andrew.DDC.game.back;

import java.util.ArrayList;

import andrew.DDC.game.back.creeps.Creep;

public interface ArenaInterface {
    ArrayList<Creep> getTargets();
    ArrayList<? extends GameObjectInterface> getObstacles();
}
