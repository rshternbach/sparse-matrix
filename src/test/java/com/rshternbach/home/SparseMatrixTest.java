package com.rshternbach.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rshternbach.home.SparseMatrix;

public class SparseMatrixTest {

	SparseMatrix sparseMatrix;

	@Before
	public void setup() {
		sparseMatrix = new SparseMatrix(2, 2);
	}

	@Test
	public void addElementTest() {
		sparseMatrix.add(10, 0, 0);
		assertEquals(10, sparseMatrix.get(0, 0).value);
	}

	@Test
	public void removeElementTest() {
		sparseMatrix.add(10, 0, 0);
		assertEquals(10, sparseMatrix.remove(0, 0));
		assertNull(sparseMatrix.get(0, 0));
	}

	@Test
	public void matrixEmptyTest() {
		sparseMatrix = new SparseMatrix(100, 100);
		assertTrue(sparseMatrix.isEmpty());
	}

	@Test
	public void matrixFullTest() {
		sparseMatrix.add(1, 0, 0);
		sparseMatrix.add(1, 0, 1);
		sparseMatrix.add(1, 1, 1);
		sparseMatrix.add(1, 1, 0);
		assertTrue(sparseMatrix.isFull());
	}

}
