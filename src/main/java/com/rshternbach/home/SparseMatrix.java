package com.rshternbach.home;

//TODO: consider adding DEBUG logging for sparse matrix methods
public class SparseMatrix {

	private int rows = 0;
	private int columns = 0;
	private int numberOfElements = 0;

	private SparseMatrixNode start = null;

	class SparseMatrixNode {

		public int value;
		public int x;
		public int y;

		public SparseMatrixNode next = null;

		private boolean visited = false;

		SparseMatrixNode(int value, int x, int y) {

			this.value = value;
			this.x = x;
			this.y = y;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}
	}

	SparseMatrix(int l, int c) {

		this.rows = l;
		this.columns = c;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public SparseMatrixNode getStart() {
		return start;
	}

	/**
	 * get method.
	 *
	 * Search if there is an element with coordenates "x" and "y" in the sparse
	 * matrix.
	 *
	 * @param int
	 *            x Horizontal coordenate.
	 * @param int
	 *            y Vertical coordenate.
	 * @return SparseMatrixNode | null Element or null, if element not exist in
	 *         matrix.
	 */
	public SparseMatrixNode get(int x, int y) {
		if (this.isEmpty())
			return null;
		SparseMatrixNode p = this.start;
		while (p != null) {
			if ((p.x == x) && (p.y == y)) {
				return p;
			}
			if (p.x > x) {
				return null;
			}
			if ((p.x == x) && (p.y > y)) {
				return null;
			}
			p = p.next;
		}
		return null;
	}

	/**
	 * getPrevious method.
	 *
	 * Utility method to Search the previous of the element with coordenates "x"
	 * and "y" in the sparse matrix.
	 *
	 * @param int
	 *            x Horizontal coordenate.
	 * @param int
	 *            y Vertical coordenate.
	 * @return SparseMatrixNode | null The method returns previous element with
	 *         the coordenates, if element exists.
	 */
	private SparseMatrixNode getPrevious(int x, int y) {
		if (this.isEmpty()) {
			return null;
		}
		SparseMatrixNode p = this.start;
		/*
		 * if (p.next == null) { return null; }
		 */
		while (p.next != null) {
			if ((p.next.x == x) && (p.next.y == y)) {
				return p;
			}
			if (p.next.x > x) {
				return p;
			}
			if ((p.next.x == x) && (p.next.y > y)) {
				return p;
			}
			p = p.next;
		}
		return p;
	}

	/**
	 * add method.
	 *
	 * Add an element in the sparse matrix. It always insert a value in matrix,
	 * replacing an existing value or creating a new element in the matrix.
	 *
	 * @param int
	 *            key Integer key.
	 * @param int
	 *            x Horizontal coordenate.
	 * @param int
	 *            y Vertical coordenate.
	 * @return void
	 */
	// TODO: add logic to add a new node to the matrix, so all the nodes will be
	// aligned in
	// an order according to their x,y values
	public void add(int value, int x, int y) {
		if ((x < 0) || (y < 0)) {
			return;
		}
		if ((x >= this.rows) || (y >= this.columns)) {
			return;
		}
		SparseMatrixNode previous = this.getPrevious(x, y);
		SparseMatrixNode element = this.get(x, y);
		// Empty sparse matrix.
		if ((previous == null) && (element == null)) {
			this.numberOfElements++;
			SparseMatrixNode node = new SparseMatrixNode(value, x, y);
			this.start = node;
		}
		// First element.
		else if ((previous == null) && (element != null)) {
			element.value = value;
		} else if ((previous != null) && (element == null)) {
			// Last element.
			if (previous.next == null) {
				this.numberOfElements++;
				SparseMatrixNode node = new SparseMatrixNode(value, x, y);
				previous.next = node;
			}
			// Element in the middle.
			else {
				this.numberOfElements++;
				SparseMatrixNode node = new SparseMatrixNode(value, x, y);
				node.next = previous.next;
				previous.next = node;
			}
		}
		// An element in the middle (or also in the end).
		else if ((previous != null) && (element != null)) {
			element.value = value;
		}
	}

	/**
	 * Remove method.
	 *
	 * Remove an element in the sparse matrix.
	 *
	 * @param int
	 *            x Horizontal coordenate.
	 * @param int
	 *            y Vertical coordenate.
	 * @return int Element key or 0 if element is not found in matrix
	 */
	public int remove(int x, int y) {
		if (this.isEmpty()) {
			return 0;
		}
		if (this.start.x == x && this.getStart().y == y) {
			int value = this.start.value;
			this.start = null;
			return value;
		}
		SparseMatrixNode node = this.get(x, y);

		if (node != null) {
			int key = node.value;
			SparseMatrixNode previous = this.getPrevious(x, y);
			if (previous != null) {
				previous.next = node.next;
			} else {
				this.start = node.next;
			}
			return key;
		}
		return 0;
	}

	/**
	 * isFull method.
	 *
	 * Determines if the sparse matrix is full, i.e., if all coordenates of
	 * matrix are filled with some value.
	 *
	 * @return boolean
	 */
	public boolean isFull() {
		return this.numberOfElements == (this.rows * this.columns);
	}

	/**
	 * isEmpty method.
	 *
	 * Determines if the sparse matrix is empty, i.e., if it does not have any
	 * coordenate filled.
	 *
	 * @return boolean
	 */
	public boolean isEmpty() {
		return this.numberOfElements == 0;
	}

	/**
	 * toString method.
	 *
	 * Provide some visual funcionality to see the elements inside the sparse
	 * matrix.
	 *
	 * @return String Representation of the sparse matrix in the moment by a
	 *         string.
	 */
	@Override
	public String toString() {
		if (this.isEmpty())
			return "Empty sparse matrix.";
		SparseMatrixNode p = this.start;
		String description = "Sparse Matrix: \n ";
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if ((p != null) && (p.x == i && p.y == j)) {
					description += String.format("%2d", p.value);
					p = p.next;
				} else {
					description += String.format("%2d", 0);
				}
				description += "  ";
			}
			description += " \n ";
		}
		description += "Elements: " + this.numberOfElements + " \n ";
		return description;
	}

}
