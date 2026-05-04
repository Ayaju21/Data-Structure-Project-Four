package application;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
public class MonthsTree implements Drawable {

	private MonthNode root;

	public MonthNode getRoot() {
		return root;
	}

	public List<MonthNode> inOrderList() {
		List<MonthNode> months = new ArrayList<>();
		inOrderListRec(root , months);
		return months;
	}

	private void inOrderListRec(MonthNode root , List<MonthNode> list) {
		if ( root != null ) {
			inOrderListRec(root.left , list);
			list.add(root);
			inOrderListRec(root.right , list);
		}
	}

	// Get the height of a node
	private int height(MonthNode node) {
		if ( node == null ) {
			return 0;
		}
		return node.height;
	}

	// Get the balance factor of a node
	private int getBalance(MonthNode node) {
		if ( node == null ) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	// Update the height of a node
	private void updateHeight(MonthNode node) {
		if ( node != null ) {
			node.height = 1 + Math.max(height(node.left) , height(node.right));
		}
	}

	// Right rotate a subtree rooted with y
	private MonthNode rightRotate(MonthNode y) {
		MonthNode x = y.left;
		MonthNode T2 = x.right;

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
	private MonthNode leftRotate(MonthNode x) {
		MonthNode y = x.right;
		MonthNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		updateHeight(x);
		updateHeight(y);

		// Return the new root
		return y;
	}

	// Insert a month into the AVL tree
	public void insert(int month) {
		root = insertRec(root , month);
	}

	private MonthNode insertRec(MonthNode node , int month) {
		// Perform the normal BST insertion
		if ( node == null ) {
			return new MonthNode(month);
		}

		if ( month < node.getMonth() ) {
			node.left = insertRec(node.left , month);
		} else if ( month > node.getMonth() ) {
			node.right = insertRec(node.right , month);
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
		if ( balance > 1 && month < node.left.getMonth() ) {
			return rightRotate(node);
		}
		// Right Right Case
		if ( balance < -1 && month > node.right.getMonth() ) {
			return leftRotate(node);
		}
		// Left Right Case
		if ( balance > 1 && month > node.left.getMonth() ) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		// Right Left Case
		if ( balance < -1 && month < node.right.getMonth() ) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		// No rotation needed, return the unchanged node
		return node;
	}

	// Delete a month from the AVL tree
	public void delete(int month) {
		root = deleteRec(root , month);
	}

	private MonthNode deleteRec(MonthNode root , int month) {
		// Perform standard BST delete
		if ( root == null ) {
			return root;
		}

		if ( month < root.getMonth() ) {
			root.left = deleteRec(root.left , month);
		} else if ( month > root.getMonth() ) {
			root.right = deleteRec(root.right , month);
		} else {
			// MonthNode with only one child or no child
			if ( (root.left == null) || (root.right == null) ) {
				MonthNode temp = null;
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
				// MonthNode with two children: Get the inorder successor (smallest
				// in the right subtree)
				MonthNode temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.setMonth(temp.getMonth());

				// Delete the inorder successor
				root.right = deleteRec(root.right , temp.getMonth());
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

	// A utility function to find the node with the smallest month value
	private MonthNode minValueNode(MonthNode node) {
		MonthNode current = node;

		/* loop down to find the leftmost leaf */
		while ( current.left != null ) {
			current = current.left;
		}

		return current;
	}

	// Search for a month in the AVL tree
	public MonthNode search(int month) {
		return searchRec(root , month);
	}

	private MonthNode searchRec(MonthNode root , int month) {
		// Base Cases: root is null or month is present at root
		if ( root == null || root.getMonth() == month ) {
			return root;
		}

		// Key is greater than root's month
		if ( month > root.getMonth() ) {
			return searchRec(root.right , month);
		}

		// Key is smaller than root's month
		return searchRec(root.left , month);
	}

	// Perform in-order traversal of the AVL tree
	public void inOrderTraversal() {
		inOrderTraversalRec(root);
		System.out.println();
	}

	private void inOrderTraversalRec(MonthNode root) {
		if ( root != null ) {
			inOrderTraversalRec(root.left);
			System.out.print(root.getMonth() + " ");
			inOrderTraversalRec(root.right);
		}
	}

	public boolean addRecord(Electricity record) {
		if ( root == null ) {
			root = new MonthNode(record.getDate().getMonthValue());
			root.addRecord(record);
			return true;
		}
		if ( this.search(record.getDate().getMonthValue()) == null ) {
			this.insert(record.getDate().getMonthValue());
			return this.search(record.getDate().getMonthValue()).addRecord(record);
		}
		return this.search(record.getDate().getMonthValue()).addRecord(record);
	}

	public boolean editRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		MonthNode day = search(record.getDate().getMonthValue());
		if ( day == null ) {
			return false;
		}
		return day.editRecord(record);
	}

	public boolean deleteRecord(Electricity record) {
		if ( root == null ) {
			return false;
		}
		MonthNode n = search(record.getDate().getMonthValue());
		if ( n == null ) {
			return false;
		}
		return n.deleteRecord(record);
	}

	public String getLevelOrderData(boolean pretify) {
		String output = "";
		if ( this.root == null ) {
			return output;
		}
		int maxHeight = root.height;
		int height = maxHeight;

		Queue<MonthNode> queue = new LinkedList<>();
		queue.add(root);

		while ( !queue.isEmpty() ) {
			int levelSize = queue.size();

			for ( int i = 0 ; i < levelSize ; i++ ) {
				MonthNode current = queue.poll();
				String data = current == null ? "    " : current.getMonthString();
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

}
