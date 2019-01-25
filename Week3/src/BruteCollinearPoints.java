import javax.sound.sampled.Line;
import java.util.Comparator;

public class BruteCollinearPoints {
    private static LineSegment[] lineSegments;
    private static Point[] augmentArray;

    public BruteCollinearPoints(Point[] points) throws IllegalArgumentException {
        int len = points.length;
        ResizeArray resizeArray = new ResizeArray();
        Comparator<Point> pointComparator;

        sort(points, 0, points.length);
        for (int i = 0; i != len - 4; ++i) {
            for (int j = i + 1; j != len - 3; ++j) {
                for (int k = j + 1; k != len - 2; ++k) {
                    for (int l = k + 1; l != len - 1; ++l) {
                        pointComparator = points[i].slopeOrder();
                        if (pointComparator.compare(points[j], points[k]) == 0
                        && pointComparator.compare(points[k], points[l]) == 0) {
                            resizeArray.PushBack(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
        lineSegments = resizeArray.GetContent();
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    private void sort(Point[] points, int start, int end) {
        int mid = (start + end) / 2;
        int i = start, j = mid, k = start;
        if (end - start > 1) {
            sort(points, start, mid);
            sort(points, mid, end);
        }
        // Merge here.
        while (i != mid && j != end) {
            if (points[i].compareTo(points[j]) > 0) {
                augmentArray[k++] = points[i++];
            } else {
                augmentArray[k++] = points[j++];
            }
        }
        while (i != mid) {
            augmentArray[k++] = points[i++];
        }
        while (j != end) {
            augmentArray[k++] = points[j++];
        }
        for (i = start; i != end; ++i) {
            points[i] = augmentArray[i];
        }
    }

    class ResizeArray {
        private LineSegment[] content;
        private int cur = 0;

        ResizeArray() {
            content = new LineSegment[8];
            cur = 0;
        }

        void PushBack(LineSegment item) {
            content[cur++] = item;
            if (cur == content.length) {
                Resize(cur << 1);
            }
        }

        void Resize(int length) {
            LineSegment[] newContent = new LineSegment[length];
            for (int i = 0; i != cur; ++i) {
                newContent[i] = content[i];
                content[i] = null;
            }
            content = newContent;
        }

        LineSegment[] GetContent() {
            Resize(cur);
            return content;
        }
    }
}