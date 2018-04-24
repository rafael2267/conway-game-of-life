package domain;

public class Cell {
    private String actualGenState;
    private String nextGenState;

    public String getActualGenState() {
        return actualGenState;
    }

    public void setActualGenState(String actualState) {
        this.actualGenState = actualState;
    }

    public String getNextGenState() {
        return nextGenState;
    }

    public void setNextGenState(String nextGenState) {
        this.nextGenState = nextGenState;
    }
}
