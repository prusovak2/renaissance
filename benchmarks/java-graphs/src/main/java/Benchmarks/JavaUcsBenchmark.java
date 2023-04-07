package Benchmarks;

import GraphBuilder.GraphBuilder;
import Nodes.UcsNode;
import Search.SearchProblem;
import GraphBuilder.UcsNodeInstanceCreator;
import Search.UniformCostSearch;

import java.util.ArrayList;
import java.util.List;



public class JavaUcsBenchmark {
    SearchProblem<UcsNode> searchProblem;

    public void generateProblemRandomly(int numNodes, int numEdges, short payloadSize, int numTargets, Integer seed){
        GraphBuilder<UcsNode> builder = new GraphBuilder<UcsNode>(numNodes, numEdges, payloadSize, UcsNodeInstanceCreator.INSTANCE);
        if(seed == null){
            searchProblem = builder.buildSearchProblem(numTargets);
        }
        else {
            searchProblem = builder.buildSearchProblem(numTargets, seed);
        }
    }

    public List<Integer> run(){
        ArrayList<UcsNode> solution =  UniformCostSearch.solve(searchProblem);
        ArrayList<Integer>  nodeLabels = new ArrayList<>();
        for(UcsNode node: solution){
            nodeLabels.add(node.Label);
        }
        return nodeLabels;
    }

    public void printProblem(){
        searchProblem.print();
    }
}
