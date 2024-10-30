package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.clients.CourierClient;
import praktikum.config.ApiConfig;
import praktikum.models.Courier;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;

import org.assertj.core.api.SoftAssertions;

import static praktikum.utils.Utils.randomString;

@RunWith(Parameterized.class)
public class CourierCreationNegativeTest {

  private CourierClient courierClient;
  private Courier courier;
  private String expectedError;
  private int expectedStatusCode;

  public CourierCreationNegativeTest(String testName, Courier courier, int expectedStatusCode, String expectedError) {
    this.courier = courier;
    this.expectedStatusCode = expectedStatusCode;
    this.expectedError = expectedError;
  }

  @Parameterized.Parameters(name = "{index}: {0}")
  public static Object[][] testData() {
    return new Object[][]{
        {"Without login field", new Courier(null, randomString(12), randomString(20)),
            SC_BAD_REQUEST,
            "Недостаточно данных для создания учетной записи"},
        {"Without password field", new Courier(randomString(8), null, randomString(20)),
            SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"},
        {"Creation with existing courier",
            new Courier(randomString(8), randomString(12), randomString(20)),
            SC_CONFLICT, "Этот логин уже используется"}
    };
  }

  @Before
  public void setUpClass() {
    RestAssured.baseURI = ApiConfig.BASE_URL;
    courierClient = new CourierClient();
  }

  @Test
  public void testCourierNegativeCases() {
    // Если тест на дублирующегося курьера, сначала создаем его
    if (expectedStatusCode == SC_CONFLICT) {
      courierClient.create(courier);
    }

    Response response = courierClient.create(courier);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(response.statusCode())
        .as("Неверный статус код для " + expectedError)
        .isEqualTo(expectedStatusCode);

    softAssertions.assertThat(response.body().asString())
        .as("Ожидается сообщение об ошибке: " + expectedError)
        .contains(expectedError);

    softAssertions.assertAll();
  }
}


