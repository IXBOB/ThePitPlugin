package com.ixbob.thepit.task.handler;

public class RandomGoldIngotSpawnSingleTaskHandler extends AbstractSingleTaskHandler{

    private int goldIngotExistAmount = 0;

    public RandomGoldIngotSpawnSingleTaskHandler(Runnable runnable, int firstDelay, int periodDelay) {
        super(runnable, firstDelay, periodDelay);
    }

    public int getGoldIngotExistAmount() {
        return goldIngotExistAmount;
    }

    public void setGoldIngotExistAmount(int goldIngotExistAmount) {
        this.goldIngotExistAmount = goldIngotExistAmount;
    }
}
