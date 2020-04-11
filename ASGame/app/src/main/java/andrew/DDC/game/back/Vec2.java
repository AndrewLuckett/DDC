package andrew.DDC.game.back;


/**
 * Immutable 2d vector type.
 * float x, float y.
 *
 * @author Andrew Luckett
 *
 */
public class Vec2 { // Immutable
    // Yes I know there are several similar types to this spread throughout default lib
    private float x;
    private float y;

    /**
     * Defaults to 0 , 0
     */
    public Vec2() {
        x = 0;
        y = 0;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns true if both Vec2's are equal in value
     *
     * @param other
     *            the other Vec2 object
     * @return bool this == other logically.
     */
    public boolean equals(Vec2 other) {
        return x == other.getX() && y == other.getY();
    }

    /**
     * Returns the distance to the specified Vec2
     *
     * @param other
     *            the other Vec2 object
     * @return distance from this Vec2 to other Vec2
     */
    public float distTo(Vec2 other) { // Pythag
        float dx = x - other.getX();
        float dy = y - other.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns the angle to the specified Vec2 in Radians
     * Polar coords
     *
     * @param other
     *            the other Vec2 object
     * @return angle from this Vec2 to other Vec2
     */
    public float angleTo(Vec2 other) {
        return (float) Math.atan2(other.getY() - y, other.getX() - x);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        return "Vec2( " + x + " , " + y + " )";
    }

}
