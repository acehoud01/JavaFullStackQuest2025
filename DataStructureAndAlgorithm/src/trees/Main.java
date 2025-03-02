package trees;

public class Main {
    public static void main(String[] args) {
//        Node<Integer> root = new Node<>(1);
//        root.left = new Node<>(2);
//        root.right = new Node<>(3);
//        root.left.left = new Node<>(4);
//        root.left.right = new Node<>(5);
//
//        System.out.println("Root: " + root.data);
//        System.out.println("Left child of root : " + root.left.data);
//        System.out.println("Right child of a root: " + root.right.data);
//        System.out.println("Left child of left : " + root.left.left.data);
//
//        Node<String> familyTree = new Node<>("Grandpa");
//        familyTree.left = new Node<>("Father");
//        familyTree.right = new Node<>("Mother");
//        System.out.println(familyTree.data);

        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.root = new Node<>(1);
        tree.root.left = new Node<>(2);
        tree.root.right = new Node<>(3);
        tree.root.left.left = new Node<>(4);
        tree.root.left.right = new Node<>(5);

        System.out.println("Preorder traversal: ");
        tree.preorder(tree.root);
        System.out.println();
        System.out.println("Inorder traversal: ");
        tree.inorder(tree.root);
        System.out.println();
        System.out.println("Postorder traversal: ");
        tree.postorder(tree.root);

    }
}
