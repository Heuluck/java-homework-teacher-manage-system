package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomArrayList {
    public static <T> ArrayList<T> toList(T[] array){
        return new ArrayList<T>(Arrays.asList(array));
    }//数组转ArrayList

    public static <T> ArrayList<T> deduplication(ArrayList<T> list){//去重
        List<T> newList = list.stream().distinct().toList();
        list.clear();
        list.addAll(newList);
        return list;
    }
}
