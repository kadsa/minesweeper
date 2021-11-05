import java.util.Set;
import java.util.HashSet;

public abstract class Field {
    protected Mines mines;
    protected Tile[] tiles;

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract Point[] getPoints();

    public void reset() {
        mines.putMines();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.NEW;
        }
    };

    public int getMinesCount() {
        return mines.minesCount;
    }

    public int getTilesCount() {
        return mines.tilesCount;
    }

    public int insideTile(float x, float y) {
        Point[] points = getPoints();
        for (int i = 0; i < points.length; i++) {
            float x0 = points[i].x;
            float y0 = points[i].y;
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

        Point[] points = getPoints();
        float centerX = points[nTile].x + 0.5f;
        float centerY = points[nTile].y + 0.5f;

        // from the center of tile probe 8 ways
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
                tiles[i] = Tile.BLOWN_UP_MINE;
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