package am.aca.wftartproject.controller;

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

    private ItemService itemService = CtxListener.getBeanFromSpring(SpringBeanType.ITEMSERVICE, ItemServiceImpl.class);
    private ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("itemTypes", ItemType.values());
        request.getRequestDispatcher("/WEB-INF/views/additem.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Item item = new Item();
        String responsePage = null;
        String filePath;
        String fileName;
        Part filePart;
        InputStream inputStream;
        Artist artistFromRequest;


        try {
            if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
                artistFromRequest = (Artist) session.getAttribute("user");
                item.setTitle(request.getParameter("title"))
                        .setDescription(request.getParameter("description"))
                        .setItemType(ItemType.valueOf(request.getParameter("type")))
                        .setPrice(Double.parseDouble(request.getParameter("price")))
                        .setArtistId(artistFromRequest.getId())
                        .setStatus(false);
                if (request.getPart("image") != null) {
                    filePart = request.getPart("image");
                    if (filePart != null) {
                        inputStream = filePart.getInputStream();
                        byte[] imageBytes = IOUtils.toByteArray(inputStream);
                        String uploadPath = "resources/images/artists/" + artistFromRequest.getId();
                        String realPath = getServletContext().getRealPath("resources/images/artists/" + artistFromRequest.getId());
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

                itemService.addItem(artistFromRequest.getId(), item);
                responsePage = "/my-works";

            }
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            session.setAttribute("errorMessage", errorMessage);
            responsePage = "/account";
        }

        try {
            response.setContentType("html/text");
            response.sendRedirect(responsePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
