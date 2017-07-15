package am.aca.wftartproject.controller;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.PurchaseHistoryServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Armen on 7/14/2017.
 */
public class AccountControllerUnitTest extends BaseUnitTest {
    @InjectMocks
    private AccountController accountController;
    private MockMvc mockMvc;
    private  User user;
    private PurchaseHistory purchaseHistory;
    @Mock
    UserService userService = new UserServiceImpl();
    @Mock
    PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryServiceImpl();


    @Before
    public void setUp() {
        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
       user = createTestUser();
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
    public void AccountPageView() throws Exception{
        //when user is not authorized
        mockMvc.perform(get("/account-details"))
                .andExpect(redirectedUrl("/signup"));

        //when authorized
        Assert.assertNotNull(user);
        doReturn(user).when(userService).findUser(user.getId());
        mockMvc.perform(get("/account-details").sessionAttr("user",user)).andExpect(forwardedUrl("account")).andExpect(status().isOk());
    }

    @Test
    public void editProfileView() throws Exception{
        //when user is non authorized
        mockMvc.perform(get("/edit-profile")).andExpect(redirectedUrl("/signup"));

        //when authorized
        Assert.assertNotNull(user);
        doReturn(user).when(userService).findUser(user.getId());
        mockMvc.perform(get("/edit-profile").sessionAttr("user",user)).andExpect(forwardedUrl("editProfile")).andExpect(status().isOk());
    }

    @Test
    public void editProfileUpdateSuccess() throws Exception{
        user.setId(5L);
        user.setFirstName("Test");
        user.setLastName("Testikyan");
        doReturn(user).when(userService).findUser(user.getId());
        doNothing().when(userService).updateUser(user);
        mockMvc.perform(post("/edit-profile")
                .sessionAttr("user",user)
                .param("firstName","aaa")
                .param("lastName","bbb"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("user",hasProperty("firstName",is("aaa"))))
                .andExpect(request().sessionAttribute("user",hasProperty("lastName",is("bbb"))))
                .andExpect(request().sessionAttribute("message",is("User updated successfully")))
                .andExpect(forwardedUrl("editProfile"));
    }
    @Test
    public void editProfileUpdateEmptyFields() throws Exception{
        user.setId(5L);
        user.setFirstName("Test");
        user.setLastName("Testikyan");
        doReturn(user).when(userService).findUser(user.getId());
        doNothing().when(userService).updateUser(user);
        mockMvc.perform(post("/edit-profile")
                .sessionAttr("user",user)
                .param("firstName","")
                .param("lastName",""))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("user",hasProperty("firstName",is(user.getFirstName()))))
                .andExpect(request().sessionAttribute("user",hasProperty("lastName",is(user.getLastName()))))
                .andExpect(request().sessionAttribute("message",is("No changes ,empty field or The entered info is not correct")))
                .andExpect(forwardedUrl("editProfile"));
    }

    @Test
    public void purchaseHistoryView() throws Exception {
        //create purchaselist an add purchasehistory
        List<PurchaseHistory> purchaseList = new ArrayList<>();
        purchaseList.add(purchaseHistory);

        //when user is non authorized
        mockMvc.perform(get("/purchase-history")).andExpect(redirectedUrl("/signup"));

        //when authorized
        Assert.assertNotNull(user);
        Assert.assertNotNull(purchaseList);
        doReturn(purchaseList).when(purchaseHistoryService).getPurchaseList(user.getId());
        mockMvc.perform(get("/purchase-history").sessionAttr("user",user)).andExpect(forwardedUrl("purchaseHistory")).andExpect(status().isOk());
    }

    @Test
    public void addItemViev()throws Exception {


    }

}
