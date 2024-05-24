package ru.otus;

import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i < 20; i++) {
            numbers.add(random.nextInt(100));
        }

        Ordering<Integer> ordering = Ordering.natural();
        System.out.println("Input list:" + numbers);

        System.out.println("=========================");
        System.out.println("List is sorted: " + ordering.isOrdered(numbers));
        System.out.println("Min: " + ordering.min(numbers));
        System.out.println("Max: " + ordering.max(numbers));

        Collections.sort(numbers, ordering.reversed());
        System.out.println("Reversed: " + numbers);
        System.out.println("=========================");
    }
}
