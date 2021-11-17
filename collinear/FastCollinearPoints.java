import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> line = null;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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


        for (int i = 0; i < pointCopy.length - 1; i++) {
            // Sort pointCopy again every start
            Arrays.sort(pointCopy);

            // Sort pointCopy depend slopeOrder
            Arrays.sort(pointCopy, pointCopy[i].slopeOrder());

            List<Point> collinear = new ArrayList<>();

            for (int j = 1; j < pointCopy.length - 2; ) {
                // Clear to make new collinear
                collinear.clear();

                collinear.add(pointCopy[0]);
                collinear.add(pointCopy[j]);

                int k = j + 1;

                while (k < pointCopy.length
                        && pointCopy[0].slopeTo(pointCopy[j])
                        == pointCopy[0].slopeTo(pointCopy[k])) {
                    collinear.add(pointCopy[k]);
                    ++k;
                }

                if (k - j >= 3) {
                    Collections.sort(collinear);
                    if (collinear.get(0).equals(pointCopy[0])) {
                        line.add(new LineSegment(pointCopy[0], pointCopy[k - 1]));
                    }
                    collinear.clear();
                }

                j = k;

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
