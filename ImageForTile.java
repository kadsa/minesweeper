import java.awt.image.BufferedImage;

public interface ImageForTile {
    BufferedImage imageFor(Tile tile);
    int size();
}
