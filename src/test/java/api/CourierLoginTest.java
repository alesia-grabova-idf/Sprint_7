package api;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static praktikum.generators.CourierGenerator.randomCourier;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.clients.CourierClient;
import praktikum.config.ApiConfig;
import praktikum.models.Courier;
import praktikum.models.CourierId;

public class CourierLoginTest {

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
  @DisplayName("Successful Login")
  public void successfulLogin() {
    Courier courier = randomCourier();
    courierClient.create(courier);
    Response loginResponse = courierClient.login(courier);
    id = loginResponse.as(CourierId.class).getId();

    assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());
    assertTrue("Ожидается наличие id в ответе", loginResponse.body().asString().contains("id"));
  }

  @Test
  @DisplayName("Login with incorrect password")
  public void loginWithIncorrectPassword() {
    Courier courier = randomCourier();
    courierClient.create(courier);

    // Меняем пароль на неверный
    Courier wrongPasswordCourier = new Courier()
        .withLogin(courier.getLogin())
        .withPassword("wrongPassword");

    Response loginResponse = courierClient.login(wrongPasswordCourier);

    assertEquals("Неверный статус код при неправильном пароле", SC_NOT_FOUND, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке", loginResponse.body().asString().contains("Учетная запись не найдена"));
  }

  @Test
  @DisplayName("Login with non existent courier")
  public void loginWithNonExistentCourier() {
    Courier nonExistentCourier = new Courier()
        .withLogin("nonExistentLogin")
        .withPassword("anyPassword");

    Response loginResponse = courierClient.login(nonExistentCourier);

    assertEquals("Неверный статус код для несуществующего курьера", SC_NOT_FOUND, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке", loginResponse.body().asString().contains("Учетная запись не найдена"));
  }

  @Test
  @DisplayName("Login without login field")
  public void loginWithoutLogin() {
    Courier courier = randomCourier();
    courierClient.create(courier);

    Courier noLoginCourier = new Courier()
        .withPassword(courier.getPassword());

    Response loginResponse = courierClient.login(noLoginCourier);

    assertEquals("Неверный статус код при отсутствии логина", SC_BAD_REQUEST, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке",
        loginResponse.body().asString().contains("Недостаточно данных для входа"));
  }

  @Test
  @DisplayName("Login without password field")
  public void loginWithoutPassword() {
    Courier courier = randomCourier();
    courierClient.create(courier);

    Courier noPasswordCourier = new Courier()
        .withLogin(courier.getLogin())
        .withPassword("");

    Response loginResponse = courierClient.login(noPasswordCourier);

    assertEquals("Неверный статус код при отсутствии пароля", SC_BAD_REQUEST, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке",
        loginResponse.body().asString().contains("Недостаточно данных для входа"));
  }
}

