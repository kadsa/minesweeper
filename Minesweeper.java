import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;



public class Minesweeper {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new MainFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class MainFrame extends JFrame {
    private SettingsDialog settingsDialog = null;

    public MainFrame() {

        setIconImage(new ImageIcon("assets/naval-mine.png").getImage());
        setTitle("minesweeper");
        setResizable(false);

      /*  Settings settings = new Settings();
        Field f = settings.makeAField();
        

        PlayingField pf = new PlayingField(f, new ImagesFromOldMinesweeperSprite());*/
        //PlayingField pf = new PlayingField(new FieldTriangle(310, 2), new ImagesFromOldMinesweeperSprite());
        PlayingField pf = new PlayingField(new Circle(500, 100), new ImagesFromOldMinesweeperSprite());
        //PlayingField pf = new PlayingField(new FieldRectangle(30, 16, 99), new ImagesFromOldMinesweeperSprite());

        JPanel pnl = new JPanel();
        pnl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnl.add(pf);
        add(pnl, BorderLayout.WEST);

        
        JPanel pnl2 = new JPanel(new BorderLayout());
        //
        pnl2.add(new Controls(pf), BorderLayout.NORTH);

        pnl2.add(new btnSettings(), BorderLayout.SOUTH);
        
        //
        add(pnl2, BorderLayout.EAST);

        pack();

        // top left on the screen on xfce linux, don't like it
        // setLocationByPlatform(true);

        // center on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    private class btnSettings extends JButton{
        btnSettings(){
            setText("settings");
            addActionListener(event -> {
                    if (settingsDialog == null){

                        MainFrame mf = (MainFrame) SwingUtilities
                            .getAncestorOfClass(MainFrame.class, this);

                            settingsDialog = new SettingsDialog(mf);
                    }
                    
                        settingsDialog.setVisible(true);
                    });
                
        }
    }
}
