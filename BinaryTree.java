// Node
class BinaryNode{
    int key;
    BinaryNode left;
    BinaryNode right;
    // Attrbutes corresponding to AVL Tree
    int height; // Height of the AVL tree considering given Node as root
    // Attrbutes corresponding to Generic Tree
    int level; // Level in the generic tree considering root as level 1
    LinkedList children;
    BinaryNode parent;
    public BinaryNode(int data){
        key = data;
        left = null;
        right = null;
        height = 0; // Just keep it 0 here (Avltree implementation)
        level = 1; // Just keep it 1 here (Generic Tree implementation)
        children = new LinkedList(); // (Generic Tree implementation)
        parent = null; //(Generic Tree implementation)
    }
}

/**
 * BinaryTree
 */
public class BinaryTree {
    BinaryNode root;
    public BinaryTree(){
        root = null;
    }

    private void preorderTraversal(BinaryNode root){
        if (root==null) {
            return;
        }
        System.out.print(root.key + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
    void preorderTraversal(){
        preorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(BinaryNode root){
        if (root==null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.print(root.key+ " ");
        inorderTraversal(root.right);
    }
    void inorderTraversal(){
        inorderTraversal(root);
        System.out.println();
    }

    private void postorderTraversal(BinaryNode root){
        if (root==null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.key + " ");
    }
    void postorderTraversal(){
        postorderTraversal(root);
        System.out.println();
    }
}