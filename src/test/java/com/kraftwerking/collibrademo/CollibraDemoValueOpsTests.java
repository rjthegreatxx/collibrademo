package com.kraftwerking.collibrademo;

import com.kraftwerking.collibrademo.model.MyObject;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = CollibraDemoApplication.class)
public class CollibraDemoValueOpsTests {

	@Autowired
	private ReactiveStringRedisTemplate redisTemplate;

	@Autowired
	ReactiveRedisTemplate<String, MyObject> reactiveRedisMyObjectTemplate;
	private ReactiveValueOperations<String, MyObject> reactiveMyObjectValueOps;


	@Test
	public void testMyObjectSetAndGet() {
		MyObject myObject = getMyObject();
		reactiveMyObjectValueOps = reactiveRedisMyObjectTemplate.opsForValue();

		Mono<Boolean> result = reactiveMyObjectValueOps.set(String.valueOf(myObject.getId()),
				myObject);

		StepVerifier.create(result)
				.expectNext(true)
				.verifyComplete();

		Mono<MyObject> fetchedMyObject = reactiveMyObjectValueOps.get(String.valueOf(myObject.getId()));

		StepVerifier.create(fetchedMyObject)
				.expectNextMatches(m -> m.getId() == myObject.getId())
				.verifyComplete();
	}

	@Test
	public void testMyObjectSetAndUpdate() {
		MyObject myObject = getMyObject();
		reactiveMyObjectValueOps = reactiveRedisMyObjectTemplate.opsForValue();

		Mono<Boolean> result = reactiveMyObjectValueOps.set(String.valueOf(myObject.getId()),
				myObject);

		StepVerifier.create(result)
				.expectNext(true)
				.verifyComplete();

		myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).setName("lisinopril22");
		result = reactiveMyObjectValueOps.set(String.valueOf(myObject.getId()),
				myObject);

		StepVerifier.create(result)
				.expectNext(true)
				.verifyComplete();

		Mono<MyObject> fetchedMyObject = reactiveMyObjectValueOps.get(String.valueOf(myObject.getId()));

		StepVerifier.create(fetchedMyObject)
				.expectNextMatches(m -> m.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril22"))
				.verifyComplete();

	}

	@Test
	public void testMyObjectSetAndDelete() {
		MyObject myObject = getMyObject();
		reactiveMyObjectValueOps = reactiveRedisMyObjectTemplate.opsForValue();

		Mono<Boolean> result = reactiveMyObjectValueOps.set(String.valueOf(myObject.getId()),
				myObject);

		StepVerifier.create(result)
				.expectNext(true)
				.verifyComplete();

		reactiveRedisMyObjectTemplate.delete(String.valueOf(myObject.getId())).block();
		Mono<MyObject> fetchedMyObject = reactiveMyObjectValueOps.get(String.valueOf(myObject.getId()));

		MyObject mm = fetchedMyObject.block();
		StepVerifier.create(fetchedMyObject)
				.verifyComplete();
	}

	private MyObject getMyObject() {
		WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

// send a GET request to the URL that returns a Flux of String values
		FluxExchangeResult<MyObject> res = webTestClient.get().uri("/api/getmyobjects")
				.exchange()
				.expectStatus().isOk()
				.returnResult(MyObject.class);

		List<MyObject> myObjectList = res.getResponseBody().collectList().block();
		assert myObjectList != null;
		return myObjectList.get(0);
	}

	private List<MyObject> getMyObjectList() {
		WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

// send a GET request to the URL that returns a Flux of String values
		FluxExchangeResult<MyObject> res = webTestClient.get().uri("/api/getmyobjects")
				.exchange()
				.expectStatus().isOk()
				.returnResult(MyObject.class);

		return res.getResponseBody().collectList().block();
	}

	@Test
	public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
		ReactiveListOperations<String, String> reactiveListOps = redisTemplate.opsForList();

		Mono<Long> lPush = reactiveListOps.leftPushAll("demo_list", "first", "second")
				.log("Pushed");
		StepVerifier.create(lPush)
				.expectNext(2L)
				.verifyComplete();
		Mono<String> lPop = reactiveListOps.leftPop("demo_list")
				.log("Popped");
		StepVerifier.create(lPop)
				.expectNext("second")
				.verifyComplete();
	}


	@Test
	public void testGetMyObjects() {
		WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

// send a GET request to the URL that returns a Flux of String values
		FluxExchangeResult<MyObject> result = webTestClient.get().uri("/api/getmyobjects")
				.exchange()
				.expectStatus().isOk()
				.returnResult(MyObject.class);

// verify the contents of the Flux
		Flux<MyObject> responseFlux = result.getResponseBody();

		StepVerifier.create(responseFlux)
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.expectNextMatches(myObject -> myObject.getData().get(0).getMedications().get(0).getAceInhibitors().get(0).getName().equals("lisinopril"))
				.verifyComplete();
	}
}