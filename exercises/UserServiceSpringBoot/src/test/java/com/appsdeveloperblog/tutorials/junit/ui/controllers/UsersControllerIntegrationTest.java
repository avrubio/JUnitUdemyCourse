package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.apache.coyote.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "/application-test.properties", properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;
    @Test
    @Order(1)
    @DisplayName("user can be created")
   void testCreateUser_whenValidDetailsProvided_returnUserDetails() throws JSONException {
        //arrange
        JSONObject userDetailsRequestJson= new JSONObject();
        userDetailsRequestJson.put("firstName", "ari");
        userDetailsRequestJson.put("lastName", "vanegas");
        userDetailsRequestJson.put("email", "ari@aol.com");
        userDetailsRequestJson.put( "password", "12345678");
        userDetailsRequestJson.put( "repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);
        //act
        ResponseEntity<UserRest> createdUserDetailsEntity = testRestTemplate.postForEntity("/users",
                request, UserRest.class);
        UserRest createdUserDetails = createdUserDetailsEntity.getBody();
        //assert
        assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
        assertEquals(userDetailsRequestJson.getString("firstName"), createdUserDetails.getFirstName(), "Returned users first name seems to incorrect");
        assertEquals(userDetailsRequestJson.getString("lastName"),
                createdUserDetails.getLastName(),
                "Returned user's last name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("email"),createdUserDetails.getEmail(), "Returned users email seems to be incorrect" );
        assertFalse(createdUserDetails.getUserId().trim().isEmpty(), "user id should not be empty");
    }

    @Test
    @Order(2)
    @DisplayName("GET /users requires JWT")
    void testGetUsers_whenMissingJWT_returns403(){
        //arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity requestEntity = new HttpEntity<>(null, headers);
        //act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "HTTP Statys code 403 forbidden should have been shown");
    }
    @Test
    @Order(3)
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJWTinAuthorizationHeader() throws JSONException {
        //arrange
        JSONObject loginCreds = new JSONObject();
        loginCreds.put("email", "ari@aol.com");
        loginCreds.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity<>(loginCreds.toString());
       //act
       ResponseEntity response= testRestTemplate.postForEntity("/users/login",request,
                null);
       authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);
       //ASSERt
        assertEquals(HttpStatus.OK, response.getStatusCode(), "http status code should be 200");
assertNotNull(authorizationToken,
        "response should have container auth");
assertNotNull(response.getHeaders().getValuesAsList("UserId").get(0),
        "Response should contain UserId");
    }

    @Test
    @Order(4)
    @DisplayName("GET /users works")
    void testGetUsers_whenValidJWTProvided_returnsUsers(){
        //arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);

        HttpEntity requestEntity = new HttpEntity(headers);
        //act

        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });
        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status code should be 200");
        assertTrue(response.getBody().size() ==1, "There should be exactly one userin the list");
    }
}
