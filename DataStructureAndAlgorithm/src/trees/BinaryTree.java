package trees;

public class BinaryTree<T extends Comparable<T>> {
    Node<T> root;

    // Preorder Traversal (Root → Left → Right)
    public void preorder(Node<T> node) {
        if (node == null) return;
        System.out.print(node.data + " ");   // Visit root
        preorder(node.left);                 // Left subtree
        preorder(node.right);                // Right subtree
    }

    // Inorder Traversal (Left → Root → Right)
    public void inorder(Node<T> node) {
        if (node == null) return;
        inorder(node.left);                // Left subtree
        System.out.print(node.data + " "); // Visit root
        inorder(node.right);               // Right subtree

    }

    // Postorder Traversal (Left → Right → Root)
    public void postorder(Node<T> node) {
        if (node == null) return;
        postorder(node.left);              // Left subtree
        postorder(node.right);             // Right subtree
        System.out.print(node.data + " "); // Right subtree
    }

    // Insert a value into the BST
    public Node<T> insert(Node<T> root, T value) {
        if (root == null) {
            return new Node<>(value); // Create a new node if the tree is empty
        }

        // Compare the value with the current node's data
        int comparison = value.compareTo(root.data);

        if (comparison < 0) {
            // Insert into the left subtree
            root.left = insert(root.left, value);
        } else if (comparison > 0) {
            // Insert into the right subtree
            root.right = insert(root.right, value);
        }
        // If comparison == 0, the value is already in the tree (duplicate), so do nothing

        return root; // Return the (possibly updated) root node
    }

        // Public method to start insertion from the root
        public void insert(T value) {
        root = insert(root, value);

    }

    // Search for a value
    public boolean search(Node<T> root, T value) {
        if (root == null) return false;
        if (root.data == value) return true;
        if (value.compareTo(root.data) < 0) return search(root.left, value);
        return search(root.right, value);
    }

    // Inorder traversal (sorted order for BST)
    public void inOrder(Node<T> root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }
}
