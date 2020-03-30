package andrew.DDC.back;

public class Vec2 {
    float x;
    float y;

    public Vec2(){
        this(0,0);
    }
    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
