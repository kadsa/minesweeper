/*
    Field in the form of an equilateral triangle
                u
               u u
              u u u
             u u u u  
*/
public class Triangle extends Field{
    public final static String iconPath = "assets/triangle.png";
    private final int edgeLen;
   
    Triangle(int tilesCount, int minesCount){
        
        edgeLen = calcEdge(tilesCount);
        int properTilesCount = ((edgeLen + 1) * edgeLen) / 2;
        
        mines = new Mines(properTilesCount, minesCount);
        tiles = new Tile[properTilesCount];

        shape = buildTriangle();

        init();
    };
     /*
        Number of tiles for an equilateral triangle w side N  = the sum 
        of the first N natural numbers(1 + 2 + 3 + ..+ N = (N + 1) * N / 2).

        To get the edge length, solve for N:
        N = (-1 + sqrt(1 + 8 * sum)) / 2

        tilesCount given(the sum) can be not enough(or too much) for a full triangle, 
        - return the closest integer edgeLen  in that case.
            u        u              u        u
           u u  ->  u u     or     u u  ->  u u
          u u      u u u          u 
    */
    private int calcEdge(int sum){
        return (int)Math.round((-1 + Math.sqrt(1 + 8 * sum)) / 2);
    }

    @Override
    public int getWidth(){
        return edgeLen;
    }

    @Override
    public int getHeight(){
        return edgeLen;
    }

    public static String iconPath(){
        return "assets/triangle.png";
    }

    private Point[] buildTriangle(){
        Point[] f = new Point[tilesCount()];

        int xmax = edgeLen;
        int ymax = edgeLen;

        int idx = tilesCount() - 1;

        //build the triangle starting w rightmost bottom-most point
        for (int i = 0; i < edgeLen; i++){
            int len = edgeLen - i;
            for (int j = 0; j < len ;j++) {
                f[idx] = new Point(xmax - j - i * 0.5f - 1, ymax - i - 1);
                idx--; 
            }
        }

        return f;
    }
}
