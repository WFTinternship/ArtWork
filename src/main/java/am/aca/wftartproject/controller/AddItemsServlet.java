package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Armen on 6/16/2017
 */
@MultipartConfig(maxFileSize = 2177215)
public class AddItemsServlet extends HttpServlet {

//    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ItemService itemService = CtxListener.getBeanFromSpring(SpringBeanType.ITEMSERVICE,ItemServiceImpl.class);
    ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("itemTypes", ItemType.values());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/additem.jsp");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Item item = new Item();
        String filePath = null;
        String fileName;
        Part filePart;
        InputStream inputStream;
        Artist artist;
        Artist findArtist;
        HttpSession session = request.getSession();
        response.setContentType("text/html");
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
                if (request.getPart("image") != null) {
                    filePart = request.getPart("image");
                    if (filePart != null) {
                        inputStream = filePart.getInputStream();
                        byte[] imageBytes = IOUtils.toByteArray(inputStream);
                        String uploadPath = "resources/images/artists/" + artist.getId();
                        String realPath = getServletContext().getRealPath("resources/images/artists/" + artist.getId());
                        File uploadDir = new File(realPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }
                        fileName = new File(item.getTitle()).getName();
                        filePath = realPath + File.separator + fileName + ".jpg";
                        FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
                        item.setPhotoURL(uploadPath + File.separator + fileName + ".jpg");

                    }
                }

                try {
                    itemService.addItem(artist.getId(), item);
                } catch (ServiceException e) {
                    String errorMessage = "The entered info is not correct";
                    request.setAttribute("errorMessage", errorMessage);
//            request.getRequestDispatcher("/signup")
//                    .forward(request,response);
                }

                try {
                    response.setContentType("html/text");
                    response.sendRedirect("/additem");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    }
}
