import java.awt.image.BufferedImage;

public class ImagesFromOldMinesweeperSprite implements ImageForTile {
    public BufferedImage imageFor(Tile tile) {
        switch (tile) {
        case NEW:
            return SpriteReader.NEW;

        case FLAGGED:
            return SpriteReader.FLAGGED;

        case QUESTION:
            return SpriteReader.QUESTION;

        case CLEAN:
            return SpriteReader.CLEAN;

        case MINE:
            return SpriteReader.MINE;

        case BLOWN_UP_MINE:
            return SpriteReader.BLOWN_UP_MINE;

        case NOT_A_MINE:
            return SpriteReader.NOT_A_MINE;

        case ONE:
            return SpriteReader.ONE;
        case TWO:
            return SpriteReader.TWO;
        case THREE:
            return SpriteReader.THREE;
        case FOUR:
            return SpriteReader.FOUR;
        case FIVE:
            return SpriteReader.FIVE;
        case SIX:
            return SpriteReader.SIX;
        case SEVEN:
            return SpriteReader.SEVEN;
        case EIGHT:
            return SpriteReader.EIGHT;

        default:
            throw new AssertionError("Unknown tile");
        }
    };

    public int size() {
        return SpriteReader.TILE_SIZE;
    }

}
