//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private final int block_dimension;
    private final int upper_bound;
    private final int[] blocks;
    private int empty_col, empty_row;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        int ept_count = 0;
        block_dimension = blocks.length;
        upper_bound = block_dimension * block_dimension;

        this.blocks = new int[upper_bound];
        for (int i = 0; i != block_dimension; ++i) {
            for (int j = 0; j != block_dimension; ++j) {
                if (blocks[i][j] > upper_bound || blocks[i][j] < 0) {
                    throw new IllegalArgumentException();
                }
                this.blocks[i * block_dimension + j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    if (ept_count != 0) {
                        throw new IllegalArgumentException("More than one empty node.");
                    }
                    ept_count += 1;
                    empty_col = j;
                    empty_row = i;
                }
            }
        }
    }

    private Board(Board ori) {
        if (ori == null) {
            throw new IllegalArgumentException();
        }
        blocks = ori.elements();
        block_dimension = ori.dimension();
        upper_bound = block_dimension * block_dimension;
        int[] ept_idx = ori.empty_index();
        empty_row = ept_idx[0];
        empty_col = ept_idx[1];
    }

    public int dimension() {
        return block_dimension;
    }

    private int[] elements() {
        return blocks.clone();
    }

    private int[] empty_index() {
        return new int[]{empty_row, empty_col};
    }

    public int hamming() {
        int res = 0;
        for (int i = 0; i < upper_bound; ++i) {
            if (blocks[i] != i + 1 && blocks[i] != 0) {
                res += 1;
            }
        }
        return res;
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < upper_bound; ++i) {
            if (blocks[i] != 0 && blocks[i] != i + 1) {
                int cur_col, cur_row, correct_col, correct_row;
                cur_col = i % block_dimension;
                cur_row = i / block_dimension;
                correct_col = (blocks[i] - 1) % block_dimension;
                correct_row = (blocks[i] - 1) / block_dimension;
                res += Math.abs(cur_col - correct_col) + Math.abs(cur_row - correct_row);
            }
        }
        return res;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int tmp = (empty_row + 1) % block_dimension * block_dimension;
        swap(tmp,tmp + 1);
        Board b = new Board(this);
        swap(tmp, tmp + 1);
        return b;
    }

    public boolean equals(Object y) {
        if (y == null || !y.getClass().isInstance(this)) {
            return false;
        }
        if (y == this) {
            return true;
        }
        int[] y_elements = ((Board) y).elements();
        return Arrays.equals(y_elements, elements());
    }

    private void swap(int i, int j) {
        int temp;
        temp = blocks[i];
        blocks[i] = blocks[j];
        blocks[j] = temp;
        if (blocks[i] == 0) {
            empty_col = i % block_dimension;
            empty_row = i / block_dimension;
        }
        if (blocks[j] == 0) {
            empty_col = j % block_dimension;
            empty_row = j / block_dimension;
        }
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbor_list = new ArrayList<>();
        int[] movement = {0, 1, 0, -1};
        for (int i = 0; i < 4; ++i) {
            int ept_col = empty_col, ept_row = empty_row;
            int tmp_col = movement[i] + empty_col, tmp_row = movement[(i + 1) % 4] + empty_row;
            if (tmp_col >= 0 && tmp_col < block_dimension && tmp_row >= 0 && tmp_row < block_dimension) {
                swap(tmp_row * block_dimension + tmp_col, ept_row * block_dimension + ept_col);
                neighbor_list.add(new Board(this));
                swap(tmp_row * block_dimension + tmp_col, ept_row * block_dimension + ept_col);
            }
        }
        return neighbor_list;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(block_dimension);
        stringBuilder.append("\n");
        for (int i = 0; i < block_dimension; ++i) {
            for (int j = 0; j < block_dimension; ++j) {
                stringBuilder.append(blocks[block_dimension * i + j]);
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
        Random random = new Random();
//        int N = random.nextInt(4) + 2;
        int N = 3;
        int[][] board = new int[N][N];
        int upper_bound = N * N;
        while (upper_bound > 0) {
            int i = random.nextInt(N);
            int j = random.nextInt(N);
            if (board[i][j] == 0) {
                board[i][j] = upper_bound--;
            }
        }
        board[random.nextInt(N)][random.nextInt(N)] = 0;
        Board b = new Board(board);
        System.out.println(b);
        System.out.println(b.manhattan());
    }
}