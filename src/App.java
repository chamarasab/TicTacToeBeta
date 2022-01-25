import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static boolean x = false;
    static boolean o = false;
    static boolean draw = false;
    static int countX = 0;
    static int countO = 0;
    static char[][] grid = new char[3][3];
    static boolean chance = true;

    public static void main(String[] args) throws Exception {
        String input = "_________";

        char[] characters = input.toCharArray();

        gameGrid(characters); // store from 1D to 2D

        showGrid(grid); // show the 2D array

        coordinates(); // add user coordinates
    }

    private static void gameGrid(char[] characters) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = characters[j % 3 + i * 3];
            }
        } // storing 1D's element on 2D.
    }

    private static void showGrid(char[][] grid) {
        System.out.println("---------");
        for (int i = 0; i < grid.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(Color.ANSI_GREEN + grid[i][j] + " " + Color.ANSI_RESET);
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void coordinates() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Color.ANSI_CYAN + "Enter the coordinates: " + Color.ANSI_RESET);

        try {
            int fc = scanner.nextInt();
            int sc = scanner.nextInt();
            if (fc < 4 && sc < 4) {
                if (grid[fc - 1][sc - 1] == '_') {
                    if (chance) {
                        grid[fc - 1][sc - 1] = 'X';
                        chance = false;

                        showGrid(grid);
                        checkConditions(grid);
                    } else {
                        grid[fc - 1][sc - 1] = 'O';
                        chance = true;

                        showGrid(grid);
                        checkConditions(grid);
                    }

                } else {
                    System.out
                            .println(Color.ANSI_RED + "This cell is occupied! Choose another one!" + Color.ANSI_RESET);
                    coordinates();
                }
            } else {
                System.out.println("You should enter numbers!");
                coordinates();
            }
        } catch (Exception e) {
            System.out.println(Color.ANSI_RED + "You should enter numbers!" + Color.ANSI_RESET);
            coordinates();
        }
        scanner.close();
    }

    private static void checkConditions(char[][] grid) {
        readHorizontal(grid);
        readVertical(grid);
        readCrossX(grid);
        readCrossY(grid);

        if (x == true) {
            System.out.println(Color.ANSI_GREEN + "X wins" + Color.ANSI_RESET);
        } else if (o == true) {
            System.out.println(Color.ANSI_GREEN + "O wins" + Color.ANSI_RESET);
        } else if (draw == false) {
            System.out.println(Color.ANSI_CYAN + "Draw" + Color.ANSI_RESET);
        } else if (draw == true) {
            coordinates();
        } else if ((x == true && o == true) || (countX - countO >= 2) || (countO - countX >= 2)) {
            System.out.println("Impossible");
        }
    }

    private static void readCrossY(char[][] grid) {
        ArrayList<Character> sample = new ArrayList<Character>();
        int k = 2;

        for (int i = 0; i < grid.length; i++) {
            sample.add(grid[i][k]);
            k--;
        }
        checkArrayList(sample);
        sample.clear();
    }

    private static void readCrossX(char[][] grid) {
        ArrayList<Character> sample = new ArrayList<Character>();

        for (int i = 0; i < grid.length; i++) {
            sample.add(grid[i][i]);
        }
        checkArrayList(sample);
        sample.clear();
    }

    private static void readVertical(char[][] grid) {
        ArrayList<Character> sample = new ArrayList<Character>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sample.add(grid[j][i]);
            }
            checkArrayList(sample);
            sample.clear();
        }
    }

    private static void readHorizontal(char[][] grid) {
        ArrayList<Character> sample = new ArrayList<Character>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sample.add(grid[i][j]);
                countXO(grid[i][j]);
            }
            checkArrayList(sample);
            sample.clear();
        }
    }

    private static void countXO(char c) {
        if (c == 'X') {
            countX += 1;
        } else if (c == 'O') {
            countO += 1;
        }
    }

    private static void checkArrayList(ArrayList<Character> sample) {
        if (sample.get(0) == sample.get(1) && sample.get(1) == sample.get(2) && sample.get(0) == sample.get(2)) {
            if (sample.contains('X')) {
                x = true;
            } else if (sample.contains('O')) {
                o = true;
            }
        } else {
            if (sample.contains('_') || sample.contains(' ')) {
                draw = true;
            }
        }
    }
}
