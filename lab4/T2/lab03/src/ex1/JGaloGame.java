package ex1;

public class JGaloGame implements JGaloInterface {
    private String title;
    private int player=0;
    private char result;
    private char[][] matrix = new char[3][3];
    int[][] dirs = {
        {0,1},
        {0,-1},
        {-1,0},
        {1,0},
        {-1,1},
        {1,1},
        {-1,-1},
        {1,-1}
    };

    public static JGaloGame create(String title) {
        return new JGaloGame(title);
    }

    private JGaloGame(String title) {
        this.title = title;
        this.player = 0;
    }

    public char getActualPlayer() {
        if (player % 2 == 0)
            return 'X';
        else
            return 'O';
    }

    public boolean setJogada(int lin, int col) {
        lin--;
        col--;

        if ((lin >= 0 && lin < 3) && (col >= 0 && col < 3)) {
            this.matrix[lin][col] = getActualPlayer();
            return true;
        }
        return false;
    }
    
    public boolean isFinished() {
        int counter = 0;
        char figure;
        int x, y, i, x1, y1, xmax, ymax;

        for (x = 0; x < 3; x++)
            for (y = 0; y < 3; y++) {
                figure = matrix[x][y];

                for (i = 0; i < dirs.length; i++) {
                    x1 = dirs[i][0];
                    y1 = dirs[i][1];
                    xmax = x+2*x1;
                    ymax = y+2*y1;
                    if (figure != 0 && xmax >= 0 && xmax < 3 && ymax >= 0 && ymax < 3)
                        // x+x1 = next (second) position in certain direction
                        if (matrix[x+x1][y+y1] == figure) {
                            if (matrix[x+x1+x1][y+y1+y1] == figure) { // x+x1+x1 = third position in certain direction
                                this.result = getActualPlayer();// returns 'X' or 'O'
                                return true;
                            }
                        }
                }

                if (figure != 0)
                    counter++;

                if (counter == 9) { // Draw
                    this.result = ' ';
                    return true;
                }
            }
        player++; // Increments only after checking if player won
        return false;
    }
    
    public char checkResult() {
        return result;
    }

    public char[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }
}
