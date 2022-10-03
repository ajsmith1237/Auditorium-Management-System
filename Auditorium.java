/*
    Code written by Ashton Smith
    ajs190019
 */

// Generic auditorium class
public class Auditorium <T>{
    private Node<T> head;

    // Constructor
    public Auditorium(int numOfSeats, int numOfRows) {

        // pointers for navigation
        Node cur = null;
        Node prevLine = null;

        // Creating new head node
        this.head = new Node<T>();
        cur = head;
        prevLine = head;

        // creating and forming the lists
        for (int y = 0; y < numOfRows; y++) // Rows
        {

            for (int x = 0; x < numOfSeats - 1; x++) // Seats in row
            {

                cur.setNext(new Node<T>());

                cur = cur.getNext();


            }

            if (y < numOfRows - 1) // creating next row down except last row
            {
                prevLine.setDown(new Node<T>());
                cur = prevLine.getDown();
                prevLine = cur;
            }

        }

    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getHead() {
        return head;
    }
}
