package ru.otus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CustomerTest {

    @Test
    @DisplayName("Проверяем, что класс Customer не сломан")
    void setterCustomerTest() {
        // given
        String expectedName = "updatedName";
        String name = "nameVas";
        Customer customer = new Customer(1, name, 2);

        // when
        customer.setName(expectedName);

        // then
        assertThat(customer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Объект Customer как ключ в карте")
    void customerAsKeyTest() {
        // given
        final long customerId = 1L;
        Customer customer = new Customer(customerId, "Ivan", 233);
        Map<Customer, String> map = new HashMap<>();

        String expectedData = "data";
        map.put(customer, expectedData);

        // when
        long newScore = customer.getScores() + 10;
        String factData = map.get(new Customer(customerId, "IvanChangeName", newScore));

        // then
        assertThat(factData).isEqualTo(expectedData);

        // when
        long newScoreSecond = customer.getScores() + 20;
        customer.setScores(newScoreSecond);
        String factDataSecond = map.get(customer);

        // then
        assertThat(factDataSecond).isEqualTo(expectedData);
    }

    @Test
    @DisplayName("Сортировка по полю score, итерация по возрастанию")
    void scoreSortingTest() {
        // given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Peter", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerService service = new CustomerService();
        service.add(customer1, "Data1");
        service.add(customer2, "Data2");
        service.add(customer3, "Data3");

        // when
        Map.Entry<Customer, String> smallestScore = service.getSmallest();
        // then
        assertThat(smallestScore.getKey()).isEqualTo(customer2);

        // when
        Map.Entry<Customer, String> middleScore = service.getNext(new Customer(10, "Key", 20));
        // then
        assertThat(middleScore.getKey()).isEqualTo(customer1);
        middleScore.getKey().setScores(10_000);
        middleScore.getKey().setName("Vasy");

        // when
        Map.Entry<Customer, String> biggestScore = service.getNext(customer1);
        // then
        assertThat(biggestScore.getKey()).isEqualTo(customer3);

        // when
        Map.Entry<Customer, String> notExists = service.getNext(new Customer(100, "Not Exists", 20_000));
        // then
        assertThat(notExists).isNull();
    }

    @Test
    @DisplayName("Модификация коллекции")
    void mutationTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Peter", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerService service = new CustomerService();
        service.add(customer1, "Data1");
        service.add(new Customer(customer2.getId(), customer2.getName(), customer2.getScores()), "Data2");
        service.add(customer3, "Data3");

        // when
        Map.Entry<Customer, String> smallestScore = service.getSmallest();
        smallestScore.getKey().setName("Vasyl");

        // then
        assertThat(service.getSmallest().getKey().getName()).isEqualTo(customer2.getName());
    }

    @Test
    @DisplayName("Возвращение в обратном порядке")
    void reversedOrderTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Peter", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerReverseOrder reverseOrder = new CustomerReverseOrder();
        reverseOrder.add(customer1);
        reverseOrder.add(customer2);
        reverseOrder.add(customer3);

        // when
        Customer last = reverseOrder.take();
        // then
        assertThat(last).usingRecursiveAssertion().isEqualTo(customer3);

        // when
        Customer middle = reverseOrder.take();
        // then
        assertThat(middle).usingRecursiveAssertion().isEqualTo(customer2);

        // when
        Customer first = reverseOrder.take();
        // then
        assertThat(first).usingRecursiveAssertion().isEqualTo(customer1);
    }
}
