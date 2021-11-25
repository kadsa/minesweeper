
public class Settings {
    public Class<?> cls;
    public int tilesCount;
    public int minesCount;

    Settings() {
        // defaults
        this.cls = Rectangle.class;
        this.tilesCount = 480;
        this.minesCount = 99;
    }

    Settings(Class<?> cls, int tilesCount, int minesCount) {
        this.cls = cls;
        this.tilesCount = tilesCount;
        this.minesCount = minesCount;
    }

    @Override
    public String toString() {
        return cls + ", " + tilesCount + ", " + minesCount;
    }
}