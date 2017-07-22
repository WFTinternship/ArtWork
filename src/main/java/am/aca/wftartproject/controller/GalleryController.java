package am.aca.wftartproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Armen on 7/22/2017
 */
@Controller
public class GalleryController {
    @RequestMapping(value = "gallery", method = RequestMethod.GET)
    public ModelAndView showGalleryPage(HttpServletRequest request) throws IOException {
        List imageUrlList;

        //get absolute path of image folder and create new file
        String uploadPath = "/resources/images/product";
        String realPath = request.getServletContext().getRealPath(uploadPath);
        File imageDir = new File(realPath);

        //iterate through image directory and  add  image names to the list we are building up
        List<String> list = Arrays.stream(imageDir.listFiles()).map(File::getName).map(imageFileName -> uploadPath + File.separator + imageFileName).collect(Collectors.toList());
        imageUrlList = list;

        //set image list as attribute  for displaying in UI
        request.setAttribute("imageUrlList", imageUrlList);

        return new ModelAndView("gallery");
    }
}
