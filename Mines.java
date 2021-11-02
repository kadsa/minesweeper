import java.util.BitSet;
import java.util.Random;

public class Mines {
    public final static int NX = 30;
    public final static int NY = 16;
    public final static int NMINES = 99;

    private final static BitSet field = new BitSet(NX * NY);
    
    public static boolean hasMine(int x, int y){
        return field.get(x + y * NX);
    }

    public static boolean isClean(int x, int y){
        return !hasMine(x, y);
    }

    public static void putMines() {
        field.clear();
        field.set(0, NMINES);
        shuffle();
    }

    /*
        Failed to find an easy way to use Collections.shuffle() on bitset.
    */
    private static void shuffle() {
        int len = NX * NY;
        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int r = rnd.nextInt(len - j) + j;
                boolean t = field.get(i);
                field.set(i, field.get(r));
                field.set(r, t);
            }
        }
    }
}
