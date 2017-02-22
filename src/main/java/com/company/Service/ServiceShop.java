package com.company.Service;

import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;

import java.util.List;

/**
 * Created by tyuly on 20.02.2017.
 */
public interface ServiceShop {
    void fillDB();
    User createUser(String name, String password);
    List<Item> getItemsForUser(User user);
    void dltBid(Bid bid);
    User getById(Integer id);
    Bid getBid(Integer id);
    Item createItem(String name, User user, String description);
    Bid createBid(User user, Item item);
    List<User> getUsers();
    List<Item> getItems();
    List<Bid> getBids();
    boolean auth(String username, String password);
    boolean checkName(String username, String password);
    User getUser(String name);
    Item getItem(Integer id);
    List<Item> getItemsForBuy(User user);
    List<Bid> getBidsUser(User user);
    void dltItem(Item item);
    public void editItem(Item item, String name, String description);
    void editUser(User us,String name, String password);
    void dltUser(User user);
    User dltUser(String name);
}
