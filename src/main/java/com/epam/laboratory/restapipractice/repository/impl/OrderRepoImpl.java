package com.epam.laboratory.restapipractice.repository.impl;

import com.epam.laboratory.restapipractice.entity.OrderEntity;
import com.epam.laboratory.restapipractice.persistence.HibernateUtil;
import com.epam.laboratory.restapipractice.repository.OrderRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
public class OrderRepoImpl implements OrderRepo {
    @Override
    public OrderEntity saveOrder(OrderEntity order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(order);
            session.get(OrderEntity.class, (Serializable) object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public OrderEntity findOrderById(Long id) {
        Transaction transaction = null;
        OrderEntity order = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT c FROM OrderEntity c WHERE c.id=:id";
            TypedQuery<OrderEntity> query = session.createQuery(hql, OrderEntity.class);
            query.setParameter("id", id);
            order = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<OrderEntity> findAllOrders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM OrderEntity ", OrderEntity.class).list();
        }
    }
}
