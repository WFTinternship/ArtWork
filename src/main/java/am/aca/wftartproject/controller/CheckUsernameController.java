package am.aca.wftartproject.controller;

import am.aca.wftartproject.repository.AbstractUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckController
{
    @Autowired
    private AbstractUserRepo abstractUserRepo;

    @RequestMapping("checkUserName")
    @ResponseBody
    public String  checkUser(@RequestParam("email") String email){
        if(abstractUserRepo.findByEmail(email)!=null){
            return "exists";
        }
        else return "notexists";
    }

}