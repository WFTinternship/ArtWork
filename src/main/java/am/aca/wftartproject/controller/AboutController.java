package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Armen on 6/27/2017
 */
@Controller
public class AboutController extends ControllerHelper {
    @RequestMapping(value = "about", method = RequestMethod.GET)
    public ModelAndView showAboutPage() {
        return new ModelAndView(ABOUT);
    }
}
