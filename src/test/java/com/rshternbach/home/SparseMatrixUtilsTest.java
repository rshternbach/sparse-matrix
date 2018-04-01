package com.rshternbach.home;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rshternbach.home.SparseMatrix;
import com.rshternbach.home.SparseMatrixUtils;

public class SparseMatrixUtilsTest {

	SparseMatrix sparseMatrix;

	@Before
	public void setup() {
		sparseMatrix = new SparseMatrix(100, 100);
		sparseMatrix.add(4, 1, 2);
		sparseMatrix.add(4, 1, 3);
		sparseMatrix.add(4, 2, 2);
		sparseMatrix.add(4, 2, 3);
		sparseMatrix.add(4, 4, 3);
		sparseMatrix.add(3, 4, 4);
		sparseMatrix.add(4, 5, 3);
		sparseMatrix.add(6, 5, 4);
	}

	@Test
	public void matrixDegreeV1Test() {
		assertEquals(2, SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix));
	}

	@Test
	public void matrixNoDegreeV1Test() {
		sparseMatrix.add(5, 40, 4);
		sparseMatrix.add(5, 40, 5);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix));
	}

	@Test
	public void matrixHighestDegreeV1Test() {
		sparseMatrix.add(5, 40, 4);
		assertEquals(2, SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix));
	}

	@Test
	public void matrixNotBalancedV1Test() {
		sparseMatrix.setColumns(10);
		sparseMatrix.setRows(100);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix));
	}

	@Test
	public void matrixEmptyV1Test() {
		sparseMatrix = new SparseMatrix(100, 100);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix));
	}

	@Test
	public void matrixDegreeV2Test() {
		assertEquals(2, SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix));
	}

	@Test
	public void matrixNoDegreeV2Test() {
		sparseMatrix.add(5, 40, 4);
		sparseMatrix.add(5, 40, 5);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix));
	}

	@Test
	public void matrixHighestDegreeV2Test() {
		sparseMatrix.add(5, 40, 4);

		assertEquals(2, SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix));
	}

	@Test
	public void matrixNotBalancedV2Test() {
		sparseMatrix.setColumns(10);
		sparseMatrix.setRows(100);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix));
	}

	@Test
	public void matrixEmptyTest() {
		sparseMatrix = new SparseMatrix(100, 100);
		assertEquals(0, SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix));
	}

}
