import transfer.ITransfer;
import transfer.Sigmoid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by slapocolypse on 4/18/17.
 */
public class baaaaah {
    double[] inout;
    ArrayList<Double> hiddens = new ArrayList<>();
    ArrayList<String> transfers = new ArrayList<>();

    public static void main(String[] args) {

        int[] sizes = new int[]{3,4,1};
        ArrayList<ITransfer> transfers = new ArrayList<>();
        transfers.add(new Sigmoid());
        transfers.add(new Sigmoid());



        double alpha = 0.05;
        ANN a = new ANN(sizes, transfers,alpha);
        a.run();

    }
}

