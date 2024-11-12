package solovev.java.javacore.syntax;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;

@ToString
public class RedBlackTree {
    /*
    properties:
    1. Node Color: Each node is either red or black.
    2. Root Property: The root of the tree is always black.
    3. Red Property: Red nodes cannot have red children (no two consecutive red nodes on any path).
    4. Black Property: Every path from a node to its descendant null nodes (leaves) has the same number of black nodes.
    5. Leaf Property: All leaves (NIL nodes) are black.
     */
    @ToString
    @EqualsAndHashCode
    static class Tree<T extends Comparable> {
        Node root;

        @ToString
        @EqualsAndHashCode
        class Node {
            boolean isBlack;
            @ToString.Exclude
            @EqualsAndHashCode.Exclude
            Node parent;

            Node left, right;
            T data;

            /**
             for empty nodes
             */
            Node() {
                isBlack = true;
            }

            /**
             * red by default
             */
            Node(T data) {
                this.data = data;
                left = new Node();
                right = new Node();
                left.setParent(this);
                right.setParent(this);
            }

            boolean isLarger(Node other) {
                return data.compareTo(other.data) > 0;
            }

            boolean isEmpty() {
                return isNull(data);
            }

            void setParent(Node parent) {
                this.parent = parent;
            }

            boolean isRed() {
                return !isBlack;
            }
        }

        void insert(T data) {
            Node newNode = new Node(data);
            if (isNull(root)) {
                root = newNode;
            } else {
                Node parent = findParent(root, newNode);
                setParent(parent, newNode);
            }
            fixBalance(newNode);
        }

        Node findParent(Node possibleParent, Node toInsert) {
            Node possiblePlace = possibleParent.isLarger(toInsert) ? possibleParent.left : possibleParent.right;
            return possiblePlace.isEmpty()
                    ? possibleParent : findParent(possiblePlace, toInsert);
        }

        void setParent(Node parent, Node child) {
            if (parent.isLarger(child)) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            child.setParent(parent);
        }

        void fixBalance(Node currentNode) {
            // Case 1: New node is root
            if (currentNode == root) {
                currentNode.isBlack = true;
                return;
            }

            // Case 2: Parent is black; nothing happends
            Node parent = currentNode.parent;
            if (parent.isBlack) {
                return;
            }

            // Case 3: Parent is red
            Node grandparent = parent.parent;
            Node uncle = (grandparent.left == parent) ? grandparent.right : grandparent.left;

            // Case 3a: Uncle is red (Recoloring)
            if (uncle.isRed()) {
                recolour(parent, uncle, grandparent);
                fixBalance(grandparent);  // Recheck properties from grandparent
                // Case 3b: Uncle is black (Rotation)
            } else {
                //parent is left child
                if (grandparent.left == parent) {
                    //current is right
                    if (parent.right == currentNode) {
                        rotateLeft(parent);
                    }
                    rotateRight(grandparent);
                    // parent is right child
                } else {
                    //current is left
                    if (parent.left == currentNode) {
                        rotateRight(parent);
                    }
                    rotateLeft(grandparent);
                }
                parent.isBlack = true;
                grandparent.isBlack = false;
            }
        }

        void recolour(Node parent, Node uncle, Node grandparent) {
            parent.isBlack = true;
            uncle.isBlack = true;
            grandparent.isBlack = false;
        }

        void rotateRight(Node node) {
            Node leftChild = node.left;
            node.left = leftChild.right;

            if (!isNull(leftChild.right)) {
                leftChild.right.parent = node;
            }
            leftChild.parent = node.parent;

            if (node.parent == null) {
                root = leftChild;
            } else if (node == node.parent.right) {
                node.parent.right = leftChild;
            } else {
                node.parent.left = leftChild;
            }

            leftChild.right = node;
            node.parent = leftChild;
        }

        void rotateLeft(Node node) {
            Node rightChild = node.right;
            node.right = rightChild.left;

            if (!isNull(rightChild.left)) {
                rightChild.left.parent = node;
            }
            rightChild.parent = node.parent;

            if (node.parent == null) {
                root = rightChild;
            } else if (node == node.parent.left) {
                node.parent.left = rightChild;
            } else {
                node.parent.right = rightChild;
            }

            rightChild.left = node;
            node.parent = rightChild;
        }
    }

    @Nested
    public class RedBlackTreeTest {

        private RedBlackTree.Tree<Integer> tree;
        private RedBlackTree.Tree<Integer> expectedTree;

        @BeforeEach
        void setUp() {
            tree = new RedBlackTree.Tree<>();
            expectedTree = new RedBlackTree.Tree<>();
        }

        @Test
        void testRootIsBlackAfterSingleInsert() {
            tree.insert(10);
            assertNotNull(tree.root);
            assertTrue(tree.root.isBlack, "The root node should be black.");
        }

        @Test
        void simpleBalancedTreeOf3Values() {
            //given
            expectedTree.root = tree.new Node(1);
            expectedTree.root.isBlack = true;
            expectedTree.root.left = tree.new Node(0);
            expectedTree.root.right = tree.new Node(2);
            //when
            tree.insert(1);
            tree.insert(2);
            tree.insert(0);
            //then
            assertEquals(expectedTree, tree);
        }

        @Test
        void simpleBalancedTreeRecoloured() {
            //given
            expectedTree.root = tree.new Node(1);
            expectedTree.root.isBlack = true;
            expectedTree.root.left = tree.new Node(0);
            expectedTree.root.right = tree.new Node(2);
            expectedTree.root.right.right = tree.new Node(3);
            //recolour
            expectedTree.root.left.isBlack =true;
            expectedTree.root.right.isBlack =true;

            //when
            tree.insert(1);
            tree.insert(2);
            tree.insert(0);
            tree.insert(3);
            //then
            assertEquals(expectedTree, tree);
        }

        @Test
        void testRedNodeHasNoRedChild() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(1);
            tree.insert(6);
            tree.insert(12);
            tree.insert(18);
            verifyRedNodeNoRedChild(tree.root);
        }

        private void verifyRedNodeNoRedChild(RedBlackTree.Tree.Node node) {
            if (node == null || node.isEmpty()) return;

            if (node.isRed()) {
                assertTrue(node.left.isBlack, "Red node has red left child");
                assertTrue(node.right.isBlack, "Red node has red right child");
            }
            verifyRedNodeNoRedChild(node.left);
            verifyRedNodeNoRedChild(node.right);
        }

        @Test
        void testBalancedTreeBlackHeight() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.insert(1);
            tree.insert(6);
            tree.insert(12);
            tree.insert(18);

            int expectedBlackHeight = calculateBlackHeight(tree.root);
            verifyBlackHeight(tree.root, 0, expectedBlackHeight);
        }

        private int calculateBlackHeight(RedBlackTree.Tree.Node node) {
            if (node == null || node.isEmpty()) return 0;
            int leftHeight = calculateBlackHeight(node.left);
            return node.isBlack ? leftHeight + 1 : leftHeight;
        }

        private void verifyBlackHeight(RedBlackTree.Tree.Node node, int currentBlackHeight, int expectedBlackHeight) {
            if (node == null || node.isEmpty()) {
                assertEquals(expectedBlackHeight, currentBlackHeight, "Black height mismatch.");
                return;
            }
            if (node.isBlack) currentBlackHeight++;
            verifyBlackHeight(node.left, currentBlackHeight, expectedBlackHeight);
            verifyBlackHeight(node.right, currentBlackHeight, expectedBlackHeight);
        }

        @Test
        void testTreeStructureAfterRotations() {
            tree.insert(4);
            tree.insert(3);
            tree.insert(5);
            tree.insert(2);  // recolour
            tree.insert(1);  // Will cause a right rotation

            RedBlackTree.Tree<Integer>.Node expectedRoot = tree.new Node(3);
            RedBlackTree.Tree<Integer>.Node expectedLeft = tree.new Node(2);
            RedBlackTree.Tree<Integer>.Node expectedRight = tree.new Node(4);
            RedBlackTree.Tree<Integer>.Node expectedLeftLeft = tree.new Node(1);
            RedBlackTree.Tree<Integer>.Node expectedRightRight = tree.new Node(5);

            expectedRoot.isBlack = true;
            expectedLeft.isBlack = false;
            expectedRight.isBlack = false;
            expectedLeftLeft.isBlack = true;
            expectedRightRight.isBlack = true;

            expectedRoot.left = expectedLeft;
            expectedRoot.right = expectedRight;
            expectedLeft.left = expectedLeftLeft;
            expectedRight.right = expectedRightRight;

            assertEquals(expectedRoot, tree.root, "Tree structure is incorrect after rotations.");
        }
    }
}
