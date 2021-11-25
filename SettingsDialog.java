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
   
    SettingsDialog(MainFrame mf){
        super(mf, "some title", true);
        this.mainFrame = mf;

        JPanel pnl = new JPanel();
        add(pnl);

        pnl.setLayout(new BorderLayout());
       
        ///
        WhacAMolePanel whacAMole = new WhacAMolePanel(
            ShapesCollector.getFieldImplementations()
            , mainFrame.settings.cls);

        pnl.add(whacAMole, BorderLayout.CENTER);


        JButton btnOk = new JButton("ok");
        btnOk.addActionListener(event -> {
            setVisible(false);

            int oldTilesCount = mainFrame.settings.tilesCount;
            int oldMinesCount = mainFrame.settings.minesCount;
            
            mainFrame.applySettings(new Settings(whacAMole.getSelectedShape()
                , oldTilesCount
                , oldMinesCount));
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> {
            setVisible(false);
        });

        pnl.add(btnOk, BorderLayout.EAST);
        pnl.add(btnCancel, BorderLayout.WEST);

        setResizable(false);

        pack();
        setLocationRelativeTo(mf);
    }
    
    class WhacAMolePanel extends JPanel{
        private final int iconSize = 32;
        
        List<FieldclassAndIconPath>fields;
        public int selected;
    
    
        WhacAMolePanel(List<FieldclassAndIconPath>fields, Class<?> selectedShape){
    
            this.fields = fields;
            
            List<ImageIcon> icons = fields.stream()
                .map(fs -> loadIcon(fs.iconPath))
                .collect(Collectors.toList());
    
            int idx = 0;
            for (; idx < fields.size() - 1; idx++) {
                if (fields.get(idx).fieldClass == selectedShape)
                    break;
            }
            this.selected = idx;
    
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    
            for (int i = 0; i < icons.size(); i++) {
                JLabel lbl = new JLabel(icons.get(i));
                lbl.putClientProperty(0, i);
                
                lbl.addMouseListener(new MouseHandler());
                
                add(lbl);
            }    
    
            whack();
        }
    
        public Class<?> getSelectedShape(){
            return fields.get(selected).fieldClass;
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
        private ImageIcon loadIcon(String iconPath){
            return  new ImageIcon(new ImageIcon(iconPath)
                .getImage().getScaledInstance(iconSize, iconSize, /*Image.SCALE_DEFAULT*/1));
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
}


