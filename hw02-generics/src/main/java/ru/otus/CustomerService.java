package ru.otus;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> storage = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        var entry = storage.firstEntry();
        return entry != null
                ? new AbstractMap.SimpleEntry<>(new Customer(entry.getKey()), entry.getValue())
                : null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var entry = storage.higherEntry(customer);
        return entry != null
                ? new AbstractMap.SimpleEntry<>(new Customer(entry.getKey()), entry.getValue())
                : null;
    }

    public void add(Customer customer, String data) {
        storage.put(customer, data);
    }
}
