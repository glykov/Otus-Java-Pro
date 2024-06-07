package ru.otus;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class CustomerReverseOrder {
    private final Queue<Customer> data = new PriorityQueue<>(Comparator.comparingLong(Customer::getId).reversed());

    public void add(Customer customer) {
        data.add(customer);
    }

    public Customer take() {
        return data.poll();
    }
}
