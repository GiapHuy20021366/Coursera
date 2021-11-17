import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> line = null;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // Copy points to avoid changing argument
        Point[] pointCopy = new Point[points.length];

        // Copy and check null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            pointCopy[i] = points[i];
        }

        line = new ArrayList<>(pointCopy.length);

        Arrays.sort(pointCopy);

        // Check equal
        for (int i = 0; i < pointCopy.length - 1; i++) {
            if (pointCopy[i].equals(pointCopy[i + 1])) {
                throw new IllegalArgumentException();
            }
        }

        // Rock version: 4 for
        int length = pointCopy.length;
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                for (int k = j + 1; k < length - 1; k++) {
                    for (int s = k + 1; s < length; s++) {
                        if (pointCopy[i].slopeTo(pointCopy[j])
                                == pointCopy[i].slopeTo(pointCopy[k])
                                && pointCopy[i].slopeTo(pointCopy[j])
                                == pointCopy[i].slopeTo(pointCopy[s])) {
                            line.add(new LineSegment(pointCopy[i], pointCopy[s]));
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return line.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return line.toArray(new LineSegment[0]);
    }
}
