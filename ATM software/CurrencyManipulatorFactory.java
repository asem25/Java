package com.javarush.task.task26.task2613;

import java.util.*;

public class CurrencyManipulatorFactory {
    private static Map<String, CurrencyManipulator> map = new HashMap<>();
    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        if (!map.containsKey(currencyCode.toUpperCase())){
            CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode.toUpperCase());
            map.put(currencyCode.toUpperCase(), manipulator);
        }


        return map.get(currencyCode.toUpperCase());
    }
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        List<CurrencyManipulator> listtores = new ArrayList<>();

        for(Map.Entry<String, CurrencyManipulator> val : map.entrySet()){
            listtores.add(val.getValue());
        }
        return listtores;
    }
}
