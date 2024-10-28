package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.clients.OrderClient;
import praktikum.config.ApiConfig;
import praktikum.generators.OrderGenerator;
import praktikum.models.Order;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreationTest {

  private OrderClient orderClient;
  private final String[] color;

  public OrderCreationTest(String testName, String[] color) {
    this.color = color;
  }

  @Parameterized.Parameters(name = "{index}: {0}")
  public static Object[][] testData() {
    return new Object[][]{
        {"Creation with BLACK color", new String[]{"BLACK"}},
        {"Creation with GREY color", new String[]{"GREY"}},
        {"Creation with BLACK and GREY colors", new String[]{"BLACK", "GREY"}},
        {"Creation without selecting color", new String[]{}}
    };
  }

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConfig.BASE_URL;
    orderClient = new OrderClient();
  }

  @Test
  public void createOrderWithColorOptions() {
    Order order = OrderGenerator.defaultOrder(color);
    Response response = orderClient.createOrder(order);

    assertEquals("Неверный статус код при создании заказа", SC_CREATED, response.statusCode());

    int track = response.jsonPath().getInt("track");
    assertNotNull("Ожидается наличие track в ответе", track);
  }
}

