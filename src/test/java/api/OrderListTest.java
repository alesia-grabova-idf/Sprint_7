package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.clients.OrderClient;
import praktikum.config.ApiConfig;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderListTest {

  private OrderClient orderClient;

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConfig.BASE_URL;
    orderClient = new OrderClient();
  }

  @Test
  @DisplayName("Get full order list")
  public void getOrdersReturnsListOfOrders() {
    Response response = orderClient.getOrders();

    assertEquals("Неверный статус код при запросе списка заказов", SC_OK, response.statusCode());
    assertTrue("Ожидается наличие списка заказов", response.jsonPath().getList("orders").size() > 0);
  }
}
