package am.aca.wftartproject.controller.unit;

import am.aca.wftartproject.controller.BaseUnitTest;
import am.aca.wftartproject.controller.ShopController;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.util.controller.TestHttpSession;
import am.aca.wftartproject.util.controller.TestRedirectAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * @author surik
 */
public class ShopControllerUnitTest extends BaseUnitTest {
    private Item testItem;
    private Artist testArtist;
    private HttpSession testSession;
    private RedirectAttributes testRedirectAttributes;

    @InjectMocks
    private ShopController shopController;

    @Mock
    private ItemService itemServiceMock;
    @Mock
    private ArtistService artistServiceMock;
    @Mock
    private HttpServletRequest testRequestMock;

    /**
     * Creates testSession, testItem and testArtist for test
     */
    @Before
    public void setUp() {
        testItem = createTestItem();
        testArtist = createTestArtist();
        testSession = new TestHttpSession();
        testRedirectAttributes = new TestRedirectAttributes();
    }

    @After
    public void tearDown() {

    }

    // region<TEST CASE>

    /**
     * @see ShopController#shopInitialPage()
     */
    @Test
    public void shopInitialPage_Success() {
        List<Item> recentlyAddedItems = new ArrayList<>();

        // Setup mock
        doReturn(recentlyAddedItems).when(itemServiceMock).getRecentlyAddedItems(anyInt());

        // Test method
        ModelAndView actualModelAndView = shopController.shopInitialPage();

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "shop");
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("artistSpecTypes"), ArtistSpecialization.values()));
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertEquals(actualModelAndView.getModel().get("itemList"), recentlyAddedItems);
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeSuccess() {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        List<Item> recentlyAddedItems = new ArrayList<>();

        // Setup mocks
        doReturn(recentlyAddedItems).when(itemServiceMock).getRecentlyAddedItems(anyInt());
        doReturn(recentlyAddedItems).when(itemServiceMock).getItemsByType(argumentCaptor.capture());

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, testItem.getItemType().getType(), "-1");

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), recentlyAddedItems);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
        assertEquals(testItem.getItemType().getType(), argumentCaptor.getValue());
    }

    /**
     * @see ShopController#shopSortingProcess(RedirectAttributes, String, String)
     */
    @Test
    public void shopSortingProcess_sortByItemTypeEmptyList() {
        List<Item> recentlyAddedItems = new ArrayList<>();

        // Setup mocks
        doReturn(recentlyAddedItems).when(itemServiceMock).getRecentlyAddedItems(anyInt());
        doReturn(recentlyAddedItems).when(itemServiceMock).getItemsByType(anyString());

        // Test method
        ModelAndView actualModelAndView = shopController.shopSortingProcess(testRedirectAttributes, testItem.getItemType().getType(), "-1");

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "shop");
        assertEquals(actualModelAndView.getModel().get("itemList"), recentlyAddedItems);
        assertTrue(Arrays.equals((Object[]) actualModelAndView.getModel().get("itemTypes"), ItemType.values()));
    }

    /**
     * @see ShopController#itemDetailView(HttpServletRequest, Long)
     */
    @Test
    public void itemDetailView_findItemSuccess() {
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Long itemId = 5L;
        List<Item> artistItems = new ArrayList<>();

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doReturn(testItem).when(itemServiceMock).findItem(argumentCaptor.capture());
        doReturn(artistItems).when(itemServiceMock).getArtistItems(anyLong(), argumentCaptor.capture(), anyLong());
        doReturn(testArtist).when(artistServiceMock).findArtist(anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemDetailView(testRequestMock, itemId);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("item-detail"));
        assertEqualItems((Item) testSession.getAttribute("itemDetail"), testItem);
        assertEquals(testSession.getAttribute("artistItems"), artistItems);
        assertEqualArtists((Artist) testSession.getAttribute("artistInfo"), testArtist);
    }

    /**
     * @see ShopController#itemDetailView(HttpServletRequest, Long)
     */
    @Test
    public void itemDetailView_findItemWithInvalidIdFailure() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doThrow(InvalidEntryException.class).when(itemServiceMock).findItem(anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemDetailView(testRequestMock, -5L);

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "item-detail");
        assertNotNull(actualModelAndView.getModel().get("message"));
        assertNull(actualModelAndView.getModel().get("itemDetail"));
        assertNull(actualModelAndView.getModel().get("artistItems"));
        assertNull(actualModelAndView.getModel().get("artistInfo"));
    }

    /**
     * @see ShopController#itemDetailView(HttpServletRequest, Long)
     */
    @Test
    public void itemDetailView_findItemFailure() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doReturn(null).when(itemServiceMock).findItem(anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemDetailView(testRequestMock, -5L);

        // Assertions
        assertEquals(actualModelAndView.getViewName(), "item-detail");
        assertNotNull(actualModelAndView.getModel().get("message"));
        assertNull(actualModelAndView.getModel().get("itemDetail"));
        assertNull(actualModelAndView.getModel().get("artistItems"));
        assertNull(actualModelAndView.getModel().get("artistInfo"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_GetUserFromSessionItemBuyingSuccess() {
        testSession.setAttribute("user", testArtist);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doNothing().when(itemServiceMock).itemBuying(any(Item.class), anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequestMock, testRedirectAttributes, testItem);

        assertEqualModelAndViews(actualModelAndView, new ModelAndView("thank-you"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_sessionUserNullFailure() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequestMock, testRedirectAttributes, testItem);

        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/login"));
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_notEnoughMoneyFailure() {
        // Error message
        String errorMessage = "You don't have enough money. Please top-up your account and try again.";

        // Set user in session
        testSession.setAttribute("user", testArtist);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doThrow(NotEnoughMoneyException.class).when(itemServiceMock).itemBuying(any(Item.class), anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequestMock, testRedirectAttributes, testItem);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/shop"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
    }

    /**
     * @see ShopController#itemBuyingProcess(HttpServletRequest, RedirectAttributes, Item)
     */
    @Test
    public void itemBuyingProcess_ItemBuyingFailure() {
        // Error message
        String errorMessage = "There is problem with website. Please try again";

        // Set user in session
        testSession.setAttribute("user", testArtist);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doThrow(ServiceException.class).when(itemServiceMock).itemBuying(any(Item.class), anyLong());

        // Test method
        ModelAndView actualModelAndView = shopController.itemBuyingProcess(testRequestMock, testRedirectAttributes, testItem);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/shop"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
    }

    // endregion
}
