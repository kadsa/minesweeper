import java.awt.image.BufferedImage;

public class ImagesFromOldMinesweeperSprite implements ImageForTile {
    public BufferedImage imageFor(Tile tile) {
        switch (tile) {
        case NEW:
            return TileImages.NEW;

        case FLAGGED:
            return TileImages.FLAGGED;

        case QUESTION:
            return TileImages.QUESTION;

        case CLEAN:
            return TileImages.CLEAN;

        case MINE:
            return TileImages.MINE;

        case BLOWN_UP_MINE:
            return TileImages.BLOWN_UP_MINE;

        case NOT_A_MINE:
            return TileImages.NOT_A_MINE;

        case ONE:
            return TileImages.ONE;
        case TWO:
            return TileImages.TWO;
        case THREE:
            return TileImages.THREE;
        case FOUR:
            return TileImages.FOUR;
        case FIVE:
            return TileImages.FIVE;
        case SIX:
            return TileImages.SIX;
        case SEVEN:
            return TileImages.SEVEN;
        case EIGHT:
            return TileImages.EIGHT;

        default:
            throw new AssertionError("Unknown tile");
        }
    };

    public int width() {
        return TileImages.TILE_SIZE;
    }

    public int height() {
        return TileImages.TILE_SIZE;
    }
}
