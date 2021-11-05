/*
    answers the question: which tile did I click?
*/
public class CoordConverter {
    private final Field field;
    private final int x0;
    private final int y0;
    private final int tileSize;
    private final int xMax;
    private final int yMax;

    CoordConverter(Field field, int x0, int y0, int nxTiles, int nyTiles, int tileSize){
        this.field = field;
        this.x0 = x0;
        this.y0 = y0;
        this.tileSize = tileSize;
        this.xMax = x0 + nxTiles * tileSize;
        this.yMax = y0 + nyTiles * tileSize;
    }

    public Point[] convert(Point[] form){
        Point[] r = new Point[form.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = new Point(Math.round(tileSize * form[i].x), Math.round(tileSize * form[i].y));
        }
        return r;

    }

    public int getWidth(){
        return tileSize * field.getWidth();
    }

    public int getHeight(){
        return tileSize * field.getHeight();
    }
    
    public int insideTile(float x, float y){
        return field.insideTile(x / tileSize, y / tileSize);
    }

    private boolean onTiles(int mouseX, int mouseY){
        boolean noLess = (mouseX >= x0) && (mouseY >= y0);
        boolean noMore = (mouseX <= xMax) && (mouseY <= yMax);

        return noLess && noMore;
    }

    public TilePosition tile(int mouseX, int mouseY){
        if (!onTiles(mouseX, mouseY))
            return TilePosition.INSTANCE.set(-1, -1);

        int x = mouseX - x0;
        int y = mouseY - y0;
        
        int nx = x / tileSize;
        int ny = y / tileSize;

        return TilePosition.INSTANCE.set(nx, ny);
    }
}
