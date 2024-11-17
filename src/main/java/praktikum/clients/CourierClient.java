package praktikum.clients;

import static io.restassured.RestAssured.given;
import static praktikum.models.CourierCreds.credsFromCourier;

import io.restassured.response.Response;
import praktikum.config.ApiConfig;
import praktikum.models.Courier;
import io.qameta.allure.Step;

public class CourierClient {

  @Step("Send POST request to /api/v1/courier")
  public Response create(Courier courier) {
    return given()
        .header("Content-type", "application/json")
        .and()
        .body(courier)
        .when()
        .post(ApiConfig.CREATE_COURIER);
  }

  @Step("Send POST request to /api/v1/courier/login")
  public Response login(Courier courier) {
    return given()
        .header("Content-type", "application/json")
        .and()
        .body(credsFromCourier(courier))
        .when()
        .post(ApiConfig.LOGIN_COURIER);
  }

  @Step("Send DELETE request to api/v1/courier/ + id")
  public Response delete(int id) {
    return given()
        .header("Content-type", "application/json")
        .and()
        .when()
        .delete((ApiConfig.DELETE_COURIER.replace("{id}", String.valueOf(id))));
  }
}
