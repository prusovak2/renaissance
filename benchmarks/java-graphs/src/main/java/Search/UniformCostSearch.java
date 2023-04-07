package Search;

import GraphBuilder.BaseNodeInstanceCreator;
import Nodes.UcsNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class UniformCostSearch {
    public static ArrayList<UcsNode> solve(SearchProblem<UcsNode> problem){
        PriorityQueue<UcsNode> minHeap = new PriorityQueue<UcsNode>(UcsNodeComparator.INSTANCE);
        UcsNode initialNode = problem.startNode;
        initialNode.cost = 0;
        initialNode.parent = null; // just to make it obvious
        minHeap.add(initialNode);

        while(!minHeap.isEmpty()){
            UcsNode currNode = minHeap.poll();
            if(problem.isTarget(currNode)){
                return buildSolution(currNode);
            }
            currNode.isExpanded = true;
            for(int i = 0; i< currNode.Neighbors.size(); i++){
                UcsNode neighbor = (UcsNode) currNode.Neighbors.get(i);
                int newNeighborCost = currNode.cost + currNode.NeighborsCosts.get(i);
                if(neighbor.isExpanded || newNeighborCost >= neighbor.cost){
                    continue;
                }
                neighbor.cost = newNeighborCost;
                neighbor.parent = currNode;
                minHeap.add(neighbor);
            }
        }
        return new ArrayList<>(); // no target nodes, solution not found
    }

    private static ArrayList<UcsNode> buildSolution(UcsNode target){
        UcsNode currNode = target;
        ArrayList<UcsNode> solution = new ArrayList<UcsNode>();
        while(currNode != null){
            solution.add(currNode);
            currNode = currNode.parent;
        }
        Collections.reverse(solution);
        return solution;
    }

    private static class UcsNodeComparator implements Comparator<UcsNode> {
        public static UcsNodeComparator INSTANCE;

        static {
            INSTANCE = new UcsNodeComparator();
        }
        @Override
        public int compare(UcsNode x, UcsNode y) {
            if(x == null || y == null){
                throw new IllegalArgumentException("Cannot compare null.");
            }
            return Integer.compare(x.cost, y.cost);
        }
    }
}
