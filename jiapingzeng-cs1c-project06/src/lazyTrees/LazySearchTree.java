package lazyTrees;

import java.util.*;

/**
 * A binary search tree structure that implements lazy deletion
 * @param <E> Type to be stored
 */

public class LazySearchTree<E extends Comparable<? super E>> implements Cloneable {

    /**
     * "soft" size of tree, does not include deleted nodes
     */
    protected int mSize;

    /**
     * "hard" size of the tree, includes both deleted and undeleted nodes
     */
    protected int mSizeHard;

    /**
     * root of the tree
     */
    protected LazySTNode mRoot;

    /**
     * initialize empty tree
     */
    public LazySearchTree() {
        clear();
    }

    /**
     * Check if tree is empty
     * @return true if empty, false if not
     */
    public boolean empty() {
        return mSize == 0;
    }

    /**
     * Return "soft" size of the tree
     * @return size of the tree
     */
    public int size() {
        return mSize;
    }

    /**
     * Return "hard" size of the tree
     * @return size of the tree
     */
    public int sizeHard() {
        return mSizeHard;
    }

    /**
     * Clear the tree
     */
    public void clear() {
        mSize = 0;
        mSizeHard = 0;
        mRoot = null;
    }

    /**
     * Return height of the tree
     * @return height of the tree
     */
    public int showHeight() {
        return findHeight(mRoot, -1);
    }

    /**
     * Find item in the tree
     * @param x Item to look for
     * @return Item found
     */
    public E find(E x) {
        LazySTNode result = find(mRoot, x);
        if (result == null) throw new NoSuchElementException();
        return result.data;
    }

    /**
     * Find smallest item in the tree
     * @return smallest item in the tree
     */
    public E findMin() {
        if (mRoot == null) throw new NoSuchElementException();
        return findMin(mRoot).data;
    }

    /**
     * Find larrgest item in the tree
     * @return largest item in the tree
     */
    public E findMax() {
        if (mRoot == null) throw new NoSuchElementException();
        return findMax(mRoot).data;
    }

    /**
     * Check if an element is in the tree
     * @param x item to look for
     * @return true if found, false otherwise
     */
    public boolean contains(E x) {
        return find(mRoot, x) != null;
    }

    /**
     * Insert item into the tree
     * @param x Item to be inserted
     * @return true if success, false otherwise
     */
    public boolean insert(E x) {
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return mSize != oldSize;
    }

    /**
     * Mark item for deletion
     * @param x item to be marked
     * @return true if success, false otherwise
     */
    public boolean remove(E x) {
        int oldSize = mSize;
        remove(mRoot, x);
        return mSize != oldSize;
    }

    /**
     * Remove item from tree
     * @param x item to be removed
     * @return true if success, false otherwise
     */
    public boolean removeHard(E x) {
        int oldSize = mSize;
        mRoot = removeHard(mRoot, x);
        return mSize != oldSize;
    }

    /**
     * Traverse through the tree include deleted nodes
     * @param func Functor to be applied
     * @param <F> Traversable functor
     */
    public <F extends Traverser<? super E>> void traverseHard(F func) {
        traverseHard(func, mRoot);
    }

    /**
     * Traverse through the tree excluding deleted nodes
     * @param func Functor to be applied
     * @param <F> Traversable functor
     */
    public <F extends Traverser<? super E>> void traverseSoft(F func) {
        traverseSoft(func, mRoot);
    }

    /**
     * Remove items marked for deletion
     * @return true if items are successfully removed, false otherwise
     */
    public boolean collectGarbage() {
        int oldSize = mSizeHard;
        mRoot = collectGarbage(mRoot);
        return mSizeHard != oldSize;
    }

    /**
     * Clone the tree
     * @return The cloned tree
     * @throws CloneNotSupportedException if clone fails
     */
    public Object clone() throws CloneNotSupportedException {
        LazySearchTree<E> newObject = (LazySearchTree<E>)super.clone();
        newObject.clear();
        newObject.mRoot = cloneSubtree(mRoot);
        newObject.mSize = mSize;
        newObject.mSizeHard = mSizeHard;
        return newObject;
    }

    // HELPERS ----------------------------------------------

    /**
     * Insert item at specific subtree
     * @param root root of subtree
     * @param x item to be inserted
     * @return item inserted
     */
    protected LazySTNode insert(LazySTNode root, E x) {
        if (root == null) {
            mSize++;
            mSizeHard++;
            return new LazySTNode(x, null, null);
        }
        int compareResult = x.compareTo(root.data);
        if (compareResult < 0) root.left = insert(root.left, x);
        else if (compareResult > 0) root.right = insert(root.right, x);
        else {
            if (root.deleted) {
                root.deleted = false;
                mSize++;
            }
        }
        return root;
    }

    /**
     * Mark item for deletion from a specific tree
     * @param root root of subtree
     * @param x item to be removed
     */
    protected void remove(LazySTNode root, E x) {
        if (root == null) return;
        int compareResult = x.compareTo(root.data);
        if (compareResult < 0) remove(root.left, x);
        else if (compareResult > 0) remove(root.right, x);
        else {
            if (!root.deleted) {
                root.deleted = true;
                mSize--;
            }
        }
    }

    /**
     * Remove item from subtree
     * @param root root of subtree
     * @param x item to be matched
     * @return root of subtree
     */
    protected LazySTNode removeHard(LazySTNode root, E x) {
        int compareResult;
        if (root == null) return null;
        compareResult = x.compareTo(root.data);
        if (compareResult < 0) root.left = removeHard(root.left, x);
        else if (compareResult > 0) root.right = removeHard(root.right, x);
        else if (root.left != null && root.right != null) {
            if (!root.deleted) mSize--;
            LazySTNode min = findMinHard(root.right);
            root.data = min.data;
            root.deleted = min.deleted;
            min.deleted = true;
            root.right = removeHard(root.right, root.data);
        } else {
            if (!root.deleted) mSize--;
            if (root.left != null) {
                root = root.left;
            } else {
                root = root.right;
            }
            mSizeHard--;
        }
        return root;
    }

    /**
     * "Hard" traverse through a specific subtree
     * @param func functor to be applied
     * @param node root of the subtree
     * @param <F> traversable functor
     */
    protected <F extends Traverser<? super E>> void traverseHard(F func, LazySTNode node) {
        if (node == null) return;
        traverseHard(func, node.left);
        func.visit(node.data);
        traverseHard(func, node.right);
    }

    /**
     * "Soft" traverse through a specific subtree
     * @param func functor to be applied
     * @param node root of the subtree
     * @param <F> traversable functor
     */
    protected <F extends Traverser<? super E>> void traverseSoft(F func, LazySTNode node) {
        if (node == null) return;
        traverseSoft(func, node.left);
        if (!node.deleted) func.visit(node.data);
        traverseSoft(func, node.right);
    }

    /**
     * Locate item in specific subtree
     * @param root root of subtree
     * @param x item to be located
     * @return located item
     */
    protected LazySTNode find(LazySTNode root, E x) {
        if (root == null) return null;
        int compareResult = x.compareTo(root.data);
        if (compareResult < 0) return find(root.left, x);
        if (compareResult > 0) return find(root.right, x);
        if (!root.deleted) return root;
        return null;
    }

    /**
     * Find smallest item of specific subtree
     * @param root root of subtree
     * @return smallest item found
     */
    protected LazySTNode findMin(LazySTNode root) {
        if (root == null) return null;
        LazySTNode min = findMin(root.left);
        if (min != null) return min;
        if (root.deleted) return findMin(root.right);
        else return root;
    }

    /**
     * Find smallest item of specific subtree, including items marked for deletion
     * @param root root of subtree
     * @return smallest item found
     */
    protected LazySTNode findMinHard(LazySTNode root) {
        if (root == null) return null;
        if (root.left == null) return root;
        return findMinHard(root.left);
    }

    /**
     * Find largest item of specific subtree
     * @param root root of subtree
     * @return largest item found
     */
    protected LazySTNode findMax(LazySTNode root) {
        if (root == null) return null;
        LazySTNode max = findMax(root.right);
        if (max != null) return max;
        if (root.deleted) return findMax(root.left);
        else return root;
    }

    /**
     * Find largest item of specific subtree, including items marked for deletion
     * @param root root of subtree
     * @return largest item found
     */
    protected LazySTNode findMaxHard(LazySTNode root) {
        if (root == null) return null;
        if (root.right == null) return root;
        return findMaxHard(root.right);
    }

    /**
     * Remove items marked for deletion under a specific subtree
     * @param root root of subtree
     * @return root of subtree
     */
    protected LazySTNode collectGarbage(LazySTNode root) {
        if (root == null) return null;
        if (root.left != null) root.left = collectGarbage(root.left);
        if (root.right != null) root.right = collectGarbage(root.right);
        if (root.deleted) root = removeHard(root, root.data);
        return root;
    }

    /**
     * Clone a specific subtree
     * @param root root of the subtree
     * @return root of cloned tree
     */
    protected LazySTNode cloneSubtree(LazySTNode root) {
        LazySTNode newNode;
        if (root == null) return null;
        newNode = new LazySTNode(root.data, cloneSubtree(root.left), cloneSubtree(root.right));
        return newNode;
    }

    /**
     * Find height of the subtree
     * @param node node to start at
     * @param height height of the tree so far
     * @return height of a subtree
     */
    protected int findHeight(LazySTNode node, int height) {
        int leftHeight, rightHeight;
        if (node == null) return height;
        height++;
        leftHeight = findHeight(node.left, height);
        rightHeight = findHeight(node.right, height);
        return leftHeight > rightHeight ? leftHeight : rightHeight;
    }

    /**
     * Store data and left/right siblings
     */
    private class LazySTNode {
        /**
         * Left and right siblings
         */
        LazySTNode left, right;

        /**
         * Data stored in the node
         */
        E data;

        /**
         * Root of this node
         */
        LazySTNode myRoot;

        /**
         * Mark node as deleted
         */
        boolean deleted;

        /**
         * Initialize empty node
         */
        public LazySTNode() {
            this(null, null, null);
        }

        /**
         * Initialize node
         * @param data data to be stored
         * @param left left sibling
         * @param right right sibling
         */
        public LazySTNode(E data, LazySTNode left, LazySTNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.deleted = false;
        }
    }
}