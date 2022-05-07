class LNode{
    private BinaryNode data;
    private LNode next;

    public LNode(){
        this.data = null;
        this.next = null;;
    }
    public LNode(BinaryNode o){
        this.data = o;
        this.next = null;
    }

    public BinaryNode getElement(){
        return data;
    }
    public LNode getNext(){
        return next;
    }

    public void setElement(BinaryNode new_data){
        this.data = new_data;
    }
    public void setNext(LNode new_next){
        this.next = new_next;
    }
}

public class LinkedList {
    LNode head;

    public LinkedList(){
        this.head=null;
    }

    public void printList(){
        LNode pointer = this.head;
        while (pointer != null){
            System.out.print(pointer.getElement().key + " ");
            /*
            if (pointer.getElement().parent==null) {
                System.out.print(" (Node address = "+pointer.getElement()+" key:"+pointer.getElement().key+" parent: Null"+") ");
            }
            else{
                System.out.print(" (Node address = "+pointer.getElement()+" key:"+pointer.getElement().key+" parent: "+pointer.getElement().parent+" parenKeyt: "+pointer.getElement().parent.key+") ");
            }
            */
            pointer = pointer.getNext();
        }
        System.out.println("");
    }

    public void push(BinaryNode new_data){
        LNode new_LNode = new LNode(new_data);
        if (this.head==null || this.head.getElement().key>new_data.key) {
            new_LNode.setNext(this.head);
            this.head = new_LNode;
            return;
        }
        
        LNode pointer = this.head;
        while ((pointer.getNext()!=null) && (pointer.getNext().getElement().key<new_data.key)){
            pointer = pointer.getNext();
        }
        new_LNode.setNext(pointer.getNext());
        pointer.setNext(new_LNode);
    }

    public void remove(BinaryNode theBinaryNode){
        if (this.head==null) {
            //System.out.print("Can't delete from an empty list\t");
            //System.out.println("The Node here is: " + theBinaryNode + " and key = "+ theBinaryNode.key+"\n");
            //System.out.println(this.head.getElement().left.left.left.left.left.left);
            return;
        }
        if (this.head.getElement().key==theBinaryNode.key) {
            this.head = this.head.getNext();
            return;
        }
        LNode pointer1 = this.head;
        while (pointer1.getNext()!=null) {
            if (pointer1.getNext().getElement().key==theBinaryNode.key){
                pointer1.setNext(pointer1.getNext().getNext());
                return;
            }
            pointer1 = pointer1.getNext();
        }
        System.out.println("Key was not found in the list");
    }

}