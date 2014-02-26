package us.yellosoft.hello.thrift;

import org.apache.thrift.TSerializer;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.TException;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class HeadphonesTest {
  private static final String BRAND = "Bose";
  private static final int QUANTITY = 1;
  private static final double PRICE = 249.95;
  private static final String URL = "http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp";
  private static final String REVIEW = "Works on my machine!";

  private static Headphones h;

  private static TSerializer byteSerializer;
  private static TDeserializer byteDeserializer;

  private static TSerializer jsonSerializer;
  private static TDeserializer jsonDeserializer;

  @BeforeClass
  public static void setupTest() {
    h = new Headphones();
    h.setBrand(BRAND);
    h.setQuantity(QUANTITY);
    h.setPrice(PRICE);
    h.setUrl(URL);
    h.setReview(REVIEW);

    byteSerializer = new TSerializer();
    byteDeserializer = new TDeserializer();

    final TProtocolFactory jsonProtocolFactory = new TJSONProtocol.Factory();

    jsonSerializer = new TSerializer(jsonProtocolFactory);
    jsonDeserializer = new TDeserializer(jsonProtocolFactory);
  }

  @Test
  public void constructorTest() {
    assertEquals(h.getBrand(), BRAND);
    assertEquals(h.getQuantity(), QUANTITY);
    assertEquals(h.getPrice(), PRICE, 0.0);
    assertEquals(h.getUrl(), URL);
    assertEquals(h.getReview(), REVIEW);

    h.unsetReview();
    assertEquals(h.getReview(), null);

    h.setReview(REVIEW);
    assertEquals(h.getReview(), REVIEW);
  }

  @Test
  public void defaultsTest() {
    final Headphones h2 = new Headphones();

    assertEquals(h2.getBrand(), BRAND);
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
    h2.setBrand(BRAND);
    h2.setQuantity(QUANTITY);
    h2.setPrice(PRICE);
    // optional url omitted
    h2.setReview(REVIEW);

    try {
      final byte[] byteArray = byteSerializer.serialize(h2);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);

      assertEquals(h0, h2);
    }
    catch (TException e) {
      throw new Exception(e);
    }

    final Headphones h3 = new Headphones();
    // required brand defaulted
    h3.setQuantity(QUANTITY);
    h3.setPrice(PRICE);
    h3.setUrl(URL);
    h3.setReview(REVIEW);

    try {
      final byte[] byteArray = byteSerializer.serialize(h3);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);

      assertEquals(h0, h3);
    }
    catch (TException e) {
      throw new Exception(e);
    }

    final Headphones h4 = new Headphones();
    h4.unsetBrand(); // required field omitted
    h4.setQuantity(QUANTITY);
    h4.setPrice(PRICE);
    h4.setUrl(URL);
    h4.setReview(REVIEW);

    try {
      final byte[] byteArray = byteSerializer.serialize(h4);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);

      assertEquals(true, false);
    }
    catch (TException e) {
      assertEquals(true, true);
    }

    final Headphones h5 = new Headphones();
    h5.setBrand(BRAND);
    h5.unsetQuantity(); // Thrift-Java bug: unset primitives are still serialized
    h5.setPrice(PRICE);
    h5.setUrl(URL);
    h5.setReview(REVIEW);

    try {
      final byte[] byteArray = byteSerializer.serialize(h5);
      final Headphones h0 = new Headphones();
      byteDeserializer.deserialize(h0, byteArray);

      assertEquals(h0, h5);
    }
    catch (TException e) {
      throw new Exception(e);
    }
  }    
}
