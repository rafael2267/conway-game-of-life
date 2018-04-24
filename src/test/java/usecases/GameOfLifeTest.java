package usecases;

import domain.Cell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameOfLifeTest {

    private static final Integer NUM_ROW = 6;
    private static final Integer NUM_COL = 6;
    private static final String LIVES = "*";
    private static final String DIE = ".";
    private static final Boolean NEXT_GEN = Boolean.TRUE;
    private static final Boolean ACTUAL_GEN = Boolean.FALSE;

    private Cell[][] testMatrix;

    @InjectMocks
    private GameOfLife gameOfLife = new GameOfLife();

    @Before
    public void before() {
        testMatrix = new Cell[NUM_ROW][NUM_COL];

        createMatrix();
    }

    @Test
    public void shouldDieWhenHasFewThanTwoLiveNeighbours() {
        setLiveCellsInMatrix(1, 3);
        gameOfLife.hasFewThanTwoLiveNeighbours(testMatrix, 1, 3);
        printMatrix(ACTUAL_GEN, "Has Few Than Two Neighbours",testMatrix);

        assertEquals(DIE, testMatrix[1][3].getNextGenState());
    }

    @Test
    public void shouldDieWhenHasMoreThenThreeLiveNeighbours() {
        setLiveCellsInMatrix(2, 2);
        setLiveCellsInMatrix(3, 3);
        setLiveCellsInMatrix(1, 1);
        setLiveCellsInMatrix(2, 3);
        setLiveCellsInMatrix(1, 2);

        gameOfLife.hasMoreThanThreeLiveNeighbours(testMatrix, 2, 2);

        printMatrix(ACTUAL_GEN, "Has More Than Three Neighbours",testMatrix);
        assertEquals(DIE, testMatrix[2][2].getNextGenState());
    }

    @Test
    public void shouldLivesWhenHasTwoLiveNeighbours() {
        setLiveCellsInMatrix(2, 2);
        setLiveCellsInMatrix(3, 3);
        setLiveCellsInMatrix(1, 1);

        gameOfLife.hasTwoOrThreeLiveNeighbours(testMatrix, 2, 2);

        printMatrix(ACTUAL_GEN, "Has Two Neighbours",testMatrix);
        assertEquals(LIVES, testMatrix[2][2].getNextGenState());
    }

    @Test
    public void shouldLivesWhenHasThreeLiveNeighbours() {
        setLiveCellsInMatrix(2, 2);
        setLiveCellsInMatrix(3, 3);
        setLiveCellsInMatrix(1, 1);
        setLiveCellsInMatrix(1, 2);

        gameOfLife.hasTwoOrThreeLiveNeighbours(testMatrix, 2, 2);

        printMatrix(ACTUAL_GEN, "Has Three Neighbours", testMatrix);
        assertEquals(LIVES, testMatrix[2][2].getNextGenState());
    }

    @Test
    public void deadCellShouldLivesWhenHasThreeLiveNeighbours() {
        setLiveCellsInMatrix(2, 2);
        setLiveCellsInMatrix(3, 3);
        setLiveCellsInMatrix(1, 1);
        setLiveCellsInMatrix(1, 2);

        gameOfLife.hasDeadCellThreeLiveNeighbours(testMatrix, 2, 3);

        printMatrix(ACTUAL_GEN, "Dead Cell", testMatrix);
        assertEquals(LIVES, testMatrix[2][3].getNextGenState());
    }

    @Test
    public void shouldPrintNextGen() {
        setLiveCellsInMatrix(1, 2);
        setLiveCellsInMatrix(2, 2);
        setLiveCellsInMatrix(3, 2);

        gameOfLife.getNextGeneration(testMatrix, NUM_ROW, NUM_COL);

        printMatrix(NEXT_GEN, "Next Generation", testMatrix);
        assertEquals(LIVES, testMatrix[2][3].getNextGenState());
    }

    public void createMatrix() {
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                Cell cell = new Cell();
                cell.setActualGenState(DIE);
                cell.setNextGenState(DIE);
                testMatrix[i][j] = cell;
            }
        }
    }

    public void setLiveCellsInMatrix(Integer row, Integer col) {
        testMatrix[row][col].setActualGenState(LIVES);
    }

    private void printMatrix(Boolean state, String testName, Cell[][] matrix) {
        System.out.print("Matriz " + testName + ": \n");
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
}
