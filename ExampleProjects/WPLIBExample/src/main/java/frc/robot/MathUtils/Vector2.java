package frc.robot.MathUtils;

/**
 * Represents a 2D point in space with basic vector operations.
 */
public class Vector2 {
    private double x;
    private double y;

    /**
     * Default constructor initializes the vector to the origin (0, 0).
     */
    public Vector2() {
        this(0.0, 0.0);
    }

    /**
     * Constructs a Vector2 with specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor creates a new Vector2 from an existing one.
     *
     * @param otherVector The vector to copy.
     */
    public Vector2(Vector2 otherVector) {
        this(otherVector.x, otherVector.y);
    }

    /**
     * Adds another vector to this vector.
     *
     * @param otherVector The vector to add.
     * @return A new Vector2 representing the result of the addition.
     */
    public Vector2 add(Vector2 otherVector) {
        return new Vector2(this.x + otherVector.x, this.y + otherVector.y);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param otherVector The vector to subtract.
     * @return A new Vector2 representing the result of the subtraction.
     */
    public Vector2 subtract(Vector2 otherVector) {
        return new Vector2(this.x - otherVector.x, this.y - otherVector.y);
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param scalar The scalar value to multiply by.
     * @return A new Vector2 representing the scaled vector.
     */
    public Vector2 multiply(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    /**
     * Multiplies this vector element-wise with another vector.
     *
     * @param otherVector The vector to multiply with.
     * @return A new Vector2 representing the element-wise multiplication.
     */
    public Vector2 multiply(Vector2 otherVector) {
        return new Vector2(this.x * otherVector.x, this.y * otherVector.y);
    }

    /**
     * Calculates the Euclidean distance between this vector and another.
     *
     * @param otherVector The vector to measure the distance to.
     * @return The Euclidean distance.
     */
    public double distance(Vector2 otherVector) {
        double deltaX = this.x - otherVector.x;
        double deltaY = this.y - otherVector.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Performs linear interpolation (lerp) between two vectors.
     *
     * @param pointOne The starting vector.
     * @param pointTwo The ending vector.
     * @param t The interpolation factor (0 ≤ t ≤ 1).
     * @return The interpolated vector.
     */
    public static Vector2 lerp(Vector2 pointOne, Vector2 pointTwo, double t) {
        return pointOne.multiply(1.0 - t).add(pointTwo.multiply(t));
    }

    /**
     * Checks if this vector is equal to another vector.
     *
     * @param otherVector The vector to compare to.
     * @return True if both vectors are equal, false otherwise.
     */
    public boolean equals(Vector2 otherVector) {
        return this.x == otherVector.x && this.y == otherVector.y;
    }

    /**
     * Clamps this vector's components to the given maximum values.
     *
     * @param maxX The maximum x value.
     * @param maxY The maximum y value.
     * @return A new clamped Vector2.
     */
    public Vector2 clamp(double maxX, double maxY) {
        double clampedX = Math.max(0, Math.min(this.x, maxX));
        double clampedY = Math.max(0, Math.min(this.y, maxY));
        return new Vector2(clampedX, clampedY);
    }

    /**
     * Calculates the magnitude (length) of the vector.
     *
     * @return The magnitude.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes the vector to have a magnitude of 1.
     *
     * @return A new normalized Vector2.
     */
    public Vector2 normalize() {
        double mag = magnitude();
        return mag == 0 ? new Vector2(0, 0) : new Vector2(x / mag, y / mag);
    }

    /**
     * Gets the x value of the vector.
     *
     * @return The x value.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y value of the vector.
     *
     * @return The y value.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x value of the vector.
     *
     * @param x The new x value.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y value of the vector.
     *
     * @param y The new y value.
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
