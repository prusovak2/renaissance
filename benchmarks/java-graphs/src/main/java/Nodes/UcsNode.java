package Nodes;

public class UcsNode extends BaseNode {
    public UcsNode parent = null;
    public int cost = Integer.MAX_VALUE;
    public boolean isExpanded = false;
    public UcsNode(int label, byte[] payload) {
        super(label, payload);
    }
}
