package com.spacetest.demoblaze.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Full End-to-End test suite for the Restful-Booker API.
 * This class covers:
 * 1. Health Check
 * 2. Authentication (to get a token)
 * 3. Full CRUD (Create, Read, Update, Delete) workflow
 * 4. Negative tests
 *
 * Tests are run in a specific order using TestNG 'priority'
 * to ensure a logical flow (e.g., create -> update -> delete).
 */
public class RestfulBookerFullTests {

    // Class-level variables to store state between tests
    private int newBookingId;
    private String authToken;

    /**
     * Sets the base URI and default configuration for all tests.
     * This runs once before any of the @Test methods.
     */
     @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        
        // --- NEW CONFIGURATION ---
        // Apply the settings from your working example globally.
        
        // 1. Configure RestAssured to NOT append charset to Content-Type
        RestAssured.config = RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false))
            .decoderConfig(DecoderConfig.decoderConfig()
                .noContentDecoders());

        // 2. Set default headers for ALL requests
        RestAssured.requestSpecification = given()
            .header("User-Agent", "Mozilla/5.0")
            .header("Origin", RestAssured.baseURI)
            .header("Connection", "close") // Add the connection header
            .header("Accept", "application/json"); // <-- CHANGE THIS LINE
    }

    /**
     * Test Case 1: Health Check
     * Simple ping test to ensure the API is online.
     */
    @Test(priority = 0, description = "Health Check (Ping)")
    public void testPing() {
        given()
            // Defaults are applied
        .when()
            .get("/ping")
        .then()
            .statusCode(201); // API documentation states /ping returns 201 Created
    }

    /**
     * Test Case 2: Authentication
     * Gets an auth token to be used in PUT, PATCH, and DELETE requests.
     */
    @Test(priority = 1, description = "Get authentication token")
    public void testAuthenticate() {
        AuthPayload authPayload = new AuthPayload("admin", "password123");

        Response response = given()
            // Defaults are applied
            .contentType(ContentType.JSON) // We still specify this
            .body(authPayload)
        .when()
            .post("/auth");
        
        response.prettyPrint();
        
        response.then()
            .statusCode(200)
            .body("token", notNullValue());

        // Extract and store the token
        authToken = response.jsonPath().getString("token");
        System.out.println("Auth Token: " + authToken);
    }
    
    /**
     * Test Case 3: Create Booking (POST)
     * Creates a new booking and saves its ID.
     */
    @Test(priority = 2, description = "Create a new booking")
    public void testCreateBooking() {
        BookingDates dates = new BookingDates("2025-01-01", "2025-01-05");
        Booking payload = new Booking("Mostafa", "ElFallal", 111, true, dates, "Breakfast");

        Response response = given()
            .log().all() // Log the request to debug
            // Defaults are applied
            .contentType(ContentType.JSON) // Content-Type is set, but charset is NOT appended
            .body(payload)
        .when()
            .post("/booking");

        response.prettyPrint();

        response.then()
            .statusCode(200)
            .body("bookingid", notNullValue())
            .body("booking.firstname", equalTo("Mostafa"))
            .body("booking.totalprice", equalTo(111));
            
        // Extract and store the new booking ID
        newBookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Created new booking with ID: " + newBookingId);
    }

    /**
     * Test Case 4: Get Created Booking (GET)
     * Verifies the booking was created correctly.
     */
    @Test(priority = 3, description = "Get details for the created booking", dependsOnMethods = "testCreateBooking")
    public void testGetCreatedBooking() {
        given()
            // Defaults are applied
            .pathParam("id", newBookingId)
        .when()
            .get("/booking/{id}")
        .then()
            .statusCode(200)
            .body("firstname", equalTo("Mostafa"))
            .body("lastname", equalTo("ElFallal"));
    }

    /**
     * Test Case 5: Update Booking (PUT)
     * Fully updates the created booking. Requires auth token.
     */
    @Test(priority = 4, description = "Fully update the created booking (PUT)", dependsOnMethods = {"testAuthenticate", "testCreateBooking"})
    public void testUpdateBookingPut() {
        // Create a new payload for the *full update*
        BookingDates newDates = new BookingDates("2025-02-10", "2025-02-15");
        Booking updatedPayload = new Booking("Walaa", "m4 3arf eh kda", 222, false, newDates, "Vegan meal");

        given()
            // Defaults are applied
            .contentType(ContentType.JSON) // Content-Type is set, but charset is NOT appended
            .cookie("token", authToken) // Add the auth token as a cookie
            .pathParam("id", newBookingId)
            .body(updatedPayload)
        .when()
            .put("/booking/{id}")
        .then()
            .statusCode(200)
            .body("firstname", equalTo("Walaa")) // Verify the updated name
            .body("totalprice", equalTo(222)) // Verify the updated price
            .body("depositpaid", equalTo(false)); // Verify the updated deposit status
    }

    /**
     * Test Case 6: Partial Update Booking (PATCH)
     * Updates only the firstname and price. Requires auth token.
     */
    @Test(priority = 5, description = "Partially update the booking (PATCH)", dependsOnMethods = {"testAuthenticate", "testCreateBooking"})
    public void testPartialUpdateBookingPatch() {
        // Using a Map is easier for partial updates
        Map<String, Object> partialUpdatePayload = new HashMap<>();
        partialUpdatePayload.put("firstname", "UpdatedName");
        partialUpdatePayload.put("totalprice", 999);

        given()
            // Defaults are applied
            .contentType(ContentType.JSON) // Content-Type is set, but charset is NOT appended
            .cookie("token", authToken) // Add the auth token
            .pathParam("id", newBookingId)
            .body(partialUpdatePayload)
        .when()
            .patch("/booking/{id}")
        .then()
            .statusCode(200)
            .body("firstname", equalTo("UpdatedName")) // Verify updated name
            .body("totalprice", equalTo(999)) // Verify updated price
            .body("lastname", equalTo("m4 3arf eh kda")); // Verify last name is unchanged from the PUT test
    }
    
    /**
     * Test Case 7: Delete Booking (DELETE)
     * Deletes the booking. Requires auth token.
     */
    @Test(priority = 6, description = "Delete the booking", dependsOnMethods = {"testAuthenticate", "testCreateBooking"})
    public void testDeleteBooking() {
        given()
            // Defaults are applied
            .contentType(ContentType.JSON) // Content-Type is set, but charset is NOT appended
            .cookie("token", authToken) // Add the auth token
            .pathParam("id", newBookingId)
        .when()
            .delete("/booking/{id}")
        .then()
            .statusCode(201); // API docs state 201 Created is returned for a successful delete
    }

    /**
     * Test Case 8: Verify Deletion
     * Tries to get the deleted booking and expects a 404.
     */
    @Test(priority = 7, description = "Verify booking was deleted", dependsOnMethods = "testDeleteBooking")
    public void testVerifyBookingDeleted() {
        given()
            // Defaults are applied
            .pathParam("id", newBookingId)
        .when()
            .get("/booking/{id}")
        .then()
            .statusCode(404); // Expect 404 Not Found
    }

    /**
* Test Case 9: Negative Test
* Tries to get a booking with an invalid (non-existent) ID.
*/
    @Test(priority = 8, description = "Attempt to get a non-existent booking")
    public void testGetNonExistentBooking() {
        int invalidId = 99999999;
        given()
                // Defaults are applied
                .pathParam("id", invalidId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(404); // Asserts that the response is 404 (Not Found)
    }

    // --- POJO (Plain Old Java Object) Classes ---
    // These classes are used to map Java objects to and from JSON.
    // They are defined here as static inner classes for simplicity.

    /**
     * Represents the payload for /auth endpoint.
     */
    static class AuthPayload {
        public String username;
        public String password;
        
        public AuthPayload(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    /**
     * Represents the main booking payload.
     */
    static class Booking {
        public String firstname;
        public String lastname;
        public int totalprice;
        public boolean depositpaid;
        public BookingDates bookingdates;
        public String additionalneeds;

        public Booking() {}
        
        public Booking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.totalprice = totalprice;
            this.depositpaid = depositpaid;
            this.bookingdates = bookingdates;
            this.additionalneeds = additionalneeds;
        }
    }

    /**
     * Represents the nested "bookingdates" object.
     */
    static class BookingDates {
        @JsonProperty("checkin")
        public String checkin;
        
        @JsonProperty("checkout")
        public String checkout;

        public BookingDates() {}

        public BookingDates(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }
    }
}