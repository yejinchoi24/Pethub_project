package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Service01Controller2 {
	
	@GetMapping("/tiger2")
	public String f1() {
		return "page";
	}
}
