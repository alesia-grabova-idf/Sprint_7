package praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.config.ApiConfig;
import praktikum.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

  @Step("Send POST request to /api/v1/orders")
  public Response createOrder(Order order) {
    return given()
        .header("Content-type", "application/json")
        .body(order)
        .when()
        .post(ApiConfig.CREATE_ORDER);
  }

  @Step("Send GET request to /api/v1/orders")
  public Response getOrders() {
    return given()
        .header("Content-type", "application/json")
        .when()
        .get(ApiConfig.GET_ORDERS);
  }
}
