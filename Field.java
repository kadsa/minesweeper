/*
  implementations can find a field of class Mines useful.
*/
public interface Field{
    
    public void reset();
    public int getWidth();
    public int getHeight();
    public int getTilesCount();
    public int getMinesCount();

    public Point[] getPoints();
    public int insideTile(float x, float y);
    public Tile get(int i);    
    public void open(int nTile);
    
    public void openAll();

    //private static boolean fits(int x, int y) 
    //private static void countMinesAround(int x, int y) 
    //private static void blowUp() 
    
    public void mark(int nTile); 
    
    public int flaggedCount();

    
}