import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Node cur;
    private class Node implements Comparable<Node> {

        private final Board board;
        private final int moves;
        private final int priority;
        private final Node previous;
        public Node(Board ori, Node previous) {
            board = ori;
            this.previous = previous;
            if (this.previous == null) {
                moves = 0;
            } else {
                moves = this.previous.getMoves() + 1;
            }
            priority = ori.manhattan() + moves;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return priority;
        }

        public Board getBoard() {
            return board;
        }

        public Node getPrevious() {
            return previous;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(getPriority(), n.getPriority());
        }

    }
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        cur = new Node(initial, null);
        MinPQ<Node> main = new MinPQ<>();
        for (Board i: cur.getBoard().neighbors()) {
            main.insert(new Node(i, cur));
        }

        Node cur_twin = new Node(initial.twin(), null);
        MinPQ<Node> vice = new MinPQ<>();
        for (Board i: cur_twin.getBoard().neighbors()) {
            vice.insert(new Node(i, cur_twin));
        }

        while (!cur.getBoard().isGoal() && !cur_twin.getBoard().isGoal()) {
            cur = main.delMin();
            for (Board i: cur.getBoard().neighbors()) {
                if (!i.equals(cur.getPrevious().getBoard())) {
                    main.insert(new Node(i, cur));
                }
            }
            cur_twin = vice.delMin();
            for (Board i: cur_twin.getBoard().neighbors()) {
                if (!i.equals(cur_twin.getPrevious().getBoard())) {
                    vice.insert(new Node(i, cur_twin));
                }
            }
        }
    }


    public boolean isSolvable() {
        return cur.getBoard().isGoal();
    }

    public int moves() {
        if (isSolvable()) {
            return cur.getMoves();
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> stack = new Stack<>();
        Node tmp = cur;
        while (tmp != null) {
            stack.push(tmp.getBoard());
            tmp = tmp.getPrevious();
        }
        return stack;
    }
}
