/*
    Code written by Ashton Smith
    ajs190019
 */

public class Node <T> {
    private Node next;
    private Node down;
    private T data;
    //private Node prev;

    // Default constructor
    public Node() {
    }

    // Overloaded constructor
    public Node(T data) {
        this.next = null;
        this.down = null;
        this.data = data;
        //this.prev = prev;
    }

    // Setters
    public void setNext(Node next) {
        this.next = next;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Getters
    public Node getNext() {
        return next;
    }

    public Node getDown() {
        return down;
    }

    public T getData() {
        return data;
    }

}
