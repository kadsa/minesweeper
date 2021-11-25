public class Rectangle extends Field{
    public final static String iconPath = "assets/rectangle.png";
    
    private final int width;
    private final int height;


    Rectangle(int xTilesCount, int yTilesCount, int minesCount){
        this.width = xTilesCount;
        this.height = yTilesCount;
        int tilesCount = width * height;

        mines = new Mines(tilesCount, minesCount);
        tiles = new Tile[tilesCount];   
        
        shape = buildRectangle();   
        
        init();
    }

    public Rectangle(int tilesCount, int minesCount){
        // make a 2x1 rectangle
        int t = (int)Math.round(Math.sqrt(tilesCount / 2));
        
        this.width = 2 * t;
        this.height = t;
        int properTilesCount = width * height;

        mines = new Mines(properTilesCount, minesCount);
        tiles = new Tile[properTilesCount];   

        shape = buildRectangle();   
        
        init();
    }
    private Point[] buildRectangle(){
        Point[] r = new Point[width * height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r[i * height + j] = new Point(i, j);
            }
        }

        return r;
    }

    @Override
    public int getWidth(){return width;}
    @Override
    public int getHeight(){return height;}
   
    /*public static String iconName(){
        return "assets/rectangle.png";
    }*/
}
