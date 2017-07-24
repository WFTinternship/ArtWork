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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class ShopController {

    private ItemService itemService;
    private ArtistService artistService;
    private static final String NO_SORTING_PARAM = "-1";
    private static final String MESSAGE_ATTR = "message";

    @Autowired
    public ShopController(ItemService itemService, ArtistService artistService) {
        this.itemService = itemService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public ModelAndView shopInitialPage() {
        // Get items and shuffle for randomize viewing
        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        Collections.shuffle(itemList);

        // Set initial information for shop.jsp page
        ModelAndView mav = new ModelAndView("shop");
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        mav.addObject("itemTypes", ItemType.values());
        mav.addObject("itemList", itemList);
        return mav;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public ModelAndView shopSortingProcess(RedirectAttributes redirectAttributes,
                                           @RequestParam("itemType") String itemTypeStr,
                                           @RequestParam("sortType") String sortingType) {
        ModelAndView mv = new ModelAndView();
        String page = "shop";

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
            page = "redirect:/shop";
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR, "There is problem with item list retrieving");
        }
        mv.setViewName(page);
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
        try {
            Item itemById = itemService.findItem(itemId);

            if (itemById == null)
                throw new RuntimeException("Item by this id is not found");

            List<Item> artistItems = itemService.getArtistItems(itemById.getArtistId(), itemById.getId(), 10L);
            Artist artist = artistService.findArtist(itemById.getArtistId());

            // Add attributes to the session object
            session.setAttribute("itemDetail", itemById);
            session.setAttribute("artistItems", artistItems);
            session.setAttribute("artistInfo", artist);
        } catch (RuntimeException exc) {
            mv.addObject("message", "There was a problem: " + exc.getMessage());
        }

        return mv;
    }

    @RequestMapping(value = "/item-detail/*", method = RequestMethod.POST)
    public ModelAndView itemBuyingProcess(HttpServletRequest request,
                                          RedirectAttributes redirectAttributes,
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
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR,
                    "You don't have enough money. Please top-up your account and try again.");
            page = "redirect:/shop";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR,
                    "There is problem with website. Please try again");
            page = "redirect:/shop";
        }
        return new ModelAndView(page);
    }
}
