import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class Minesweeper {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new MainFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            reset();
        });
    }

    public static void reset() {
        Mines.putMines();
        Field.brandNew();
    }
}

class MainFrame extends JFrame {
    public MainFrame() {

        setIconImage(new ImageIcon("assets/naval-mine.png").getImage());
        setTitle("minesweeper");
        setResizable(false);

        PlayingField pf = new PlayingField(new ImagesFromOldMinesweeperSprite());

        JPanel pnl = new JPanel();
        pnl.add(pf);
        add(pnl, BorderLayout.WEST);

        //
        add(new Controls(pf), BorderLayout.EAST);

        //
        pack();

        // top left on the screen on xfce linux, don't like it
        // setLocationByPlatform(true);

        // center on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }
}
