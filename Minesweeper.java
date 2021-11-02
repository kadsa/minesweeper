import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class Minesweeper {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            System.out.println("event queue..");
            var frame = new SimpleFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            Mines.putMines();
            Field.brandNew();
        });
    }
}

class SimpleFrame extends JFrame {
    public SimpleFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setIconImage(new ImageIcon("assets/naval-mine.png").getImage());

        CImage ci = new CImage();
        add(ci);
        ci.addMouseListener(new MouseHandler());

        System.out.println("simple frame..");
    }

}

class CImage extends JComponent {
    // get this function out of here
    private static BufferedImage NotHere(Tile tile) {
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
    }

    public void paintComponent(Graphics g) {

        for (int i = 0; i < Mines.NX; i++) {
            for (int j = 0; j < Mines.NY; j++) {
                g.drawImage(NotHere(Field.get(i, j)), 100 + i * TileImages.TILE_SIZE, 100 + j * TileImages.TILE_SIZE,
                        null);
            }
        }
    }
}

class MouseHandler extends MouseInputAdapter {
    public void mouseClicked(MouseEvent event) {
        // very very bad, make it static pls
        CoordConverter cc = new CoordConverter(100, 100, Mines.NX, Mines.NY, TileImages.TILE_SIZE,
                TileImages.TILE_SIZE);

        Point p = event.getPoint();
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();
        TilePosition tp = cc.tile(mouseX, mouseY);

        if (event.getButton() == MouseEvent.BUTTON1)
            Field.open(tp);
        else
            Field.mark(tp);

        event.getComponent().repaint();
    }
}
