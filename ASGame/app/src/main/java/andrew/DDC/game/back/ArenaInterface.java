package andrew.DDC.game.back;

import java.util.ArrayList;

import andrew.DDC.game.back.creeps.Creep;
import andrew.DDC.game.back.towers.TowerTypes;

public interface ArenaInterface {
    int getSize();
    ArrayList<Creep> getTargets();
    ArrayList<? extends GameObjectInterface> getObstacles();
    void setSelected(TowerTypes tt);
    void addProjectile(GameObjectInterface proj);
}
