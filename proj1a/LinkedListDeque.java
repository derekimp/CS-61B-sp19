
public class LinkedListDeque<Type> {

    /** Node in the linked list that could hold any type of data. */
    public class Node {
        public Type item;
        public Node next;
        public Node prev;

        /** Constructor of Node, pointing each node to its previous item and next item. */
        public Node (Type first, Node prev_item, Node next_item) {
            item = first;
            prev = prev_item;
            next = next_item;
        }
    }

    /**
     * Parameters of LinkedListDeque.
     */
    private Node sentinel;
    private int size;

    /**
     * Empty constructor of LinkedListDeque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Copy constructor of LinkedListDeque.
     * Created a deep copy of other.
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        int otherLength = other.size();
        size = otherLength;

        Node temp = sentinel;

        for (int i = 0; i < otherLength; i += 1) {
            Type new_item = (Type) other.get(i);
            Node next_node = new Node(new_item, null, null);

            temp.next = next_node;
            next_node.prev = temp;
            temp = temp.next;
        }

        temp.next = sentinel;
        sentinel.prev = temp;
    }

    /**
     * Constructor of LinkedListDeque, holding
     */
    public LinkedListDeque(Type first) {
        sentinel = new Node(first, null, null);
        Node first_node = new Node(first, sentinel, sentinel);
        sentinel.next = first_node;
        sentinel.prev = first_node;
        size = 1;
    }

    /**
     * Insert an item to the first position in the list, but after the sentinel node.
     * @param item
     */
    public void addFirst(Type item) {
        Node next_node = sentinel.next;
        Node cur_node = new Node(item, sentinel, next_node);
        sentinel.next = cur_node;
        next_node.prev = cur_node;
        size += 1;
    }

    /**
     * Insert an item to the last position in the list, followed by the sentinel node.
     * @param item
     */
    public void addLast(Type item) {
        Node prev_node = sentinel.prev;
        Node cur_node = new Node(item, prev_node, sentinel);
        prev_node.next = cur_node;
        sentinel.prev = cur_node;
        size += 1;
    }

    /**
     * Check if the list is Empty.
     * @return True if the size of the linked list is 0.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the number of items in the linked list.
     * @return int, size of the linked list.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        if (this.isEmpty()) {
            System.out.println("The linked list is empty.");
        }

        Node temp = sentinel;
        temp = temp.next;
        while (temp.next != sentinel) {
            String output = temp.item + " ";
            System.out.print(output);
            temp = temp.next;
        }

        System.out.println(temp.item);
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public Type removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        Type res = sentinel.next.item;
        Node next_node = sentinel.next.next;
        sentinel.next = next_node;
        next_node.prev = sentinel;

        size -= 1;

        return res;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public Type removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        Type res = sentinel.prev.item;
        Node prev_node = sentinel.prev.prev;
        prev_node.next = sentinel;
        sentinel.prev = prev_node;

        size -= 1;

        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return Type item
     */
    public Type get(int index) {
        Node temp = sentinel;
        temp = temp.next;
        while (index != 0 & temp != sentinel) {
            temp = temp.next;
            index -= 1;
        }

        if (temp == sentinel) {
            return null;
        }

        return temp.item;
    }
}