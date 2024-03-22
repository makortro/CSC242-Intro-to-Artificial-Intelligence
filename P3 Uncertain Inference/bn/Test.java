package bn;

import bn.core.BayesianNetwork;
import bn.base.*;
import bn.parser.*;
import bn.inference.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        //arg[1] isn't number, run exact inferencer
        if (!Character.isDigit(args[0].charAt(0))) {
            ExactInference exact = new ExactInference();

            //parse xml file
            if (args[0].endsWith("xml")) {
                XMLBIFParser xmlbifparser = new XMLBIFParser();
                Distribution dstr;
                try {
                    BayesianNetwork nw = xmlbifparser.readNetworkFromFile(args[0]);
                    Assignment assgn = new Assignment();
                    
                    //read args from command line
                    for (int i = 2; i < args.length - 1; i += 2) {
                        assgn.put(nw.getVariableByName(args[i]), new StringValue(args[i + 1]));
                    }
                    dstr = exact.enumerationAsk(nw, nw.getVariableByName(args[1]), assgn); //take vars, run exact inference
                    System.out.println(dstr);
                } catch (IOException | ParserConfigurationException | SAXException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else { //parse bif
                BIFParser bifparser;
                Distribution dstr;
                try {
                    bifparser = new BIFParser(new FileInputStream(args[0]));
                    try {
                        BayesianNetwork nw = bifparser.parseNetwork();
                        Assignment assgn = new Assignment();
                        
                        //read args from command line
                        for (int i = 2; i < args.length - 1; i += 2) {
                            assgn.put(nw.getVariableByName(args[i]), new StringValue(args[i + 1]));
                        }
                        dstr = exact.enumerationAsk(nw, nw.getVariableByName(args[1]), assgn);
                        System.out.println(dstr);
                    } catch (IOException e) {
                        //TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e1) {
                    //TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else { //rejection or likelihood
            int sampleAmount = Integer.parseInt(args[0]);
            int alg = args[args.length - 1].equalsIgnoreCase("rejection") ? 1 : 2;
            LikelihoodWeighting likelihood = new LikelihoodWeighting();
            RejectionSampling rejection = new RejectionSampling();

            //parse xml file
            if (args[1].endsWith("xml")) {
                XMLBIFParser xmlbifparser = new XMLBIFParser();
                Distribution dstr;
                try { //read network from file given
                    BayesianNetwork nw = xmlbifparser.readNetworkFromFile(args[1]);
                    Assignment assgn = new Assignment();
                    
                    //read args from command line
                    for (int i = 3; i < args.length - 2; i += 2) {
                        assgn.put(nw.getVariableByName(args[i]), new StringValue(args[i + 1]));
                    }

                    //if alg is 1 rejection, else likelihood
                    if (alg == 1) {
                        dstr = rejection.rejectionSampling(nw.getVariableByName(args[2]), assgn, (bn.base.BayesianNetwork) nw, sampleAmount);
                    } else {
                        dstr = likelihood.likelihoodWeighting(nw.getVariableByName(args[2]), assgn, nw, sampleAmount);
                    }
                    System.out.println(dstr);
                } catch (IOException | ParserConfigurationException | SAXException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else { //parse bif
                BIFParser bifparser;
                Distribution dstr;
                try {
                    bifparser = new BIFParser(new FileInputStream(args[1]));
                    try {
                        BayesianNetwork nw = bifparser.parseNetwork();
                        Assignment assgn = new Assignment();
                        
                        //read args from command line
                        for (int i = 3; i < args.length - 2; i += 2) {
                            assgn.put(nw.getVariableByName(args[i]), new StringValue(args[i + 1]));
                        }
                        
                        //if alg is 1 rejection, else likelihood
                        if (alg == 1) {
                            dstr = rejection.rejectionSampling(nw.getVariableByName(args[2]), assgn, (bn.base.BayesianNetwork) nw, sampleAmount);
                        } else {
                            dstr = likelihood.likelihoodWeighting(nw.getVariableByName(args[2]), assgn, nw, sampleAmount);
                        }
                        System.out.println(dstr);
                    } catch (IOException e) {
                        //TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e1) {
                    //TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}