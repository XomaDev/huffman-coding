package xyz.kumaraswamy.huffman;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Node implements Comparable<Node> {


  private final double freq;

  private Node leftNode;
  private Node rightNode;

  public Node(Node leftNode, Node rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;

    freq = leftNode.getFreq() + rightNode.getFreq();
  }

  @Override
  public String toString() {
    return "Node{" +
        "freq=" + freq +
        ", leftNode=" + leftNode +
        ", rightNode=" + rightNode +
        '}';
  }

  @Override
  public int compareTo(Node node) {
    return Double.compare(freq, node.getFreq());
  }
}
