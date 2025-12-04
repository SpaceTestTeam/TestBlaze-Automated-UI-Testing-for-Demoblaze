package com.spacetest.demoblaze.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBookerTests {

    // Global variables to store data between tests
    static int bookingId;
    static String token;
    
    // --- SETUP & TEARDOWN (@BeforeClass / @AfterClass) ---

    @BeforeClass
    public void setup() {
        // Set the Base URI once for the entire class
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // --- 418 ERROR AVOIDANCE CONFIGURATION ---
        // 1. Configure RestAssured to NOT append charset to Content-Type
        RestAssured.config = RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false))
            .decoderConfig(DecoderConfig.decoderConfig()
                .noContentDecoders());

        // 2. Set default headers for ALL requests to mimic a real client
        RestAssured.requestSpecification = RestAssured.given()
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            .header("Origin", RestAssured.baseURI)
            .header("Connection", "close")
            .header("Accept", "application/json");

        System.out.println("--- Starting API Tests Execution ---");
    }

    @AfterClass
    public void tearDown() {
        // Runs after all tests are finished
        System.out.println("--- API Tests Execution Finished ---");
        System.out.println("Created Booking ID was: " + bookingId);
    }

    // --- INTERNAL CLASSES (POJOs) FOR JSON DATA ---
    // Using @JsonProperty ensures exact mapping regardless of field names

    static class AuthCredentials {
        @JsonProperty("username")
        public String username;
        @JsonProperty("password")
        public String password;

        public AuthCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    static class BookingDates {
        @JsonProperty("checkin")
        public String checkin;
        @JsonProperty("checkout")
        public String checkout;

        public BookingDates() {} // Default constructor for Jackson

        public BookingDates(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }
    }

    static class BookingBody {
        @JsonProperty("firstname")
        public String firstname;
        @JsonProperty("lastname")
        public String lastname;
        @JsonProperty("totalprice")
        public int totalprice;
        @JsonProperty("depositpaid")
        public boolean depositpaid;
        @JsonProperty("bookingdates")
        public BookingDates bookingdates;
        @JsonProperty("additionalneeds")
        public String additionalneeds;

        public BookingBody() {} // Default constructor

        public BookingBody(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.totalprice = totalprice;
            this.depositpaid = depositpaid;
            this.bookingdates = bookingdates;
            this.additionalneeds = additionalneeds;
        }
    }

    // --- TEST SCENARIOS ---

    @Test(priority = 1, description = "Health Check (Ping)")
    public void testPing() {
        Response response = RestAssured.given()
                .get("/ping"); 
        
        Assert.assertEquals(response.getStatusCode(), 201, "Ping should return 201 Created");
    }

    @Test(priority = 2, description = "Create Auth Token (POST)")
    public void testAuthenticate() {
        AuthCredentials auth = new AuthCredentials("admin", "password123");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(auth)
                .post("/auth");

        Assert.assertEquals(response.getStatusCode(), 200, "Auth should return 200 OK");
        token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        System.out.println("Token generated: " + token);
    }

    @Test(priority = 3, description = "Create Booking (POST)")
    public void testCreateBooking() {
        BookingDates dates = new BookingDates("2018-01-01", "2019-01-01");
        BookingBody bookingData = new BookingBody("Jim", "Brown", 111, true, dates, "Breakfast");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bookingData)
                .post("/booking");

        Assert.assertEquals(response.getStatusCode(), 200, "Create Booking should return 200 OK");
        
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Booking ID should be created");
        System.out.println("Booking ID created: " + bookingId);
    }

    @Test(priority = 4, description = "Get All Booking IDs (GET)")
    public void testGetBookingIds() {
        Response response = RestAssured.given()
                .get("/booking");

        Assert.assertEquals(response.getStatusCode(), 200, "Get All Bookings should return 200 OK");
        Assert.assertTrue(response.getBody().asString().length() > 0, "List should not be empty");
    }

    @Test(priority = 5, description = "Get Specific Booking (GET)")
    public void testGetCreatedBooking() {
        Response response = RestAssured.given()
                .get("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 200, "Get Specific Booking should return 200 OK");
        Assert.assertEquals(response.jsonPath().getString("firstname"), "Jim", "First name should match");
    }

    @Test(priority = 6, description = "Update Booking (PUT)")
    public void testUpdateBookingPut() {
        BookingDates dates = new BookingDates("2018-01-01", "2019-01-01");
        BookingBody updateData = new BookingBody("James", "Brown", 111, true, dates, "Breakfast");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body(updateData)
                .put("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 200, "Update Booking should return 200 OK");
        Assert.assertEquals(response.jsonPath().getString("firstname"), "James", "Name should be updated");
    }

    @Test(priority = 7, description = "Partial Update Booking (PATCH)")
    public void testPartialUpdateBookingPatch() {
        // Simple string payload for partial update
        String patchPayload = "{\n" +
                "    \"firstname\" : \"Jimmy\"\n" +
                "}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body(patchPayload)
                .patch("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 200, "Partial Update should return 200 OK");
        Assert.assertEquals(response.jsonPath().getString("firstname"), "Jimmy", "Name should be partially updated");
    }

    @Test(priority = 8, description = "Delete Booking (DELETE)")
    public void testDeleteBooking() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .delete("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 201, "Delete Booking should return 201 Created");
    }

    @Test(priority = 9, description = "Verify that the deleted booking ID now returns 404")
    public void testVerifyBookingDeleted() {
        Response response = RestAssured.given()
                .get("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 404, "Deleted booking should not be found");
    }

    @Test(priority = 10, description = "Verify that a non-existent ID returns 404")
    public void testGetNonExistentBooking() {
        Response response = RestAssured.given()
                .get("/booking/9999999");

        Assert.assertEquals(response.getStatusCode(), 404, "Non-existent booking should return 404");
    }

    // --- NEGATIVE SCENARIOS (Required by Instructor) ---

    @Test(priority = 11, description = "Negative: Attempt Login with Invalid Credentials")
    public void testAuth_Invalid() {
        AuthCredentials invalidAuth = new AuthCredentials("admin", "WRONG_PASSWORD");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidAuth)
                .post("/auth");

        Assert.assertEquals(response.getStatusCode(), 200, "API returns 200 even for bad creds");
        Assert.assertTrue(response.getBody().asString().contains("Bad credentials"), "Body should contain 'Bad credentials'");
    }

    @Test(priority = 12, description = "Negative: Attempt to create booking with missing data")
    public void testCreateBooking_Negative_BadRequest() {
        String invalidPayload = "{\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidPayload)
                .post("/booking");

        // Asserts 500 (Known Bug) instead of 400
        Assert.assertEquals(response.getStatusCode(), 500, "Expect 500 (Known Bug) instead of 400");
    }

    @Test(priority = 13, description = "Negative: Attempt to Update without Token")
    public void testUpdateBooking_Negative_Unauthorized() {
        BookingDates dates = new BookingDates("2018-01-01", "2019-01-01");
        BookingBody updateData = new BookingBody("Hacker", "NoToken", 111, true, dates, "None");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                // Cookie intentionally removed
                .body(updateData)
                .patch("/booking/" + bookingId);

        Assert.assertEquals(response.getStatusCode(), 403, "Should return Forbidden without token");
    }

    @Test(priority = 14, description = "Negative: Method Not Allowed")
    public void testMethodNotAllowed() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("/booking");

        Assert.assertEquals(response.getStatusCode(), 404, "Should return 404 (Server returns 404 for invalid methods)");
    }
}