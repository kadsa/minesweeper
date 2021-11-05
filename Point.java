public class Point /*<T>*/  {
    /*public T x;
    public T y;

    Point(T x, T y){
        this.x = x; this.y = y;
    }
*/
    public float x;
    public float y;

    Point(float x, float y){
        this.x = x; this.y = y;}

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
