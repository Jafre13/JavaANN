import java.util.ArrayList;

/**
 * Created by slapocolypse on 4/18/17.
 */
public class baaaaah {
    double[] inout;
    ArrayList<Double> hiddens = new ArrayList<>();
    ArrayList<String> transfers = new ArrayList<>();

    public static void main(String[] args){

        int[] sizes = new int[]{10,5,4,2,1};
        ANN a = new ANN(sizes);
        a.run();

    }

    public void addLayer(double d,String s){
        hiddens.add(d);
        hiddens.toArray()
        transfers.add(s)
    }

    public void generateInlayer(){
        inout[0] = 3
    }
    public void generateOutlayer(){
        inout[1] = 2
    }
    public void generateHiddenlayer(){
        hidddens.add()
    }
}
