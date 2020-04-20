package andrew.DDC.core;

import android.graphics.Point;
import android.os.Bundle;

import androidx.core.util.Consumer;

public interface GameInterface {
    void update(float dtms);
    Bundle handleInput(Point p);
    void forEachDrawable(Consumer<Drawable> d);
    int getScore();
    int getCoins();
    boolean isRunning();

    int getWave();
}
