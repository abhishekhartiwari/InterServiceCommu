package com.abhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abhi.model.Person;
/**
 * Common controller to fetch details in parraller from both the services.
 */
@RestController
@RequestMapping("/user-details")
public class CommonController {
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/{id}")
	public Person getPersonDetails(@PathVariable("id") String id) {
		StopWatch sw = new StopWatch();
		sw.start();
		ResponseEntity<String> n = restTemplate.exchange("http://localhost:8081/person/name/"+id, HttpMethod.GET, null, String.class);
		ResponseEntity<String> s = restTemplate.exchange("http://localhost:8082/salary/"+id, HttpMethod.GET, null, String.class);
		Person p = new Person();
		p.setName(n.getBody());
		p.setSalary(s.getBody());
		sw.stop();
		System.out.println("time taken: "+sw.getTotalTimeSeconds());
		return p;
	}
}
