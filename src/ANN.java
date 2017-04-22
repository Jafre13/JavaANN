import com.sun.javaws.exceptions.ErrorCodeResponseException;
import transfer.ITransfer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by slapocolypse on 4/18/17.
 */

public class ANN {
    int[] l_size;
    double[][] layers;
    ArrayList<double[][]> weights;
    double[][] errors;
    double[][] deltas;
    ArrayList<ITransfer> transfers;
    double[] input;
    int[] y;
    double alpha;


    public ANN(int[] sizes, ArrayList<ITransfer> transfers, double alpha) {
        this.weights = new ArrayList<>();
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
        for (int i = 0; i < layers.length; i++) {
            double[][] tmp = new double[l_size[i]][l_size[i + 1]];
            for (int j = 0; j < tmp.length; j++) {
                for (int k = 0; k < tmp[0].length; k++) {
                    tmp[j][k] = 2 * Math.random() - 1;
                }
            }
            weights.add(tmp);
            //System.out.println(weights[i][0]);
        }
    }


    //taking the input through the layers
    public void runThroughLayers(double[] in) {
        input = in;
        //Run through all layers, using weights
        for (int i = 0; i < l_size.length - 1; i++) {
            //Temporary array with layer results
            double[] t = new double[l_size[i + 1]];
            for (int k = 0; k < weights.get(i)[0].length; k++) {
                double sum = 0;
                for (int j = 0; j < input.length; j++) {
                    sum += weights.get(i)[j][k] * input[j];
                }
                t[k] = transfers.get(i).tranfer(sum);

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
            tmpDelta = new double[layers[i].length];
            tmpError = new double[layers[i].length];


            double[][] tmpLayer = Helpers.transpose(weights.get(i));
            int count = 0;
            for (double[] t : tmpLayer) {
                double sum = 0;
                for (double d : t) {
                    for (double s : deltas[i + 1]) {
                        sum += d * s;
                    }
                }
                tmpError[count] = sum;
                count++;
            }
            count = 0;
            for (double d : tmpError) {
                double sum = 0;
                for (double s : layers[i]) {
                    sum += d * (transfers.get(i).derivative(s));
                }
                tmpDelta[count] = sum;
                count++;
            }


            errors[i] = tmpError;
            deltas[i] = tmpDelta;
        }

    }

    public void updateWeights() {
        for (int i = layers.length - 1; i >= 1; i--) {
            double[][] full = new double[l_size[i]][];
            double sum = 0;
            for (int l = 0; l < layers[i - 1].length; l++) {
                double[] temp = new double[l_size[i + 1]];
                for (int t = 0; t < deltas[i + 1].length; t++) {
                    sum = weights.get(i)[l][t] + (alpha * (layers[i - 1][l] * deltas[i + 1][t]));
                    temp[t] = sum;
                }
                full[l] = temp;


            }

            weights.set(i, full);
        }
        double[][] full = new double[l_size[0]][];
        double sum = 0;
        for (int l = 0; l < l_size[0]; l++) {
            double[] temp = new double[l_size[1]];
            for (int t = 0; t < deltas[0].length; t++) {
                sum =weights.get(0)[l][t] + (alpha * (layers[0][l] * deltas[0][t]));
                temp[t] = sum;
            }
            full[l] = temp;

        }
        weights.set(0,full);
    }



    public void run() {
        setupWeights();
        input = new double[]{1, 0, 0};

        for (int j = 0; j < 1000; j++) {
            runThroughLayers(input);
            calcErrorAndDelta(y[0]);
            updateWeights();
            System.out.println("RESULT :" + layers[layers.length - 1][0]);
        }
//        runThroughLayers(input);
//        calcErrorAndDelta(y[0]);
//        updateWeights();
//        System.out.println(Arrays.deepToString(errors));
//        System.out.println(Arrays.deepToString(deltas));


    }
}
