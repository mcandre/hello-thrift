namespace * us.yellosoft.hello.thrift

struct Headphones {
  1: required string brand = "Bose"; /* Brand name */
  2: required i32 quantity;          /* Non-negative */
  3: required double price;          /* USD */
  4: optional string url;            /* Product page */
  5: optional string review;         /* Consumer review */
}