package andrew.DDC.game.back;

import andrew.DDC.core.Drawable;

public interface GameObjectInterface {
    void update(float dtms);
    Drawable getDrawable();
    boolean isExpired();
}
