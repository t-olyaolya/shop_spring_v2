package com.company.Controller;

import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;
import com.company.Service.ServiceShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.company.Service.LangBundle.bundle;

/**
 * Created by tyuly on 21.02.2017.
 */

@RestController
@RequestMapping("/")
@ComponentScan("com.company")
public class CRUDController {
    @Autowired
    ServiceShop service;

    //чтение всех
    @RequestMapping(value = { "crud/user" },method = RequestMethod.GET)
    public ResponseEntity<List<String>> crudUsers() {
        return allUsers();
    }

    @RequestMapping(value = { "crud/item" },method = RequestMethod.GET)
    public ResponseEntity<List<String>> crudItems() {
      return allItems();
    }

    @RequestMapping(value = { "crud/bid" },method = RequestMethod.GET)
    public ResponseEntity<List<String>> crudBids() {
        return allBids();
    }

    //чтение одного

    @RequestMapping(value = { "crud/user/{id}" },method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String crudGetUser(ModelMap model,  @PathVariable("id") Integer id) {
        try {
            User user = service.getById(id);
            return user.toString();
        }
        catch (NullPointerException e) {
            return  bundle.getString("err");
        }

    }

    @RequestMapping(value = { "crud/item/{id}" },method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String crudGetItem(ModelMap model,  @PathVariable("id") Integer id) {
        try {
            Item item = service.getItem(id);
            return item.toString();
        }
        catch (NullPointerException e) {
            return  bundle.getString("err");
        }
    }

    @RequestMapping(value = { "crud/bid/{id}" },method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public String crudGetBid(ModelMap model,  @PathVariable("id") Integer id) {
        try {
        Bid bid = service.getBid(id);
        return bid.toString();
        }
        catch (NullPointerException e) {
            return  bundle.getString("err");
        }
    }

    //удаление. удаляются только те сущности, которые не связаны с другими(например, пользователь без покупок)
    @RequestMapping(value = "crud/user/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        try {
            service.dltUser(service.getById(id));
            return new ResponseEntity<String>(  bundle.getString("completed"),HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<String>(bundle.getString("notdel"),HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "crud/item/{id}", method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> deleteItem(@PathVariable("id") Integer id) {
        try {
            service.dltItem(service.getItem(id));
            return new ResponseEntity<String>(  bundle.getString("completed"),HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<String>(bundle.getString("notdel"),HttpStatus.CONFLICT);
        }

    }


    @RequestMapping(value = "crud/bid/{id}", method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> deleteBid(@PathVariable("id") Integer id) {
        try {
            service.dltBid(service.getBid(id));
            return new ResponseEntity<String>(  bundle.getString("completed"),HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<String>(bundle.getString("notdel"),HttpStatus.CONFLICT);
        }

    }

    //добавление

 /*    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }
    @RequestMapping(value = "crud/users", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@ModelAttribute("user") User user, ModelMap model) {
        if(!service.checkName(user.getName().toLowerCase(),user.getPassword())) {
            service.createUser(user.getName(), user.getPassword());
            return new ResponseEntity<String>(bundle.getString("completed"), HttpStatus.OK);
        }
        else
            return new ResponseEntity<String>(bundle.getString("err"), HttpStatus.OK);
    }

    @ModelAttribute("item")
    public Item newItem() {
        return new Item();
    }
    @RequestMapping(value = "crud/items", method = RequestMethod.POST)
    public ResponseEntity<String> addItem(@ModelAttribute("item") Item item) {
            service.createItem(item.getName(),null, item.getDescription());
            return new ResponseEntity<String>(bundle.getString("completed"), HttpStatus.OK);

    } */
 @RequestMapping(value = "crud/user", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
 public ResponseEntity<String> createUser(@RequestBody User user) {
     try {
         if (!service.checkName(user.getName(),user.getPassword())) {
             service.createUser(user.getName(), user.getPassword());
             return new ResponseEntity<String>(bundle.getString("completed"), HttpStatus.CREATED);
         }
         else {
             return new ResponseEntity<String>(bundle.getString("already"), HttpStatus.CONFLICT);
         }
     }
     catch (Exception e) {
         return new ResponseEntity<String>(HttpStatus.CONFLICT);
     }
 }

    @RequestMapping(value = "crud/item", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> createItem(@RequestBody Item item) {
        try {
                service.createItem(item.getName(),item.getUser(),item.getDescription());
                return new ResponseEntity<String>(bundle.getString("completed"), HttpStatus.CREATED);
            }
        catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "crud/bid", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> createBid(@RequestBody Bid bid) {
        try {
            service.createBid(bid.getUser(), bid.getItem());
            return new ResponseEntity<String>(bundle.getString("completed"), HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    //изменение

    @RequestMapping(value = "crud/user/{id}", method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        User user1 = service.getById(id);

        if (user==null) {
            return new ResponseEntity<String>(bundle.getString("err"),HttpStatus.NOT_FOUND);
        }
        try {
            service.editUser(user1,user.getName(),user.getPassword());
            return new ResponseEntity<String>(user.toString(), HttpStatus.OK);}
        catch (Exception e) {
            return new ResponseEntity<String>(bundle.getString("notedit"), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "crud/item/{id}", method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseEntity<String> updateItem(@PathVariable("id") Integer id, @RequestBody Item item) {
        Item item1 = service.getItem(id);;

        if (item1==null) {
            return new ResponseEntity<String>(bundle.getString("err"),HttpStatus.NOT_FOUND);
        }
        try {
            service.editItem(item1, item.getName(), item.getDescription());
            return new ResponseEntity<String>(item.toString(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(bundle.getString("notedit"), HttpStatus.CONFLICT);
        }
    }


    public ResponseEntity<List<String>> allUsers () {
        List<User> list = service.getUsers();
        List<String> strings = new ArrayList<>();
        for (User user:list) {
            strings.add(user.toString());

        }
        return new ResponseEntity<List<String>>(strings, HttpStatus.OK);
    }

    public ResponseEntity<List<String>> allBids() {
        List<Bid> list = service.getBids();
        List<String> strings = new ArrayList<>();
        for (Bid bid:list) {
            strings.add(bid.toString());

        }
        return new ResponseEntity<List<String>>(strings, HttpStatus.OK);
    }

    public ResponseEntity<List<String>> allItems() {
        List<Item> list = service.getItems();
        List<String> strings = new ArrayList<>();
        for (Item item:list) {
            strings.add(item.toString());

        }
        return new ResponseEntity<List<String>>(strings, HttpStatus.OK);
    }
}
