package am.aca.wftartproject.web;

import am.aca.wftartproject.controller.ItemComparator;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class ShopController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ArtistService artistService;

    @RequestMapping(value = "/shop",method = RequestMethod.GET)
    public ModelAndView shopPageView(){
        ModelAndView mav = new ModelAndView("shop");
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        mav.addObject("itemTypes", ItemType.values());
        mav.addObject("itemList", itemService.getRecentlyAddedItems(100));
        return mav;
    }
    @RequestMapping(value = "/shop",method = RequestMethod.POST)
    public ModelAndView shopProcessor(HttpServletRequest request){
        String itemTypeStr = request.getParameter("itemType");
        String sortingType = request.getParameter("sortType");

        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        try {
            if (!"-1".equals(itemTypeStr)) {
                itemList = itemService.getItemsByType(itemTypeStr);
            }

            if(!"-1".equals(sortingType)){
                itemList = ItemComparator.getSortedItemList(sortingType,itemList);
            }

            request.setAttribute("itemList", itemList);
            request.setAttribute("itemTypes", ItemType.values());


        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new ModelAndView("shop");
    }

    @RequestMapping(value = "shop-cart",method = RequestMethod.GET)
    public ModelAndView shopCartView(){
        return new ModelAndView("shop-cart");
    }

    @RequestMapping(value = "item-detail/*",method = RequestMethod.GET)
    public ModelAndView itemDetailView(HttpServletRequest request){
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);

        HttpSession session = request.getSession();

        Item itemById = itemService.findItem(itemId);

        session.setAttribute("itemDetail", itemById);
        session.setAttribute("artistItems", itemService.getArtistItems(itemById.getArtist_id()));
        session.setAttribute("artistInfo", artistService.findArtist(itemById.getArtist_id()));

        return new ModelAndView("item-detail");
    }

    @RequestMapping(value = "item-detail/*", method = RequestMethod.POST)
    public ModelAndView itemDetailProcess(HttpServletRequest request){
        String page = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return new ModelAndView("logIn");
        }
        if(session.getAttribute("user").getClass() == Artist.class){
            Artist artist = (Artist) session.getAttribute("user");
            try {
                Item item = (Item) session.getAttribute("itemDetail");
                itemService.itemBuying(item, artist.getId());
                page = "thank-you";
            } catch (NotEnoughMoneyException ex) {
                session.setAttribute("msgNotEnoughMoney",
                        "You don't have enough money. Please top-up your account and try again.");
                page = "redirect:/shop";
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                page = "index";
            }
        }
        if(session.getAttribute("user").getClass() == User.class){
            User user = (User)session.getAttribute("user");
            try {
                Item item = (Item) session.getAttribute("itemDetail");
                itemService.itemBuying(item, user.getId());
                page = "thank-you";
            } catch (NotEnoughMoneyException ex) {
                session.setAttribute("msgNotEnoughMoney",
                        "You don't have enough money. Please top-up your account and try again.");
                page = "redirect:/shop";
            } catch (RuntimeException ex) {
                page = "index";
            }
        }
        return new ModelAndView(page);
    }
}