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
public class ContactController extends ControllerHelper {

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public ModelAndView showContactPage() {
        return new ModelAndView(CONTACT);
    }
}
