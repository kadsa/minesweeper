
public class Field {

    private final static Tile[][] tiles = new Tile[Mines.NX][Mines.NY];

    public static void brandNew() {
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

        if (Mines.hasMine(tp.x, tp.y))
            blowUp();
        else
            countMinesAround(tp.x, tp.y);
    }

    // to check out images, how they fit together
    public static void openAll(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (!Mines.hasMine(i, j))
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
        // for recursive calls
        if (tiles[x][y].isOpen())
            return;

        int nmines = 0;
        for (int i = -1; i <= +1; i++) {
            for (int j = -1; j <= +1; j++) {

                if ((i == 0) && (j == 0))
                    continue;
                if (!fits(x + i, y + j))
                    continue;

                if (Mines.hasMine(x + i, y + j))
                    nmines++;
            }
        }

        switch (nmines) {
        case 0:
            tiles[x][y] = Tile.CLEAN;
            break;
        case 1:
            tiles[x][y] = Tile.ONE;
            break;
        case 2:
            tiles[x][y] = Tile.TWO;
            break;
        case 3:
            tiles[x][y] = Tile.THREE;
            break;
        case 4:
            tiles[x][y] = Tile.FOUR;
            break;
        case 5:
            tiles[x][y] = Tile.FIVE;
            break;
        case 6:
            tiles[x][y] = Tile.SIX;
            break;
        case 7:
            tiles[x][y] = Tile.SEVEN;
            break;
        case 8:
            tiles[x][y] = Tile.EIGHT;
            break;

        default:
            break;
        }

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

                if (Mines.hasMine(i, j))
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
}
