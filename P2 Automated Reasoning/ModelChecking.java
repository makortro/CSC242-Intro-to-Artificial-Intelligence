/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelChecking {
    public void checkModel(KB kb, Clause c) {
        Boolean result = ttEntails(kb, c);
        if(result) {
            System.out.println("Response from ttEntails():    TRUE - KB ENTAILS the query!");
        } else {
            System.out.println("Response from ttEntails():    FALSE - KB DOES NOT entail the query!");   
        }
    }

    /*
     *  function TT-ENTAILS?(KB, α) returns true or false
        inputs: KB , the knowledge base, a sentence in propositional logic
                α, the query, a sentence in propositional logic
        symbols ← a list of the proposition symbols in KB and α
        return TT-CHECK-ALL(KB,α,symbols,{})
     */

    public Boolean ttEntails(KB kb, Clause alpha) {
        //System.out.println("in ttEntails()");
        List<Literal> symbols = new ArrayList<Literal>();
        // Get symbols from kb
        for(Clause c : kb.getClauses()) {
            symbols.addAll(c.getLiterals());
            /* for(Literal l : c.getLiterals()) {
                if(!symbols.contains(l)) {
                    symbols.add(l);
                }
            } */
        }
        // Get symbols from query -- only add if doesn't already exist in kb
        for(Literal l : alpha.getLiterals()) {
            if(!symbols.contains(l)) {
                symbols.add(l);
            }
        }
        return ttCheckAll(kb, alpha, symbols, new HashSet<Literal>());
    }

    /*
     *  function TT-CHECK-ALL(KB,α,symbols,model) returns true or false
            if EMPTY?(symbols) then
                if PL-TRUE?(KB,model) then return PL-TRUE?(α,model)
                else return true // when KB is false, always return true
            else do
                P ← FIRST(symbols)
                rest ← REST(symbols)
                return (TT-CHECK-ALL(KB,α,rest,model ∪ {P = true}) and TT-CHECK-ALL(KB,α,rest,model ∪ {P = false }))
     */
    public Boolean ttCheckAll(KB kb, Clause alpha, List<Literal> symbols, Set<Literal> model) {
        //System.out.println("in ttCheckAll");
        //System.out.println("printing model before checking symbols.isEmpty()... " + model.toString());
        if(symbols.isEmpty()) {
            //System.out.println("symbols.isEmpty()");
            if(plTrue(kb, model)) {
                //System.out.println("plTrue(kb,model) TRUE");
                //System.out.println("printing model: " + model.toString());
                return plTrue(alpha, model);
            } else {
                //System.out.println("plTrue(kb,model) FALSE");
                //System.out.println("printing model: " + model.toString());
                return true; //when KB is false, always return true
            }
        } else {
            /*
            System.out.println("in else ttCheckAll");
            //instances for true
            Literal pT = symbols.iterator().next();
            Set<Literal> restT = new HashSet<>(symbols);
            restT.remove(pT);
            Map<Literal, Boolean> modelTrue = new HashMap<>(model);
            modelTrue.put(pT, true);
            //instances for false
            Literal pF = sCopy.iterator().next();
            Set<Literal> restF = new HashSet<>(sCopy);
            restF.remove(pF);
            Map<Literal, Boolean> modelFalse = new HashMap<>(model);
            modelFalse.put(pF, false); 
            return (ttCheckAll(kb, alpha, restT, modelTrue) && ttCheckAll(kb, alpha, restF, modelFalse));
            */

            Literal p = symbols.get(0);
            //System.out.println("p = " + p.toString());
            symbols.remove(p);
            List<Literal> rest = symbols;
            //System.out.println("rest = " + rest.toString());
            //symbols.subList(1, symbols.size());
            //rest.remove(p);
            return (ttCheckAll(kb, alpha, rest, extendModel(p, true, model)) && ttCheckAll(kb, alpha, rest, extendModel(p, false, model)));
        }
    }

    /* Generates new set of models that extend given model by setting truth values. */
    public Set<Literal> extendModel(Literal l, Boolean b, Set<Literal> m) {
        Set<Literal> newModel = new HashSet<Literal>(m);
        int i = l.getSymbol();
        newModel.add(new Literal(i, b));
        return newModel;
    }

    /**
     * Iterates through each literal in the clause and checks if its symbol has a corresponding truth
     * value in the model. If the literal exists in the model, the function returns true if the truth
     * values match, otherwise returns false.
     */
    public Boolean plTrue(Clause clause, Set<Literal> model) {
        for(Literal l : clause.getLiterals()) {
            if(l.isTrue(model)) {
                return true;
            }
        }
        return false;
        /* for(Literal l : clause.getLiterals()) {
            if(model.containsKey(l) && model.get(l) == true) {
                return true;
            }
        }
        return false; */
        /* Boolean hasTrueLiteral = false;
        for(Literal l : clause.getLiterals()) {
            String symbol = l.getSymbol();
            Boolean truthVal = l.isNegated() ? false : true;
            if(model.containsKey(l)) {
                hasTrueLiteral = true;
                break;
            }
        }
        return hasTrueLiteral; */
        /* for(Literal l : clause.getLiterals()) {
            String symbol = l.getSymbol();
            Boolean truthVal = l.isNegated();
            if(model.containsKey(symbol)) {
                if(model.get(symbol) == truthVal) {
                    return true;
                }
            }
            /* if(model.containsKey(l)) {
                if(model.get(l) == truthVal) {
                    return true;
                }
            }
        }
        return false; */
    }

    /* Same as plTrue above, but intakes KB instead of clause; iterates through clauses in KB then literals in clauses. */
    public Boolean plTrue(KB kb, Set<Literal> model) {
        for(Clause c: kb.getClauses()) {
            for(Literal l : c.getLiterals()) {
                if(l.isTrue(model)) {
                    return true;
                }
            }
        }
        return false;
        
        /* for(Clause c : kb.getClauses()) {
            Boolean foundTrue = false;
            for(Literal l : c.getLiterals()) {
                if(model.containsKey(l) && model.get(l) == true) {
                    foundTrue = true;
                    break;
                }
            }
            if(!foundTrue) {
                return false;
            }
        }
        return true; */
        /* Boolean hasTrueLiteral = false;
        for(Clause c : kb.getClauses()) {
            for(Literal l : c.getLiterals()) {
                String symbol = l.getSymbol();
                Boolean truthVal = l.isNegated() ? false : true;
                if(model.containsKey(l)) {
                    hasTrueLiteral = true;
                    break;
                }
            }
        }
        return hasTrueLiteral; */
        
        /* for(Clause c : kb.getClauses()) {
            for(Literal l : c.getLiterals()) {
                String symbol = l.getSymbol();
                Boolean truthVal = l.isNegated();
                if(model.containsKey(symbol)) {
                    if(model.get(symbol) == truthVal) {
                        return true;
                    }
                }
                /* if(model.containsKey(l)) {
                    if(model.get(l) == truthVal) {
                        return true;
                    }
                }
            }
        }
        return false; */
    }
}