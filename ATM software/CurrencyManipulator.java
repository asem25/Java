package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;
import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public void addAmount(int denomination, int count){
        if (!denominations.containsKey(denomination)) {
            denominations.put(denomination, count);
        }else {
            denominations.put(denomination, denominations.get(denomination) + 1);
        }
    }
    public int getTotalAmount(){
        int total = 0;
        for (Map.Entry<Integer, Integer> val : denominations.entrySet()){
            total += (val.getValue() * val.getKey());
        }
        return total;
    }
    public boolean hasMoney(){
        return getTotalAmount() > 0;
    }
    public boolean isAmountAvailable(int expectedAmount){
        return getTotalAmount() >= expectedAmount;
    }
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;
        HashMap<Integer, Integer> copyDenominations = new HashMap<>(denominations);

        ArrayList<Integer> keys = new ArrayList<>(this.denominations.keySet());

        Collections.sort(keys);
        Collections.reverse(keys);

        TreeMap<Integer, Integer> resultMap = new TreeMap<>();

        for (Integer denomination : keys) {
            final int key = denomination;
            int value = copyDenominations.get(key);
            while (true) {
                if (sum < key || value == 0) {
                    copyDenominations.put(key, value);
                    break;
                }
                sum -= key;
                value--;

                if (resultMap.containsKey(key))
                    resultMap.put(key, resultMap.get(key) + 1);
                else
                    resultMap.put(key, 1);
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException();
        else {
            this.denominations.clear();
            this.denominations.putAll(copyDenominations);
        }
        return resultMap;
    }

}
