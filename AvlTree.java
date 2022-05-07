/**
 * AVlTree
 */

public class AvlTree extends BinaryTree {
    protected int size(BinaryNode root){
        if (root==null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }
    
    private BinaryNode searchRecursive(BinaryNode root,int key){
        if (root == null || key==root.key) {
            return root;
        }
        else if (key < root.key) {
            return searchRecursive(root.left, key);
        }
        else {
            return searchRecursive(root.right, key);
        }
    }
    BinaryNode search(int key){
        return searchRecursive(root,key);
    }

    // Helper functions
    private int getHeight(BinaryNode root){
        if (root==null) {
            return -1;
        } else {
            return root.height;
        }
    }
    private BinaryNode clockwise(BinaryNode root){
        BinaryNode var;
        var = root.left;
        root.left = var.right;
        var.right = root;
        root.height = 1 + Math.max(getHeight(root.left),getHeight(root.right));
        var.height = 1 + Math.max(getHeight(var.left),getHeight(var.right));
        return var;
    }
    private BinaryNode anticlockwise(BinaryNode root){
        BinaryNode var;
        var = root;
        root = var.right;
        var.right = root.left;
        root.left = var;
        var.height = 1 + Math.max(getHeight(var.left),getHeight(var.right));
        root.height = 1 + Math.max(getHeight(root.left),getHeight(root.right));
        return root;
    }

    private BinaryNode insertRecursive(BinaryNode root, int key) throws IllegalKeyException{
        if (root==null) {
            root = new BinaryNode(key);
            return root;
        }
        
        if (root.key > key) root.left = insertRecursive(root.left, key);
        else if (root.key < key) root.right= insertRecursive(root.right, key);
        else throw new IllegalKeyException();

        root.height = 1 + Math.max(getHeight(root.left),getHeight(root.right));

        ///// Rotations Now /////
        if (getHeight(root.left) - getHeight(root.right) > 1) { // Left - X case
            if (key < root.left.key) { // Left - Left case
                // Clockwise Root
                root = clockwise(root);
            }
            else { // Left - Right case
                // Anti-clockwise Root.left
                root.left = anticlockwise(root.left);
                // Clockwise Root
                root = clockwise(root);
            }
        }
        else if (getHeight(root.right) - getHeight(root.left) > 1){ // Right - X case
            if (key > root.right.key) { // Right - Right case
                // Anti-clockwise Root
                root = anticlockwise(root);
            }
            else { // Right - Left case
                // Clockwise Root.right
                root.right = clockwise(root.right);
                root = anticlockwise(root);
            }
        }

        return root;
    }
    void insertKey(int key) throws IllegalKeyException{
        root = insertRecursive(root, key);
    }

    private BinaryNode deleteRecursive(BinaryNode root, int key){
        //System.out.print("FUCK\t");
		//System.out.println(this.toStringCheck(10));
        //if (root==null) System.out.println("Printing in deleting recusrsively for key " + key + " . Root = none ");
        //else System.out.println("Printing in deleting recusrsively for key " + key + " . Root = " + root + " Root_key = " + root.key);
        //this.printOrg();
        
        if (root==null) {
            //System.out.println("Case 0");
            return root;
        }
        if (root.key > key) {
            //System.out.println("Case 1");
            root.left = deleteRecursive(root.left, key);         
        }
        else if (root.key < key) {
            //System.out.println("Case 2");
            root.right = deleteRecursive(root.right, key);
        }
        else {
            //System.out.println("Case 3");
            if (root.left == null && root.right == null) {
                //System.out.println("Case 3-0");
                root = null;
                return root;
            }
            else if (root.left == null) {
                //System.out.println("Case 3-1");
                return root.right;
            }
            else if (root.right == null) {
                //System.out.println("Case 3-2");
                return root.left;
            }
            else {
                //System.out.println("Case 3-3");
                // Finding minimum in right subtree
                BinaryNode var = root.right;
                while (var.left != null) {
                    var = var.left;
                }

                root.key = var.key;

                ///// Generic Tree Adjustments /////////////////////
                root.children = new LinkedList();               ////
                LinkedList rootChildren = root.children;        ////
                LinkedList varChildren = var.children;          ////
                LNode pointer = varChildren.head;               ////
                while (pointer!=null) {                         ////
                    rootChildren.push(pointer.getElement());    ////
                    pointer.getElement().parent = root;         ////
                    pointer = pointer.getNext();}               ////
                var.children.head = null;                       ////
                root.parent = var.parent;                       ////
                if (var.parent!=null) {                         ////
                    var.parent.children.remove(var);            ////
                    var.parent.children.push(root);}            ////
                root.level = var.level;                         ////
                ////////////////////////////////////////////////////
                root.right = deleteRecursive(root.right, root.key);
            }
        }

        root.height = 1 + Math.max(getHeight(root.left),getHeight(root.right));

        ///// Rotations Now /////
        if (getHeight(root.left) - getHeight(root.right) > 1) { // Left - X case
            if (getHeight(root.left.left) >= getHeight(root.left.right)) { // Left - Left case
                // Clockwise Root
                root = clockwise(root);
            }
            else { // Left - Right case
                // Anti-clockwise Root.left
                root.left = anticlockwise(root.left);
                // Clockwise Root
                root = clockwise(root);
            }
        }
        else if (getHeight(root.right) - getHeight(root.left) > 1){ // Right - X case
            if (getHeight(root.right.right) >= getHeight(root.right.left)) { // Right - Right case
                // Anti-clockwise Root
                root = anticlockwise(root);
            }
            else { // Right - Left case
                // Clockwise Root.right
                root.right = clockwise(root.right);
                root = anticlockwise(root);
            }
        }
        return root;
    }
    void deleteKey(int key){
        root = deleteRecursive(root, key);
    }

    public void printNodeDetails(BinaryNode node){
        System.out.println("----------");
        if (node==null){
            System.out.print("This node is null");
        }
        else if (node.parent==null && node.children.head==null) {
            System.out.print("Node is at address " + node + " . Key = " + node.key + " . Parent = null. Level = "
            + node.level + " . Height = " + node.height
            + " Children are: None\n" );
        }
        else if (node.parent==null && node.children.head!=null) {
            System.out.print("Node is at address " + node + " . Key = " + node.key + " . Parent = null. Level = "
            + node.level + " . Height = " + node.height
            + " Children are: ");
            node.children.printList();
        }
        else if(node.parent!=null && node.children.head==null){
            System.out.print("Node is at address " + node + " . Key = " + node.key + 
            " . Parent = " + node.parent + " and parent Key = " + node.parent.key +  " . Level = "
            + node.level + " . Height = " + node.height
            + " Children are: None\n" );
        }
        else{
            System.out.print("Node is at address " + node + " . Key = " + node.key + 
            " . Parent = " + node.parent + " and parent Key = " + node.parent.key +  " . Level = "
            + node.level + " . Height = " + node.height
            + " Children are: ");
            node.children.printList();
        }

        if (node.left==null && node.right==null) {
            System.out.println("Left and right are null\n");
          }
          else if (node.left!=null && node.right==null) {
            System.out.print("Node.left = " + node.left.key + " and node.right is null\n");
          }
          else if(node.left==null && node.right!=null){
            System.out.print("Node.left is null and node.right = " + node.right.key + "\n");
          }
          else{
            System.out.print("Node.left = " + node.left.key +  " and node.right = " + node.right.key + "\n");
          }
        // System.out.println("The memory address given by .search(key) method = " + this.search(node.key));
        System.out.println("----------");
    }


    private void RecursivePrintOrg(BinaryNode root){
        if (root==null) {
            return;
        }
        printNodeDetails(root);
        RecursivePrintOrg(root.left);
        RecursivePrintOrg(root.right);
    }
    protected void printOrg(){
        System.out.println("===================STARTING=============================");
        RecursivePrintOrg(this.root);
        System.out.println("====================ENDING=============================");
    }

}
