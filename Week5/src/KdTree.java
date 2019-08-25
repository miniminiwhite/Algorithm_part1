import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static final RectHV CONTAINER = new RectHV(
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
    );

    private class Node {
        private final double x;
        private final double y;
        private boolean isVertical;
        private int size;
        private Node leftChild, rightChild;
        private RectHV leftRect, rightRect;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            size = 1;
        }

        public Node(double x, double y, boolean isVertical) {
            this(x, y);
            this.isVertical = isVertical;
        }

        public int compareTo(Node d) {
            if (d.x == x && d.y == y) {
                return 0;
            }
            if (this.isVertical) {
                if (Double.compare(d.x, x) < 0) {
                    return -1;
                }
                return 1;
            }
            if (Double.compare(d.y, y) < 0) {
                return -1;
            }
            return 1;
        }

        public void setRects(RectHV rect) {
            if (isVertical) {
                leftRect = new RectHV(rect.xmin(), rect.ymin(), x, rect.ymax());
                rightRect = new RectHV(x, rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), y);
                rightRect = new RectHV(rect.xmin(), y, rect.xmax(), rect.ymax());
            }
        }
    }

    private Node root;

    public                   KdTree()  {                                // construct an empty set of points
        root = null;
    }

    public           boolean isEmpty()  {                     // is the set empty?
        return size(root) == 0;
    }

    public               int size() {                         // number of points in the set
        return size(root);
    }

    private              int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        root = insert(
                new Node(p.x(), p.y(), true),
                root,
                true,
                CONTAINER
        );
    }

    private             Node insert(Node ins, Node des, boolean isVertical, RectHV rect) {
        if (des == null) {
            ins.isVertical = isVertical;
            ins.setRects(rect);
            return ins;
        }
        int cmp = des.compareTo(ins);
        if (cmp > 0) {
            des.rightChild = insert(ins, des.rightChild, !isVertical, des.rightRect);
        } else if (cmp < 0) {
            des.leftChild = insert(ins, des.leftChild, !isVertical, des.leftRect);
        }
        des.size = 1 + size(des.leftChild) + size(des.rightChild);
        return des;
    }

    public           boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        return contains(root, new Node(p.x(), p.y()));
    }

    private          boolean contains(Node cur, Node d) {
        if (cur == null) {
            return false;
        }
        int cmp = cur.compareTo(d);
        if (cmp < 0) {
            return contains(cur.leftChild, d);
        } else if (cmp > 0) {
            return contains(cur.rightChild, d);
        }
        return true;
    }

    public void draw() {
        return;
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        Queue<Point2D> res = new Queue<>();
        Queue<Node> searchQueue = new Queue<>();
        Node curNode;
        Point2D curPoint;

        searchQueue.enqueue(root);
        while (!searchQueue.isEmpty()) {
            curNode = searchQueue.dequeue();
            if (curNode == null) {
                continue;
            }
            curPoint = new Point2D(curNode.x, curNode.y);
            if (rect.contains(curPoint)) {
                res.enqueue(curPoint);
            }
            if (curNode.leftRect.intersects(rect)) {
                searchQueue.enqueue(curNode.leftChild);
            }
            if (curNode.rightRect.intersects(rect)) {
                searchQueue.enqueue(curNode.rightChild);
            }
        }
        return res;
    }

    public           Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException("Null parameter");
        }

        if (isEmpty()) {
            return null;
        }

        return nearest(p, root, new Point2D(root.x, root.y), CONTAINER);
    }

    private          Point2D nearest(Point2D query, Node cur, Point2D best, RectHV rect) {
        if (cur == null) {
            return best;
        }

        Point2D curPoint = new Point2D(cur.x, cur.y);
        double distCurSq, distNearestSq, distRect;

        distCurSq = query.distanceSquaredTo(curPoint);
        distNearestSq = query.distanceSquaredTo(best);
        distRect = rect.distanceSquaredTo(query);

        if (distCurSq < distNearestSq) {
            best = curPoint;
            distNearestSq = distCurSq;
        }

        if (distNearestSq > distRect * distRect) {
            if (cur.compareTo(new Node(query.x(), query.y())) > 0) {
                best = nearest(query, cur.rightChild, best, cur.rightRect);
                best = nearest(query, cur.leftChild, best, cur.leftRect);
            } else {
                best = nearest(query, cur.leftChild, best, cur.leftRect);
                best = nearest(query, cur.rightChild, best, cur.rightRect);
            }
        }

        return best;
    }

    public static void main(String[] args) {                  // unit testing of the methods (optional)
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        System.out.println(kdTree.nearest(new Point2D(1.0, 0.816)));
    }
}