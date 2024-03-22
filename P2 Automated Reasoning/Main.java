/*
 * AUTHORS: John Moses, MaKayla Ortega Robinson
 */

public class Main {
    public static void main(String[] args) {
        // Part 1 - Representing Clauses
        part1();
        // Part 2 - Model Checking
        part2();
    }

    public static void part1() {
        KB kb = new KB();
        RepresentingClauses rc = new RepresentingClauses();

        System.out.println("--------------------------------------");
        System.out.println("--------------- PART 1 ---------------");
        System.out.println("-------- Representing Clauses --------");
        System.out.println("--------------------------------------");
        System.out.println("Clauses in CNF file: (x1 ∨ x3 ∨ ¬x4) ∧ (x4) ∧ (x2 ∨ ¬x3)");
        System.out.println("The clauses will be printed in the following [unordered] format: [[1, 3, -4], [-3, 2], [4]]"); 
        System.out.println("\n...Reading CNF file 'part1.cnf'...");
        System.out.println("...Adding literals to clauses to KB...");
        System.out.println();
        kb = rc.readCNF(kb, "part1.cnf");
        System.out.println("...Printing KB...");
        System.out.println("    " + kb.toString());
        System.out.println("--------------------------------------");
        System.out.println("------------- END PART 1 -------------");
        System.out.println("--------------------------------------\n");
    }

    public static void part2() {
        KB kb1 = new KB();
        RepresentingClauses rc = new RepresentingClauses();
        System.out.println("--------------------------------------");
        System.out.println("--------------- PART 2 ---------------");
        System.out.println("----------- Model Checking -----------");
        System.out.println("--------------------------------------");
        System.out.println("NOTE: All PL sentences were translated to CNF by hand\n");
        System.out.println("Problem 1: show that {P, P ⇒ Q} |= Q");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ SYMBOLS -> INTEGERS TABLE\n"
                            + "~ P = 1\n"
                            + "~ Q = 2\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ CNF:\n"
                            + "(1 ∧ 2)\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n...Reading CNF file 'part2_prob1.cnf'...");
        System.out.println("...Adding literals to clauses to KB...\n");
        kb1 = rc.readCNF(kb1, "part2_prob1.cnf");
        System.out.println("...Printing KB...");
        System.out.println("    " + kb1.getClauses().toString());
        System.out.println("...Adding query literal to clause...");
        Clause q1 = new Clause();
        q1.addLiteral(new Literal(2, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q1.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        ModelChecking mc = new ModelChecking();
        mc.checkModel(kb1, q1);

        KB kb2 = new KB();
        kb2.getClauses().clear();
        System.out.println("\n\nProblem 2: The Wumpus World Example");
        System.out.println("Background knowledge:");
        System.out.println("    R1: ¬P1,1 ≡ (¬P1,1) in CNF");
        System.out.println("    R2: B1,1 ⇔ P1,2 ∨ P2,1 ≡ (¬B1,1 ∨ P1,2 ∨ P2,1) ∧ (¬P1,2 ∨ B1,1) ∧ (¬P2,1 ∨ B1,1) in CNF");
        System.out.println("    R3: B2,1 ⇔ P1,1 ∨ P2,2 ∨ P3,1 ≡ (¬B2,1 ∨ P1,1 ∨ P2,2 ∨ P3,1) ∧ (¬P1,1 ∨ B2,1) ∧ (¬P2,2 ∨ B2,1) ∧ (¬P3,1 ∨ B2,1) in CNF");
        System.out.println("    R7: B1,2 ⇔ P1,1 ∨ P2,2 ∨ P1,3 ≡ (¬B1,2 ∨ P1,1 ∨ P2,2 ∨ P1,3) ∧ (¬P1,1 ∨ B1,2) ∧ (¬P2,2 ∨ B1,2) ∧ (¬P1,3 ∨ B1,2) in CNF");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ SYMBOLS -> INTEGERS TABLE\n"
                            + "~ B1,1 = 1\n"
                            + "~ B1,2 = 2\n"
                            + "~ B2,1 = 3\n"
                            + "~ P1,1 = 4\n"
                            + "~ P1,2 = 5\n"
                            + "~ P1,3 = 6\n"
                            + "~ P2,1 = 7\n"
                            + "~ P2,2 = 8\n"
                            + "~ P3,1 = 9\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ CNF:\n"
                            + "~ R1: (-4)\n"
                            + "~ R2: (-1 ∨ 5 ∨ 7) ∧ (-5 ∨ 1) ∧ (-7 ∨ 1)\n"
                            + "~ R3: (-3 ∨ 4 ∨ 8 ∨ 9) ∧ (-4 ∨ 3) ∧ (-8 ∨ 3) ∧ (-9 ∨ 3)\n"
                            + "~ R7: (-2 ∨ 4 ∨ 8 ∨ 6) ∧ (-4 ∨ 2) ∧ (-8 ∨ 2) ∧ (-6 ∨ 2)\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n...Reading CNF file 'part2_prob2.cnf'...");
        System.out.println("...Adding literals to clauses to KB...");
        kb2 = rc.readCNF(kb2, "part2_prob2.cnf");
        System.out.println("...Printing KB...");
        System.out.println("    " + kb2.getClauses().toString());

        System.out.println("2.2.1");
        System.out.println("\nThe agent starts at [1,1].");
        System.out.println("Add perception: R4: ¬B1,1 ≡ (¬B1,1) in CNF");
        System.out.println("...Adding literal to clause to KB...");
        Clause c1 = new Clause();
        //c1.addLiteral(new Literal("B1,1", true));
        c1.addLiteral(new Literal(1, true));
        kb2.addClause(c1);
        System.out.println("...Printing KB...");
        System.out.println("    " + kb2.toString());
        System.out.println("\nShow that this KB entails ¬P1,2 and ¬P2,1, but not P2,2 or ¬P2,2. "
                            + "The agent doesn’t know enough to conclude anything about P2,2.");
        // TO DO SEPARATE THESE BC CONJUNCTION
        System.out.println("Query (entails) ¬P1,2 and ¬P2,1 ≡ (¬P1,2) ∧ (¬P2,1) in CNF");
        System.out.println("...Adding query literals to clause...");
        Clause q2 = new Clause();
        q2.addLiteral(new Literal(5, true));
        q2.addLiteral(new Literal(7, true));

        System.out.println("...Printing query clause...");
        System.out.println("    " + q2.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb2, q2);
        System.out.println("\nQuery (does not entail) P2,2 or ¬P2,2 ≡ (P2,2 ∨ ¬P2,2) in CNF");
        System.out.println("...Adding query literals to clause...");
        Clause q3 = new Clause();
        q3.addLiteral(new Literal(8, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q3.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb2, q3);
        Clause q4 = new Clause();
        q4.addLiteral(new Literal(8, true));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q4.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb2, q4);

        System.out.println("\n2.2.2");
        System.out.println("The agent moves to [2,1]");
        System.out.println( "Add perception: R5: B2,1 ≡ (B2,1) in CNF");
        System.out.println("...Adding literal to clause to KB...");
        Clause c2 = new Clause();
        c2.addLiteral(new Literal(3, false));
        kb2.addClause(c2);
        System.out.println("...Printing KB...");
        System.out.println("     " + kb2.toString());
        System.out.println("\nShow that this KB entails P2,2 ∨ P3,1, but not P2,2, ¬P2,2, P3,1, or ¬P3,1. "
                            + "The agent knows more, but not enough.");
        System.out.println("Query (entails) P2,2 or P3,1 ≡ (P2,2) ∨ (P3,1) in CNF");
        System.out.println("...Adding query literals to clause...");
        Clause q5 = new Clause();
        q5.addLiteral(new Literal(8, false));
        q5.addLiteral(new Literal(9, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q5.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...");
        mc.checkModel(kb2, q5);

        System.out.println("\n2.2.3");
        System.out.println("The agent moves to [1,2]");
        System.out.println( "Add perception: R5: ¬B1,2 ≡ (¬B1,2) in CNF");
        System.out.println("...Adding literal to clause to KB...");
        Clause c3 = new Clause();
        c3.addLiteral(new Literal(2, true));
        kb2.addClause(c3);
        System.out.println("...Printing query clause...");
        System.out.println("    " + c3.toString());
        System.out.println("...Printing KB...");
        System.out.println("     " + kb2.toString());
        System.out.println("\nShow that this KB entails ¬P2,2 ∨ P3,1.");
        System.out.println("Query (entails) ¬P2,2 or P3,1 ≡ (P2,2) ∨ (P3,1) in CNF");
        System.out.println("...Adding query literals to clause...");
        Clause q6 = new Clause();
        q6.addLiteral(new Literal(8, true));
        q6.addLiteral(new Literal(9, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q6.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...");
        mc.checkModel(kb2, q6);

        KB kb3 = new KB();
        kb3.getClauses().clear();
        System.out.println("\n\n\nProblem 3:  If the unicorn is mythical, then it is immortal, but if it is not\n"
                            + "mythical, then it is a mortal mammal. If the unicorn is either immortal or a mammal,\n"
                            + "then it is horned. The unicorn is magical if it is horned.");
        System.out.println("(a) Can we prove that the unicorn is mythical?\n"
                            + "(b) Can we prove that the unicorn is magical?\n"
                            + "(c) Can we prove that the unicorn is horned?\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ PROPOSITIONAL VARIABLES:\n"
                            + "~ My: the unicorn is mythical\n"
                            + "~ I: the unicorn is immortal\n"
                            + "~ MM: the unicorn is a mortal mammal\n"
                            + "~ H: the unicorn is horned\n"
                            + "~ Mg: the unicorn is magical\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~ PL STATEMENTS:\n"
                            + "~ 1) My → I ≡ (¬My ∨ I) in CNF\n"
                            + "~ 2) ¬My → MM ≡ (My ∨ MM) in CNF\n"
                            + "~ 3) (I ∨ MM) → H ≡ (¬I ∨ H) (¬MM ∨ H) in CNF\n"
                            + "~ 4) H → Mg ≡ (¬H ∨ Mg)");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ SYMBOLS -> INTEGERS TABLE\n"
                            + "~ My = 1\n"
                            + "~ I = 2\n"
                            + "~ MM = 3\n"
                            + "~ H = 4\n"
                            + "~ Mg = 5\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "~ CNF:\n"
                            + "~ 1) (-1 ∨ 2)\n"
                            + "~ 2) (1 ∨ 3)\n"
                            + "~ 3) (-2 ∨ 4) ∧ (-3 ∨ 4)\n"
                            + "~ 4) (-4 ∨ 5)\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n...Reading CNF file 'part2_prob3.cnf'...");
        System.out.println("...Adding literals to clauses to KB...\n");
        kb3 = rc.readCNF(kb3, "part2_prob3.cnf");
        System.out.println("\n...Printing KB...");
        System.out.println("    " + kb3.getClauses().toString());
        System.out.println("\nQuery (a) entails My");
        System.out.println("\n...Adding query literal to clause...");
        Clause q8 = new Clause();
        q8.addLiteral(new Literal(1, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q8.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb3, q8);

        System.out.println("\nQuery (b) entails Mg");
        System.out.println("\n...Adding query literal to clause...");
        Clause q9 = new Clause();
        q9.addLiteral(new Literal(5, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q9.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb3, q9);

        System.out.println("\nQuery (c) entails H");
        System.out.println("\n...Adding query literal to clause...");
        Clause q10 = new Clause();
        q10.addLiteral(new Literal(4, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q10.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb3, q10);

        System.out.println("\nQuery (I v MM)");
        System.out.println("\n...Adding query literal to clause...");
        Clause q11 = new Clause();
        q11.addLiteral(new Literal(2, false));
        q11.addLiteral(new Literal(3, false));
        System.out.println("...Printing query clause...");
        System.out.println("    " + q11.toString());
        System.out.println("...Sending KB and query clause to ttEntails()...\n");
        mc.checkModel(kb3, q11);

        
        System.out.println("--------------------------------------");
        System.out.println("------------- END PART 2 -------------");
        System.out.println("--------------------------------------\n\n");
    }
}