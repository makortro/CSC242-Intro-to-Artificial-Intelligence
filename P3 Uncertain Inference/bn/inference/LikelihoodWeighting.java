package bn.inference;

import bn.core.*;
import bn.base.Distribution;

/*
 * LIKELIHOOD-WEIGHTING (see Figure 14.15) fixes the values for the evidence vari- ables E
 * and samples only the nonevidence variables. This guarantees that each event gener- ated
 * is consistent with the evidence. Not all events are equal, however. Before tallying the
 * counts in the distribution for the query variable, each event is weighted by the likelihood
 * that the event accords to the evidence, as measured by the product of the conditional probabilities
 * for each evidence variable, given its parents. Intuitively, events in which the actual evidence
 * appears unlikely should be given less weight.
 */

public class LikelihoodWeighting {
    public static class WeightedAssignment {
        Assignment event;
        double weight;

        WeightedAssignment(Assignment e, double w) {
            this.event = e;
            this.weight = w;
        }
    }
    
    // function WEIGHTED-SAMPLE(bn,e) returns an event and a weight
    public static WeightedAssignment weightedSample(BayesianNetwork bn, Assignment e) {
        // w ← 1; x ← an event with n elements initialized from e
        double w = 1.0;
        Assignment x = e.copy();
        //for each variable Xi in X1,...,Xn do
        for(RandomVariable Xi : bn.getVariablesSortedTopologically()) {
            //if Xi is an evidence variable with value xi in e
            if(e.containsKey(Xi)) {
                //then w←w × P(Xi = xi | parents(Xi))
                x.put(Xi, e.get(Xi));
                w *= bn.getProbability(Xi, x);
            } else {
                //else x[i] ← a random sample from P(Xi | parents(Xi ))
                x.put(Xi, randSample(bn, Xi, x));
            }       
        }
        //return x, w
        return new WeightedAssignment(x, w);
    }

    // function LIKELIHOOD-WEIGHTING(X, e, bn, N) returns an estimate of P(X|e)
    //      inputs:
    //          X -> the query variable
    //          e -> observed values for variables E
    //          bn -> a Bayesian network specifying joint distribution P(X1, . . . , Xn)
    //          N -> the total number of samples to be generated
    public Distribution likelihoodWeighting(RandomVariable X, Assignment e, BayesianNetwork bn, int N) {
        //local variables: W, a vector of weighted counts for each value of X (initially zero)
        Distribution W = new Distribution(X);
        for(Value val : X.getRange()) {
            W.set(val, 0.0);
        }
        // for j = 1 to N do
        for(int i = 0; i < N; i++) {
            // x, w ← WEIGHTED-SAMPLE(bn, e)
            WeightedAssignment sample = weightedSample(bn, e);
            Assignment se = sample.event;
            double sw = sample.weight;
            // W[x]←W[x]+w where x is the value of X in x
            W.set(se.get(X), W.get(se.get(X)) + sw);
        }
        //return NORMALIZE(W)
        W.normalize();
        return W;
    }

    public static Value randSample(BayesianNetwork bn, RandomVariable X, Assignment e) {
        double rand = Math.random();
        double comp = 0.0;

        for(Value val : X.getRange()) {
            Assignment copy = e.copy();
            copy.put(X, val);
            comp += bn.getProbability(X, copy);
            if(rand <= comp)
                return val;
        }
        return null;
    }
}