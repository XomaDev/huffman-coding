package xyz.kumaraswamy.huffman;

import lombok.Getter;

@Getter
public class Leaf extends Node {

  private final byte byt;

  public Leaf(byte byt, double freq) {
    super(freq);
    this.byt = byt;
  }

  public String byteAsTxt() {
    return new String(new byte[] {byt});
  }

  @Override
  public String toString() {
    return "Leaf{" +
        "byt=" + byteAsTxt() +
        '}';
  }
}
