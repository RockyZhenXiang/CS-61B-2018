package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Stack<Integer> stack;
    private int s; // source id
    private Maze maze;
    private int[] linkedTo;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1,1);
        stack = new Stack<Integer>();
        stack.push(s);
        linkedTo = new int[edgeTo.length];
        linkedTo[0] = -1;
    }

    @Override
    public void solve() {
        dfs();
    }

    private void dfs() {
        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (marked[current]) {
                draw(current);
                return;
            }
            marked[current] = true;

            for (int nei: maze.adj(current)) {
                stack.push(nei);
                linkedTo[nei] = current;
                announce();
            }
        }

    }

    private void draw(int last) {
        edgeTo[last] = linkedTo[last];
        int pointer = linkedTo[last];
        while (pointer != last) {
            edgeTo[pointer] = linkedTo[pointer];
            pointer = linkedTo[pointer];
        }
    }

}

