import java.util.Set;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.*;

public abstract class Field {
    //icon for a settings panel
    //should be redefined in the children
    public static final String iconPath = "assets/hexa.png";
    
    private boolean blownUp;
    /*
        these fields are meant to be initialized in the
        constructor of the child class
    */
    protected Mines mines;
    protected Tile[] tiles;
    protected Point[] shape;

    /*
        dimensions of the shape, considering we 
        are building this shape out of squares of size 1x1

        TODO: calc width and height using the shape given.
    */
    public abstract int getWidth();
    public abstract int getHeight();

    /*
      shapes are built from small squares of size 1

      return an array of coordinates of the top left corner of 
      each square
    */
    public Point[] getShape(){
        return shape;
    }

    /*
        should be called before using mines and Tiles and/or
        to reset the field.

        usually init() is the last call in the constructor
    */
    public void init() {
        mines.putMines();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.NEW;
        }

        blownUp = false;
    };

    public int getMinesCount() {
        return mines.minesCount;
    }

    public int tilesCount() {
        return mines.tilesCount;
    }
/*
    public static String iconPath(){
        return "assets/hexa.png";
    }
    /*
    public static String shapeName(){
        return "need a name for the shape here";
    }*/
    public int openCount(){
        return (int)Arrays.stream(tiles).filter(tile -> tile.isOpen()).count();
    }
    
    public boolean isBlownUp(){
        return blownUp;
    }
    public boolean isDemined(){
        if (blownUp)
            return false;
        else
            return tilesCount() == (openCount() + flaggedCount());
    }
    
    /*
        answers the question: which tile did I click?
    */
    public int insideTile(float x, float y) {
        Point[] shape = getShape();
        for (int i = 0; i < shape.length; i++) {
            float x0 = shape[i].x;
            float y0 = shape[i].y;
            float x1 = x0 + 1;
            float y1 = y0 + 1;

            boolean betweenX = (x >= x0 && x <= x1);
            boolean betweenY = (y >= y0 && y <= y1);

            if (betweenX && betweenY)
                return i;
        }
        return -1;
    }

    public Tile get(int i) {
        return tiles[i];
    }

    public void open(int nTile) {
        if (mines.hasMine(nTile))
            blowUp();
        else
            countMinesAround(nTile);
    };

    private void countMinesAround(int nTile) {
        if (tiles[nTile].isOpen())
            return;

        Point[] points = getShape();
        float centerX = points[nTile].x + 0.5f;
        float centerY = points[nTile].y + 0.5f;

        // from the center of the tile probe 8 ways
        // up, down, left, right
        // upleft, upright, downright, downleft
        Set<Integer> tilesAround = new HashSet<>();
        for (int i = -1; i <= +1; i++) {
            for (int j = -1; j <= +1; j++) {
                if (i == 0 && j == 0)
                    continue;

                int t = insideTile(centerX + i * 0.7f, centerY + j * 0.7f);
                if (t >= 0) {
                    tilesAround.add(t);
                }
            }
        }

        int nmines = 0;
        for (Integer t : tilesAround) {
            if (mines.hasMine(t))
                nmines++;
        }

        tiles[nTile] = Tile.forNumber[nmines];

        if (nmines == 0)
            for (Integer t : tilesAround) {
                countMinesAround(t);
            }
    }

    private void blowUp() {
        blownUp = true;

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isOpen())
                continue;

            if (mines.hasMine(i))
                tiles[i] = Tile.BLOWN_UP_MINE;
            else {
                if (tiles[i] == Tile.FLAGGED)
                    tiles[i] = Tile.NOT_A_MINE;
                else
                    tiles[i] = Tile.CLEAN;
            }
        }
    }

    public void openAll() {
        for (int i = 0; i < tiles.length; i++) {
            if (mines.hasMine(i))
                tiles[i] = Tile.MINE;
            else
                open(i);
        }
    };

    public void mark(int i) {
        tiles[i] = tiles[i].mark();
    }

    public int flaggedCount() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile == Tile.FLAGGED)
                count++;
        }

        return count;
    };

}