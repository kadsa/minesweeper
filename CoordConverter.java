/*
    answers the question: which tile did I click?
*/
public class CoordConverter {
    private final int x0;
    private final int y0;
    //private final int nxTiles;
    //private final int nyTiles;
    private final int tileWidth;
    private final int tileHeight;
    private final int xMax;
    private final int yMax;

    CoordConverter(int x0, int y0, int nxTiles, int nyTiles, int tileWidth, int tileHeight){
        this.x0 = x0;
        this.y0 = y0;
       // this.nxTiles = nxTiles;
       // this.nyTiles = nyTiles;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.xMax = x0 + nxTiles * tileWidth;
        this.yMax = y0 + nyTiles * tileHeight;
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
        
        int nx = x / tileWidth;
        int ny = y / tileHeight;

        return TilePosition.INSTANCE.set(nx, ny);
    }
}
