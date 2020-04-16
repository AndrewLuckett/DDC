package andrew.DDC.game.back;

import android.graphics.Point;
import android.os.Bundle;

import androidx.core.util.Consumer;

import andrew.DDC.core.Drawable;
import andrew.DDC.core.GameInterface;

public class TdGame implements GameInterface {
    public TdGame(int size){

    }

    @Override
    public void update(float dtms) {

    }

    @Override
    public Bundle handleInput(Point p) {
        return null;
    }

    @Override
    public void forEachDrawable(Consumer<Drawable> d) {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getCoins() {
        return 0;
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
