import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class BST{
    static class TNode{
        TNode left;
        TNode right;
        int data;

        public TNode(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static TNode insert(TNode root, int data){
        if(root == null) return new TNode(data);
        if(root.data < data) root.right = insert(root.right, data);
        else if(root.data > data) root.left = insert(root.left, data);
        return root;
    }

    public static TNode delete(TNode root, int data){
        if(root == null) return root;
        if(root.data < data) root.right = delete(root.right, data);
        else if(root.data > data) root.left = delete(root.left, data);
        else{
            if(root.right == null) return root.left;
            else if(root.left == null) return root.right;
            else{
                int minValue = getMin(root.right);
                root.data = minValue;
                root.right = delete(root.right, minValue);
            }
        }
        return root;
    }

    public static boolean search(TNode root, int data){
        if(root == null) return false;
        if(root.data == data) return true;
        else if(root.data < data) return search(root.right, data);
        else return search(root.left, data);
    }
    public static int getMin(TNode root){
        if(root == null) return -1;
        if(root.left == null) return root.data;
        else{
            return getMin(root.left);
        }
    }

    public static void inOrderTraversal(TNode root){
        if(root == null) return;
        inOrderTraversal(root.left);
        System.out.print(root.data+"->");
        inOrderTraversal(root.right);
    }

    public static void preOrderTraversal(TNode root){
        if(root == null) return;
        System.out.print(root.data+"->");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }

    public static void postOrderTraversal(TNode root){
        if(root == null) return;
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.data+"->");
    }

    public static void levelOrderTraversal(TNode root){
        if(root == null) return;
        Queue<TNode> queue = new LinkedList<TNode>();
        queue.add(root);
        while(!queue.isEmpty()){
            TNode node = queue.remove();
            System.out.print(node.data+"->");
            if(node.left!=null) queue.add(node.left);
            if(node.right!=null) queue.add(node.right);
        }
        System.out.println("Level Order Traversal");
    }

    public static void leftView(TNode root){
        if(root == null) return;
        Queue<TNode> queue = new LinkedList<TNode>();
        int i=0;
        queue.add(root);
        while(!queue.isEmpty()){
            i=0;
            int size = queue.size();
            while(i<size){
                i++;
                TNode node = queue.remove();
                if(i==1) System.out.print(node.data+"->");
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
            }
        }
        System.out.println("Left View");
    }

    public static void rightView(TNode root){
        if(root == null) return;
        Queue<TNode> queue = new LinkedList<TNode>();
        int i=0;
        queue.add(root);
        while(!queue.isEmpty()){
            i=0;
            int size = queue.size();
            while(i<size){
                i++;
                TNode node = queue.remove();
                if(i==1) System.out.print(node.data+"->");
                if(node.right!=null) queue.add(node.right);
                if(node.left!=null) queue.add(node.left);
            }
        }
        System.out.println("Right View");
    }

    public static void main(String args[])throws java.io.IOException{
        TNode root = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        while(st.hasMoreTokens()){
            root = BST.insert(root, Integer.parseInt(st.nextToken()));
        }
        BST.inOrderTraversal(root);
        System.out.println("Inorder Traversal");
        BST.preOrderTraversal(root);
        System.out.println("Pre Traversal");
        BST.postOrderTraversal(root);
        System.out.println("Post Traversal");
        BST.delete(root, Integer.parseInt(br.readLine().trim()));
        BST.inOrderTraversal(root);
        System.out.println("Inorder Traversal");
        boolean isFound = BST.search(root, Integer.parseInt(br.readLine().trim()));
        if(isFound) System.out.println("Value Found");
        else System.out.println("Value Not Found");
        BST.levelOrderTraversal(root);
        BST.leftView(root);
        BST.rightView(root);
    }
}