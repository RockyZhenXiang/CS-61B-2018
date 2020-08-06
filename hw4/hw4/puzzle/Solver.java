package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private static class SearchNode implements Comparable<SearchNode>{
        private WorldState myWorldState;
        private int moves;
        private int cost;
        private SearchNode myParent;

        public SearchNode(WorldState worldState, int move, SearchNode parent) {
            myWorldState = worldState;
            moves = move;
            myParent = parent;
            cost = moves + worldState.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode o) {

            return cost - o.cost;
        }
    }

    private MinPQ<SearchNode> minPQ;
    private Deque<WorldState> solWorlds;
    private int minMove;
    public int test;

    public Solver(WorldState initial) {
        SearchNode first = new SearchNode(initial, 0, null);
        minPQ = new MinPQ<>(SearchNode::compareTo);
        minPQ.insert(first);
        solWorlds = new ArrayDeque<>();

        while(!minPQ.isEmpty()) {
            SearchNode candidate = minPQ.delMin();
            if (candidate.myWorldState.isGoal()) {
                minMove = candidate.moves;
                while(candidate != null) {
                    solWorlds.addFirst(candidate.myWorldState);
                    candidate = candidate.myParent;
                }
                return;
            } else {
                for (WorldState nei : candidate.myWorldState.neighbors()) {
                    if (!nei.equals(grand(candidate))) {
                        SearchNode neiNode = new SearchNode(nei, candidate.moves + 1, candidate);
                        minPQ.insert(neiNode);
                        test += 1;
                    }
                }
            }
        }

    }

    /**
     * Returns the worldState of the grandparent of the current node
     * If either the grandparent or the parent is null, returns null
     */
    private WorldState grand(SearchNode node) {
        if (node.myParent != null) {
            if (node.myParent.myParent != null) {
                return node.myParent.myParent.myWorldState;
            }
        }
        return null;
    }

    public int moves() {

        return minMove;
    }

    public Iterable<WorldState> solution() {

        return solWorlds;
    }
}
