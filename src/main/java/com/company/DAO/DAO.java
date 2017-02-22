package com.company.DAO;

import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by tyuly on 07.02.2017.
 */
public interface DAO {
    List<Item> getItemsForBuy(User user);
    List<Bid> getBidsUser(User user);
    void dltBid(Bid bid);
    Bid getBid(Integer id);
    List<User> getUsers();
    List<Item> getItems();
    List<Bid> getBids();
    User getUser(String name);
    Item getItem(Integer id);
    void dltItem(Item item);
    void dltUser(User user);
    void saveUser(User user);
    void saveItem(Item item);
    void saveBid(Bid bid);
    User getUserById(Integer id);
    List<Item> getItemsForUser(User user);


}
