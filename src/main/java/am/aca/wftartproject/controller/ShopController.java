package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.util.ItemComparator;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class ShopController extends ControllerHelper {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ArtistService artistService;

    @RequestMapping(value = "/shop",method = RequestMethod.GET)
    public ModelAndView shopPageView()
    {
        ModelAndView mav = new ModelAndView("shop");
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        mav.addObject("itemTypes", ItemType.values());
        mav.addObject("itemList", itemService.getRecentlyAddedItems(100));
        return mav;
    }
    @RequestMapping(value = "/shop",method = RequestMethod.POST)
    public ModelAndView shopProcessor(HttpServletRequest request)
    {
        String itemTypeStr = request.getParameter("itemType");
        String sortingType = request.getParameter("sortType");
        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        try
        {
            if (!"-1".equals(itemTypeStr))
            {
                itemList = itemService.getItemsByType(ItemType.valueOf(itemTypeStr));
            }

            if(!"-1".equals(sortingType))
            {
                itemList = ItemComparator.getSortedItemList(sortingType,itemList);
            }
            request.setAttribute("itemList", itemList);
            request.setAttribute("itemTypes", ItemType.values());
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }
        return new ModelAndView(SHOP);
    }

    @RequestMapping(value = "shop-cart",method = RequestMethod.GET)
    public ModelAndView shopCartView(){
        return new ModelAndView("shop-cart");
    }

    @RequestMapping(value = "item-detail/*",method = RequestMethod.GET)
    public ModelAndView itemDetailView(HttpServletRequest request)
    {
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
        HttpSession session = request.getSession();
        Item itemById = itemService.findItem(itemId);
        session.setAttribute("itemDetail", itemById);
        session.setAttribute("artistItems", itemService.getArtistItems(itemById.getArtist().getId()));
        session.setAttribute("artistInfo", artistService.findArtist(itemById.getArtist().getId()));
        return new ModelAndView(ITEM_DETAIL);
    }

    @RequestMapping(value = "item-detail/*", method = RequestMethod.POST)
    public ModelAndView itemDetailProcess(HttpServletRequest request){
        HttpSession session = request.getSession();
        Item item = (Item) session.getAttribute("itemDetail");
        String page = "";
        if(session.getAttribute(USER)!=null)
        {
            AbstractUser abstractUser = (AbstractUser) session.getAttribute(USER);
            try
            {
                itemService.itemBuying(item, abstractUser);
                page = THANK_YOU;
            }
            catch (NotEnoughMoneyException ex)
            {
                session.setAttribute("msgNotEnoughMoney", "You don't have enough money. Please top-up your account and try again.");
                page = REDIRECT_SHOP;
            }
            catch (RuntimeException ex)
            {
                System.out.println(ex.getMessage());
                page = HOME;
            }
        }
        else page = LOGIN;
        return new ModelAndView(page);
    }
}
