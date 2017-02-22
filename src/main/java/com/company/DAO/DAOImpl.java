package com.company.DAO;

import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by tyuly on 07.02.2017.
 */
@Repository
public class DAOImpl implements DAO {

    @Autowired
    public SessionFactory sessionFactory;

    Session session;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session session () {
        try {
            session = getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
            return session;
        }
        return session;
    }

    @Override
    public List<User> getUsers() {
        Session session = session();
        Criteria criteria = session.createCriteria(User.class);
        return criteria.list();
    }

    @Override
    public List<Item> getItems() {
        Session session = session();
        Criteria criteria = session.createCriteria(Item.class);
        return criteria.list();
    }

    @Override
    public List<Bid> getBids() {
        Session session = session();
        Criteria criteria = session.createCriteria(Bid.class);
        return criteria.list();
    }

    @Override
    public User getUser(String name) {
        Session session = session();
        List<User> users = session.createCriteria(User.class).list();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Item> getItemsForUser(User user) {
        Session session = session();
        List<Item> items = session.createCriteria(Item.class).list();
        List<Item> itemsUser = new ArrayList<>();
        for (Item item : items) {
            if (item.getUser().getId().equals(user.getId()) && !item.getBuy()) {
                itemsUser.add(item);
            }
        }
        session.flush();
        return itemsUser;
    }

    @Override
    public List<Item> getItemsForBuy(User user) {
        Session session = session();
        List <Item> items =  session.createCriteria(Item.class).list();
        List<Item> listForBuy = new ArrayList<>();
        for (Item item:items) {
                if (!item.getBuy()) {
                    listForBuy.add(item);
                }
            }
        return listForBuy;
    }

    @Override
    public List<Bid> getBidsUser(User user) {
        Session session = session();
        List<Bid> bidList = new ArrayList<>();
        List<Bid> list = session.createCriteria(Bid.class).list();
        for (Bid bid: list) {
            if (bid.getUser().getId().equals(user.getId())) {
                bidList.add(bid);
            }
        }
        return bidList;
    }


    @Override
    public Item getItem(Integer id) {
        Session session = session();
        return (Item) session.get(Item.class,id);
    }


    @Override
    public Bid getBid(Integer id) {
        Session session = session();
        return (Bid) session.get(Bid.class,id);
    }

    @Override
    public void dltItem(Item item) {
        Session session = session();
        session.delete(item);

    }

    @Override
    public void dltBid(Bid bid) {
        Session session = session();
        session.delete(bid);

    }

    @Override
    public void dltUser(User user) {
        Session session = session();
        session.delete(user);
    }

    @Override
    public void saveUser(User user) {
        Session session = session();
        session.persist(user);
    }

    @Override
    public void saveItem(Item item) {
        Session session = session();
        session.persist(item);
    }

    @Override
    public void saveBid(Bid bid) {
        Session session = session();
        session.persist(bid);
    }

    @Override
    public User getUserById(Integer id) {
        Session session = session();
        return (User) session.get(User.class,id);
    }
}
