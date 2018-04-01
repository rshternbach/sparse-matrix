package com.rshternbach.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HomeApplication.class, args);
		LOGGER.info("Starting home sparse matrix application...");
		SparseMatrix sparseMatrix = new SparseMatrix(100, 100);
		// TODO: Consider loading the sparse matrix to the application from a
		// CSR file
		sparseMatrix.add(4, 1, 2);
		sparseMatrix.add(4, 1, 3);
		sparseMatrix.add(4, 2, 2);
		sparseMatrix.add(4, 2, 3);
		sparseMatrix.add(4, 4, 3);
		sparseMatrix.add(4, 5, 3);
		sparseMatrix.add(3, 4, 4);
		sparseMatrix.add(6, 5, 4);

		LOGGER.debug(sparseMatrix.toString());

		// Version 1 algorithm
		SparseMatrixUtils.getSparseMatrixDegreeVersion1(sparseMatrix);

		// Version 2 algorithm
		SparseMatrixUtils.getSparseMatrixDegreeVersion2(sparseMatrix);
	}
}
