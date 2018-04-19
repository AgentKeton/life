package life;

public class Board {
    private boolean[][] board;
    private boolean[][] initial;

    private static int mod(int x, int m) {
        return (x % m + m) % m;
    }

    public int getNeighborCount(int x, int y, int xsize, int ysize){
        int cnt = 0;

        for(int i = -1; i < 2; ++i ) {
            for(int j = -1; j < 2; ++j ) {

                if ( i == 0 && j == 0 ) {
                    continue;
                }

                int ix = i == 0 ? x : mod(x+i, xsize);
                int iy = j == 0 ? y : mod(y+j, ysize);

                if (board[ix][iy]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public Board(boolean [][] initial) {
        this.initial = initial;
        this.board = initial;
    }

    public void next() {
        int w = board.length;
        int h = board[0].length;

        boolean newBoard[][] = new boolean[w][h];

        for(int i = 0; i < w; ++i ) {
            for(int j = 0; j < h; ++j ) {
                int cnt = getNeighborCount(i, j, w, h);
                newBoard[i][j] = cnt == 3 || (cnt == 2 && board[i][j]);
            }
        }
        board = newBoard;
    }

    public void init() {
        board = initial;
    }

    public boolean[][] getCells() {
        return board;
    }

}