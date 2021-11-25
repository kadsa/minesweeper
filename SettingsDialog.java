import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class SettingsDialog extends JDialog{
    private MainFrame mainFrame;
    
    private final int iconSize = 32;

    SettingsDialog(MainFrame mf){
        super(mf, "some title", true);
        this.mainFrame = mf;
        
        JPanel pnl = new JPanel();
        add(pnl);

        pnl.setLayout(new BorderLayout());
       
        JButton btnOk = new JButton("ok");
        btnOk.addActionListener(event -> {
            setVisible(false);
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> {
            setVisible(false);
        });

        pnl.add(btnOk, BorderLayout.EAST);
        pnl.add(btnCancel, BorderLayout.WEST);

        setResizable(false);

        ///
        List<FieldclassAndIconPath>fields = ShapesCollector.getFieldImplementations();
        List<ImageIcon> icons 
            = fields.stream()
            .map(fs -> loadIcon(fs.iconPath))
            .collect(Collectors.toList());

        ///
        WhacAMolePanel whacAMole = new WhacAMolePanel(icons, icons.size() - 1);
        pnl.add(whacAMole, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(mf);
    }

    private ImageIcon loadIcon(String iconPath){
        return  new ImageIcon(new ImageIcon(iconPath)
            .getImage().getScaledInstance(iconSize, iconSize, /*Image.SCALE_DEFAULT*/1));
    }
}

class WhacAMolePanel extends JPanel{
    public int selected;

    WhacAMolePanel(List<ImageIcon> icons, int selected){
        this.selected = selected;

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        for (int i = 0; i < icons.size(); i++) {
            JLabel lbl = new JLabel(icons.get(i));
            lbl.putClientProperty(0, i);
            
            lbl.addMouseListener(new MouseHandler());
            
            add(lbl);
        }    

        whack();
    }
    private void whack(){
        for (Component comp: getComponents()){
            if (!(comp instanceof JLabel))
                continue;

            JLabel lbl = (JLabel)comp;
            int i = (int)lbl.getClientProperty(0);

            Border b;
            if (i == selected)
                b = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
            else
                b = BorderFactory.createBevelBorder(BevelBorder.RAISED);

            lbl.setBorder(b);
        }
    }
    private class MouseHandler extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {

            if (event.getButton() != MouseEvent.BUTTON1)
                return;

            if (event.getComponent().getClass() != JLabel.class)
                return;
             
            JLabel lbl = (JLabel)(event.getComponent());
            selected = (int)lbl.getClientProperty(0);
            whack();
        }
    }
}
