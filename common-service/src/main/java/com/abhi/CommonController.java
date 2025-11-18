package com.abhi;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
	@Value("${salary.url}")
	private String salaryUrl;
	@Value("${name.url}")
	private String nameUrl;
	
	@GetMapping("/{id}")
	public Person getPersonDetails(@PathVariable("id") String id) {
		Person p = new Person();
		StopWatch sw = new StopWatch();
		sw.start();
		//ResponseEntity<String> n = restTemplate.exchange("http://localhost:8081/person/name/"+id, HttpMethod.GET, null, String.class);
		//ResponseEntity<String> s = restTemplate.exchange("http://localhost:8082/salary/"+id, HttpMethod.GET, null, String.class);
		CompletableFuture.allOf(
				CompletableFuture.supplyAsync(()->  restTemplate.exchange(nameUrl+id, HttpMethod.GET, null, String.class))
				.thenAccept(x->p.setName(x.getBody())),
				CompletableFuture.supplyAsync(()-> restTemplate.exchange(salaryUrl+id, HttpMethod.GET, null, String.class))
				.thenAccept(x->p.setName(x.getBody()))
				).join();
		//p.setName(n.getBody());
		//p.setSalary(s.getBody());
		sw.stop();
		System.out.println("time taken: "+sw.getTotalTimeSeconds());
		return p;
	}
}
