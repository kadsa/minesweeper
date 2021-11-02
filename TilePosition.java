/*
    singleton; (x, y) pair
    trying to mend the lack of multiple return values in Java 
    
    creating an object every time just to return x,y seems excessive
*/
public class TilePosition {
    public final static TilePosition INSTANCE = new TilePosition();

    private TilePosition() {
    };

    public int x;
    public int y;

    public TilePosition set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
