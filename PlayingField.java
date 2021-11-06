import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

class PlayingField extends JComponent {
    final private int padding = 5;

    final public Field field;
    final private ImageForTile imageForTile;
    final private int isize;

    final private CoordConverter cc;

    PlayingField(Field field, ImageForTile imageForTile) {
        this.field = field;
        this.imageForTile = imageForTile;
        this.isize = imageForTile.size();

        this.cc = new CoordConverter(field, isize);

        this.addMouseListener(new MouseHandler());
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(cc.getWidth() + 2 * padding, cc.getHeight() + 2 * padding);
    }

    @Override
    public void paintComponent(Graphics g) {
       
        Point[] p = cc.transform(field.getShape());
        
        for (int i = 0; i < p.length; i++) {
            g.drawImage(imageForTile.imageFor(field.get(i)), (int)p[i].x, (int)p[i].y, null);
        }
    }
    
    private class MouseHandler extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            java.awt.Point p = event.getPoint();
            int mouseX = (int) p.getX();
            int mouseY = (int) p.getY();

            int nTile = cc.insideTile(mouseX, mouseY);
            if (nTile < 0)
                return;

            if (event.getButton() == MouseEvent.BUTTON1)
                field.open(nTile);
            else
                field.mark(nTile);

            event.getComponent().repaint();
        }
    }
}