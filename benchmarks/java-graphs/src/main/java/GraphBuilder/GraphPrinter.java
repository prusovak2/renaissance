package GraphBuilder;

import Nodes.BaseNode;

import java.util.ArrayList;

public class GraphPrinter {
    public static <TNode extends  BaseNode> void printGraph(ArrayList<TNode> nodes) {
        for (TNode  node : nodes) {
            System.out.print(node.Label + " [");
            if (node.Payload != null) {
                for (byte b : node.Payload) {
                    System.out.print(b + " ");
                }
            }
            System.out.println("]");
            for (BaseNode neighbor : node.Neighbors) {
                System.out.println(node.Label + " -> " + neighbor.Label);
            }
        }
    }
}
