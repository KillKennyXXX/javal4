
import java.util.Random;
import java.util.Scanner;

public class TicTacToeExtended {

    private static int SIZE = 3;
    private static int WS = 3;
    private static final char DOT_EMPTY = '•';
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    public static char[][] MAP;
    private static final String HEADER_FIRST_SYMBOL = "♥";
    private static final String SPACE_MAP = " ";
    private static Scanner in = new Scanner(System.in);
    private static Random random = new Random();
    private static int turnsCount = 0;
    private static int xRow = -1;
    private static int yCol = -1;

    public static void turnGame() {
        do {
            System.out.println("\n\nИГРА НАЧИНАЕТСЯ!!!");
            init();
            printMap();
            playGame();
        } while (isContinueGame());
        endGame();
    }

    private static void init() {
        turnsCount = 0;
        System.out.println("\n\nВведите размер игрового поля:");
        SIZE = getValidSizeFromScanner();
        MAP = new char[SIZE][SIZE];
        WS = winSize();
        initMap();
    }

    private static int winSize() {
        //подобрать победную серию фишек для выбранного размера
        //размер 3-6 -> победная серия 3
        //размер 7-10 -> победная серия 4
        //размер 10+ -> победная серия 5
        if (SIZE > 2 && SIZE < 7) {
            return 3;
        } else if (SIZE > 6 && SIZE < 11) {
            return 4;
        } else {
            return 5;
        }
    }

    private static void initMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MAP[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        printMapHeader();
        printMapBody();
    }

    private static void printMapHeader() {
        System.out.print(HEADER_FIRST_SYMBOL + SPACE_MAP);
        for (int i = 0; i < SIZE; i++) {
            printMapNumber(i);
        }
        System.out.println();
    }

    private static void printMapNumber(int i) {
        System.out.print(i + 1 + SPACE_MAP);
    }

    private static void printMapBody() {
        for (int i = 0; i < SIZE; i++) {
            printMapNumber(i);
            for (int j = 0; j < SIZE; j++) {
                System.out.print(MAP[i][j] + SPACE_MAP);
            }
            System.out.println();
        }
    }

    private static void playGame() {
        while (true) {
            humanTurn();
            printMap();
            if (checkEnd(DOT_HUMAN)) {
                break;
            }

            aiTurn();
            printMap();
            if (checkEnd(DOT_AI)) {
                break;
            }
        }
    }

    private static void humanTurn() {
        System.out.println("ХОД ЧЕЛОВЕКА!");

        int rowNumber;
        int columnNumber;

        while (true) {
            System.out.print("Введите координату строки: ");
            rowNumber = getValidNumberFromScanner() - 1;

            System.out.print("Введите координату столбца: ");
            columnNumber = getValidNumberFromScanner() - 1;

            if (isCellFree(rowNumber, columnNumber)) {
                break;
            }
            System.out.printf("Ячейка с координатами: %d:%d уже занята%n%n", rowNumber + 1, columnNumber + 1);
        }

        MAP[rowNumber][columnNumber] = DOT_HUMAN;
        turnsCount++;
    }

    private static int getValidNumberFromScanner() {
        while (true) {
            if (in.hasNextInt()) {
                int n = in.nextInt();
                if (isNumberValid(n)) {
                    return n;
                }
                System.out.println("!!!Проверьте значение координаты. Должно быть от 1 до " + SIZE);
            } else {
                System.out.println("!!!Ввод допускает лишь целые числа!");
                in.next();
            }
        }
    }

    private static int getValidSizeFromScanner() {
        while (true) {
            if (in.hasNextInt()) {
                int n = in.nextInt();
                if (isSizeValid(n)) {
                    return n;
                }
                System.out.println("!!!Проверьте значение  Должно быть целым числом от 1 до 20 ");
            } else {
                System.out.println("!!!Ввод допускает лишь целые числа!");
                in.next();
            }
        }
    }

    private static boolean isNumberValid(int n) {
        return n >= 1 && n <= SIZE;
    }

    private static boolean isSizeValid(int n) {
        return n >= 1 && n <= 20;
    }

    private static boolean isCellFree(int rowNumber, int columnNumber) {
        if (MAP[rowNumber][columnNumber] == DOT_EMPTY) {
            xRow = rowNumber;
            yCol = columnNumber;
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkEnd(char symbol) {

        if (checkWin(symbol)) {
            if (symbol == DOT_HUMAN) {
                System.out.println("УРА! ВЫ ПОБЕДИЛИ!");
            } else {
                System.out.println("ВОССТАНИЕ БЛИЗКО... ИИ ПОБЕДИЛ...");
            }
            return true;
        }

        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }

        return false;
    }

    private static boolean checkWin(char symbol) {

        //проерка строки
        int count = 0;
        for (int j = 0; j < SIZE; j++) {
            if (MAP[xRow][j] == symbol) {
                count++;
                if (count == WS) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        //проерка столбца
        count = 0;

        for (int i = 0; i < SIZE; i++) {
            if (MAP[i][yCol] == symbol) {
                count++;
                if (count == WS) {
                    return true;
                }
            } else {
                count = 0;
            }
        }


        return false;
    }

    private static boolean checkDraw() {
/*        for (char[] chars : MAP) {
            for (char symbol : chars) {
                if (symbol == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;*/

        return turnsCount >= SIZE * SIZE;
    }

    private static void aiTurn() {
        System.out.println("ХОД КОМУКТЕРА!");

        int rowNumber;
        int columnNumber;

/*        while (true) {
            rowNumber = random.nextInt(SIZE);
            columnNumber = random.nextInt(SIZE);
            if (isCellFree(rowNumber, columnNumber)) {
                break;
            }
        }*/
        do {
            rowNumber = random.nextInt(SIZE);
            columnNumber = random.nextInt(SIZE);
        } while (!isCellFree(rowNumber, columnNumber));

        MAP[rowNumber][columnNumber] = DOT_AI;
        turnsCount++;
    }

    private static boolean isContinueGame() {
        System.out.println("Хотите продолжить? yn");
        String isContinue = in.next();
        if (isContinue == "y" || isContinue == "yes" || isContinue == "да" || isContinue == "+" || isContinue == "д") {


            return true;
        } else {
            return false;
        }
    }

    ;


    private static void endGame() {
        in.close();
        System.out.println("Ты заходи, если что");
    }
}
