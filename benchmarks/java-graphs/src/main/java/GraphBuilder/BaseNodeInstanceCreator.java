package GraphBuilder;

import Nodes.BaseNode;

public class BaseNodeInstanceCreator implements InstanceCreator<BaseNode>{
    public static BaseNodeInstanceCreator INSTANCE;

    static {
        INSTANCE = new BaseNodeInstanceCreator();
    }
    @Override
    public BaseNode create(int label, byte[] payload) {
        return new BaseNode(label, payload);
    }
}
