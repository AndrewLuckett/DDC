package andrew.DDC.game.back.creeps;

import andrew.DDC.R;
import andrew.DDC.core.Drawable;
import andrew.DDC.game.back.ArenaInterface;
import andrew.DDC.game.back.Vec2;

public class FastCreep extends BasicCreep {
    public FastCreep(ArenaInterface container, Vec2 pos, float multiplier) {
        super(container, pos, multiplier);
        velo *= 1.5f;
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable(R.drawable.cr_fast, angle * TODEGREES + 90, pos);
    }
}
