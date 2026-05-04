package application;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DaysTree implements Drawable {

	private DayNode root;

	public DayNode getRoot() {
		return root;
	}

	public List<DayNode> inOrderList() {
		List<DayNode> days = new ArrayList<>();
		inOrderListRec(root , days);
		return days;
	}

	private void inOrderListRec(DayNode root , List<DayNode> list) {
		if ( root != null ) {
			inOrderListRec(root.left , list);
			list.add(root);
			inOrderListRec(root.right , list);
		}
	}

	// Get the height of a node
	private int height(DayNode node) {
		if ( node == null ) {
			return 0;
		}
		return node.height;
	}

	// Get the balance factor of a node
	private int getBalance(DayNode node) {
		if ( node == null ) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	// Update the height of a node
	private void updateHeight(DayNode node) {
		if ( node != null ) {
			node.height = 1 + Math.max(height(node.left) , height(node.right));
		}
	}

	// Right rotate a subtree rooted with y
	private DayNode rightRotate(DayNode y) {
		DayNode x = y.left;
		DayNode T2 = x.right;

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
	private DayNode leftRotate(DayNode x) {
		DayNode y = x.right;
		DayNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		updateHeight(x);
		updateHeight(y);

		// Return the new root
		return y;
	}

	// Insert a day into the AVL tree
	public void insert(int day) {
		root = insertRec(root , day);
	}

	private DayNode insertRec(DayNode node , int day) {
		// Perform the normal BST insertion
		if ( node == null ) {
			return new DayNode(day);
		}

		if ( day < node.day ) {
			node.left = insertRec(node.left , day);
		} else if ( day > node.day ) {
			node.right = insertRec(node.right , day);
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
		if ( balance > 1 && day < node.left.day ) {
			return rightRotate(node);
		}
		// Right Right Case
		if ( balance < -1 && day > node.right.day ) {
			return leftRotate(node);
		}
		// Left Right Case
		if ( balance > 1 && day > node.left.day ) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		// Right Left Case
		if ( balance < -1 && day < node.right.day ) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		// No rotation needed, return the unchanged node
		return node;
	}

	// Delete a day from the AVL tree
	public void delete(int day) {
		root = deleteRec(root , day);
	}

	private DayNode deleteRec(DayNode root , int day) {
		// Perform standard BST delete
		if ( root == null ) {
			return root;
		}

		if ( day < root.day ) {
			root.left = deleteRec(root.left , day);
		} else if ( day > root.day ) {
			root.right = deleteRec(root.right , day);
		} else {
			// DayNode with only one child or no child
			if ( (root.left == null) || (root.right == null) ) {
				DayNode temp = null;
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
				// DayNode with two children: Get the inorder successor (smallest
				// in the right subtree)
				DayNode temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.day = temp.day;

				// Delete the inorder successor
				root.right = deleteRec(root.right , temp.day);
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

	// A utility function to find the node with the smallest day value
	private DayNode minValueNode(DayNode node) {
		DayNode current = node;

		/* loop down to find the leftmost leaf */
		while ( current.left != null ) {
			current = current.left;
		}

		return current;
	}

	// Search for a day in the AVL tree
	public DayNode search(int day) {
		return searchRec(root , day);
	}

	private DayNode searchRec(DayNode root , int day) {
		// Base Cases: root is null or day is present at root
		if ( root == null || root.day == day ) {
			return root;
		}

		// Key is greater than root's day
		if ( day > root.day ) {
			return searchRec(root.right , day);
		}

		// Key is smaller than root's day
		return searchRec(root.left , day);
	}

	// Perform in-order traversal of the AVL tree
	public void inOrderTraversal() {
		inOrderTraversalRec(root);
		System.out.println();
	}

	private void inOrderTraversalRec(DayNode root) {
		if ( root != null ) {
			inOrderTraversalRec(root.left);
			System.out.print(root.day + " ");
			inOrderTraversalRec(root.right);
		}
	}

	public boolean addRecord(Electricity record) {
		if ( root == null ) {
			root = new DayNode(record.getDate().getDayOfMonth());
			root.setRecord(record);
			return true;
		} else {
			DayNode day = search(record.getDate().getDayOfMonth());
			if ( day == null ) {
				insert(record.getDate().getDayOfMonth());
				day = search(record.getDate().getDayOfMonth());
				day.setRecord(record);
				return true;
			}
			return false;
		}
	}

	public boolean editRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		DayNode day = search(record.getDate().getDayOfMonth());
		if ( day != null ) {
			day.setRecord(record);
		}
		return day != null;
	}

	public boolean deleteRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		DayNode day = search(record.getDate().getDayOfMonth());
		if ( day != null ) {
			delete(record.getDate().getDayOfMonth());
		}
		return day != null;
	}

	public String getLevelOrderData(boolean pretify) {
		String output = "";
		if ( this.root == null ) {
			return output;
		}
		int maxHeight = root.height;
		int height = maxHeight;

		Queue<DayNode> queue = new LinkedList<>();
		queue.add(root);

		while ( !queue.isEmpty() ) {
			int levelSize = queue.size();

			for ( int i = 0 ; i < levelSize ; i++ ) {
				DayNode current = queue.poll();
				String data = current == null ? "  " : current.day + "";
				output += drawLevelElement(data , maxHeight , height , 2 , pretify);

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

}
