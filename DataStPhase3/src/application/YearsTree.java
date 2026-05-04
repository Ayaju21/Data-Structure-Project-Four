package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class YearsTree implements Drawable {

	private YearNode root;

	public YearNode getRoot() {
		return root;
	}

	public List<YearNode> inOrderList() {
		List<YearNode> years = new ArrayList<>();
		inOrderListRec(root , years);
		return years;
	}

	private void inOrderListRec(YearNode root , List<YearNode> list) {
		if ( root != null ) {
			inOrderListRec(root.left , list);
			list.add(root);
			inOrderListRec(root.right , list);
		}
	}

	// Get the height of a node
	private int height(YearNode node) {
		if ( node == null ) {
			return 0;
		}
		return node.height;
	}

	// Get the balance factor of a node
	private int getBalance(YearNode node) {
		if ( node == null ) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	// Update the height of a node
	private void updateHeight(YearNode node) {
		if ( node != null ) {
			node.height = 1 + Math.max(height(node.left) , height(node.right));
		}
	}

	// Right rotate a subtree rooted with y
	private YearNode rightRotate(YearNode y) {
		YearNode x = y.left;
		YearNode T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		updateHeight(y);
		updateHeight(x);

		// Return the new root
		return x;
	}

	// Left rotate a subtree rooted with x
	private YearNode leftRotate(YearNode x) {
		YearNode y = x.right;
		YearNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		updateHeight(x);
		updateHeight(y);

		// Return the new root
		return y;
	}

	// Insert a year into the AVL tree
	public void insert(int year) {
		root = insertRec(root , year);
	}

	private YearNode insertRec(YearNode node , int year) {
		// Perform the normal BST insertion
		if ( node == null ) {
			return new YearNode(year);
		}

		if ( year < node.year ) {
			node.left = insertRec(node.left , year);
		} else if ( year > node.year ) {
			node.right = insertRec(node.right , year);
		} else {
			// Duplicate keys are not allowed in AVL trees
			return node;
		}

		// Update height of the current node
		updateHeight(node);

		// Get the balance factor to check if this node became unbalanced
		int balance = getBalance(node);

		// Perform rotations if needed
		// Left Left Case
		if ( balance > 1 && year < node.left.year ) {
			return rightRotate(node);
		}
		// Right Right Case
		if ( balance < -1 && year > node.right.year ) {
			return leftRotate(node);
		}
		// Left Right Case
		if ( balance > 1 && year > node.left.year ) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		// Right Left Case
		if ( balance < -1 && year < node.right.year ) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		// No rotation needed, return the unchanged node
		return node;
	}

	// Delete a year from the AVL tree
	public void delete(int year) {
		root = deleteRec(root , year);
	}

	private YearNode deleteRec(YearNode root , int year) {
		// Perform standard BST delete
		if ( root == null ) {
			return root;
		}

		if ( year < root.year ) {
			root.left = deleteRec(root.left , year);
		} else if ( year > root.year ) {
			root.right = deleteRec(root.right , year);
		} else {
			// YearNode with only one child or no child
			if ( (root.left == null) || (root.right == null) ) {
				YearNode temp = null;
				if ( temp == root.left ) {
					temp = root.right;
				} else {
					temp = root.left;
				}

				// No child case
				if ( temp == null ) {
					temp = root;
					root = null;
				} else { // One child case
					root = temp; // Copy the contents of the non-empty child
				}
			} else {
				// YearNode with two children: Get the inorder successor (smallest
				// in the right subtree)
				YearNode temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.year = temp.year;

				// Delete the inorder successor
				root.right = deleteRec(root.right , temp.year);
			}
		}

		// If the tree had only one node then return
		if ( root == null ) {
			return root;
		}

		// Update height of the current node
		updateHeight(root);

		// Get the balance factor to check if this node became unbalanced
		int balance = getBalance(root);

		// Perform rotations if needed
		// Left Left Case
		if ( balance > 1 && getBalance(root.left) >= 0 ) {
			return rightRotate(root);
		}
		// Left Right Case
		if ( balance > 1 && getBalance(root.left) < 0 ) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}
		// Right Right Case
		if ( balance < -1 && getBalance(root.right) <= 0 ) {
			return leftRotate(root);
		}
		// Right Left Case
		if ( balance < -1 && getBalance(root.right) > 0 ) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}

		return root;
	}

	// A utility function to find the node with the smallest year value
	private YearNode minValueNode(YearNode node) {
		YearNode current = node;

		/* loop down to find the leftmost leaf */
		while ( current.left != null ) {
			current = current.left;
		}

		return current;
	}

	// Search for a year in the AVL tree
	public YearNode search(int year) {
		return searchRec(root , year);
	}

	private YearNode searchRec(YearNode root , int year) {
		// Base Cases: root is null or year is present at root
		if ( root == null || root.year == year ) {
			return root;
		}

		// Key is greater than root's year
		if ( year > root.year ) {
			return searchRec(root.right , year);
		}

		// Key is smaller than root's year
		return searchRec(root.left , year);
	}

	// Perform in-order traversal of the AVL tree
	public void inOrderTraversal() {
		inOrderTraversalRec(root);
		System.out.println();
	}

	private void inOrderTraversalRec(YearNode root) {
		if ( root != null ) {
			inOrderTraversalRec(root.left);
			System.out.print(root.year + " ");
			inOrderTraversalRec(root.right);
		}
	}

	public String getLevelOrderData(boolean pretify) {
		String output = "";
		if ( this.root == null ) {
			return output;
		}
		int maxHeight = root.height;
		int height = maxHeight;

		Queue<YearNode> queue = new LinkedList<>();
		queue.add(root);

		while ( !queue.isEmpty() ) {
			int levelSize = queue.size();
			for ( int i = 0 ; i < levelSize ; i++ ) {
				YearNode current = queue.poll();
				String data = current == null ? "    " : current.year + "";
				output += drawLevelElement(data , maxHeight , height , 4 , pretify);
				if ( current != null ) {
					queue.add(current.left);
					queue.add(current.right);
				}
			}
			output += "\n";
			height--;
		}
		return output.replaceAll("(\\n[ ]*\\n)" , "\n");
	}

	public boolean addRecord(Electricity record) {
		if ( root == null ) {
			root = new YearNode(record.getDate().getYear());
			root.addRecord(record);
			return true;
		}
		if ( this.search(record.getDate().getYear()) == null ) {
			this.insert(record.getDate().getYear());
			return this.search(record.getDate().getYear()).addRecord(record);
		}
		return false;
	}

	public boolean editRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		YearNode n = search(record.getDate().getYear());
		if ( n == null ) {
			return false;
		}
		return n.editRecord(record);
	}

	public boolean deleteRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		YearNode n = search(record.getDate().getYear());
		if ( n == null ) {
			return false;
		}
		return n.deleteRecord(record);
	}

}