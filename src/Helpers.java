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



}
