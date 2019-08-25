import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private final SET<Point2D> point2DSET;

    public                   PointSET() {                              // construct an empty set of points
        point2DSET = new SET<>();
    }

    public           boolean isEmpty() {                      // is the set empty?
        return point2DSET.isEmpty();
    }

    public               int size() {                         // number of points in the set
        return point2DSET.size();
    }

    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        point2DSET.add(p);
    }

    public           boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        return point2DSET.contains(p);
    }

    public              void draw() {                         // draw all points to standard draw
        return;
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        SET<Point2D> ret = new SET<>();
        for (Point2D i: point2DSET) {
            if (rect.contains(i)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException("Null parameter.");
        }

        double dist = Integer.MAX_VALUE;
        double temp;
        Point2D ret = null;
        for (Point2D i: point2DSET) {
            temp = p.distanceSquaredTo(i);
            if (ret == null || temp < dist) {
                dist = temp;
                ret = i;
            }
        }
        return ret;
    }

    public static void main(String[] args) {                  // unit testing of the methods (optional)

    }
}