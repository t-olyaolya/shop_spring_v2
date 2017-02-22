package com.company.Service;

import com.company.DAO.DAO;
import com.company.DAO.DAOImpl;
import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyuly on 20.02.2017.
 */
@Service
//@Transactional
public class ServiceShopImpl implements ServiceShop{

    private DAO dao;


    @Autowired
    ServiceShopImpl(DAO dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public void fillDB() {
        User user1 = createUser("User1", "1111");
        User user2 = createUser("User2", "2222");
        User user3 = createUser("User3","9876");
        Item item1 = createItem("Item1", user1, "1");
        Item item2 = createItem("Item2", user2, "2");
        Item item3 = createItem("Item3", user1, "3");
        Bid bid = createBid(user1, item2);
        Bid bid2 = createBid(user2, item1);

    }
    @Transactional
    @Override
    public User createUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        dao.saveUser(user);
        return user;
    }

    @Transactional
    @Override
    public User getById(Integer id) {
        return dao.getUserById(id);

    }
    @Transactional
    @Override
    public Item createItem(String name, User user, String description) {
        Item item = new Item();
        item.setName(name);
        item.setUser(user);
        item.setDescription(description);
        dao.saveItem(item);
        return item;
    }
    @Transactional
    @Override
    public Bid createBid(User user, Item item) {
        Bid bid = new Bid();
        bid.setUser(user);
        bid.setItem(item);
        item.setBuy(true);
        dao.saveItem(item);
        dao.saveBid(bid);
        return bid;
    }

    @Transactional
    @Override
    public List<User> getUsers() {
        return dao.getUsers();
    }
    @Transactional
    @Override
    public List<Item> getItems() {
        return dao.getItems();
    }
    @Transactional
    @Override
    public List<Bid> getBids() {
        return dao.getBids();
    }
    @Transactional
    @Override
    public boolean auth(String username, String password) {
        List<User> userList = dao.getUsers();
        for (User user : userList) {
            if ((user.getName().equals(username)) && (user.getPassword().equals(password))) {
                return true;
            }
        }
        return false;
    }
    @Transactional
    @Override
    public boolean checkName(String username, String password) {
        List<User> userList = dao.getUsers();
        for (User user : userList) {
            if ((user.getName().equals(username))) {
                return true;
            }
        }
        return false;
    }
    @Transactional
    @Override
    public User getUser(String name) {
     return dao.getUser(name);
    }

    @Transactional
    @Override
    public Item getItem(Integer id) {
       return dao.getItem(id);
    }

    @Transactional
    @Override
    public Bid getBid(Integer id) {
        return dao.getBid(id);
    }

    @Transactional
    @Override
    public List<Item> getItemsForBuy(User user) {
        List<Item> list = dao.getItemsForBuy(user);
        return list;
    }
    @Transactional
    @Override
    public List<Item> getItemsForUser(User user) {
        List<Item> list = dao.getItemsForUser(user);
        return list;
    }

    @Transactional
    @Override
    public List<Bid> getBidsUser(User user) {
        List<Bid> list = dao.getBidsUser(user);
        return list;
    }
    @Transactional
    @Override
    public void dltBid(Bid bid) {
        dao.dltBid(dao.getBid(bid.getId()));
    }

    @Transactional
    @Override
    public void dltItem(Item item) {
        dao.dltItem(dao.getItem(item.getId()));
    }
    @Transactional
    @Override
    public void editItem(Item it, String name, String description) {
        Item item = dao.getItem(it.getId());
        item.setName(name);
        item.setDescription(description);
        dao.saveItem(item);

    }
    @Transactional
    @Override
    public void editUser(User us, String name, String password) {
        User user = dao.getUserById(us.getId());
        user.setName(name);
        user.setPassword(password);
        dao.saveUser(user);
    }
    @Transactional
    @Override
    public void dltUser(User user) {
        dao.dltUser(dao.getUserById(user.getId()));

    }

    @Transactional
    @Override
    public User dltUser(String name) {
        User user = dao.getUser(name);
        dao.dltUser(user);
        return user;
    }
}
