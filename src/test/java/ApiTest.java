
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.BookingUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ApiTest {

    private static final String BASE_URL =
            "https://restful-booker.herokuapp.com";

    // 1. Create a booking and verify its details
    @Test
    public void createAndVerifyBookingTest() {

        int bookingId = createBooking();

        given()
                .when()
                .get(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(5000L))
                .body("firstname", equalTo("Santhiya"))
                .body("lastname", equalTo("Devi"))
                .body("totalprice", equalTo(1500))
                .body("depositpaid", equalTo(true))
                .body("additionalneeds", equalTo("Breakfast"));

        System.out.println(
                "Booking retrieved and verified successfully!");
    }

    // 2. Update a complete booking using PUT
    @Test
    public void updateBookingTest() {

        int bookingId = createBooking();
        String token = getAuthToken();

        String updatedBooking = """
                {
                    "firstname": "Santhiya",
                    "lastname": "Muthaiah",
                    "totalprice": 2000,
                    "depositpaid": false,
                    "bookingdates": {
                        "checkin": "2026-11-10",
                        "checkout": "2026-11-15"
                    },
                    "additionalneeds": "Lunch"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .body(updatedBooking)
                .when()
                .put(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Santhiya"))
                .body("lastname", equalTo("Muthaiah"))
                .body("totalprice", equalTo(2000))
                .body("depositpaid", equalTo(false))
                .body("additionalneeds", equalTo("Lunch"));

        System.out.println(
                "Booking updated and verified successfully!");
    }

    // 3. Delete a booking and verify it no longer exists
    @Test
    public void deleteBookingTest() {

        int bookingId = createBooking();
        String token = getAuthToken();

        System.out.println("Booking ID to delete: " + bookingId);

        given()
                .cookie("token", token)
                .when()
                .delete(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(201);

        System.out.println("Booking deleted successfully!");

        given()
                .when()
                .get(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(404);

        System.out.println(
                "Deleted booking returned 404 as expected.");
    }

    // 4. Partially update a booking using PATCH
    @Test
    public void patchBookingTest() {

        int bookingId = createBooking();
        String token = getAuthToken();

        String patchBody = """
                {
                    "lastname": "Muthaiah"
                }
                """;

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .cookie("token", token)
                .body(patchBody)
                .when()
                .patch(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("lastname", equalTo("Muthaiah"));

        System.out.println("PATCH update successful!");

        // Verify the changed and unchanged fields
        given()
                .when()
                .get(BASE_URL + "/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Santhiya"))
                .body("lastname", equalTo("Muthaiah"))
                .body("totalprice", equalTo(1500))
                .body("depositpaid", equalTo(true));

        System.out.println(
                "PATCH verified: last name updated; other fields retained.");
    }

    // 5. Verify an invalid booking ID returns 404
    @Test
    public void getBookingWithInvalidIdTest() {

        given()
                .when()
                .get(BASE_URL + "/booking/999999999")
                .then()
                .statusCode(404);

        System.out.println(
                "Invalid booking ID correctly returned 404.");
    }

    // 6. Verify the Content-Type response header
    @Test
    public void getBookingResponseHeaderTest() {

        given()
                .when()
                .get(BASE_URL + "/booking")
                .then()
                .statusCode(200)
                .header(
                        "Content-Type",
                        org.hamcrest.Matchers.containsString(
                                "application/json"));

        System.out.println(
                "Response Content-Type header verified successfully!");
    }

    // Reusable booking creation method
    private int createBooking() {
        return BookingUtils.createBooking();
    }

    // Reusable authentication token method
    private String getAuthToken() {

        String authBody = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(authBody)
                        .when()
                        .post(BASE_URL + "/auth");

        response.then().statusCode(200);

        return response.jsonPath().getString("token");
    }
}
