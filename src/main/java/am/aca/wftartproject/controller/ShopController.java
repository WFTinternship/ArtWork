package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.servlet.ItemComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class ShopController {

    private static final String NO_SORTING_PARAM = "-1";
    private ItemService itemService;
    private ArtistService artistService;

    @Autowired
    public ShopController(ItemService itemService, ArtistService artistService) {
        this.itemService = itemService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public ModelAndView shopInitialPage() {
        // Set initial information for shop.jsp page
        ModelAndView mav = new ModelAndView("shop");
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        mav.addObject("itemTypes", ItemType.values());
        mav.addObject("itemList", itemService.getRecentlyAddedItems(100));
        return mav;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public ModelAndView shopSortingProcess(@RequestParam("itemType") String itemTypeStr,
                                      @RequestParam("sortType") String sortingType) {
        ModelAndView mv = new ModelAndView("shop");

        // Get items list by client chosen sorting parameters
        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        try {
            if (!NO_SORTING_PARAM.equals(itemTypeStr)) {
                itemList = itemService.getItemsByType(itemTypeStr);
            }

            if (!NO_SORTING_PARAM.equals(sortingType)) {
                itemList = ItemComparator.getSortedItemList(sortingType, itemList);
            }

            // Add required attributes for shop.jsp page
            mv.addObject("itemList", itemList);
            mv.addObject("itemTypes", ItemType.values());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return mv;
    }

    @RequestMapping(value = "shop-cart", method = RequestMethod.GET)
    public ModelAndView shopCartView() {
        return new ModelAndView("shop-cart");
    }

    @RequestMapping(value = "/item-detail/{itemId}", method = RequestMethod.GET)
    public ModelAndView itemDetailView(HttpServletRequest request,
                                       @PathVariable("itemId") Long itemId) {
        HttpSession session = request.getSession();
        ModelAndView mv = new ModelAndView("item-detail");

        // Get required information and add attributes for view page
        Item itemById = itemService.findItem(itemId);
        List<Item> artistItems = itemService.getArtistItems(itemById.getArtistId(), itemById.getId(), 10L);
        Artist artist = artistService.findArtist(itemById.getArtistId());

        // Add attributes to the session object
        session.setAttribute("itemDetail", itemById);
        session.setAttribute("artistItems", artistItems);
        session.setAttribute("artistInfo", artist);

        return mv;
    }

    @RequestMapping(value = "/item-detail/*", method = RequestMethod.POST)
    public ModelAndView itemBuyingProcess(HttpServletRequest request,
                                   @SessionAttribute("itemDetail") Item item) {
        String page;

        // Get and check whether the user is authenticated
        AbstractUser abstractUser = (AbstractUser) request.getSession().getAttribute("user");
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        // Item buying process
        try {
            itemService.itemBuying(item, abstractUser.getId());
            page = "thank-you";
        } catch (NotEnoughMoneyException ex) {
            request.getSession().setAttribute("message",
                    "You don't have enough money. Please top-up your account and try again.");
            //TODO "message" session attribute should be removed after showing.
            page = "redirect:/shop";
        } catch (RuntimeException ex) {
            request.getSession().setAttribute("message",
                    "There is problem with website. Please try again");
            page = "redirect:/shop";
        }

        return new ModelAndView(page);
    }
}
