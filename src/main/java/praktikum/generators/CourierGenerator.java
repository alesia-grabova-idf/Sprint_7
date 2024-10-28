package praktikum.generators;

import static praktikum.utils.Utils.randomString;

import praktikum.models.Courier;

public class CourierGenerator {

  public static Courier randomCourier() {
    return new Courier()
        .withLogin(randomString(8))
        .withPassword(randomString(12))
        .withFirstName(randomString(20));
  }
}
