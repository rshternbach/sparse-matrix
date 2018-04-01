package com.rshternbach.home;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rshternbach.home.SparseMatrix.SparseMatrixNode;

public class SparseMatrixUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SparseMatrixUtils.class);

	// These arrays are used to get row and column numbers
	// of 8 neighbors of a given cell
	private static final int rowNbr[] = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static final int colNbr[] = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

	/**
	 * isSafe method.
	 *
	 * Utility method for getSparseMatrixDegree method to check if element in
	 * the coordinates exists, and not visited for DFS search algorithm
	 *
	 * @param SparseMatrix
	 *            the matrix to check the element
	 * @param int
	 *            x Horizontal coordinate.
	 * @param int
	 *            y Vertical coordinate.
	 * @return boolean true if safe| false if not safe
	 *
	 */
	private static boolean isSafe(SparseMatrix m, int x, int y) {
		return (x >= 0) && (x < m.getRows()) && (y >= 0) && (y < m.getColumns())
				&& (m.get(x, y) != null && !m.get(x, y).isVisited());
	}

	/**
	 * DFS method.
	 *
	 * Utility method for getSparseMatrixDegree to search for values in sparse
	 * matrix degree with DFS algorithm
	 *
	 * @param SparseMatrix
	 *            the matrix to search
	 * @param int
	 *            x Horizontal coordinate.
	 * @param int
	 *            y Vertical coordinate.
	 * @return int degree count summary of an "island" count
	 *
	 */
	private static int DFS(SparseMatrix m, int x, int y, int n) {
		// Mark this cell as visited
		if (m.get(x, y) != null) {
			m.get(x, y).setVisited(true);
			n++;
		}
		// Recursion for all connected neighbours
		for (int k = 0; k < 8; ++k)
			if (isSafe(m, x + rowNbr[k], y + colNbr[k]))
				n = DFS(m, x + rowNbr[k], y + colNbr[k], n);
		return n;
	}

	/**
	 * getSparseMatrixDegreeVersion1 method.
	 *
	 * a method to search for sparse matrix degree using DFS algorithm
	 *
	 * @param SparseMatrix
	 *            the matrix to check the degree
	 * @return int highest degree level of sparse matrix
	 */

	public static int getSparseMatrixDegreeVersion1(SparseMatrix m) {
		if (m.getColumns() != m.getRows()) {
			LOGGER.info("Matrix is not balanced! rows={}, columns={}", m.getRows(), m.getColumns());
			return 0;
		}

		if (m.isEmpty()) {
			LOGGER.info("Matrix is empty!");
			return 0;
		}
		int count = 0;
		int level = 0;
		ArrayList<Integer> levels = new ArrayList<>();
		for (int i = 0; i < m.getRows(); ++i)
			for (int j = 0; j < m.getColumns(); ++j)
				if (m.get(i, j) != null && !m.get(i, j).isVisited()) {
					level = DFS(m, i, j, level);
					if (isSquare(level)) {
						level = (int) Math.sqrt(level);
						levels.add(count, level);
						count++;
						LOGGER.info("island number={} has degree n={}", count, level);
						level = 0;
					} else {
						LOGGER.info("Island number={} is not an N level degree, this is not N level sparse matrix!",
								count + 1);
						return 0;
					}
				}
		Collections.sort(levels);
		LOGGER.info("Sparse Matrix highest degree={}", levels.get(count - 1));
		Collections.sort(levels);
		return levels.get(count - 1);
	}

	/**
	 * getSparseMatrixDegreeVersion2 method.
	 *
	 * a method to search for sparse matrix degree (more efficient way then
	 * version 1)
	 *
	 * @param SparseMatrix
	 *            the matrix to check the degree
	 * @return int highest degree level of sparse matrix
	 */

	public static int getSparseMatrixDegreeVersion2(SparseMatrix m) {
		if (m.getColumns() != m.getRows()) {
			LOGGER.info("Matrix is not balanced! rows={}, columns={}", m.getRows(), m.getColumns());
			return 0;
		}

		if (m.isEmpty()) {
			LOGGER.info("Matrix is empty!");
			return 0;
		}
		SparseMatrixNode start = m.getStart();
		ArrayList<Integer> levels = new ArrayList<>();
		int x1 = start.x;
		int y1 = start.y;
		int level = 1;
		int counter = 0;
		boolean isChanged = true;
		SparseMatrixNode node = start.next;
		while (node != null) {
			if (isChanged == false) {
				if (isSquare(level)) {
					level = (int) Math.sqrt(level);
					levels.add(counter, level);
					LOGGER.info("Island number={} has degree n={}", counter + 1, level);
					level = 1;
					counter++;
				} else {
					LOGGER.info("Island number={} is not an N level degree, this is not N level sparse matrix!",
							counter + 1);
					return 0;
				}
			}
			int x2 = node.x;
			int y2 = node.y;
			isChanged = false;
			for (int k = 0; k < 8; k++) {
				if (x2 == x1 + rowNbr[k] && y2 == y1 + colNbr[k]) {
					level++;
					isChanged = true;
				}

			}
			// last node in the matrix
			if (node.next == null) {
				if (isSquare(level)) {
					level = (int) Math.sqrt(level);
					levels.add(counter, level);
					LOGGER.info("Island number={} has degree n={}", counter + 1, level);
				} else {
					LOGGER.info("Island number={} is not an N level degree, this is not N level sparse matrix!",
							counter + 1);
					return 0;
				}
				if (isChanged == false) {
					levels.add(1);
					counter++;
					LOGGER.info("island number={} has degree n=1", counter + 1);
				}
			}
			x1 = x2;
			y1 = y2;
			node = node.next;
		}
		Collections.sort(levels);
		LOGGER.info("Sparse Matrix highest degree={}", levels.get(counter));
		Collections.sort(levels);
		return levels.get(counter);

	}

	private static boolean isSquare(int num) {
		double sqrt = Math.sqrt(num);
		int x = (int) sqrt;
		if (Math.pow(sqrt, 2) == Math.pow(x, 2)) {
			return true;
		}
		return false;
	}

}
