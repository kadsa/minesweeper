import java.util.*;

/*
    Field in the form of an equilateral triangle
                u
               u u
              u u u
             u u u u  

    
*/
public class FieldTria implements Field{
    private final Mines mines;
    private final Tile[] tiles;
    private final int edgeLen;
    private final Point[] points;
   
    FieldTria(int tilesCount, int minesCount){
        
        edgeLen = calcEdge(tilesCount);
        int properTilesCount = ((edgeLen + 1) * edgeLen) / 2;
        
        mines = new Mines(properTilesCount, minesCount);
        tiles = new Tile[properTilesCount];

        points = buildTriangle();

        reset();
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

    public void reset(){
        mines.putMines();
        
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.NEW;
        }
    };

    public int getWidth(){
        return edgeLen;
    }

    public int getHeight(){
        return edgeLen;
    }

    public int getMinesCount(){
        return mines.minesCount;
    }

    public int getTilesCount(){
        return mines.tilesCount;
    }

    private Point[] buildTriangle(){
        Point[] f = new Point[getTilesCount()];

        int xmax = edgeLen;
        int ymax = edgeLen;

        int idx = getTilesCount() - 1;

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
    public Point[] getPoints(){
        return points;
    }

    public int insideTile(float x, float y){
        for (int i = 0; i < points.length; i++) {
            float x0 = points[i].x;
            float y0 = points[i].y;
            float x1 = x0 + 1;
            float y1 = y0 + 1;

            boolean betweenX = (x >= x0 &&  x <= x1);
            boolean betweenY = (y >= y0 &&  y <= y1);

            if (betweenX && betweenY)
                return i;
        }   
        return -1;
    }
    public Tile get(int i){return tiles[i];}   
    public void open(int nTile){
        if (mines.hasMine(nTile))
            blowUp();
        else
            countMinesAround(nTile);
    };
    
    private void countMinesAround(int nTile){
        if (tiles[nTile].isOpen())    
            return;
        
        
        float centerX = points[nTile].x + 0.5f;
        float centerY = points[nTile].y + 0.5f;

        //from the center of tile probe 8 ways
        // up, down, left, right
        // upleft, upright, downright, downleft
        Set<Integer> tilesAround = new HashSet<>();
        for (int i = -1; i <= +1; i++) {
            for (int j = -1; j <= +1; j++) {
                if (i == 0 && j == 0)
                    continue;

                int t = insideTile(centerX + i * 0.7f,  centerY + j * 0.7f);
                if (t >= 0){
                    tilesAround.add(t);
                }
            }
        }

        int nmines = 0;
        for (Integer t : tilesAround) {
            if (mines.hasMine(t))
                nmines++;
        }
        
        tiles[nTile] = Tile.forNumber[nmines];

        if (nmines == 0)
            for (Integer t : tilesAround) {
                    countMinesAround(t);
        }    
    }

    private void blowUp(){
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isOpen())
                continue;

            if (mines.hasMine(i))
                    tiles[i] = Tile.BLOWN_UP_MINE;
                else {
                    if (tiles[i] == Tile.FLAGGED)
                        tiles[i] = Tile.NOT_A_MINE;
                    else
                        tiles[i] = Tile.CLEAN;
                }
        }
    }
    public void openAll(){
        for (int i = 0; i < tiles.length; i++) {
            if (mines.hasMine(i))
                tiles[i] = Tile.BLOWN_UP_MINE;
            else
                open(i);
        }
    };

    public void mark(int i){
        tiles[i] = tiles[i].mark();
    } 
    
    public int flaggedCount(){
        int count = 0;
        for (Tile tile : tiles) {
            if (tile == Tile.FLAGGED)    
                count++;
        }

        return count;
    };
}
