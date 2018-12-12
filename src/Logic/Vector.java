package Logic;

public class Vector extends Matrix {
    /**
     * Sukuria 1 eilutes matrica, su nurodytomis reiksmemis. Toks objektas kode traktuojamas kaip vektorius.
     * Is esmes nera reikalingas programos veikimui, galima naudoti ir Matrix klase
     * Sukurtas patogumo ir aiskumo delei
     * @param width - vektoriaus ilgis
     * @param coords - vektoriaus koordinates
     */
    public Vector(int width, int[] coords) {
        super(1, width);
        this.setRow(0, coords);
    }


}
