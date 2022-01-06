package xyz.kumaraswamy.huffman.data;

import java.math.BigInteger;

public class BigIntegerHelp {

  public static char[] toBinaryArray(byte[] bytes) {
    if (bytes.length == 2) {
      return "".toCharArray();
    }
    BigInteger integer = new BigInteger(bytes);
    return integer.toString(2).toCharArray();
  }
}
