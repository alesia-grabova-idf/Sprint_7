package api;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.clients.CourierClient;
import praktikum.config.ApiConfig;
import praktikum.models.Courier;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static praktikum.generators.CourierGenerator.randomCourier;

import io.restassured.response.Response;
import praktikum.models.CourierId;

public class CourierCreationSuccessTest {

  private CourierClient courierClient;
  private int id;

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConfig.BASE_URL;
    courierClient = new CourierClient();
  }

  @After
  public void tearDown() {
    if (id != 0) {
      courierClient.delete(id);
    }
  }

  @Test
  @DisplayName("Successful courier creation")
  public void createCourier() {
    Courier courier = randomCourier();
    Response response = courierClient.create(courier);

    assertEquals("Неверный статус код", SC_CREATED, response.statusCode());
    assertTrue("Ожидается ответ {ok: true}", response.jsonPath().getBoolean("ok"));

    Response loginResponse = courierClient.login(courier);
    id = loginResponse.as(CourierId.class).getId();

    assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());
  }
}

