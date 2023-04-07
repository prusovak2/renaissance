package Search;

import Nodes.BaseNode;

import java.util.ArrayList;
import java.util.List;

public class IterativeDeepening {

    public static ArrayList<BaseNode> solve(SearchProblem<BaseNode> problem) {
        int depthLimit = 0;

        while (true) {
            ArrayList<BaseNode> path = depthLimitedSearch(problem.startNode, problem, depthLimit);
            if (path != null) {
                return path;
            }
            depthLimit++;
        }
    }

    public static ArrayList<BaseNode> depthLimitedSearch(BaseNode current, SearchProblem<BaseNode> problem, int depthLimit) {
        if (problem.isTarget(current)) {
            ArrayList<BaseNode> path = new ArrayList<BaseNode>();
            path.add(current);
            return path;
        }

        if (depthLimit == 0) {
            return null;
        }

        for (int i = 0; i < current.Neighbors.size(); i++) {
            BaseNode neighbor = current.Neighbors.get(i);
            ArrayList<BaseNode> path = depthLimitedSearch(neighbor, problem, depthLimit - 1);
            if (path != null) {
                path.add(0, current); // prepend
                return path;
            }
        }

        // If we've searched all neighbors and haven't found the goal node, return null to signal failure
        return null;
    }
}
