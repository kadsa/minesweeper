public class MinesTria implements Mines{
    private final int nx;
    private final int ny;
    private final int minesCount;
    
    public MinesTria(int nx, int ny, int minesCount){
        this.nx = nx; this.ny = ny; this.minesCount = minesCount;
    }  
    
    @Override
    public int getNX(){return nx;}
    @Override
    public int getNY(){return ny;}
    @Override
    public int getMinesCount(){return minesCount;}


    @Override
    public  boolean hasMine(int x, int y){return false;}
    @Override
    public  void putMines(){};
}
