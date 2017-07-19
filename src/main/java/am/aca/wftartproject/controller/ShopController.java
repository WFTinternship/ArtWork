package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.util.ItemComparator;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class ShopController extends ControllerHelper {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LogInController.class);

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public ModelAndView shopPageView() {

        //create new m.a.v and set appropriate objects and forward to shop page
        ModelAndView mav = new ModelAndView("shop");
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        mav.addObject("itemTypes", ItemType.values());
        mav.addObject("itemList", itemService.getRecentlyAddedItems());

        return mav;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public ModelAndView shopProcessor(HttpServletRequest request) {

        //get item type and sort type parameters for items sorting in shop page
        String itemTypeStr = request.getParameter("itemType");
        String sortingType = request.getParameter("sortType");
        List<Item> itemList = itemService.getRecentlyAddedItems();

        //check attribute parameters for valid value
        try {
            if (!"-1".equals(itemTypeStr)) {
                itemList = itemService.getItemsByType(ItemType.valueOf(itemTypeStr));
            }

            if (!"-1".equals(sortingType)) {
                itemList = ItemComparator.getSortedItemList(sortingType, itemList);
            }

            //set items list and types in request
            request.setAttribute("itemList", itemList);
            request.setAttribute("itemTypes", ItemType.values());

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }

        return new ModelAndView(SHOP);
    }

    @RequestMapping(value = "shop-cart", method = RequestMethod.GET)
    public ModelAndView shopCartView() {
        return new ModelAndView("shop-cart");
    }

    @RequestMapping(value = "item-detail/*", method = RequestMethod.GET)
    public ModelAndView itemDetailView(HttpServletRequest request) {
        HttpSession session = request.getSession();

        //get item id from servlet path
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);

        // find appropriate item by id  from db
        Item itemById = itemService.findItem(itemId);

        //set retrieved item , artists items by item id , and artist info from db
        session.setAttribute("itemDetail", itemById);
        session.setAttribute("artistItems", itemService.getArtistItems(itemById.getArtist().getId()));
        session.setAttribute("artistInfo", artistService.findArtist(itemById.getArtist().getId()));

        return new ModelAndView(ITEM_DETAIL);
    }

    @RequestMapping(value = "item-detail/*", method = RequestMethod.POST)
    public ModelAndView itemDetailProcess(HttpServletRequest request) {
        String page;
        //get current session  and get Item attribute from it
        HttpSession session = request.getSession();
        Item item = (Item) session.getAttribute("itemDetail");

        //retrieve User from db and process item buying method
        if (session.getAttribute(USER) != null) {
            AbstractUser abstractUser = (AbstractUser) session.getAttribute(USER);
            try {
                itemService.itemBuying(item, abstractUser);
                page = THANK_YOU;
            } catch (NotEnoughMoneyException ex) {
                session.setAttribute("msgNotEnoughMoney", "You don't have enough money. Please top-up your account and try again.");
                page = REDIRECT_SHOP;
            } catch (RuntimeException ex) {
                LOGGER.error(ex.getMessage());
                page = HOME;
            }
        } else page = LOGIN;

        return new ModelAndView(page);
    }
}
