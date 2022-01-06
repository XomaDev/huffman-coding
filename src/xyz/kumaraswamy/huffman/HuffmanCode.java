package xyz.kumaraswamy.huffman;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;
import xyz.kumaraswamy.huffman.data.Writer;

public class HuffmanCode {

  private final byte[] bytes;
  private final boolean debug;

  private HashMap<Byte, Integer> frequencies;
  private HashMap<Byte, String> huffmanCodes;

  private Node root;

  private final Writer writer;

  @SuppressWarnings("unused")
  public HuffmanCode(byte[] bytes) {
    this(bytes, false);
  }

  public HuffmanCode(byte[] bytes, boolean debug) {
    this.bytes = bytes;
    this.debug = debug;
    frequenciesMapping();

    writer = new Writer(frequencies);

    if (debug) {
      frequencies.forEach(
          (b, i) -> System.out.println("Freq " + new String(new byte[]{b}) + ": " + i));
    }
  }

  private void frequenciesMapping() {
    frequencies = new HashMap<>();
    huffmanCodes = new HashMap<>();

    for (byte byt : bytes) {
      frequencies.merge(byt, 1, Integer::sum);
    }
  }

  public byte[] encode() throws IOException {
    root = generateQue(frequencies).poll();

    if (root instanceof Leaf) {
      huffmanCodes.put(((Leaf) root).getByt(), "0");
      writer.writeHeader((byte) 1);
    } else {
      generateHuffmanCodes(root, "");
      writer.writeHeader((byte) 0);
    }
    return writer.write(getEncodedBytes());
  }

  protected static PriorityQueue<Node> generateQue(HashMap<Byte, Integer> frequencies) {
    PriorityQueue<Node> queue = new PriorityQueue<>();
    frequencies.forEach((b, i) -> queue.add(new Leaf(b, i)));

    while (queue.size() > 1) {
      queue.add(new Node(queue.poll(),
          Objects.requireNonNull(queue.poll())));
    }
    return queue;
  }

  public static byte[] decode(byte[] bytes) {
    return new HuffmanDecode(bytes).decode();
  }

  private void generateHuffmanCodes(Node node, String huffman) {
    if (node instanceof Leaf) {
      if (debug) {
        System.out.println("Huffman Code for " + ((Leaf) node).byteAsTxt() + ": " + huffman);
      }
      huffmanCodes.put(((Leaf) node).getByt(), huffman);
      return;
    }
    generateHuffmanCodes(node.getLeftNode(), huffman.concat("0"));
    generateHuffmanCodes(node.getRightNode(), huffman.concat("1"));
  }

  public Node getRootNode() {
    return root;
  }

  private byte[] getEncodedBytes() {
    StringBuilder result = new StringBuilder("1");

    for (byte byt : bytes) {
      result.append(huffmanCodes.get(byt));
    }
    result.append(1);
    if (debug) {
      System.out.println(
          result.substring(
              1, result.length() - 1));
    }
    return new BigInteger(result.toString(), 2).toByteArray();
  }
}
