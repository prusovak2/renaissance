package GraphBuilder;

import Nodes.BaseNode;
import Search.SearchProblem;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.*;

public class GraphBuilder<TNode extends BaseNode> {
    private final long numEdges;
    private final int numNodes;
    private final long maxNumEdges;
    private final short nodePayloadSize;
    private final int  EDGE_COST_UPPER_BOUND = 500;

    private Random random;
    private SecureRandom secureRandom;
    private boolean useSecureRandom = false;
    private long addedEdgesCounter = 0;

    InstanceCreator<TNode> instanceCreator;

    public GraphBuilder(int numNodes, long numEdges, short nodePayloadSize, InstanceCreator<TNode> instanceCreator){
        this.maxNumEdges = (numNodes * (numNodes- (long)1))/ (long)2; // num edges in complete graph
        if(numEdges < numNodes-1 || numEdges > maxNumEdges){
            throw new IllegalArgumentException(
                    MessageFormat.format("Incompatible number of nodes {0} and edges {1}. Cannot create connected graph. For {0} nodes, between {2} an {3} edges is required."
                            , numNodes, numEdges, numNodes-1, maxNumEdges));
        }
        if(nodePayloadSize < 0){
            throw new IllegalArgumentException("Negative size of node's payload is invalid");
        }
        this.numEdges = numEdges;
        this.numNodes = numNodes;
        this.nodePayloadSize = nodePayloadSize;
        this.instanceCreator = instanceCreator;
    }

    public SearchProblem<TNode> buildSearchProblem(int numTargets, long seed){
        ArrayList<TNode> graphNodes = buildGraph(seed);
        return buildSearchProblemImpl(graphNodes, numTargets);
    }

    public  SearchProblem<TNode> buildSearchProblem(int numTargets){
        ArrayList<TNode> graphNodes = buildGraph();
        return buildSearchProblemImpl(graphNodes, numTargets);
    }

    public ArrayList<TNode> buildGraph(long seed){
        this.secureRandom = null;
        this.random = new Random(seed);
        useSecureRandom = false;
        return buildGraphImpl();
    }

    public ArrayList<TNode> buildGraph(){
        this.random = null;
        this.secureRandom = new SecureRandom();
        useSecureRandom = true;
        return buildGraphImpl();
    }

    private SearchProblem<TNode> buildSearchProblemImpl(ArrayList<TNode> graphNodes, int numTargets){
        if(numTargets >= numNodes){
            throw new IllegalArgumentException("Too many target nodes.");
        }
        int[] distinctIndices = generateRandomDistinctIndices(numTargets +1); // targets and start
        int startIdx = distinctIndices[0];
        int[] targetIndices = Arrays.copyOfRange(distinctIndices, 1, distinctIndices.length);
        return new SearchProblem<TNode>(graphNodes, startIdx, targetIndices);
    }

    private ArrayList<TNode> buildGraphImpl(){
        ArrayList<TNode> spanningTree = createRandomSpanningTree();
        while (addedEdgesCounter < numEdges){
            TNode node1 = spanningTree.get(generateRandomInt(spanningTree.size()));
            TNode node2 = spanningTree.get(generateRandomInt(spanningTree.size()));
            if(node1 == node2)
                continue;
            if(node1.Neighbors.contains(node2))
                continue;
            int cost = generateRandomInt(EDGE_COST_UPPER_BOUND);
            node1.addNeighbor(node2, cost);
            node2.addNeighbor(node1, cost);
            addedEdgesCounter++;
        }
        return spanningTree;
    }

    private ArrayList<TNode> createRandomSpanningTree(){
        ArrayList<TNode> nodes = generateNodes();
        HashSet<TNode> nodesToBeAdded = new HashSet<>(nodes);
        HashSet<TNode> nodesInSpanningTree = new HashSet<TNode>();

        TNode lastVisitedNode = nodes.get(generateRandomInt(numNodes));
        nodesInSpanningTree.add(lastVisitedNode);
        nodesToBeAdded.remove(lastVisitedNode);
        addedEdgesCounter = 0;
        while (!nodesToBeAdded.isEmpty()){
            TNode newNeighbor = nodes.get(generateRandomInt(numNodes));
            if(!nodesInSpanningTree.contains(newNeighbor)){
                int cost = (int)generateRandomLong((long)EDGE_COST_UPPER_BOUND);
                // graph is directed, add edge in both directions
                lastVisitedNode.addNeighbor(newNeighbor, cost);
                newNeighbor.addNeighbor(lastVisitedNode, cost);
                nodesInSpanningTree.add(newNeighbor);
                nodesToBeAdded.remove(newNeighbor);
                addedEdgesCounter++;
            }
            lastVisitedNode = newNeighbor; // so that generated spanning tree is general tree, not just path
            assert nodesInSpanningTree.size() == numNodes;
            assert addedEdgesCounter == numNodes - 1;
        }
        return new ArrayList<TNode>(nodesInSpanningTree);
    }

    private ArrayList<TNode> generateNodes(){
        ArrayList<TNode> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++){
            byte[] payload = generateRandomPayload();
            TNode node = instanceCreator.create(i, payload);
            nodes.add(node);
        }
        return nodes;
    }

    private long generateRandomLong(long upperBound){
        if(useSecureRandom){
            return secureRandom.nextLong(upperBound);
        }
        else{
            return  random.nextLong(upperBound);
        }
    }

    private int generateRandomInt(int upperBound){
        if(useSecureRandom){
            return secureRandom.nextInt(upperBound);
        }
        else{
            return  random.nextInt(upperBound);
        }
    }

    private byte[] generateRandomPayload(){
        byte[] payload = new byte[this.nodePayloadSize];
        if(useSecureRandom){
            this.secureRandom.nextBytes(payload);
        }
        else {
            this.random.nextBytes(payload);
        }
        return payload;
    }

    private int[] generateRandomDistinctIndices(int amount){
        HashSet<Integer> result = new HashSet<>();

        while (result.size() < amount) {
            int num;
            if(useSecureRandom){
                num = secureRandom.nextInt(numNodes);
            }
            else{
                num = random.nextInt(numNodes);
            }
            result.add(num);
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
