package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Rocky Zhenxiang Fang
 *  @soruse Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s; // source id
    private int t; // target id
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new ArrayDeque();
        fringe.add(maze.xyTo1D(sourceX, sourceY));

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!fringe.isEmpty()) {
            int current = fringe.poll();
            marked[current] = true;
            announce();

            if (current == t) {
                targetFound = true;
                return;
            }

            for (int nei: maze.adj(current)) {
                if (!marked[nei]) {
                    fringe.add(nei);
                    distTo[nei] = distTo[current] + 1;
                    edgeTo[nei] = current;
                }
            }
        }

    }

    @Override
    public void solve() {
        bfs();
    }
}

