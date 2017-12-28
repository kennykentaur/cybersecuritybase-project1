package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "redirect:/done";
    }
    
    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "done";
    }

    // Vulnerability 5 - Top 10 2013-A7-Missing Function Level Access Control
    @RequestMapping(value = "/dropevent", method = RequestMethod.GET)
    public String dropEvent() {
        signupRepository.deleteAll();
        return "redirect:/done";
    }
}
