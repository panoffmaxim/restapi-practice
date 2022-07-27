package com.epam.laboratory.restapipractice.repository.impl;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.persistence.HibernateUtil;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
public class ClientRepoImpl implements ClientRepo {
    @Override
    public ClientEntity saveClient(ClientEntity client) {
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
        return client;
    }

    @Override
    public Boolean updateClient(ClientEntity client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE ClientEntity set clientName = :clientName " + "WHERE id = :clientId";
            Query query = session.createQuery(hql);
            query.setParameter("clientName", client.getClientName());
            query.setParameter("clientId", 1);
            int result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean deleteClientById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ClientEntity client = session.get(ClientEntity.class, id);
            if (client != null) {
                String hql = "DELETE FROM ClientEntity " + "WHERE id = :clientId";
                Query query = session.createQuery(hql);
                query.setParameter("clientId", id);
                int result = query.executeUpdate();
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

    @Override
    public ClientEntity findClientById(Long id) {
        Transaction transaction = null;
        ClientEntity client = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT c FROM ClientEntity c WHERE c.id=:id";
            TypedQuery<ClientEntity> query = session.createQuery(hql, ClientEntity.class);
            query.setParameter("id", id);
            client = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<ClientEntity> findAllClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ClientEntity", ClientEntity.class).list();
        }
    }

    @Override
    public ClientEntity findByClientName(String clientName) {
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
}
