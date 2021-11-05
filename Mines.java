import java.util.BitSet;
import java.util.Random;

class Mines {
    public final int tilesCount; //480
    public final int minesCount; //99

    private final BitSet field;
    
    Mines(int tilesCount, int minesCount){
        this.tilesCount = tilesCount; 
        this.minesCount = minesCount;
        
        field = new BitSet(tilesCount);
    }

    public boolean hasMine(int x){
        return field.get(x);
    }

    public boolean isClean(int x){
        return !hasMine(x);
    }

    public void putMines() {
        field.clear();
        field.set(0, minesCount);
        shuffle();
    }

    /*
        Failed to find an easy way to use Collections.shuffle() on bitset.
    */
    private void shuffle() {
        Random rnd = new Random();

        for (int i = 0; i < tilesCount; i++) {
            for (int j = i + 1; j < tilesCount; j++) {
                int r = rnd.nextInt(tilesCount - j) + j;
                boolean t = field.get(i);
                field.set(i, field.get(r));
                field.set(r, t);
            }
        }
    }
}