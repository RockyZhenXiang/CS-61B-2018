package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class StartingMenu {
    private int width;
    private int height;


    // Constructor
    public StartingMenu(int wid, int hei) {
        width = wid;
        height = hei;

        // Setup default Canvas
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.black);

        // Print "CS61B: THE GAME"
        Font heading = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(heading);
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) width / 2, (double) height * 4 / 5, "CS61B: THE GAME");

        // Print "New Game (N)"
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) width / 2, (double) height / 2 + 5, "New Game (N)");

        // Print "New Game (N)"
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) width / 2, (double) height / 2, "Load Game (L)");

        // Print "New Game (N)"
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) width / 2, (double) height / 2 - 5, "Quit (Q)");

        StdDraw.enableDoubleBuffering();
        StdDraw.show();

    }

    /**
     * Reads the input of keyboard
     * if "N" or "n" is pressed, create a new world
     * if "L" or "l" is pressed, load game from memory
     * if "Q" or "q" is pressed, quit the game and save it
     */

    public void readKeyBoard() {
        Boolean flag = true;
        while (flag) {
            while (StdDraw.hasNextKeyTyped()) {
                char typed = StdDraw.nextKeyTyped();
                if (typed == 'N' || typed == 'n') {
                    System.out.println("Create a new world");
                    flag = false;
                    break;
                } else if (typed == 'L' || typed == 'l') {
                    System.out.println("Load Game");
                    flag = false;
                    break;
                } else if (typed == 'Q' || typed == 'q') {
                    System.out.println("Quit Game");
                    flag = false;
                    break;
                }
            }
        }
    }
}
