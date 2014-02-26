package us.yellosoft.thrift;

import org.apache.thrift.TSerializer;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class HeadphonesTest {
  private static final String brand = "Brand";
  private static final int quantity = 1;
  private static final double price = 250.0;

  private static Headphones h;
  private static TSerializer serializer;
  private static TDeserializer deserializer;

  @BeforeClass
  public static void setupTest() {
    h = new Headphones();
    h.setBrand(brand);
    h.setQuantity(quantity);
    h.setPrice(price);

    serializer = new TSerializer();
    deserializer = new TDeserializer();
  }

  @Test
  public void constructorTest() {
    assertEquals(h.getBrand(), brand);
    assertEquals(h.getQuantity(), quantity);
    assertEquals(h.getPrice(), price, 0.0);
  }

  @Test
  public void serializationTest() throws TException {
    final byte[] byteArray = serializer.serialize(h);
    final Headphones h2 = new Headphones();
    deserializer.deserialize(h2, byteArray);

    assertEquals(h2, h);
  }
}
