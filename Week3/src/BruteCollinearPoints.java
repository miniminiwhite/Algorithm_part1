import edu.princeton.cs.algs4.Merge;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        try {
            Point[] localPoints = new Point[points.length];
            lineSegments = new ArrayList<>();
            for (int i = 0; i != points.length; ++i) {
                localPoints[i] = points[i];
                if (points[i] == null) {
                    throw new IllegalArgumentException();
                }
            }
            Merge.sort(localPoints);
            for (int i = 1; i < localPoints.length; ++i) {
                if (localPoints[i].compareTo(localPoints[i - 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            for (int i = 0; i < localPoints.length - 3; ++i) {
                for (int j = i + 1; j < localPoints.length - 2; ++j) {
                    for (int k = j + 1; k < localPoints.length - 1; ++k) {
                        for (int l = k + 1; l < localPoints.length; ++l) {
                            double slope1 = localPoints[i].slopeTo(localPoints[j]);
                            double slope2 = localPoints[j].slopeTo(localPoints[k]);
                            double slope3 = localPoints[k].slopeTo(localPoints[l]);
                            if (slope1 == slope2 && slope2 == slope3) {
                                lineSegments.add(new LineSegment(localPoints[i], localPoints[l]));
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }

   public static void main(String[] args) {
        Point[] point = new Point[1];
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(null);
    }
}