package bn.inference;

import java.util.ArrayList;
import java.util.List;
import bn.core.*;
import bn.base.Distribution;
import bn.base.Assignment;
import bn.base.BayesianNetwork;

/*
 * Rejection sampling is a general method for producing samples from a hard-to-sample
 * distribution given an easy-to-sample distribution. In its simplest form, it can be
 * used to compute conditional probabilities—that is, to determine P(X |e).
 * The REJECTION-SAMPLING algorithm is shown in Figure 14.14. First, it generates
 * samples from the prior distribution specified by the network. Then, it rejects all
 * those that do not match the evidence. Finally, the estimate Pˆ(X = x | e) is obtained
 * by counting how often X = x occurs in the remaining samples.
 */

public class RejectionSampling {
    
    // function PRIOR-SAMPLE(bn) returns an event sampled from the prior specified by bn
    //     inputs:
    //         bn -> a Bayesian network specifying joint distribution P(X1, . . . , Xn)
    public Assignment priorSample(BayesianNetwork bn) {
        //x ← an event with n elements
        Assignment x = new Assignment();
        List<RandomVariable> topSort = bn.getVariablesSortedTopologically();
        //foreach variable Xi in X1,...,Xn do
        for(RandomVariable Xi : topSort) {
            ArrayList<Double> weightList = new ArrayList<>();
            for(Value val : Xi.getRange()) {
                x.put(Xi, val);
                weightList.add(bn.getNodeForVariable(Xi).getCPT().get(val, x));
            }
            //x[i] ← a random sample from P(Xi | parents(Xi))
            double rand = Math.random();
            double total = 0.0;
            int index = 0;
            for(Value val : Xi.getRange()) {
                total += weightList.get(index);
                if(rand <= total) {
                    x.put(Xi, val);
                    break;
                }
                index++;
            }
        }
        //return x
        return x;
    }

    // function REJECTION-SAMPLING(X, e, bn, N) returns an estimate of P(X|e)
    //      inputs:
    //          X -> the query variable
    //          e -> observed values for variables E
    //          bn -> a Bayesian network
    //          N -> the total number of samples to be generated
    public Distribution rejectionSampling(RandomVariable X, Assignment e, BayesianNetwork bn, int N) {
        //local variables: N, a vector of counts for each value of X (initially zero)
        Distribution count = new Distribution(X);
        for(Value v : X.getRange()) {
            count.set(v, 0);
        }
        //for j=1 to N do
        for(int i = 0; i < N; i++) {
            //x ← PRIOR-SAMPLE(bn)
            Assignment x = priorSample(bn);
            //if x is consistent with e then
            if(x.containsAll(e)) {
                //N[x]←N[x]+1 where x is the value of X in x
                count.put(x.get(X), (count.get(x.get(X)) + 1));
            }
        }
        //return NORMALIZE(N)
        count.normalize();
        return count;
    }
}