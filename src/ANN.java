import java.util.Arrays;

/**
 * Created by slapocolypse on 4/18/17.
 */

public class ANN {
    int[] l_size;
    double[][] layers;
    double[][] weights;
    double[] y;
    double alpha;



    public ANN(int[] sizes) {
        weights = new double[sizes.length - 1][];
        layers = new double[sizes.length- 1][];
        l_size = sizes;
        y = new double[]{0, 1};
    }


    //Random initialization of weights
    public void setupWeights() {
        for (int i = 0; i < weights.length; i++) {
            double[] tmp = new double[l_size[i] * l_size[i + 1]];
            for (int j = 0; j < tmp.length; j++) {
                tmp[j] = 2 * Math.random() - 1;
            }
            weights[i] = tmp;
            //System.out.println(weights[i][0]);
        }
        //System.out.println(Arrays.deepToString(weights));
    }


    //taking the input through the layers
    public void runThroughLayers(double[] input) {

        //Run through all layers, using weights
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Layer " + (i + 1));
            //Temporary array with layer results
            double[] t = new double[l_size[i + 1]];

            //used for weights vs inputs
            int n = 0;

            //calculate first hidden/output layer
            for (int j = 0; j < l_size[i + 1]; j++) {
                System.out.println("neuron " + (j + 1));
                double sum = 0;
                //multiply each neron with it's weight and sum
                for (int g = 0; g < l_size[i]; g++) {
                    System.out.println("Summing");
                    System.out.println("weight number " + ((g * l_size[i + 1]) + n));
                    sum += weights[i][(g * l_size[i + 1]) + n] * input[g];
                }
                System.out.println("Sum " + sigmoid(sum));
                System.out.println(t.length + " " + i );
                t[j] = sigmoid(sum);

                n++;

            }
        layers[i] = t;
        input = layers[i];
        }

        System.out.println(Arrays.deepToString(layers));
    }

    public double sigmoid(double x) {
        return (1 / (1 + Math.exp(x)));
    }

    public double getDelta() {
        return 0;
    }

    public void run() {
        setupWeights();
        double[] i = new double[]{1, 2, 3,4,5,6,7,8,9,10};
        runThroughLayers(i);

    }
}
