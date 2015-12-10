package us.yellosoft.hello;

import us.yellosoft.hello.thrift.Headphones;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class HeadphonesAdvertiserTest {
  private static final String BRAND = "Bose";
  private static final int QUANTITY = 10;
  private static final double PRICE = 125.0;
  private static final String URL = "http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp";
  private static final String REVIEW = "Works on my machine.";

  private static Headphones h;

  @BeforeClass
  public static void setupTest() {
    h = new Headphones();
    h.setBrand(BRAND);
    h.setQuantity(QUANTITY);
    h.setPrice(PRICE);
    h.setUrl(URL);
    h.setReview(REVIEW);
  }

  @Test
  public void advertiserTest() {
    final String ad = HeadphonesAdvertiser.advertise(h);

    assertEquals(ad, "This week only, buy Bose\'s elite headphones 50% off, at 125.00!\nHurry before we\'re sold out, we only have 10 left!\nGet them now at http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp\nCustomers remark, \"Works on my machine.\" !");
  }
}
