package praktikum.generators;

import praktikum.models.Order;

public class OrderGenerator {

  public static Order defaultOrder(String[] color) {
    return new Order()
        .withFirstName("Naruto")
        .withLastName("Uchiha")
        .withAddress("Konoha, 142 apt.")
        .withMetroStation(4)
        .withPhone("+7 800 355 35 35")
        .withRentTime(5)
        .withDeliveryDate("2020-06-06")
        .withComment("Saske, come back to Konoha")
        .withColor(color);
  }
}
