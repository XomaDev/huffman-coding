package xyz.kumaraswamy.huffman.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.StringJoiner;

public class Writer {

  public static final char DATA_SEPARATOR = ',';
  public static final char LINE_DATA_SEPARATOR = '~';

  public static final char LINE_SEP = '\n';

  private final HashMap<Byte, Integer> freqs;

  private final ByteArrayOutputStream headers = new ByteArrayOutputStream();

  public Writer(HashMap<Byte, Integer> freqs) {
    this.freqs = freqs;
  }

  public void writeHeader(byte b) {
    headers.write(b);
  }

  public byte[] write(byte[] bytes) throws IOException {
    String dataSep = Character.toString(DATA_SEPARATOR);

    StringJoiner treeBytes = new StringJoiner(dataSep),
        treeFreqs = new StringJoiner(dataSep);

    freqs.keySet().forEach(b -> {
      treeBytes.add(Byte.toString(b));
      treeFreqs.add(Integer.toString(freqs.get(b)));
    });

    StringJoiner joined = new StringJoiner(
        Character.toString(LINE_DATA_SEPARATOR));

    joined.add(treeBytes.toString());
    joined.add(treeFreqs.toString());

    ByteArrayOutputStream writer = new ByteArrayOutputStream();
    writer.write(
        Base64.getEncoder().encode(
            joined.toString().getBytes()
        )
    );
    writer.write(LINE_SEP);
    writer.write(headers.toByteArray());
    writer.write(LINE_SEP);
    writer.write(bytes);
    return writer.toByteArray();
  }
}
