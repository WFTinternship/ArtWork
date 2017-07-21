package am.aca.wftartproject.controller;

import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.service.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armen on 7/14/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class AccountControllerUnitTest extends BaseUnitTest {
    private MockMvc mockMvc;
    private User user;
    private Artist artist;
    private PurchaseHistory purchaseHistory;
    @Mock
    UserService userService ;
    @Mock
    ArtistService artistService ;
    @Mock
    ItemService itemService ;
    @Mock
    PurchaseHistoryService purchaseHistoryService ;
    @InjectMocks
    AccountController accountController;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        user = createTestUser();
        artist = createTestArtist();
        purchaseHistory = createTestPurchaseHistory();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

//    @After
//    public void tearDown() {
//        //delete test user object
//        testUser = null;
//        userService = null;
//    }

    @Test
    public void AccountPageView() throws Exception {
        //when user is not authorized
        mockMvc.perform(get("/account-details"))
                .andExpect(redirectedUrl(ControllerHelper.SIGNUP));

        //when authorized
        Assert.assertNotNull(user);
        doReturn(user).when(userService).findUser(user.getId());
        mockMvc.perform(get("/account-details")
                .sessionAttr("user", user))
                .andExpect(forwardedUrl(ControllerHelper.ACCOUNT))
                .andExpect(status().isOk());
    }

    @Test
    public void editProfileView() throws Exception {
        //when user is non authorized
        mockMvc.perform(get("/edit-profile"))
                .andExpect(redirectedUrl(ControllerHelper.SIGNUP));

        //when authorized
        Assert.assertNotNull(user);
        doReturn(user).when(userService).findUser(user.getId());
        mockMvc.perform(get("/edit-profile")
                .sessionAttr("user", user))
                .andExpect(forwardedUrl(ControllerHelper.EDIT_PROFILE))
                .andExpect(status().isOk());
    }

    @Test
    public void editProfileUpdateSuccess_Post() throws Exception {
        user.setId(5L);
        user.setFirstName("Test");
        user.setLastName("Testikyan");
        doReturn(user).when(userService).findUser(user.getId());
        doNothing().when(userService).updateUser(user);
        //test
        mockMvc.perform(post("/edit-profile")
                .sessionAttr("user", user)
                .param("firstName", "aaa")
                .param("lastName", "bbb"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("user", hasProperty("firstName", is("aaa"))))
                .andExpect(request().sessionAttribute("user", hasProperty("lastName", is("bbb"))))
                .andExpect(request().attribute("message", is("User updated successfully")))
                .andExpect(forwardedUrl(ControllerHelper.EDIT_PROFILE));
    }

    @Test
    public void editProfileUpdateEmptyFields_Post() throws Exception {
        user.setId(5L);
        user.setFirstName("Test");
        user.setLastName("Testikyan");
        doReturn(user).when(userService).findUser(user.getId());
        doNothing().when(userService).updateUser(user);

        //testինգ մետհօդ
        mockMvc.perform(post("/edit-profile")
                .sessionAttr("user", user)
                .param("firstName", "")
                .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("user", hasProperty("firstName", is(user.getFirstName()))))
                .andExpect(request().sessionAttribute("user", hasProperty("lastName", is(user.getLastName()))))
                .andExpect(request().attribute("message", is("No changes ,empty field or The entered info is not correct")))
                .andExpect(forwardedUrl(ControllerHelper.EDIT_PROFILE));
    }

    @Test
    public void purchaseHistoryView() throws Exception {
        //create purchaselist an add purchasehistory
        List<PurchaseHistory> purchaseList = new ArrayList<>();
        purchaseList.add(purchaseHistory);

        //when user is non authorized
        mockMvc.perform(get("/purchase-history"))
                .andExpect(redirectedUrl(ControllerHelper.SIGNUP));

        //when authorized
        Assert.assertNotNull(user);
        Assert.assertNotNull(purchaseList);
        doReturn(purchaseList).when(purchaseHistoryService).getPurchaseList(user.getId());
        mockMvc.perform(get("/purchase-history")
                .sessionAttr("user", user))
                .andExpect(forwardedUrl(ControllerHelper.PURCHASE_HISTORY))
                .andExpect(status().isOk());
    }

    @Test
    public void addItemPage_Get() throws Exception {
        //when user is non authorized
        mockMvc.perform(get("/add-item")).andExpect(redirectedUrl(ControllerHelper.SIGNUP));

        //when authorized
        Assert.assertNotNull(user);
        mockMvc.perform(get("/add-item")
                .sessionAttr("user", user))
                .andExpect(forwardedUrl(ControllerHelper.ADD_ITEM))
                .andExpect(request().sessionAttribute("itemTypes", is(ItemType.values())))
                .andExpect(status().isOk());
    }

    @Test
    public void addItemPage_Post() throws Exception {
        artist.setId(5L);
        artist.setFirstName("Test");
        artist.setLastName("Testikyan");
        FileInputStream file =new FileInputStream(new File("C:\\Users\\Armen\\ArtWork\\src\\main\\webapp\\resources\\images\\avatar.jpg") );
        MockMultipartFile multipartFile = new MockMultipartFile("files", "pic", "multipart/form-data",file);
        doReturn(artist).when(artistService).findArtist(anyLong());
        Item testItem = createTestItem();
        testItem.setId(5L);
        doNothing().when(itemService).addItem(anyObject());
        //test
        mockMvc.perform(fileUpload("/add-item")
                .file(multipartFile)
                .sessionAttr("user", artist)
                .param("title", "aaa")
                .param("description", "bbb")
                .param("type", "PAINTING")
                .param("price", "25"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("item", hasProperty("title", is("aaa"))))
                .andExpect(request().sessionAttribute("item", hasProperty("description", is("bbb"))))
                .andExpect(request().sessionAttribute("item", hasProperty("itemType", is(ItemType.valueOf("PAINTING")))))
                .andExpect(request().attribute("message", is("Your ArtWork has been successfully added, Now you can see it in My ArtWorks page")))
                .andExpect(forwardedUrl(ControllerHelper.ADD_ITEM));
    }

}
