public interface Deque<Item> {

    public void addFirst(Item item);

    public void addLast(Item item);

    /**
     * Return true if the size is 0.
     * @return
     */
    public boolean isEmpty();

    public int size();

    public void printDeque();

    public Item removeFirst();

    public Item removeLast();

    public Item get(int index);
}