package com.course.PhotoNetwork;

import com.course.PhotoNetwork.model.dto.*;
import com.course.PhotoNetwork.model.types.BookingEnum;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookingTests {

	@Autowired
	private TestRestTemplate template;

	private static Long customerId;
	private static Long masterId;
	private static Long serviceId;
	private static Long bookingId;
	private static Long defaultServiceId;

	private static String customerEmail;
	private static String masterEmail;

	@Test
	@Order(1)
	public void test1init() {

		UserDtoOut customer = registerUsers("testcustomer@text.com", "testcustomer", "test");
		this.customerEmail = customer.getEmail();
		this.customerId = customer.getId();

		UserDtoOut master = registerUsers("testmaster@text.com", "testmaster", "test");
		this.masterEmail = master.getEmail();
		this.masterId = master.getId();

		ServiceDto service = registerDefaultService("testservice");
		this.defaultServiceId = service.getId();
		ServiceDto masterService = registerMasterService(master.getId(), Sets.newLinkedHashSet(service));
		masterService.setPrice(100);
		this.serviceId = masterService.getId();
	}

	@Test
	@Order(2)
	public void test2bookService() {

		BookingServiceDtoIn booking = new BookingServiceDtoIn(String.valueOf(this.masterId), String.valueOf(this.serviceId), "31.05.2020 12:00");

		HttpEntity<BookingServiceDtoIn> request = new HttpEntity(booking);
		ResponseEntity<BookingServiceDtoOut> result = template.withBasicAuth(this.customerEmail, "test")
				.postForEntity("/api/booking", request, BookingServiceDtoOut.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());

		this.bookingId = result.getBody().getBookingId();

	}

	@Test
	@Order(3)
	public void test3addReview() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("authorId", String.valueOf(this.customerId));
		map.add("masterId", String.valueOf(this.masterId));
		map.add("rate", "5");
		map.add("String", "some cool comment");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// try to add comment
		ResponseEntity<ReviewDtoOut> response = template.withBasicAuth(this.customerEmail, "test").postForEntity("/api/review", request, ReviewDtoOut.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());        // 400 т.к. статус заказа != FINISHED

		// change booking status to FINISHED
		BookingUserInfoDtoIn booking = new BookingUserInfoDtoIn(String.valueOf(this.bookingId), String.valueOf(BookingEnum.NEW.getId()));


		ResponseEntity<BookingServiceDtoOut> statusResponse = template.withBasicAuth(this.customerEmail, "test").postForEntity("/api/booking/status", new HttpEntity<>(booking), BookingServiceDtoOut.class);
		assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
		assertNotNull(statusResponse.getBody());
		assertEquals(BookingEnum.PAID_CLIENT,statusResponse.getBody().getStatus());

		booking.setPrevStatusId(String.valueOf(statusResponse.getBody().getStatus().getId()));

		statusResponse = template.withBasicAuth(this.masterEmail, "test").postForEntity("/api/booking/status", new HttpEntity<>(booking), BookingServiceDtoOut.class);
		assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
		assertNotNull(statusResponse.getBody());
		assertEquals(BookingEnum.PAID_MASTER,statusResponse.getBody().getStatus());

		booking.setPrevStatusId(String.valueOf(statusResponse.getBody().getStatus().getId()));

		statusResponse = template.withBasicAuth(this.masterEmail, "test").postForEntity("/api/booking/status", new HttpEntity<>(booking), BookingServiceDtoOut.class);
		assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
		assertNotNull(statusResponse.getBody());
		assertEquals(BookingEnum.FINISH_AWAITS,statusResponse.getBody().getStatus());

		booking.setPrevStatusId(String.valueOf(statusResponse.getBody().getStatus().getId()));

		statusResponse = template.withBasicAuth(this.customerEmail, "test").postForEntity("/api/booking/status", new HttpEntity<>(booking), BookingServiceDtoOut.class);
		assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
		assertNotNull(statusResponse.getBody());
		assertEquals(BookingEnum.FINISHED,statusResponse.getBody().getStatus());

		//try to write comment again
		response = template.withBasicAuth(this.customerEmail, "test").postForEntity("/api/review", request, ReviewDtoOut.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(4)
	public void test4cleanUp() {

		//delete master
		template.withBasicAuth("admin@adm.com", "verystrongpass").delete("/api/account/" + this.masterId);
		//delete customer
		template.withBasicAuth("admin@adm.com", "verystrongpass").delete("/api/account/" + this.customerId);
		//delete service
		template.withBasicAuth("admin@adm.com", "verystrongpass").delete("/api/service/" + this.defaultServiceId);
	}

	public UserDtoOut registerUsers(String email, String username, String password) {

		ResponseEntity<UserDtoOut> checkIfRegistered = template.withBasicAuth(email, password)
				.getForEntity("/api/account?email=" + email, UserDtoOut.class);

		if (checkIfRegistered.getStatusCode() != HttpStatus.UNAUTHORIZED) {
			assertNotNull(checkIfRegistered.getBody());
			assertEquals(email, checkIfRegistered.getBody().getEmail());
			return checkIfRegistered.getBody();
		} else {

			UserDtoIn newUser = new UserDtoIn();
			newUser.setEmail(email);
			newUser.setPassword(password);
			newUser.setUsername(username);

			HttpEntity<UserDtoIn> request = new HttpEntity(newUser);
			ResponseEntity<UserDtoOut> regResult = template.postForEntity("/api/account/registration", request, UserDtoOut.class);
 			assertEquals(HttpStatus.OK, regResult.getStatusCode());

			return regResult.getBody();
		}
	}

	private ServiceDto registerDefaultService(String name) {

		ResponseEntity<ServiceDto> res = template.withBasicAuth("admin@adm.com", "verystrongpass").postForEntity("/api/service?service_name=" + name, null, ServiceDto.class);
		assertEquals(HttpStatus.OK, res.getStatusCode());

		return res.getBody();
	}

	private ServiceDto registerMasterService(long masterId, Set<ServiceDto> masterServices) {
		UserServicesDto dto = new UserServicesDto();
		dto.setServices(masterServices);
		dto.setUserId(masterId);

		ResponseEntity<UserServicesDto> res = template.withBasicAuth(this.masterEmail, "test").postForEntity("/api/service/services", new HttpEntity(dto), UserServicesDto.class);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertNotNull(res.getBody());

		return res.getBody().getServices().iterator().next();
	}

}
