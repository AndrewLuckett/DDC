package andrew.DDC.mid;

import andrew.DDC.back.Vec2;

public class Drawable {
    private int GId;
    private float rotation;
    private float Xoff;
    private float Yoff;
    private boolean tower;

    public Drawable(int GId, float rotation, float Xoff, float Yoff, boolean base){
        this.GId = GId;
        this.rotation = rotation;
        this.Xoff = Xoff;
        this.Yoff = Yoff;
        this.tower = base;
    }

    public Drawable(int GId, float rotation, float Xoff, float Yoff){
        this(GId,rotation,Xoff,Yoff,false);
    }

    public Drawable(int GId, float rotation, Vec2 pos, boolean base){
        this(GId, rotation, pos.getX(), pos.getY(), base);
    }

    public Drawable(int GId, float rotation, Vec2 pos){
        this(GId, rotation, pos.getX(), pos.getY(), false);
    }

    public int getGId() {
        return GId;
    }

    public float getRotation() {
        return rotation;
    }

    public float getXoff() {
        return Xoff;
    }

    public float getYoff() {
        return Yoff;
    }

    public boolean isTower() {
        return tower;
    }
}
