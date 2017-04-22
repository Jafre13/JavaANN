/**
 * Created by slapocolypse on 4/18/17.
 */
public class Helpers {

    public static double dot(double[] a, double[] b){

        int n = a.length;

        double sum = 0;
        for (int i = 0; i<n;i++){
            sum += a[i]*b[i];
        }
        return sum;
    }

    public static double[][] transpose (double[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
            return array;

        int width = array.length;
        int height = array[0].length;

        double[][] array_new = new double[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }



}
