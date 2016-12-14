package r12;

public class heatMapObject {

    private int[] array = new int[4];

    public heatMapObject(int[] d) {
        array = d;
    }

    public int[] getArray() {
        return array;
    }

    public void soutArray() {
        for (int i : array) {
            System.out.println(i);
        }
    }
}
