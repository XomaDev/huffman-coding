package xyz.kumaraswamy.huffman;

import static xyz.kumaraswamy.huffman.HuffmanCode.generateQue;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import xyz.kumaraswamy.huffman.data.BigIntegerHelp;
import xyz.kumaraswamy.huffman.data.Writer;

public class HuffmanDecode {

  private final byte[] bytes;

  public HuffmanDecode(byte[] bytes) {
    this.bytes = bytes;
  }

  public byte[] decode() {
    ByteArrayOutputStream encoded = new ByteArrayOutputStream();
    ByteArrayOutputStream tree = new ByteArrayOutputStream();
    ByteArrayOutputStream headers = new ByteArrayOutputStream();

    int state = 0;

    for (byte byt : bytes) {
      if (byt == 10) {
        if (state == 2) {
          encoded.write(byt);
        } else {
          state++;
        }
      } else if (state == 0) {
        tree.write(byt);
      } else if (state == 1) {
        headers.write(byt);
      } else {
        encoded.write(byt);
      }
    }
    String treeDecoded = new String(
        Base64.getDecoder()

            .decode(tree.toByteArray()));

    String[] keysAnValues = treeDecoded.split(
        Character.toString(
            Writer.LINE_DATA_SEPARATOR));

    String dataSep = Character.toString(Writer.DATA_SEPARATOR);

    String[] keys = keysAnValues[0].split(dataSep);
    String[] values = keysAnValues[1].split(dataSep);

    int len = keys.length;
    HashMap<Byte, Integer> frequencies = new HashMap<>(len);

    for (int i = 0; i < len; i++) {
      frequencies.put(
          Byte.parseByte(keys[i]),
          Integer.parseInt(values[i]));
    }
    Node root = generateQue(frequencies).poll();
    return decodeNode(root, encoded.toByteArray(), headers.toByteArray());
  }

  private byte[] decodeNode(Node root, byte[] encoded, byte[] headers) {
    ByteArrayOutputStream result = new ByteArrayOutputStream();

    boolean isOneB = headers[0] == 1;
    char[] bin = BigIntegerHelp.toBinaryArray(encoded);

    Node tempN = root;
    for (int i = 1; i < bin.length - 1; i++) {
      char ch = bin[i];
      if (isOneB) {
        result.write(((Leaf) tempN).getByt());
      } else {
        tempN = ch == '0' ? tempN.getLeftNode() : tempN.getRightNode();
        if (tempN instanceof Leaf) {
          result.write(((Leaf) tempN).getByt());
          tempN = root;
        }
      }
    }
    return result.toByteArray();
  }
}
