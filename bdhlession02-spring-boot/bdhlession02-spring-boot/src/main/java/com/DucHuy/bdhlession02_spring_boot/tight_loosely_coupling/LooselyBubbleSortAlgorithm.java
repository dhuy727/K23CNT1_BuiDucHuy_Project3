package com.DucHuy.bdhlession02_spring_boot.tight_loosely_coupling;

import java.util.Arrays;

public class LooselyBubbleSortAlgorithm implements SortAlgorithm{
    @Override
    public void sort(int[] array) {
        System.out.println("Sort by bubble algorithm");
        Arrays.stream(array).sorted().forEach(System.out::println);
    }
}
