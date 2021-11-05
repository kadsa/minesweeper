
public class FieldRect {
    private final static Tile[][] tiles = new Tile[MinesRect.NX][MinesRect.NY];

    public static void reset() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = Tile.NEW;
            }
        }
    }

    public static Tile get(int x, int y) {
        return tiles[x][y];
    }

    public static void open(TilePosition tp) {
        if (MinesRect.hasMine(tp.x, tp.y))
            blowUp();
        else
            countMinesAround(tp.x, tp.y);
    }

    // to check out the images, how they fit together
    public static void openAll() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (!MinesRect.hasMine(i, j))
                    open(TilePosition.INSTANCE.set(i, j));
            }
        }
    }

    private static boolean fits(int x, int y) {
        boolean noLess = (x >= 0) && (y >= 0);
        boolean Less = (x < tiles.length) && (y < tiles[0].length);

        return noLess && Less;
    }

    private static void countMinesAround(int x, int y) {
        // stop in case of recursive calls,
        // already looked around this one
        if (tiles[x][y].isOpen())
            return;

        // 
        int nmines = 0;
        for (int i = -1; i <= +1; i++) {
            for (int j = -1; j <= +1; j++) {

                if ((i == 0) && (j == 0))
                    continue;
                if (!fits(x + i, y + j))
                    continue;

                if (MinesRect.hasMine(x + i, y + j))
                    nmines++;
            }
        }
        // mark the tile with the corresponding number of mines
        tiles[x][y] = Tile.forNumber[nmines];

        // if no mines around this tile,
        // count mines for 8(at most) tiles around recursively.
        if (nmines == 0)
            for (int i = -1; i <= +1; i++) {
                for (int j = -1; j <= +1; j++) {

                    if ((i == 0) && (j == 0))
                        continue;
                    if (!fits(x + i, y + j))
                        continue;

                    countMinesAround(x + i, y + j);
                }
            }
    }

    private static void blowUp() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].isOpen())
                    continue;

                if (MinesRect.hasMine(i, j))
                    tiles[i][j] = Tile.BLOWN_UP_MINE;
                else {
                    if (tiles[i][j] == Tile.FLAGGED)
                        tiles[i][j] = Tile.NOT_A_MINE;
                    else
                        tiles[i][j] = Tile.CLEAN;
                }
            }
        }
    }

    public static Tile mark(TilePosition tp) {
        int x = tp.x;
        int y = tp.y;
        tiles[x][y] = tiles[x][y].mark();
        return tiles[x][y];
    }

    public static int flaggedCount(){
        int count = 0;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile == Tile.FLAGGED)    
                    count++;
            }    
        }
        return count;
    }
}
