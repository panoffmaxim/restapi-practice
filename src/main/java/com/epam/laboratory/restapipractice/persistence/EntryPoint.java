package com.epam.laboratory.restapipractice.persistence;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.OrderEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EntryPoint {

    public static void main(String[] args) {
        OrderEntity order = new OrderEntity();
        order.setClientName("Alexander's order");
        List<OrderEntity> sample = new ArrayList<>();
        sample.add(order);

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        ClientEntity client = new ClientEntity();

        client.setClientName("Alexander Bell");
        client.setOrders(sample);

        session.save(client);
        session.getTransaction().commit();
    }
}
