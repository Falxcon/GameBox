package connect6;

public class Connect6Board {

    static final int NONE  = 0;
    static final int BLACK = 1;
    static final int WHITE = 2;

    private int[][] gameField;
    private int countDraw, countMove, player;

    public Connect6Board(int size) {
        gameField = new int[size][size];
        countDraw = 0;
        countMove = 1;
        player = 1;

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = NONE;
            }
        }
    }

    /**
     * Vergleicht die gespielten Steine mit der Gesamtfläche des Brettes
     */
    public boolean checkDraw() {
        if (countDraw >= getSize() * getSize()) {
            return true;
        }
        return false;
    }

    /**
     * Setzt auf die angegebenen Koordinaten einen Spielstein, falls noch keiner vorhanden ist
     *
     * @param row Reihe in die der Spielstein gesetzt wird
     * @param col Spalte in die der Spielstein gesetzt wird
     */
    public boolean playPiece(int row, int col) {
        if (row >= getSize() || row < 0 || col >= getSize() || col < 0) {
            return false;
        }
        if (gameField[row][col] == NONE) {
            gameField[row][col] = player;
            countDraw++;
            countMove++;
            return true;
        }
        return false;
    }

    /**
     * Wechselt den Spieler nach 2 Spielzügen.
     */
    public void changePlayer() {
        if (countMove >= 2) {
            countMove = 0;
            if (getPlayer() == BLACK) {
                player = WHITE;
            } else {
                player = BLACK;
            }
        }
    }

    /**
     * Überprüft ob 6 gleiche Spielsteine Vertikal/Horizontal/Diagonal vorhanden sind,
     * nur die angegebene Reihe/Spalte wird überprüft.
     *
     * @param row Reihe die überprüft werden soll
     * @param col Spalte die überprüft werden soll
     */
    public boolean checkWinner(int row, int col) {
        return (checkWinVerHor(row, col) || checkWinDiagonalRtoL(row, col) || checkWinDiagonalLtoR(row, col));
    }

    /**
     * Überprüft ob 6 gleiche Spielsteine Vertikal oder Horizontal vorhanden sind,
     * nur die angegebene Reihe/Spalte wird überprüft.
     *
     * @param row Reihe die überprüft werden soll
     * @param col Spalte die überprüft werden soll
     */
    private boolean checkWinVerHor(int row, int col) {
        int countVer = 0;
        int countHor = 0;
        for (int i = 0; i < getSize(); i++) {
            // Vertikal
            if (gameField[i][col] == player) {
                countVer++;
            } else {
                countVer = 0;
            }
            // Horizontal
            if (gameField[row][i] == player) {
                countHor++;
            } else {
                countHor = 0;
            }
            // Überprüfung
            if (countVer >= 6 || countHor >= 6) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft ob 6 gleiche Spielsteine Diagonal von Rechts nach Links vorhanden sind,
     * nur die Diagonale der angegebenen Reihe/Spalte wird überprüft.
     *
     * @param row Reihe die überprüft werden soll
     * @param col Spalte die überprüft werden soll
     */
    private boolean checkWinDiagonalRtoL(int row, int col) {
        int count = 0;
        for (int x = row + col, y = 0; y <= row + col; x--, y++) {
            if (x < getSize() && y < getSize()) {
                if (gameField[x][y] == player) {
                    count++;
                } else {
                    count = 0;
                }
            }
            if (count >= 6) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft ob 6 gleiche Spielsteine Diagonal von Links nach Rechts vorhanden sind,
     * nur die Diagonale der angegebenen Reihe/Spalte wird überprüft.
     *
     * @param row Reihe die überprüft werden soll
     * @param col Spalte die überprüft werden soll
     */
    private boolean checkWinDiagonalLtoR(int row, int col) {
        int count = 0;
        if (row > col) {
            for (int x = row - col, y = 0; x < getSize(); x++ , y++) {
                if (gameField[x][y] == player) {
                    count++;
                } else {
                    count = 0;
                }
                if (count >= 6) {
                    return true;
                }
            }
        } else {
            for (int x = 0, y = col - row; y < getSize(); x++, y++) {
                if (gameField[x][y] == player) {
                    count++;
                } else {
                    count = 0;
                }
                if (count >= 6) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getStone(int row, int col) {
        return gameField[row][col];
    }

    public int getPlayer() {
        return player;
    }

    public int getSize() {
        return gameField.length;
    }
}
