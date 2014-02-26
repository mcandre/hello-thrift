package us.yellosoft.thrift;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HeadphonesTest {
  @Test
  public void constructorTest() {
    final String brand = "Brand";
    final int quantity = 1;
    final double price = 250.0;

    final Headphones h = new Headphones();
    h.setBrand(brand);
    h.setQuantity(quantity);
    h.setPrice(price);

    assertEquals(h.getBrand(), brand);
    assertEquals(h.getQuantity(), quantity);
    assertEquals(h.getPrice(), price, 0.0);
  }
}
