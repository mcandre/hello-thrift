package us.yellosoft.thrift;

import org.apache.thrift.TSerializer;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.TException;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class HeadphonesTest {
  private static final String brand = "Brand";
  private static final int quantity = 1;
  private static final double price = 250.0;

  private static Headphones h;

  private static TSerializer byteSerializer;
  private static TDeserializer byteDeserializer;

  private static TSerializer jsonSerializer;
  private static TDeserializer jsonDeserializer;

  @BeforeClass
  public static void setupTest() {
    h = new Headphones();
    h.setBrand(brand);
    h.setQuantity(quantity);
    h.setPrice(price);

    byteSerializer = new TSerializer();
    byteDeserializer = new TDeserializer();

    final TProtocolFactory jsonProtocolFactory = new TJSONProtocol.Factory();

    jsonSerializer = new TSerializer(jsonProtocolFactory);
    jsonDeserializer = new TDeserializer(jsonProtocolFactory);
  }

  @Test
  public void constructorTest() {
    assertEquals(h.getBrand(), brand);
    assertEquals(h.getQuantity(), quantity);
    assertEquals(h.getPrice(), price, 0.0);
  }

  @Test
  public void byteSerializationTest() throws TException {
    final byte[] byteArray = byteSerializer.serialize(h);
    final Headphones h2 = new Headphones();
    byteDeserializer.deserialize(h2, byteArray);

    assertEquals(h2, h);
  }

  @Test
  public void jsonSerializationTest() throws TException {
    final String json = jsonSerializer.toString(h);
    final Headphones h2 = new Headphones();
    jsonDeserializer.fromString(h2, json);

    assertEquals(h2, h);
  }
}
