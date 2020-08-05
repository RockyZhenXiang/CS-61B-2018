package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private static class SearchNode implements Comparable<SearchNode>{
        private WorldState myWorldState;
        private int moves;
        private SearchNode myParent;

        public SearchNode(WorldState worldState, int move, SearchNode parent) {
            myWorldState = worldState;
            moves = move;
            myParent = parent;
        }

        @Override
        public int compareTo(SearchNode o) {
            int myED = myWorldState.estimatedDistanceToGoal();
            int oED = o.myWorldState.estimatedDistanceToGoal();

            return moves + myED - o.moves - oED;
        }
    }

    private MinPQ<SearchNode> minPQ;
    private Deque<WorldState> solWorlds;
    private int minMove;

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
                    SearchNode neiNode = new SearchNode(nei, candidate.moves + 1, candidate);
                    minPQ.insert(neiNode);
                }
            }
        }

    }

    public int moves() {

        return minMove;
    }

    public Iterable<WorldState> solution() {

        return solWorlds;
    }
}
