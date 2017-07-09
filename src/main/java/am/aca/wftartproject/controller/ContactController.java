package am.aca.wftartproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Armen on 6/27/2017
 */
@Controller
public class ContactController {
    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public ModelAndView showContactPage(){
        return new ModelAndView("contact");
    }
}
