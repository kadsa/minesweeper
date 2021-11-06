/*
    
*/
public class CoordConverter {
    private final Field field;
    private final int tileSize;

    CoordConverter(Field field, int tileSize){
        this.field = field;
        this.tileSize = tileSize;
    }

    public Point[] transform(Point[] shape){
        Point[] r = new Point[shape.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = new Point(Math.round(tileSize * shape[i].x), Math.round(tileSize * shape[i].y));
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
}
