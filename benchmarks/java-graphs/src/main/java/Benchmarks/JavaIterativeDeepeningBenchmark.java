package Benchmarks;
import GraphBuilder.GraphBuilder;
import GraphBuilder.BaseNodeInstanceCreator;
import Nodes.BaseNode;
import Search.IterativeDeepening;
import Search.SearchProblem;

import java.util.ArrayList;
import java.util.List;

public class JavaIterativeDeepeningBenchmark {
    SearchProblem<BaseNode> searchProblem;

    public void generateProblemRandomly(int numNodes, int numEdges, short payloadSize, int numTargets, Integer seed){
        GraphBuilder<BaseNode> builder = new GraphBuilder<BaseNode>(numNodes, numEdges, payloadSize, BaseNodeInstanceCreator.INSTANCE);
        if(seed == null){
            searchProblem =  builder.buildSearchProblem(numTargets);
        }
        else {
            searchProblem = builder.buildSearchProblem(numTargets, seed);
        }
    }

    public List<Integer> run(){
        ArrayList<BaseNode> solution =  IterativeDeepening.solve(searchProblem);
        ArrayList<Integer>  nodeLabels = new ArrayList<>();
        for(BaseNode node: solution){
            nodeLabels.add(node.Label);
        }
        return nodeLabels;
    }

    public void printProblem(){
        searchProblem.print();
    }
}
