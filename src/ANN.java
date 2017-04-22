import com.sun.javaws.exceptions.ErrorCodeResponseException;
import transfer.ITransfer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by slapocolypse on 4/18/17.
 */

public class ANN {
    int[] l_size;
    double[][] layers;
    double[][] weights;
    double[][] errors;
    double[][] deltas;
    ArrayList<ITransfer> transfers;
    double[] input;
    int[] y;
    double alpha;


    public ANN(int[] sizes, ArrayList<ITransfer> transfers, double alpha) {
        this.weights = new double[sizes.length - 1][];
        this.layers = new double[sizes.length - 1][];
        this.deltas = new double[sizes.length][];
        this.transfers = new ArrayList<>(transfers);
        this.errors = new double[sizes.length][];
        this.l_size = sizes;
        this.y = new int[]{1};
        this.alpha = alpha;
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
        for (int i = 0; i < l_size.length-1; i++) {
            //Temporary array with layer results
            int n = 0;
            double[] t = new double[l_size[i+1]];
            //calculate first hidden/output layer
            for (int j = 0; j < l_size[i+1]; j++) {
                double sum = 0;
                //multiply each neron with it's weight and sum
                for (int g = 0; g < l_size[i]; g++) {
                    sum += weights[i][n] * input[g];
                    n++;
                }
                t[j] = transfers.get(i).tranfer(sum);

            }
            layers[i] = t;
            input = layers[i];
        }

    }

    public void calcErrorAndDelta(int y) {
        //TODO ALL TEMPORARY, NEEDS FIXING
        //This is for the output layer
        double[] tmpError;
        double[] tmpDelta;
        tmpError = new double[]{layers[layers.length - 1][0] - y};
        System.out.println(" ERROR" + (layers[layers.length - 1][0] - y));
        errors[errors.length - 1] = tmpError;
        tmpDelta = new double[]{errors[errors.length - 1][0] * transfers.get(transfers.size() - 1).derivative(layers[layers.length - 1][0])};
        deltas[deltas.length - 1] = tmpDelta;
        //Now for the rest of the layers.
        for (int i = layers.length - 1; i >= 0; i--) {
            double sum = 0;
            tmpDelta = new double[weights[i].length];
            tmpError = new double[weights[i].length];
            int n = 0;
            for (int j = 0; j < l_size[i]; j++) {
                for (int k = 0; k < l_size[i + 1]; k++) {
                    tmpError[n] = layers[i][k] * weights[i][n];
                    tmpDelta[n] = tmpError[n] * transfers.get(i).derivative(layers[i][k]);
                    n++;
                }
            }
            errors[i] = tmpError;
            deltas[i] = tmpDelta;
        }

    }

    public void updateWeights() {
        for (int i = layers.length - 1; i >= 1; i--) {
            int n = 0;
            for (int j = 0; j < l_size[i+1]; j++) {
                for (int k = 0; k < l_size[i]; k++) {
                    weights[i][n] -= alpha * (layers[i-1][n] * deltas[i][n] );
                    System.out.println(weights[i][n]);
                    n++;
                }

            }
        }
        int n = 0;
        for (int j = 0; j < l_size[0]; j++) {
            for (int k = 0; k < l_size[1]; k++) {
                weights[0][n] -= alpha * (input[k] * deltas[0][n] );
                n++;
            }

        }


    }


    public void run() {
        setupWeights();
        input = new double[]{1,0,2,3,4};

        for (int j = 0; j < 1000; j++) {
            runThroughLayers(input);
            calcErrorAndDelta(y[0]);
            updateWeights();
            System.out.println("RESULT :" +  layers[layers.length - 1][0]);
        }
//        runThroughLayers(input);
//        calcErrorAndDelta(y[0]);
//        updateWeights();
//        System.out.println(Arrays.deepToString(errors));
//        System.out.println(Arrays.deepToString(deltas));


    }
}
