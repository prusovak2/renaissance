package GraphBuilder;

import Nodes.UcsNode;

public class UcsNodeInstanceCreator implements InstanceCreator<UcsNode> {
    public static UcsNodeInstanceCreator INSTANCE;

    static {
        INSTANCE = new UcsNodeInstanceCreator();
    }

    @Override
    public UcsNode create(int label, byte[] payload) {
        return new UcsNode(label, payload);
    }
}
