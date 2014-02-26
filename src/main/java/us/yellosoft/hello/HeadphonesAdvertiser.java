package us.yellosoft.hello;

import us.yellosoft.hello.thrift.Headphones;

public class HeadphonesAdvertiser {
  public static String advertise(final Headphones headphones) {
    return "This week only, buy " + headphones.getBrand() + "\'s elite headphones 50% off, " +
      "at " + headphones.getPrice() + "!\n" +
      "Hurry before we're sold out, we only have " + headphones.getQuantity() + " left!\n" +
      "Get them now at " + headphones.getUrl() + "\n" +
      "Customers remark, \"" + headphones.getReview() + "\" !";
  }

  public static void main(final String[] args) {
    final Headphones headphones = new Headphones();
    headphones.setBrand("Bose");
    headphones.setQuantity(10);
    headphones.setPrice(125.0);
    headphones.setUrl("http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp");
    headphones.setReview("Works on my machine.");

    System.out.println(advertise(headphones));
  }
}
