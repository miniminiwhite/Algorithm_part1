import edu.princeton.cs.algs4.Merge;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        lineSegments = new ArrayList<>();
        try {
            Point[] localPoints = new Point[points.length];
            Point[] augmentPoints = new Point[points.length];
            System.arraycopy(points, 0, localPoints, 0, points.length);
            Merge.sort(localPoints);
            for (int i = 0; i < localPoints.length; ++i) {
                if (localPoints[i] == null || i > 0 && localPoints[i].compareTo(localPoints[i - 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            for (int i = 0; i < localPoints.length - 3; ++i) {
                int j = 0, k = i + 1;
                int cnt = 2;
                double slope1, slope2, slope3;
                System.arraycopy(localPoints, 0, augmentPoints, 0, localPoints.length);
                Arrays.sort(augmentPoints, 0, i, augmentPoints[i].slopeOrder());
                Arrays.sort(augmentPoints, i + 1, localPoints.length, augmentPoints[i].slopeOrder());
                slope1 = augmentPoints[i].slopeTo(augmentPoints[k]);
                while (++k != localPoints.length) {
                    slope2 = augmentPoints[i].slopeTo(augmentPoints[k]);
                    if (slope1 == slope2){
                        cnt += 1;
                    } else {
                        if (cnt > 3) {
                            while ((slope3 = augmentPoints[i].slopeTo(augmentPoints[j])) < slope1 && j < i) {
                                ++j;
                            }
                            if (slope3 != slope1) {
                                lineSegments.add(new LineSegment(augmentPoints[i], augmentPoints[k - 1]));
                            }
                        }
                        cnt = 2;
                    }
                    slope1 = slope2;
                }
                if (cnt > 3) {
                    while ((slope3 = augmentPoints[i].slopeTo(augmentPoints[j])) < slope1 && j < i) {
                        ++j;
                    }
                    if (slope3 != slope1) {
                        lineSegments.add(new LineSegment(augmentPoints[i], augmentPoints[k - 1]));
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
        Point[] points = new Point[1];
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
    }
}