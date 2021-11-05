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

        int x0 = padding; int y0 = padding;
        //this.cc = new CoordConverter(x0, y0, MinesRect.NX, MinesRect.NY, isize);
        this.cc = new CoordConverter(field, x0, y0, 0, 0, isize);

        this.addMouseListener(new MouseHandler());
    }

    @Override
    public Dimension getPreferredSize(){

        /*
        int imagesWidth = isize * MinesRect.NX;
        int imagesHeight = isize * MinesRect.NY;
        
        return new Dimension(imagesWidth + 2 * padding, imagesHeight + 2 * padding);
        */
        return new Dimension(cc.getWidth() + 2 * padding, cc.getHeight() + 2 * padding);
    }

    @Override
    public void paintComponent(Graphics g) {
        int x0 = padding; int y0 = padding;
        /*
        for (int i = 0; i < MinesRect.NX; i++) {
            for (int j = 0; j < MinesRect.NY; j++) {
                g.drawImage(imageForTile.imageFor(FieldRect.get(i, j)), x0 + i * iwidth, y0 + j * iheight, null);
            }
        }
        */

        Point[] fm = field.getPoints();
        Point[] p = cc.convert(fm);
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
           // TilePosition tp = cc.tile(mouseX, mouseY);

            int nTile = cc.insideTile(mouseX, mouseY);
            if (nTile < 0)
                return;

            if (event.getButton() == MouseEvent.BUTTON1)
                field.open(nTile);
            else
                field.mark(nTile);
            /*
            if (event.getButton() == MouseEvent.BUTTON1)
                FieldRect.open(tp);
            else
                FieldRect.mark(tp);
*/
            event.getComponent().repaint();
        }
    }
}