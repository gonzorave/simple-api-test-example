package com.github.gonzorave.test.api.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


public class ControllerTest {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    RequestSpecification baseSpecification = builder.setBaseUri("http://localhost:8188").build();

    @Test
    public void test_ping() {
        RestAssured
                .given()
                .spec(baseSpecification)
                .get("/ping")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void get_employees() {
        RestAssured
                .given()
                .spec(baseSpecification)
                .get("/employees")
                .then()
                .assertThat()
                .body(Matchers.not(Matchers.emptyArray()))
                .extract().as(Employee[].class);
    }

    @Test
    public void add_employees() {
//      Employee newEmployee = new Employee(9085, "Myesha Dare V", "advocate", 30);
        Employee reanna = new Employee(2829, "Mr. Reanna Hauck", "window cleaner", 45);
        RestAssured.given().spec(baseSpecification).body(reanna).post("/employees/add").then().assertThat();

//        List<Employee> employees = RestAssured.given().spec(baseSpecification).get("/employees").getBody().jsonPath().getList(".", Employee.class);
        RestAssured.given().spec(baseSpecification).get("/employees")
                .then().body("find {it.id == 2829}", Matchers.allOf(
                Matchers.hasEntry("title", "window cleaner"),
                Matchers.hasEntry("name", "Mr. Reanna Hauck")
        ))
                .body("find {it.id == 2829}.title", Matchers.equalTo("window cleaner"))
                .body("find {it.id == 2829}.name", Matchers.equalTo("Mr. Reanna Hauck"));
    }



}
