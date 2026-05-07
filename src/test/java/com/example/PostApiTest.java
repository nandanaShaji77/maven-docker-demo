package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * 4 simple REST Assured tests against the public JSONPlaceholder API.
 * Run with: mvn test
 */
public class PostApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // -----------------------------------------------------------------------
    // Test 1 — GET all posts: verify status code and list is non-empty
    // -----------------------------------------------------------------------
    @Test
    void getPostsReturns200AndNonEmptyList() {
        given()
                .log().uri()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)));   // list has at least 1 item
    }

    // -----------------------------------------------------------------------
    // Test 2 — GET single post: verify specific fields in the response body
    // -----------------------------------------------------------------------
    @Test
    void getPostByIdReturnsCorrectFields() {
        given()
                .log().uri()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",     equalTo(1))
                .body("userId", equalTo(1))
                .body("title",  not(emptyOrNullString()))
                .body("body",   not(emptyOrNullString()));
    }

    // -----------------------------------------------------------------------
    // Test 3 — POST create a new post: verify 201 and echoed payload
    // -----------------------------------------------------------------------
    @Test
    void createPostReturns201AndEchosPayload() {
        String requestBody = """
                {
                  "title":  "Test Title",
                  "body":   "Test body content.",
                  "userId": 5
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().body()
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title",  equalTo("Test Title"))
                .body("body",   equalTo("Test body content."))
                .body("userId", equalTo(5))
                .body("id",     notNullValue());          // server assigns an id
    }

    // -----------------------------------------------------------------------
    // Test 4 — GET non-existent post: verify 404
    // -----------------------------------------------------------------------
    @Test
    void getNonExistentPostReturns404() {
        given()
                .log().uri()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }
}
