import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
    Field in the form of a circle
*/
public class FieldCircle extends Field{
    private final int diameter;
   
    FieldCircle(int tilesCount, int minesCount){
        
        diameter = calcDiameter(tilesCount);
        
        shape = buildCircle();

        int properTilesCount = shape.length;
        
        mines = new Mines(properTilesCount, minesCount);
        tiles = new Tile[properTilesCount];

        init();
    };
    
    /*
        take tilesCount as an area of a circle
    */
    private int calcDiameter(int area){

        return  (int)Math.round(2 * Math.sqrt(area / Math.PI));
    }

    @Override
    public int getWidth(){
        return diameter;
    }

    @Override
    public int getHeight(){
        return diameter;
    }

    private Point[] buildCircle(){
        //
        int radius = diameter / 2; 
        List<Integer> squaresPerRow = new ArrayList<>(radius);
        for (int i = 0; i < radius; i++) {
            int cn = (int)Math.round(Math.sqrt(diameter * diameter - i * i * 4));
            squaresPerRow.add(cn);         
        }

        List<Point> c = new ArrayList<>();
        List<Point> cMirror = new ArrayList<>();

        boolean oddDiameter = (diameter % 2 == 1);
        int oddCorrection = oddDiameter ? 1 : 0;

        float fDiameter = (float)diameter;

        for (int i = 0; i < squaresPerRow.size(); i++) {
            
            int rowLen = squaresPerRow.get(squaresPerRow.size() - 1 - i);
            
            for (int j = 0; j < rowLen; j++) {
                c.add(new Point((fDiameter - rowLen) / 2 + j, i));
                cMirror.add(new Point((fDiameter - rowLen) / 2 + j, diameter - i - 1 - oddCorrection));
            }
        }

        Collections.reverse(cMirror);
        c.addAll(cMirror);

        return c.toArray(new Point[c.size()]);
    }
    
    @Override
    public Point[] getShape(){
        return shape;
    }
}
