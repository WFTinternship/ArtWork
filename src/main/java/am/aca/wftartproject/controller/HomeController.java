package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class HomeController extends ControllerHelper {

    @RequestMapping(value = {"/", "index"})
    public ModelAndView welcome() {
        return new ModelAndView(HOME);
    }
}
