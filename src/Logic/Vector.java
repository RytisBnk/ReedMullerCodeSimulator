package Logic;

public class Vector extends Matrix {
    public Vector(int width, int[] coords) {
        super(1, width);
        this.setRow(0, coords);
    }


}
