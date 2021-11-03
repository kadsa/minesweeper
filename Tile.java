public enum Tile {
    // hidden
    NEW,
    FLAGGED,
    QUESTION,

    // open
    CLEAN,
    MINE,
    BLOWN_UP_MINE,
    NOT_A_MINE,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    // 0 -> CLEAN, 1 -> ONE, ...
    public final static Tile[] forNumber = { CLEAN, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT };

    //can only mark hidden tiles(w right click)
    // empty -> flag -> question mark -> empty -> ...
    public Tile mark() {
        switch (this) {
       
        case NEW:
            return FLAGGED;

        case FLAGGED:
            return QUESTION;

        case QUESTION:
            return NEW;

        default:
            return this;
        }
    }

    public boolean isOpen(){
        switch (this) {
            case CLEAN:
            case MINE:
            case BLOWN_UP_MINE:
            case NOT_A_MINE:
            case ONE:    
            case TWO:    
            case THREE:    
            case FOUR:    
            case FIVE:    
            case SIX:    
            case SEVEN:    
            case EIGHT:    
                return true;
        
            default:
                return false;
        }
    }

    public boolean isHidden(){
        return !(this.isOpen());
    }
};