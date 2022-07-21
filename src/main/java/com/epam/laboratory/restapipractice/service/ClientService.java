package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.persistence.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ClientService {
    public void saveClient(ClientEntity client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(client);
            session.get(ClientEntity.class, (Serializable) object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void insertClient() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "INSERT INTO ClientEntity (clientName, phone, password, enabled, orders) "
                    + "SELECT clientName, phone, password, enabled, orders FROM ClientEntity";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Boolean updateClient(ClientEntity client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE ClientEntity set clientName = :clientName " + "WHERE id = :clientId";
            Query query = session.createQuery(hql);
            query.setParameter("clientName", client.getClientName());
            query.setParameter("clientId", 1);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return true;
    }

    public Boolean deleteClient(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ClientEntity client = session.get(ClientEntity.class, id);
            if (client != null) {
                String hql = "DELETE FROM ClientEntity " + "WHERE id = :clientId";
                Query query = session.createQuery(hql);
                query.setParameter("clientId", id);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return true;
    }

    public ClientEntity getClient(Long id) {
        Transaction transaction = null;
        ClientEntity client = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM ClientEntity S WHERE S.id = :clientId";
            Query query = session.createQuery(hql);
            query.setParameter("clientId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                client = (ClientEntity) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return client;
    }

    public ClientEntity getClientName(String clientName) {
        Transaction transaction = null;
        ClientEntity client = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM ClientEntity C WHERE C.clientName = :clientName";
            Query query = session.createQuery(hql);
            query.setParameter("clientName", clientName);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                client = (ClientEntity) results.get(1);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return client;
    }

    public List<ClientEntity> getClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ClientEntity", ClientEntity.class).list();
        }
    }
}
