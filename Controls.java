import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

class Controls extends JPanel{
    
    private final static int padding = 15;
    private final PlayingField pf;
    private final MinesCount minesCount;
    private final Clock clock;


    Controls(PlayingField pf){
        this.pf = pf;
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        add(Box.createRigidArea(new Dimension(padding, padding)));
        
        minesCount = new MinesCount();
        add(minesCount);
    
        add(Box.createVerticalGlue());
        
        clock = new Clock();
        add(clock);

        add(Box.createVerticalGlue());
        
        add(new btnReset());

        add(Box.createRigidArea(new Dimension(padding, padding)));
    }


    private class btnReset extends JButton{
        btnReset(){
            setText("reset");
            addActionListener(event -> {
                pf.field.reset();
                minesCount.reset();
                clock.restart();
                pf.repaint();
            });
        }

    }

    private class Clock extends JLabel{
        private final Timer timer;
        private long elapsed;

        Clock(){
            timer = new Timer(1000, event -> {
                elapsed++;
                updateShownTime();
            });
            restart();    
        }

        void restart(){
            setText("00:00");
            elapsed = 0;

            timer.restart();
        }

        private void updateShownTime(){
            long minutes = elapsed / 60L;
            long seconds = elapsed % 60L;
           
            String sm = (minutes > 9) ? String.valueOf(minutes) : "0" + String.valueOf(minutes);
            String ss = (seconds > 9) ? String.valueOf(seconds) : "0" + String.valueOf(seconds);
           
            setText(sm + ":" + ss);
        } 
    }

    private class MinesCount extends JLabel{
        MinesCount(){
            setForeground(Color.RED);
            setText(String.valueOf(pf.field.getMinesCount()));
            
            //on right mouse clicks on the playing field
            //update shown mines count
            pf.addMouseListener(new MouseHandler());
        }

        void reset(){
            updateShownMinesCount();   
        }
        void updateShownMinesCount(){
            int minesLeft = pf.field.getMinesCount() - pf.field.flaggedCount();
            minesLeft = minesLeft > 0 ? minesLeft : 0;
          
            setText(String.valueOf(minesLeft));
        }
        private class MouseHandler extends MouseInputAdapter{
            @Override
            public void mouseClicked(MouseEvent event) {
                // == MouseEvent.BUTTON2 doesnt pick up a right click!?
                if (event.getButton() != MouseEvent.BUTTON1)    
                    updateShownMinesCount();
            }   
        }
    }
}