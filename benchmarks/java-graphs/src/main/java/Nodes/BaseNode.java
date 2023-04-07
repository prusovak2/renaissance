package Nodes;

import java.util.ArrayList;
import java.util.List;

public class BaseNode {
    public int Label;
    public byte[] Payload;
    public List<BaseNode> Neighbors = new ArrayList<BaseNode>();
    public List<Integer> NeighborsCosts =  new ArrayList<Integer>();

    public BaseNode(int label, byte[] payload){
        this.Label = label;
        this.Payload = payload;
    }

    public void addNeighbor(BaseNode newNeighbor, int cost){
        Neighbors.add(newNeighbor);
        NeighborsCosts.add(cost);
    }
}
