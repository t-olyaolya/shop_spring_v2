package com.company.Controller;

import com.company.Model.Bid;
import com.company.Model.Item;
import com.company.Model.User;
import com.company.Service.ServiceShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.company.Service.LangBundle.bundle;
import static com.company.Service.LangBundle.locale;

/**
 * Created by tyuly on 20.02.2017.
 */
@Controller
@RequestMapping("/")
@ComponentScan("com.company")
public class MainController {
    User user = null;
    Item item = null;

   // @Autowired
    ServiceShop service;

    @Autowired
    MainController(ServiceShop service) {
        this.service = service;
        service.fillDB();
    }

    @RequestMapping(value = { "/" },method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        return welcome(model);

    }

    public String welcome(ModelMap model) {
        if (user != null) {
            List<Item> list = service.getItemsForBuy(user);
            model.addAttribute("user", user.getName());
            model.addAttribute("list", list);
            return "shop";
        }
        else {
            List<Item> list = service.getItemsForBuy(user);
            model.addAttribute("info", bundle.getString("noLog"));
            model.addAttribute("shop", bundle.getString("shop"));
            model.addAttribute("shop1", bundle.getString("shop"));
            model.addAttribute("choose", bundle.getString("choose"));
            model.addAttribute("reg", bundle.getString("reg"));
            model.addAttribute("log", bundle.getString("log"));
            model.addAttribute("item", bundle.getString("item"));
            model.addAttribute("desc", bundle.getString("desc"));
            model.addAttribute("list", list);
            return "welcome";
        }
    }

    @RequestMapping(value = { "/lang" },method = RequestMethod.GET)
    public String lang(ModelMap model) {
        return "lang";
    }

    @RequestMapping(value = { "/lang_ru" },method = RequestMethod.GET)
    public String lang_ru(ModelMap model) {
        locale = new Locale("ru");
        bundle = ResourceBundle.getBundle("message", locale);
        return welcome(model);
    }

    @RequestMapping(value = { "/lang_en" },method = RequestMethod.GET)
    public String lang_en(ModelMap model) {
        locale = new Locale("en");
        bundle = ResourceBundle.getBundle("message", locale);
        return welcome(model);
    }

    @RequestMapping(value = { "/registry" },method = RequestMethod.GET)
    public String registry(ModelMap model) {
        return "registry";
    }

    @RequestMapping(value = {"/reg"}, method = RequestMethod.GET)
    public String reg(ModelMap model, @RequestParam("name") String name, @RequestParam("password") String password) {
        if (!service.checkName(name.toLowerCase(),password)) {
            service.createUser(name, password);
            model.addAttribute("info",  bundle.getString("completed"));
        } else {
            model.addAttribute("info",  bundle.getString("already"));
        }
        return "info";
    }

    @RequestMapping(value = {"/log"}, method = RequestMethod.GET)
    public String log(ModelMap model, @RequestParam("name") String name, @RequestParam("password") String password) {
        if (service.auth(name,password)) {
            user = service.getUser(name);
            List<Item> list = service.getItemsForBuy(user);
            model.addAttribute("user",user.getName());
            model.addAttribute("list", list);
            return "shop";

        } else {
            model.addAttribute("info",  bundle.getString("incor"));
            return "info";
        }
    }

    @RequestMapping(value = { "/login" },method = RequestMethod.GET)
    public String log(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = { "/account" },method = RequestMethod.GET)
    public String acc(ModelMap model) {
        return "account";
    }

    @RequestMapping(value = { "/mybids" },method = RequestMethod.GET)
    public String myBids(ModelMap model)  {
        List<Bid> list = service.getBidsUser(user);
        List<Item> item = new ArrayList<>();
        for (Bid bid : list) {
            item.add(bid.getItem());
        }

        model.addAttribute("user", user.getName());
        model.addAttribute("list", item);
        return "userBids";
    }

    @RequestMapping(value = { "/myitems" },method = RequestMethod.GET)
    public String myItems(ModelMap model) {
        List<Item> list = service.getItemsForUser(user);
        model.addAttribute("user", user.getName());
        model.addAttribute("list", list);
        return "myItems";
    }

    @RequestMapping(value = { "/allitems" },method = RequestMethod.GET)
    public String allItems(ModelMap model){
        List<Item> list = service.getItems();
        model.addAttribute("user",user.getName());
        model.addAttribute("list", list);
        return "allItems";
    }

    @RequestMapping(value = { "/edituser" },method = RequestMethod.GET)
    public String editUser(ModelMap model)  {
        return "editUser";
    }

    @RequestMapping(value = {"useraction"}, method = RequestMethod.GET)
    public String useraction(ModelMap model,@RequestParam("name") String name, @RequestParam("password") String password) {
        service.editUser(user, name, password);
        user.setName(name);
        model.addAttribute("info", bundle.getString("chData"));
        return "info_shop";
    }

    @RequestMapping(value = { "/dltuser" },method = RequestMethod.GET)
    public String dltUser(ModelMap model) {
        try {
            service.dltUser(user);
            user = null;
            model.addAttribute("info",bundle.getString("delus"));
            return "info";
        }
        catch (Exception e) {
            model.addAttribute("info",bundle.getString("notdel"));
            return "info_shop";

        }

    }

    @RequestMapping(value = { "/logout" },method = RequestMethod.GET)
    public String logout(ModelMap model) {
        user = null;
        model.addAttribute("info", bundle.getString("out"));
        return "info";

    }

    @RequestMapping(value = {"/buyItem/{id}"}, method = RequestMethod.GET)
    public String buy(ModelMap model, @PathVariable("id") Integer id) {
        User user1 = service.getUser(user.getName());
        Item item = service.getItem(id);
        service.createBid(user1, item);
        model.addAttribute("info", bundle.getString("bought"));
        return "info_shop";
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public String edit(ModelMap model, @PathVariable("id") Integer id) {
        item = service.getItem(id);
        return "editItem";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String addItem(ModelMap model) {
        return "newItem";
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public String deleteItem(ModelMap model, @PathVariable("id") Integer id) {
        Item item = service.getItem(id);
        service.dltItem(item);
        model.addAttribute("info",  bundle.getString("iDel"));
        return  "info_shop";
    }

    @RequestMapping(value = {"/edit/itemaction"}, method = RequestMethod.GET)
    public String editItem(ModelMap model, @RequestParam("name") String name,@RequestParam("description") String description) {
        service.editItem(item, name, description);
        model.addAttribute("info", bundle.getString("chData"));
        return "info_shop";
    }

    @RequestMapping(value = {"/itemaction"}, method = RequestMethod.GET)
    public String AddItem(ModelMap model, @RequestParam("name") String name,@RequestParam("description") String description) {
        service.createItem(name, service.getById(user.getId()), description);
        model.addAttribute("info",  bundle.getString("iAdd"));
        return "info_shop";

    }

}
