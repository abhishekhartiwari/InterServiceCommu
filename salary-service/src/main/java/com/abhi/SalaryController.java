package com.abhi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {
	
	@GetMapping("/{id}")
	public String getSalary(@PathVariable("id") String id) throws InterruptedException {
		Thread.sleep(4000);
		if(id.equalsIgnoreCase("USER_1"))
			return "$ 70000";
		throw new RuntimeException();
	}

}
