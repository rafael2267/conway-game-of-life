package usecases;

import domain.Cell;

import java.util.Scanner;

public class GameOfLife {

    private static final String DIE = ".";
    private static final String LIVES = "*";
    private static final Boolean IS_NOT_NEXT_GEN = Boolean.FALSE;
    private static final Boolean IS_NEXT_GEN = Boolean.TRUE;

    public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife();

        Scanner reader = new Scanner(System.in);
        System.out.println("Digite o número de linhas: ");
        Integer row = reader.nextInt();

        System.out.println("Digite o número de colunas: ");
        Integer col = reader.nextInt();

        System.out.println("Digite o número de Gerações: ");
        Integer numGen = reader.nextInt();
        reader.nextLine();

        Cell[][] matrix = new Cell[row][col];

        System.out.printf("Digite os valores da matriz[%d, %d]: \n", row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Cell line = new Cell();
                System.out.printf("[%d, %d]: ", i, j);
                String value = reader.nextLine();
                line.setActualGenState(value);
                line.setNextGenState(DIE);
                matrix[i][j] = line;
            }
        }

        reader.close();

        System.out.println("Geração Inicial!");
        gameOfLife.printMatrix(IS_NOT_NEXT_GEN, matrix);
        for (int i = 0; i < numGen; i++) {
            System.out.printf("Geração %d: \n", i+1);
            gameOfLife.getNextGeneration(matrix, row, col);
            gameOfLife.printMatrix(IS_NEXT_GEN, matrix);
            gameOfLife.setNextToActualGen(matrix, row, col);
        }
    }

    public void getNextGeneration(Cell[][] matrix, Integer row, Integer col) {
        for (int matrixRow = 0; matrixRow < row; matrixRow++) {
            for (int matrixCol = 0; matrixCol < col; matrixCol++) {
                hasFewThanTwoLiveNeighbours(matrix, matrixRow, matrixCol);
                hasMoreThanThreeLiveNeighbours(matrix, matrixRow, matrixCol);
                hasTwoOrThreeLiveNeighbours(matrix, matrixRow, matrixCol);
                hasDeadCellThreeLiveNeighbours(matrix, matrixRow, matrixCol);
            }
        }
    }

    public void hasFewThanTwoLiveNeighbours(Cell[][] matrix, Integer matrixRow, Integer matrixCol) {
        int liveNeighboursNum = getLiveNeighboursNum(matrix, matrixRow, matrixCol);

        if (matrix[matrixRow][matrixCol].getActualGenState().equals(LIVES)) {
            if (liveNeighboursNum < 2) {
                matrix[matrixRow][matrixCol].setNextGenState(DIE);
            }
        }
    }

    public void hasMoreThanThreeLiveNeighbours(Cell[][] matrix, Integer matrixRow, Integer matrixCol) {
        int liveNeighboursNum = getLiveNeighboursNum(matrix, matrixRow, matrixCol);

        if (matrix[matrixRow][matrixCol].getActualGenState().equals(LIVES)) {
            if (liveNeighboursNum > 3) {
                matrix[matrixRow][matrixCol].setNextGenState(DIE);
            }
        }
    }

    public void hasTwoOrThreeLiveNeighbours(Cell[][] matrix, Integer matrixRow, Integer matrixCol) {
        int liveNeighboursNum = getLiveNeighboursNum(matrix, matrixRow, matrixCol);

        if (matrix[matrixRow][matrixCol].getActualGenState().equals(LIVES)) {
            if (liveNeighboursNum == 3 || liveNeighboursNum == 2) {
                matrix[matrixRow][matrixCol].setNextGenState(LIVES);
            }
        }
    }

    public void hasDeadCellThreeLiveNeighbours(Cell[][] matrix, Integer matrixRow, Integer matrixCol) {
        int liveNeighboursNum = getLiveNeighboursNum(matrix, matrixRow, matrixCol);

        if (matrix[matrixRow][matrixCol].getActualGenState().equals(DIE)) {
            if (liveNeighboursNum == 3) {
                matrix[matrixRow][matrixCol].setNextGenState(LIVES);
            }
        }
    }

    private int getLiveNeighboursNum(Cell[][] matrix, Integer row, Integer col) {
        int liveNeighboursNum = 0;

        Boolean rowEdgeUp = false;
        Boolean rowEdgeDown = false;
        Boolean colEdgeLeft = false;
        Boolean colEdgeRight = false;

        if (row == 0) {
            rowEdgeUp = true;
        } else if (row == matrix.length - 1) {
            rowEdgeDown = true;
        }

        if (col == 0) {
            colEdgeLeft = true;
        } else if (col == matrix[row].length - 1) {
            colEdgeRight = true;
        }

        if (!rowEdgeUp && matrix[row - 1][col].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!rowEdgeUp && !colEdgeRight && matrix[row - 1][col + 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!colEdgeRight && matrix[row][col + 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!rowEdgeDown && !colEdgeRight && matrix[row + 1][col + 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!rowEdgeDown && matrix[row + 1][col].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!rowEdgeDown && !colEdgeLeft && matrix[row + 1][col - 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!colEdgeLeft && matrix[row][col - 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }
        if (!rowEdgeUp && !colEdgeLeft && matrix[row - 1][col - 1].getActualGenState().equals(LIVES)) {
            liveNeighboursNum++;
        }

        return liveNeighboursNum;
    }

    private void printMatrix(Boolean state, Cell[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[i].length - 1; j++) {
                if (state) {
                    System.out.print(matrix[i][j].getNextGenState() + "\t");
                } else {
                    System.out.print(matrix[i][j].getActualGenState() + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setNextToActualGen(Cell[][] matrix, Integer row, Integer col){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j].setActualGenState(matrix[i][j].getNextGenState());
                matrix[i][j].setNextGenState(DIE);
            }
        }
    }
}
