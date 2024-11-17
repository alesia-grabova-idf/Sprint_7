package praktikum.generators;

import static praktikum.utils.Utils.randomString;

import praktikum.models.Courier;

public class CourierGenerator {

  public static Courier randomCourier() {
    return new Courier(
        (randomString(8)),
        (randomString(12)),
        (randomString(20))
    );
  }
}
