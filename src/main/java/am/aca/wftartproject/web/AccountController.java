package am.aca.wftartproject.web;

import am.aca.wftartproject.controller.CtxListener;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBean;
import am.aca.wftartproject.util.SpringBeanType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import java.io.InputStream;
import java.util.Base64;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class AccountController {
    User user;
    User finduser;
    Item item;
    Artist artist;
    Artist findArtist;
    Cookie[] cookies;
    String userEmailFromCookie = null;
    HttpSession session;
    @Autowired
    UserService userService;
    @Autowired
    ArtistService artistService;
    @Autowired
    PurchaseHistoryService purchaseHistoryService;
    @Autowired
    ItemService itemService;

    @RequestMapping(value = {"/account"})
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        cookies = request.getCookies();

        try {
            if (session.getAttribute("user") != null) {
                if (session.getAttribute("user").getClass() == User.class) {
                    user = (User) session.getAttribute("user");
                    finduser = userService.findUser(user.getId());
                    if (user != null) {
                        request.setAttribute("user", finduser);
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
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                }
            } else {
                if (cookies != null) {
                    for (Cookie ckElement : cookies) {
                        if (ckElement.getName().equals("userEmail")) {
                            userEmailFromCookie = ckElement.getValue();
                        }
                    }
                    if (userEmailFromCookie != null) {
                        if (artistService.findArtist(userEmailFromCookie) != null) {
                            Artist artistFromCookies = artistService.findArtist(userEmailFromCookie);
                            HttpSession sessionForArtist = request.getSession(true);
                            session.setAttribute("user", artistFromCookies);
                        } else {
                            if (userService.findUser(userEmailFromCookie) != null) {
                                User userFromCookies = userService.findUser(userEmailFromCookie);
                                HttpSession sessionForUser = request.getSession(true);
                                session.setAttribute("user", userFromCookies);
                            }
                        }
                    }
                } else return new ModelAndView("/signUp");
            }
        } catch (ServiceException e) {
            String errorMessage = String.format("There is problem with artist info retrieving: %s", e.getMessage());
            throw new RuntimeException(errorMessage, e);
        }
        return new ModelAndView("/account");
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        return new ModelAndView("edit-profile");
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
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
            return new ModelAndView("login");
        }
        return new ModelAndView("purchase-history");
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response){
        request.setAttribute("itemTypes", ItemType.values());
        return new ModelAndView("additem");
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        session = request.getSession();
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
    public ModelAndView myWorks(HttpServletRequest request,HttpServletResponse response){
        session = request.getSession();
        if(session.getAttribute("user")!= null  && session.getAttribute("user").getClass() == Artist.class) {
            artist= (Artist) request.getSession().getAttribute("user");
            request.setAttribute("artistItems", itemService.getArtistItems(artist.getId(),8888L,100L));
        }
        else return new ModelAndView("login");
        return new ModelAndView("my-works");
    }

}
