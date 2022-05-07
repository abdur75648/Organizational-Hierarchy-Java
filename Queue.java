public class Queue {

    static class QueueNode{
        private BinaryNode data = null;
        private QueueNode next = null;
        // Constructors
        private QueueNode(BinaryNode o){this.data = o;}
        private QueueNode() {}
        // Accessor methods
        private BinaryNode getElement(){return data;}
        private QueueNode getNext(){return next;}
        // Modifier methods
        // public void setElement(BinaryNode new_data){this.data = new_data;}
        private void setNext(QueueNode new_next){this.next = new_next;}
    }

    // Head Node: Simialr to Top Pointer
    private QueueNode head;
    private QueueNode tail;

    // Constructor
    public Queue(){
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty(){
        return this.head==null;
    }

    public int getSize(){
        if (this.head==null) {
            return 0;
        }
        QueueNode var = this.head;
        int count = 0;
        while (var!=null) {
            var = var.getNext();
            count+=1;
        }
        return count;
    }

    public void enqueue(BinaryNode new_data){
        QueueNode new_node = new QueueNode(new_data);
        if (this.head==null) {
            this.head = new_node;
            this.tail = new_node;
        } else {
            this.tail.setNext(new_node);
            this.tail = new_node;
        }
    }

    public BinaryNode dequeue(){
        if (this.head == null) {
            return null;
        }
        else if (this.head==this.tail) {
            BinaryNode popped = this.head.data;
            this.head = null;
            this.tail = null;
            return popped;
        }
        else {
            BinaryNode popped = this.head.data; // this.head.getElement();
            this.head = this.head.next; // this.head.getNext();
            return popped;
        }
    }
 
    public BinaryNode getFront(){
        if (this.head == null) {
            return null;
        }
        else {
            return this.head.getElement();
        }
    }

    public BinaryNode getRear(){
        if (this.head == null) {
            return null;
        }
        else {
            return this.tail.getElement();
        }
    }
}
