package com.kraftwerking.collibrademo;

import com.kraftwerking.collibrademo.model.MyObject;
import com.kraftwerking.collibrademo.service.CollibraDemoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = CollibraDemoApplication.class)
public class CollibraDemoControllerTests {

	@Autowired
	CollibraDemoService collibraDemoService;

	@Value("${collibra.demo.base.url}")
	private String collibraDemoBaseUrl;

	@Value("${collibra.demo.set.uri}")
	private String collibraDemoSetUri;

	@Value("${collibra.demo.get.uri}")
	private String collibraDemoGetUri;

	@Value("${collibra.demo.delete.uri}")
	private String collibraDemoDeleteUri;

	@Test
	public void testSettMyObject() {
		MyObject myObject = collibraDemoService.getMyObjectsFromFile().get(0);
		WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(collibraDemoBaseUrl).build();

		webTestClient.post()
				.uri(collibraDemoSetUri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(myObject), MyObject.class)
				.exchange()
				.expectStatus().isOk();

	}
}