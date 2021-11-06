public class FieldRectangle extends Field{
    private final int width;
    private final int height;

    FieldRectangle(int xTilesCount, int yTilesCount, int minesCount){
        this.width = xTilesCount;
        this.height = yTilesCount;
        int tilesCount = width * height;

        mines = new Mines(tilesCount, minesCount);
        tiles = new Tile[tilesCount];   
        
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
}
