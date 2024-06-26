package br.ufrj.ic.matrix;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MatrixImpl implements Matrix {
    private List<List<Integer>> data;

    public MatrixImpl() {
        this.data = new ArrayList<>();
    }

    public MatrixImpl(List<List<Integer>> data) {
        this.data = data;
    }

    @Override
    public void addRow(List<Integer> row) {
        this.data.add(row);
    }

    @Override
    public void addElement(Integer row, Integer element) {
        this.data.get(row).add(element);
    }

    @Override
    public Integer get(Integer row, Integer col) {
        return this.data.get(row).get(col);
    }

    @Override
    public Integer getRowCount() {
        return this.data.size();
    }

    @Override
    public Integer getColumnCount(Integer row) {
        return this.data.get(row).size();
    }

    @Override
    public List<List<Integer>> getData() {
        return this.data;
    }

    @Override
    public Matrix multiply(Matrix B) {
        List<List<Integer>> C = new ArrayList<>();
        for (int i = 0; i < this.getRowCount(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < B.getColumnCount(0); j++) {
                int sum = 0;
                for (int k = 0; k < this.getColumnCount(i); k++) {
                    sum += this.get(i, k) * B.get(k, j);
                }
                row.add(sum);
            }
            C.add(row);
        }
        return new MatrixImpl(C);
    }

    @Override
    public Matrix getSubMatrix(int startRow, int startCol, int size) {
        List<List<Integer>> subMatrixData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(this.data.get(startRow + i).get(startCol + j));
            }
            subMatrixData.add(row);
        }
        return new MatrixImpl(subMatrixData);
    }

    @Override
    public void setSubMatrix(int startRow, int startCol, Matrix subMatrix) {
        for (int i = 0; i < subMatrix.getRowCount(); i++) {
            for (int j = 0; j < subMatrix.getColumnCount(0); j++) {
                this.data.get(startRow + i).set(startCol + j, subMatrix.get(i, j));
            }
        }
    }

    public static Matrix add(Matrix A, Matrix B) {
        int size = A.getRowCount();
        List<List<Integer>> resultData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(A.get(i, j) + B.get(i, j));
            }
            resultData.add(row);
        }
        return new MatrixImpl(resultData);
    }

    public static Matrix subtract(Matrix A, Matrix B) {
        int size = A.getRowCount();
        List<List<Integer>> resultData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(A.get(i, j) - B.get(i, j));
            }
            resultData.add(row);
        }
        return new MatrixImpl(resultData);
    }

    public static Matrix padMatrix(Matrix matrix, int newSize) {
        int originalSize = matrix.getRowCount();

        if(originalSize == newSize) return matrix;

        Matrix paddedMatrix = new MatrixImpl();
        for (int i = 0; i < newSize; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < newSize; j++) {
                if (i < originalSize && j < originalSize) {
                    row.add(matrix.get(i, j));
                } else {
                    row.add(0);
                }
            }
            paddedMatrix.addRow(row);
        }
        return paddedMatrix;
    }

    public static Matrix unpadMatrix(Matrix matrix, int originalRows, int originalCols) {
        List<List<Integer>> resultData = new ArrayList<>();

        for (int i = 0; i < originalRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < originalCols; j++) {
                row.add(matrix.get(i, j));
            }
            resultData.add(row);
        }
        return new MatrixImpl(resultData);
    }

    @Override
    public void print() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(i); j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void printInFile(String outputFilename) {
        try (PrintWriter writer = new PrintWriter(outputFilename)) {
            for (List<Integer> row : data) {
                for (Integer element : row) {
                    writer.print(element + " ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to write to the file: " + e.getMessage());
        }
    }
}
