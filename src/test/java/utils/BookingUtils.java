
package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingUtils {

    private static final String BASE_URL =
            "https://restful-booker.herokuapp.com";

    public static int createBooking() {

        String requestBody = """
                {
                    "firstname": "Santhiya",
                    "lastname": "Devi",
                    "totalprice": 1500,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2026-11-01",
                        "checkout": "2026-11-05"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post(BASE_URL + "/booking");

        response.then().statusCode(200);

        int bookingId = response.jsonPath().getInt("bookingid");

        System.out.println("Created Booking ID: " + bookingId);

        return bookingId;
    }
}
