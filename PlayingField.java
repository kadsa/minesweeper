import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

class PlayingField extends JComponent {
    final private int padding = 5;

    final private ImageForTile imageForTile;
    final private int iwidth;
    final private int iheight;

    final private CoordConverter cc;

    PlayingField(ImageForTile imageForTile) {
        this.imageForTile = imageForTile;
        this.iwidth = imageForTile.width();
        this.iheight = imageForTile.height();

        int x0 = padding; int y0 = padding;
        this.cc = new CoordConverter(x0, y0, Mines.NX, Mines.NY, iwidth, iheight);

        this.addMouseListener(new MouseHandler());
    }

    @Override
    public Dimension getPreferredSize(){

        int imagesWidth = iwidth * Mines.NX;
        int imagesHeight = iheight * Mines.NY;
        
        return new Dimension(imagesWidth + 2 * padding, imagesHeight + 2 * padding);
    }

    @Override
    public void paintComponent(Graphics g) {
        int x0 = padding; int y0 = padding;

        for (int i = 0; i < Mines.NX; i++) {
            for (int j = 0; j < Mines.NY; j++) {
                g.drawImage(imageForTile.imageFor(Field.get(i, j)), x0 + i * iwidth, y0 + j * iheight, null);
            }
        }
    }

    private class MouseHandler extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
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
}