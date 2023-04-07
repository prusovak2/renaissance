package GraphBuilder;

import Nodes.BaseNode;
import java.util.function.*;

@FunctionalInterface
public interface InstanceCreator<TNode extends BaseNode> {
    TNode create(int label, byte[] payload);
}
