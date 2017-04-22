package transfer;

/**
 * Created by slapocolypse on 4/19/17.
 */
public class Sigmoid implements ITransfer {
    @Override
    public double tranfer(double x) {

        return (1 / (1 + Math.exp(x)));
    }

    @Override
    public double derivative(double x) {

        return x*(1-x);
    }
}
