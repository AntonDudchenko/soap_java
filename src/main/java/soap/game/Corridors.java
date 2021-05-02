package soap.game;


class Cell {
    private Boolean left = false;
    private Boolean right = false;
    private Boolean up = false;
    private Boolean down = false;
    private Boolean isDone = false;
    private int user = -1;


    public Boolean isDoneCheck() {
        if (right && left && up && down) {
            isDone = true;
            return true;
        }
        return false;
    }


    public void setPlayer(int id) {
        user = id;
    }


    public int getPlayer() {
        return user;
    }


    public Boolean getBorder(int border) {
        switch (border) {
            case 0:
                return left;
            case 1:
                return right;
            case 2:
                return up;
            case 3:
                return down;
            default:
                return false;
        }
    }


    public Boolean setBorder(int border) {
        switch (border) {
            case 0:
                if (!left) left = true;
                break;
            case 1:
                if (!right) right = true;
                break;
            case 2:
                if (!up) up = true;
                break;
            case 3:
                if (!down) down = true;
                break;
        }
        return isDoneCheck();
    }
}


public class Corridors {
    private Cell[][] field;
    private int size;


    public Corridors(int s) {
        size = s;
        field = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
                field[i][0].setBorder(0);
                field[i][size - 1].setBorder(1);
                field[0][i].setBorder(2);
                field[size - 1][i].setBorder(3);
        }
    }


    public int getSize() {
        return size;
    }


    public void printField() {
        for (int i = 0; i < size; i++) {
            System.out.print("   \033[33m\033[4m \033[0m");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            String res = new String("");
            for (int j = 0; j < size; j++) {
                Cell c = field[i][j];
                if (!c.getBorder(3)) {
                    if (!c.isDoneCheck()) {
                        if (c.getBorder(0)) {
                            res += " \033[33m|\033[0m *";
                        } else {
                            res += " | *";
                        }
                    } else {
                        res += " \033[33m| +\033[0m";
                    }
                } else {
                    if (!c.isDoneCheck()) {
                        if (c.getBorder(0)) {
                            res += " \033[33m|\033[0m \033[33m\033[4m*\033[0m";
                        } else {
                            res += " | \033[33m\033[4m*\033[0m";
                        }
                    } else {
                        res += " \033[33m| \033[4m+\033[0m";
                    }
                }
            }
            System.out.println(res + " \033[33m|\033[0m");
        }
    }


    public Boolean isGameEnd() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!field[i][j].isDoneCheck()) {
                    return false;
                }
            }
        }
        return true;
    }


    public Boolean setBorder(int x, int y, int border) {
        return field[x][y].setBorder(border);
    }


    public Boolean isDoneCheck(int x, int y) {
        return field[x][y].isDoneCheck();
    }


    public void setPlayer(int x, int y, int id) {
        field[x][y].setPlayer(id);
        System.out.println(String.format("Player %d close cell with indices %d %d", id, x, y));
    }


    public int getPlayer(int x, int y) {
        return field[x][y].getPlayer();
    }
}
