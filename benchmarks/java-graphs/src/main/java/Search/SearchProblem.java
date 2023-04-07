package Search;

import GraphBuilder.GraphPrinter;
import Nodes.BaseNode;

import java.util.ArrayList;

public class SearchProblem<TNode extends BaseNode> {
    public final TNode startNode;
    private final ArrayList<TNode> nodes;
    private final ArrayList<TNode> targetNodes = new ArrayList<>();

    public SearchProblem(ArrayList<TNode> graphNodes, int startIndex, int[] targetNodesIndices){
        if(targetNodesIndices.length >= graphNodes.size()){
            throw new IllegalArgumentException("Too many target nodes.");
        }
        if(startIndex > graphNodes.size()){
            throw new IllegalArgumentException("Start node index out of bounds.");
        }
        nodes = graphNodes;
        startNode = graphNodes.get(startIndex);
        for(int idx: targetNodesIndices){
            targetNodes.add(nodes.get(idx));
        }
    }

    public boolean isTarget(TNode node){
        return targetNodes.contains(node);
    }

    public void print(){
        System.out.println("Start node: " + startNode.Label);
        System.out.println("Target nodes:");
        System.out.print("[ ");
        for(TNode node: targetNodes){
            System.out.print(node.Label+ " ");
        }
        System.out.println("]");
        GraphPrinter.printGraph(nodes);
    }
}
