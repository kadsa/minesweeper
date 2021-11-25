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
    private  JPanel pnlField;
    private  JPanel pnlControls;
    public Settings settings;

    public MainFrame() {

        setIconImage(new ImageIcon("assets/naval-mine.png").getImage());
        setTitle("minesweeper");
        setResizable(false);    

        applySettings(new Settings());

        // top left on the screen on xfce linux, don't like it
        // setLocationByPlatform(true);

        // center on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2); 
    }

    public void applySettings(Settings settings){
        this.settings = settings;

        if (pnlField != null)
            remove(pnlField);
        
        if (pnlControls != null)
            remove(pnlControls);

        setResizable(true);    

        Field f = ShapesCollector.makeAField(settings.cls, settings.tilesCount, settings.minesCount);

        PlayingField pf = new PlayingField(f, new ImagesFromOldMinesweeperSprite());

        pnlField = new JPanel();
        pnlField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pnlField.add(pf);
        add(pnlField, BorderLayout.WEST);
        
        pnlControls = new JPanel(new BorderLayout());
        //
        pnlControls.add(new Controls(pf), BorderLayout.NORTH);

        pnlControls.add(new btnSettings(), BorderLayout.SOUTH);
        
        //
        add(pnlControls, BorderLayout.EAST);

        pack();
        
        setResizable(false);
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
