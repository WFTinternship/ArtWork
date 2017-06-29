package am.aca.wftartproject.web;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class AccountController {
    private User user;
    private User finduser;
    private Item item;
    private Artist artist;
    private Artist findArtist;
    private Cookie[] cookies;
    private String userEmailFromCookie = null;

    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/account"})
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        cookies = request.getCookies();
        String page = "redirect:/signup";

        try {
            if (session.getAttribute("user") != null) {
                if (session.getAttribute("user").getClass() == User.class) {
                    user = (User) session.getAttribute("user");
                    finduser = userService.findUser(user.getId());
                    if (user != null) {
                        request.setAttribute("user", finduser);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                } else if (session.getAttribute("user").getClass() == Artist.class) {
                    artist = (Artist) session.getAttribute("user");
                    findArtist = artistService.findArtist(artist.getId());
                    String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                    if (findArtist != null) {
                        request.getSession().setAttribute("image", image);
                        request.getSession().setAttribute("user", findArtist);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = String.format("There is problem with artist info retrieving: %s", e.getMessage());
            throw new RuntimeException(errorMessage, e);
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) {
        String page = "edit-profile";
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                finduser = userService.findUser(user.getId());
                request.setAttribute("user", finduser);
                if (user != null) {
                    response.setContentType("text/html");
                    if (request.getParameter("firstname") != null) {
                        finduser.setFirstName(request.getParameter("firstname"));
                    }
                    if (request.getParameter("lastname") != null) {
                        finduser.setLastName(request.getParameter("lasstname"));
                    }
                    if (request.getParameter("age") != null) {
                        finduser.setAge(Integer.parseInt(request.getParameter("age")));
                    }

                    try {
                        userService.updateUser(finduser.getId(), finduser);
                        request.getSession().setAttribute("user", finduser);
                        request.setAttribute("user", finduser);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage", errorMessage);

                    }
                    request.setAttribute("user", finduser);
                    request.getSession().setAttribute("user", finduser);
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                findArtist = artistService.findArtist(artist.getId());
                if (artist != null) {
                    response.setContentType("text/html");
                    if (request.getParameter("firstname") != null) {
                        findArtist.setFirstName(request.getParameter("firstname"));
                    }
                    if (request.getParameter("lastname") != null) {
                        findArtist.setLastName(request.getParameter("lastname"));
                    }
                    if (request.getParameter("age") != null) {
                        findArtist.setAge(Integer.parseInt(request.getParameter("age")));
                    }
                    if (request.getParameter("specialization") != null) {
                        findArtist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
                    }
                    if (request.getParameter("oldpassword") != null && request.getParameter("oldpassword").equals(findArtist.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        findArtist.setPassword(request.getParameter("newpassword"));
                    }
                    if ((image) != null) {
                        byte[] imageBytes = image.getBytes();
                        findArtist.setArtistPhoto(imageBytes);
                    }

                    try {
                        artistService.updateArtist(artist.getId(), findArtist);
                        request.getSession().setAttribute("user", findArtist);
                        request.setAttribute("user", findArtist);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage", errorMessage);
                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            }
        }
        return new ModelAndView("edit-profile");
    }

    @RequestMapping(value = {"purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistory(HttpServletRequest request, HttpServletResponse response) {
        String page = "purchase-history";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                request.setAttribute("purchaseHistory", purchaseHistoryService.getPurchase(user.getId()));
            }
            if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                request.setAttribute("purchaseHistory", purchaseHistoryService.getPurchase(artist.getId()));
            }
        } else {
            page = "redirect:/signup";
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {
        String page = "additem";
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        request.setAttribute("itemTypes", ItemType.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        item = new Item();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) session.getAttribute("user");
            findArtist = artistService.findArtist(artist.getId());
            if (findArtist != null) {
                request.setAttribute("user", findArtist);
                item.setTitle(request.getParameter("title"))
                        .setDescription(request.getParameter("description"))
                        .setItemType(ItemType.valueOf(request.getParameter("type")))
                        .setPrice(Double.parseDouble(request.getParameter("price")))
                        .setArtistId(artist.getId()).setStatus(true);
                if (image != null) {
                    byte[] imageBytes = image.getBytes();
                    String uploadPath = "resources/images/artists/" + artist.getId();
                    String realPath = request.getServletContext().getRealPath("resources/images/artists/" + artist.getId());
                    File uploadDir = new File(realPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    String fileName = new File(item.getTitle()).getName();
                    String filePath = realPath + File.separator + fileName + ".jpg";
                    FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
                    item.setPhotoURL(uploadPath + File.separator + fileName + ".jpg");

                }
            }

            try {
                itemService.addItem(artist.getId(), item);
            } catch (ServiceException e) {
                String errorMessage = "The entered info is not correct";
                request.setAttribute("errorMessage", errorMessage);
            }

        }
        return new ModelAndView("additem");
    }

    @RequestMapping(value = {"my-works"}, method = RequestMethod.GET)
    public ModelAndView myWorks(HttpServletRequest request, HttpServletResponse response) {
        String page = "my-works";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) request.getSession().getAttribute("user");
            request.setAttribute("artistItems", itemService.getArtistItems(artist.getId(), 8888L, 100L));
        } else page = "redirect:/signup";
        return new ModelAndView("my-works");
    }

}
