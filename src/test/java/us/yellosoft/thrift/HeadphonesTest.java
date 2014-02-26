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
  private static final String brand = "Bose";
  private static final int quantity = 1;
  private static final double price = 249.95;
  private static final String url = "http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp";

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
    h.setUrl(url);

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
  public void defaultsTest() {
    final Headphones h2 = new Headphones();

    assertEquals(h2.getBrand(), brand);
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

  @Test
  public void optionalsTest() throws Exception {
    final Headphones h2 = new Headphones();
    h2.setBrand(brand);
    h2.setQuantity(quantity);
    h2.setPrice(price);
    // optional url omitted

    try {
      byte[] byteArray = byteSerializer.serialize(h2);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);
    }
    catch (TException e) {
      throw new Exception(e);
    }

    final Headphones h3 = new Headphones();
    // required brand defaulted
    h3.setQuantity(quantity);
    h3.setPrice(price);
    h3.setUrl(url);

    try {
      byte[] byteArray = byteSerializer.serialize(h3);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);
    }
    catch (TException e) {
      throw new Exception(e);
    }

    final Headphones h4 = new Headphones();
    h4.unsetBrand(); // required field omitted
    h4.setQuantity(quantity);
    h4.setPrice(price);
    h4.setUrl(url);

    try {
      byte[] byteArray = byteSerializer.serialize(h4);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);
      assertEquals(true, false);
    }
    catch (TException e) { }

    final Headphones h5 = new Headphones();
    h5.setBrand(brand);
    h5.unsetQuantity(); // Thrift-Java bug: unset primitives are still serialized
    h5.setPrice(price);
    h5.setUrl(url);

    try {
      byte[] byteArray = byteSerializer.serialize(h5);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);
    }
    catch (TException e) {
      throw new Exception(e);
    }
  }    
}
