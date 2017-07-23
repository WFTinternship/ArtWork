package am.aca.wftartproject.controller.integration;

import am.aca.wftartproject.controller.BaseIntegrationTest;
import am.aca.wftartproject.controller.ShopController;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.servlet.ItemComparator;
import am.aca.wftartproject.util.controller.TestHttpServletRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private ShopController shopController;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ShoppingCardService shoppingCardService;

    @Before
    public void setUp() {
        testRequest = new TestHttpServletRequest();
        testArtist = createTestArtist();
        testItem = createTestItem();
        artistService.addArtist(testArtist);
        itemService.addItem(testArtist.getId(), testItem);
    }

    @After
    public void tearDown() {
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
     * @see ShopController#shopSortingProcess(String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeSuccess() {
        List<Item> itemList = itemService.getItemsByType(testItem.getItemType().getType());

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testItem.getItemType().getType(), "-1");

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
    }

    /**
     * @see ShopController#shopSortingProcess(String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeFailure() {
        List<Item> items = new ArrayList<>();

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess("fakeType", "-1");

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), items);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
    }

    /**
     * @see ShopController#shopSortingProcess(String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemPriceSuccess() {
        String sortingByPriceIdStr = "1";
        List<Item> itemList = itemService.getRecentlyAddedItems(100);
        itemList = ItemComparator.getSortedItemList(sortingByPriceIdStr, itemList);

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess("-1", sortingByPriceIdStr);

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
    }

    /**
     * @see ShopController#shopSortingProcess(String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemPriceFailure() {
        String fakeSortingId = "fakeId";

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess("-1", fakeSortingId);


        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("message"), "There is problem with item list retrieving");
        assertNull(actualModelAndView.getModel().get("itemList"));
        assertNull(actualModelAndView.getModel().get("itemTypes"));
    }

    /**
     * @see ShopController#shopSortingProcess(String, String)
     */
    @Test
    public void shopSortingProcess_sortItemsByTypesAndItemType_Success() {
        String sortingByPriceIdStr = "1";
        List<Item> itemList = itemService.getItemsByType(testItem.getItemType().getType());
        itemList = ItemComparator.getSortedItemList(sortingByPriceIdStr, itemList);

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testItem.getItemType().getType(), sortingByPriceIdStr);

        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), itemList);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
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
     * @see ShopController#itemBuyingProcess(HttpServletRequest, Item)
     */
    @Test
    public void itemBuyingProcess() {
//TODO
    }

    // endregion
}
