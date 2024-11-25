package praktikum.config;

public class ApiConfig {

  public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
  // Courier endpoints
  public static final String CREATE_COURIER = "/api/v1/courier";
  public static final String LOGIN_COURIER = "/api/v1/courier/login";
  public static final String DELETE_COURIER = "/api/v1/courier/{id}";

  // Order endpoints
  public static final String CREATE_ORDER = "/api/v1/orders";
  public static final String GET_ORDERS = "/api/v1/orders";
}
