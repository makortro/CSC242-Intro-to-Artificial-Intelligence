package bn.inference;
import java.util.List;

import bn.core.BayesianNetwork;
import bn.core.RandomVariable;
import bn.core.Value;
import bn.core.Assignment;
import bn.base.Distribution;

public class ExactInference {
    // function ENUMERATE-ALL(vars, e) returns a real number
    public double enumerateAll(BayesianNetwork bn, Assignment e, int i) {
        List<RandomVariable> list = bn.getVariablesSortedTopologically();
        //if EMPTY?(vars) then return 1.0
        if(i >= list.size())
            return 1.0;
        //Y ← FIRST(vars)
        RandomVariable Y = list.get(i);
        //if Y has value y in e
        if(e.containsKey(Y)) {
            //then return P (y | parents(Y )) × ENUMERATE-ALL(REST(vars), e)
            // e = e.copy();
            return bn.getProbability(Y, e) * enumerateAll(bn, e, i+1);
        } 
        else {
            //else return sum(y) P(y|parents(Y)) × ENUMERATE-ALL(REST(vars),ey)
            double sum = 0;
            for(Value val : Y.getRange()) {
                //where ey is e extended with Y = y
                e.put(Y, val);
                Assignment ey = e.copy();
                double probability = bn.getProbability(Y, ey);
                double enum2 = enumerateAll(bn, ey, i+1);
                sum += probability*enum2;
            }
            return sum;
        }
    }

    // function ENUMERATION-ASK(X , e, bn) returns a distribution over X
    //     inputs:
    //         X -> the query variable
    //         e -> observed values for variables E
    //         bn -> a Bayes net with variables {X} ∪ E ∪ Y /* Y = hidden variables */
    public Distribution enumerationAsk(BayesianNetwork bn, RandomVariable X, Assignment e) {
        //Q(X) ← a distribution over X (initially empty)
        Distribution Q = new Distribution(X);
        //for each value xi of X do
        for(Value xi :X.getRange()) {  
            //Q(xi) ← ENUMERATE-ALL(bn.VARS, exi)
            //where exi is e extended with X = xi
			Assignment exi = e.copy();
			exi.put(X, xi);
			Q.put(xi, enumerateAll(bn, exi, 0));
		}
        //return NORMALIZE(Q(X))
		Q.normalize();
		return Q;
    }
}