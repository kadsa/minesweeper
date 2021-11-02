import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileImages {
   
    public final static int TILE_SIZE = 16;
    private final static String sheetName = "assets/PC Computer - Minesweeper - Everything.png";
    private static BufferedImage spriteSheet;

    static {
        try {
            spriteSheet = ImageIO.read(new File(sheetName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static BufferedImage NEW = getSprite(0, 0);
    public final static BufferedImage CLEAN = getSprite(1, 0);
    public final static BufferedImage FLAGGED = getSprite(2, 0);
    public final static BufferedImage QUESTION = getSprite(3, 0);
    public final static BufferedImage MINE = getSprite(5, 0);
    public final static BufferedImage BLOWN_UP_MINE = getSprite(6, 0);
    public final static BufferedImage NOT_A_MINE = getSprite(7, 0);
    public final static BufferedImage ONE = getSprite(0, 1);
    public final static BufferedImage TWO = getSprite(1, 1);
    public final static BufferedImage THREE = getSprite(2, 1);
    public final static BufferedImage FOUR = getSprite(3, 1);
    public final static BufferedImage FIVE = getSprite(4, 1);
    public final static BufferedImage SIX = getSprite(5, 1);
    public final static BufferedImage SEVEN = getSprite(6, 1);
    public final static BufferedImage EIGHT = getSprite(7, 1);
    
    private static BufferedImage getSprite(int x, int y) {
        return spriteSheet.getSubimage(14 + x * (TILE_SIZE + 1), 195 + y * (TILE_SIZE + 1), TILE_SIZE, TILE_SIZE);
    }

}