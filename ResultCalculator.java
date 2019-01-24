package game;
import java.util.List;
import static game.TicTacToe.EMPTY;

public class ResultCalculator {

    private List<Tile> tiles;
    private String winner = null;

    public String calculate(List<Tile> tiles) {
        this.tiles = tiles;
        this.winner = null;

        compareValues(0, 1, 2);
        compareValues(1, 4, 7);
        compareValues(0, 3, 6);
        compareValues(3, 4, 5);
        compareValues(6, 7, 8);
        compareValues(2, 5, 8);
        compareValues(0, 4, 8);
        compareValues(2, 4, 6);

        return winner;
    }

    private void compareValues(int index1, int index2, int index3) {
        String val1 = tiles.get(index1).getCurrentValue();
        String val2 = tiles.get(index2).getCurrentValue();
        String val3 = tiles.get(index3).getCurrentValue();

        if (!(val1.equals(EMPTY)) && val1.equals(val2) && val1.equals(val3)) {
            winner = val1;
        }
    }
}
