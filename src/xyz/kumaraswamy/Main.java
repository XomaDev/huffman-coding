package xyz.kumaraswamy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import xyz.kumaraswamy.huffman.HuffmanCode;

public class Main {

  private static final String path = "\\Huffman Coding\\files\\file.txt";

  public static void main(String[] args) throws IOException {
    byte[] input = Files.readAllBytes(new File("\\Huffman Coding\\files\\input.png").toPath());

    HuffmanCode code = new HuffmanCode(input);

    byte[] encoded = code.encode();

    System.out.println(code.getRootNode());
    FileOutputStream stream = new FileOutputStream(path);
    stream.write(encoded);
    stream.close();

    byte[] decoded = HuffmanCode.decode(encoded);

    try (FileOutputStream result =
        new FileOutputStream(
            "\\Huffman Coding\\files\\decoded.png")) {
      result.write(decoded);
    }
  }
}
