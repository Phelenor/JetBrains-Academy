package tictactoe;

import java.util.Scanner;

public class Main {
    public static boolean repeat = false;
    public static int symbol = 0;

    public static void main(String[] args) {
        play();
    }

    public static void play() {
        int x, y;
        Scanner reader = new Scanner(System.in);
        char[][] game = makeGame("_________");
        printField(game);

        while (checkState(game).equals("Game not finished")) {
            System.out.print("Enter the coordinates: ");

            if (reader.hasNextInt()) {
                x = reader.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                reader.next();
                reader.next();
                continue;
            }

            if (reader.hasNextInt()) {
                y = reader.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                reader.next();
                continue;
            }

            if (x < 1 || x > 3 || y < 1 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            game = makeMove(game, x, y);
            if (repeat) {
                repeat = false;
                continue;
            }
            printField(game);
            symbol++;
        }
        System.out.println(checkState(game));
    }

    public static char[][] makeMove(char[][] game, int x, int y) {
        char move;
        if (symbol % 2 == 0)
            move = 'X';
        else
            move = 'O';

        char[][] gameFind = new char[3][3];

        char[][] gameFind2 = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameFind2[i][j] = game[2 - i][j];
            }
        }

        if (gameFind2[y - 1][x - 1] == '_') {
            gameFind2[y - 1][x - 1] = move;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            repeat = true;
        }
        //transposing
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameFind[i][j] = gameFind2[2 - i][j];
            }
        }

        return gameFind;
    }


    public static void printField(char[][] input) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(input[i][j] + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("---------");
    }

    public static char[][] makeGame(String input) {
        char[][] field = new char[3][3];
        char[] in = input.toCharArray();

        int car = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++, car++) {
                field[i][j] = in[car];
            }
        }
        return field;
    }

    public static String checkState(char[][] game) {
        boolean check = false;
        boolean impossible = false;
        boolean empty = false;
        char winner = '_';
        int counter = 0, x = 0, o = 0;

        for (int i = 0; i < 3; i++) {
            if (game[i][0] == game[i][1] && game[i][0] == game[i][2]) {
                if (game[i][0] == '_') {
                    continue;
                }
                check = true;
                counter++;
                winner = game[i][0];
            }

            if (game[0][i] == game[1][i] && game[0][i] == game[2][i]) {
                if (game[0][i] == '_') {
                    continue;
                }
                check = true;
                counter++;
                winner = game[0][i];
            }
        }

        if (game[0][0] == game[1][1] && game[0][0] == game[2][2] && game[0][0] != '_') {
            check = true;
            counter++;
            winner = game[0][0];
        } else if (game[0][2] == game[1][1] && game[0][2] == game[2][0] && game[0][2] != '_') {
            check = true;
            counter++;
            winner = game[0][2];
        }

        for (char[] c : game) {
            for (char symbol : c) {
                if (symbol == '_') {
                    empty = true;
                } else if (symbol == 'X') {
                    x++;
                } else if (symbol == 'O') {
                    o++;
                }
            }
        }
        if (Math.abs(x - o) > 1) {
            return "Impossible";
        } else if (counter == 0 && empty) {
            return "Game not finished";
        } else if (counter == 0) {
            return "Draw";
        } else if (counter > 2) {
            return "Impossible";
        } else {
            return winner + " wins";
        }
    }
}
