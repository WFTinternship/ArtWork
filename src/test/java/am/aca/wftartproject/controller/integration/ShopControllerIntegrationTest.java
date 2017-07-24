package am.aca.wftartproject.controller.integration;

import am.aca.wftartproject.controller.BaseIntegrationTest;
import am.aca.wftartproject.controller.ShopController;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.servlet.ItemComparator;
import am.aca.wftartproject.util.controller.TestHttpServletRequest;
import am.aca.wftartproject.util.controller.TestHttpSession;
import am.aca.wftartproject.util.controller.TestRedirectAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestItem;
import static junit.framework.TestCase.*;

/**
 * @author surik
 */
public class ShopControllerIntegrationTest extends BaseIntegrationTest {
    private Item testItem;
    private Artist testArtist;
    private TestHttpServletRequest testRequest;
    private TestRedirectAttributes testRedirectAttributes;

    @Autowired
    private ShopController shopController;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ShoppingCardService shoppingCardService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @Before
    public void setUp() {
        testRequest = new TestHttpServletRequest();
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setPrice(100.0);
        artistService.addArtist(testArtist);
        itemService.addItem(testArtist.getId(), testItem);
        testRedirectAttributes = new TestRedirectAttributes();
    }

    @After
    public void tearDown() {
        if (purchaseHistoryService.getPurchase(testArtist.getId(), testItem.getId()) != null) {
            purchaseHistoryService.deletePurchase(testArtist.getId(), testItem.getId());
        }

        if (testArtist.getShoppingCard() != null) {
            if (testArtist.getShoppingCard().getId() != null) {
                shoppingCardService.deleteShoppingCardByBuyerId(testArtist.getId());
            }
        }

        if (testItem.getId() != null) {
            itemService.deleteItem(testItem.getId());
        }

        if (testArtist.getId() != null) {
            artistService.deleteArtist(testArtist.getId());
        }

        testArtist = null;
        testItem = null;
    }

    // region <TEST CASE>

    /**
     * @see ShopController#shopInitialPage()
     */
    @Test
    public void shopInitialPage() {
        // Test method
        ModelAndView actualModelAndView = shopController.shopInitialPage();

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "shop");
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("artistSpecTypes"), ArtistSpecialization.values()));
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertNotNull(actualModelAndView.getModel().get("itemList"));
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeSuccess() {
        List<Item> itemList = itemService.getItemsByType(testItem.getItemType().getType());

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, testItem.getItemType().getType(), "-1");

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeFailure() {
        List<Item> items = new ArrayList<>();

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, "fakeType", "-1");

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), items);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemPriceSuccess() {
        String sortingByPriceIdStr = "1";
        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        itemList = ItemComparator.getSortedItemList(sortingByPriceIdStr, itemList);

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, "-1", sortingByPriceIdStr);

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemPriceFailure() {
        String errorMessage = "There is problem with item list retrieving";
        String fakeSortingId = "fakeId";

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, "-1", fakeSortingId);


        assertEquals(actualModelAndView.getViewName(), "redirect:/shop");
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
        assertNull(actualModelAndView.getModel().get("itemList"));
        assertNull(actualModelAndView.getModel().get("itemTypes"));
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortItemsByTypesAndItemType_Success() {
        String sortingByPriceIdStr = "1";
        List<Item> itemList = itemService.getItemsByType(testItem.getItemType().getType());
        itemList = ItemComparator.getSortedItemList(sortingByPriceIdStr, itemList);

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, testItem.getItemType().getType(), sortingByPriceIdStr);

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see ShopController#shopCartView()
     */
    @Test
    public void shopCartView() {
        // Test method
        ModelAndView actualModelAndView = shopController.shopCartView();

        assertEqualModelAndViews(actualModelAndView, new ModelAndView("shop-cart"));
    }

    /**
     * @see ShopController#itemDetailView(HttpServletRequest, Long)
     */
    @Test
    public void itemDetailView_Success() {
        List<Item> artistItems = itemService.getArtistItems(testArtist.getId(), testItem.getId(), 10L);
        
        // Test method
        ModelAndView actualModelAndView = shopController.itemDetailView(testRequest, testItem.getId());
        
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("item-detail"));
        assertEqualItems((Item) testRequest.getSession().getAttribute("itemDetail"), testItem);
        assertEquals(testRequest.getSession().getAttribute("artistItems"), artistItems);
        assertEqualArtists((Artist) testRequest.getSession().getAttribute("artistInfo"), testArtist);
    }

    /**
     * @see ShopController#itemDetailView(HttpServletRequest, Long)
     */
    @Test
    public void itemDetailView_itemFindFailure() {
        Long fakeId = 8000L;

        // Test method
        ModelAndView actualModelAndView = shopController.itemDetailView(testRequest, fakeId);

        assertNotNull(actualModelAndView.getModel().get("message"));
        assertEquals(actualModelAndView.getViewName(), "item-detail");
        assertNull(testRequest.getSession().getAttribute("itemDetail"));
        assertNull(testRequest.getSession().getAttribute("artistItems"));
        assertNull(testRequest.getSession().getAttribute("artistInfo"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_NoUserInRequest() {
        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequest, testRedirectAttributes, testItem);

        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/login"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_Success() {
        // Create testSession with user attribute
        HttpSession sessionWithUser = new TestHttpSession();
        sessionWithUser.setAttribute("user", testArtist);
        testRequest.setHttpSession(sessionWithUser);

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequest, testRedirectAttributes, testItem);

        assertEqualModelAndViews(actualModelAndView, new ModelAndView("thank-you"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_NotEnoughMoneyFailure() {
        // Error message
        String errorMessage = "You don't have enough money. Please top-up your account and try again.";

        // Set testArtist balance 0.0
        testArtist.getShoppingCard().setBalance(0.0);
        artistService.updateArtist(testArtist.getId(), testArtist);
        shoppingCardService.updateShoppingCard(testArtist.getShoppingCard().getId(), testArtist.getShoppingCard());

        // Create testSession with user attribute
        HttpSession sessionWithUser = new TestHttpSession();
        sessionWithUser.setAttribute("user", testArtist);
        testRequest.setHttpSession(sessionWithUser);

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequest, testRedirectAttributes, testItem);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/shop"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_Failure() {
        // Error message
        String errorMessage = "There is problem with website. Please try again";

        // Create testSession with user attribute
        HttpSession sessionWithUser = new TestHttpSession();
        sessionWithUser.setAttribute("user", testArtist);
        testRequest.setHttpSession(sessionWithUser);

        testItem.setStatus(true);
        itemService.updateItem(testItem.getId(), testItem);

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequest, testRedirectAttributes, testItem);


        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/shop"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
    }

    // endregion
}
