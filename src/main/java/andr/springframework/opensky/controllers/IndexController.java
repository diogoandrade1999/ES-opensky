package andr.springframework.opensky.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		var name = "asfseaf";
		model.addAttribute("ola", name);
		return "index";
	}

}