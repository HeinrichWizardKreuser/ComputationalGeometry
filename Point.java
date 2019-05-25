/*******************************************************************************
 * This is a coordinate in 2D or 3D space. Offers methods you'd think necessary
 * for classic location data types. Can also act as a vector for some vector
 * functions such as dot and cross product.
 *
 * The 3 methods in the "INTERSECTION" section are not my own work, you can find
 * the methods written in C (originally, I converted them to java) at
 * https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
 *
 * @author Heinrich Kreuser
 *
 * Date: 25 May 2019
 *
 *         MIT License
 *
 *         Copyright (c) [2019] [Heinrich Kreuser]
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 ******************************************************************************/
public class Point implements Comparable<Point> {

	/* The x, y and z coordinates of this point */
	public double x = 0d;
	public double y = 0d;
  public double z = 0d;

	/*****************************************************************************
	 *                           CONSTRUCTORS
	 ****************************************************************************/
	 /* 2d constructor */
	public Point(double x, double y) {
    setTo(x, y);
  }

	/* 3d constructor */
  public Point(double x, double y, double z) {
    setTo(x, y, z);
  }

	/**
	 * Constructs a point from a given string
	 *
	 * @param s the string in the form [x, y] or [x, y, z] where x, y and z are
	 * 				double values describing the coordinates
	 */
	public Point(String s) {
		s.replaceAll("[[]", "");
		s.replaceAll("[]]", "");
		String[] sarr = s.split("[,]");
		int n = sarr.length;
		double[] darr = new double[n];
		for (int i = 0; i < n; i++) {
			darr[i] = Double.parseDouble(sarr[i]);
		}
		if (n == 2) {
      setTo(darr[0], darr[1]);
    } else if (n == 3) {
      setTo(darr[0], darr[1], darr[2]);
		} else {
			throw new IllegalArgumentException("Invalid dimension size " + n + " for "
			 + s + "!!");
		}
	}

	/* Constructor for array */
	public Point(double[] arr) {
    if (arr.length == 2) {
      setTo(arr[0], arr[1]);
    } else if (arr.length == 3) {
      setTo(arr[0], arr[1], arr[2]);
    } else {
      throw new IllegalArgumentException("Invalid array size " + arr.length + "!!");
    }
	}

	/* Setter methods */
	public void setTo(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	public void setTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setTo(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/*****************************************************************************
   *                           XYZ MATH
   ****************************************************************************/
	/* Distance methods*/
	public double dist(Point p) {
		return Math.sqrt( (x-p.x)*(x-p.x) + (y-p.y)*(y-p.y) + (z-p.z)*(z-p.z) );
	}
	public double dist(double x, double y) {
		return dist(new Point(x, y));
	}
	public double dist(double x, double y, double z) {
		return dist(new Point(x, y, z));
	}

	/**
	 * Returns a point with distance d from this point and in direction degrees
	 *
	 * @param degrees the direction of the returned point relative to this point
	 * @param d the distance the returned point must be
	 * @return a point with distance d from this point and in direction degrees
	 */
	public Point directed(double degrees, double d) {
		double x = this.x + d * Math.cos(Math.toRadians(degrees));
		double y = this.y + d * Math.sin(Math.toRadians(degrees));
		return new Point(x, y);
	}

	/* Point arithmatic */
	public Point plus(Point a) {
		return new Point(x + a.x, y + a.y, z + a.z);
	}
	public Point plus(double x, double y) {
		return new Point(this.x + x, this.y + y);
	}

	public Point minus(Point a) {
		return new Point(x - a.x, y - a.y, z - a.z);
	}
	public Point minus(double x, double y) {
		return new Point(this.x - x, this.y - y);
	}

	/*****************************************************************************
   *                           VECTOR MATH
   ****************************************************************************/
  // determinant
	public double cross(Point p) {
		return x * p.y - y * p.x;
	}

	// dot product
  public double dot(Point p) {
    return x * p.x + y * p.y + z * p.z;
  }

	/*****************************************************************************
	 *                           EXTRAS
	 ****************************************************************************/
	@Override
	public String toString() {
		if (z == 0) {
			return "[" + x + ", " + y + "]";
		}
		return "[" + x + ", " + y + ", " + z + "]";
  }

	/**
	 * Converts this point to an array
	 *
	 * @return an array containing this location's x, y and z (if z is nonzero)
	 *				 coordinates in that order
	 */
	public double[] toArray() {
		if (z == 0) {
			return new double[]{x, y};
		}
		return new double[]{x, y, z};
	}

	/* Standard equals methods for equality comparison */
	public boolean equals(Point p) {
		return epsilon(this.x, p.x) && epsilon(this.y, p.y) && epsilon(this.z, p.z);
	}
	public boolean equals(double x, double y) {
		return epsilon(this.x, x) && epsilon(this.y, y);
	}
  public boolean equals(double x, double y, double z) {
		return epsilon(this.x, x) && epsilon(this.y, y) && epsilon(this.z, z);
	}

	/**
	 * Checks whether the given values are within one epsilon difference from
	 * each other. If they are, they are considered to be equal. This is for when
	 * number calculations have a slight error.
	 */
	public static boolean epsilon(double a, double b) {
		return Math.abs(a-b) < EPSILON;
	}
	/*
	 * If two values have < EPSILON diifference from each other, they are
	 * considered to be equal
	 */
	private static final double EPSILON = 1E-6;

 	/**
	 * Retrieves a copy of this point
	 */
	public Point copy() {
		return new Point(this.x, this.y, this.z);
	}

	/**
	 * Override for comparable API. A point is less than another if it's x value
	 * is less. If their x values are equal, then it is smaller if it's y value
	 * is less. Else, they are equal and we return 0.
	 * This idea for how to compare points originates from a divide-and-conquer
	 * approach to delaunay triangulation of a map/collection of points
	 */
	@Override
  public int compareTo(Point p) {
		if (epsilon(this.y, p.y)) {
			if (epsilon(this.x, p.x)) {
				return 0;
			}
			return this.y < p.y ? -1 : +1;
		}
		return this.x < p.x ? -1 : +1;
  }

	/*****************************************************************************
   *                           INTERSECTION
	 * The following are not my own work. You can find out more about the authors
	 * and functions like these on the page I got this from:
	 * https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
   ****************************************************************************/
	/**
	 * Checks whether line segmen a-b and c-d are intersecting
	 * @return true if line segement ab and cd intersects
	 */
	public static boolean intersects(Point a, Point b, Point c, Point d) {
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(a, b, c);
		int o2 = orientation(a, b, d);
		int o3 = orientation(c, d, a);
		int o4 = orientation(c, d, b);
		// General case
		if (o1 != o2 && o3 != o4) {
			return true;
		}
		// Special Cases
		// a, b and c are colinear and c lies on segment ab
		if (o1 == 0 && onSegment(a, c, b)) {
			return true;
		}
		// a, b and d are colinear and d lies on segment ab
		if (o2 == 0 && onSegment(a, d, b)) {
			return true;
		}
		// c, d and a are colinear and a lies on segment cd
		if (o3 == 0 && onSegment(c, a, d)) {
			return true;
		}
		// c, d and b are colinear and b lies on segment cd
		if (o4 == 0 && onSegment(c, b, d)) {
			return true;
		}
		return false; // Doesn't fall in any of the above cases
	}
	/** Point[] / line parameter version of the above */
	public static boolean intersects(Point[] ab, Point[] cd) {
		return intersects(ab[0], ab[1], cd[0], cd[1]);
	}

	/**
	 * To find orientation of ordered triplet (p, q, r).
	 * The function returns following values
	 * 0 --> p, q and r are colinear
	 * 1 --> Clockwise
	 * 2 --> Counterclockwise
	 * See https://www.geeksforgeeks.org/orientation-3-ordered-points/
	 * for details of below formula.
	 */
	private static int orientation(Point p, Point q, Point r) {
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
		if (val == 0) {
			return 0; // colinear
		}
		return val > 0 ? 1 : 2; // clock or counterclock wise
	}

	/**
	 * Given three colinear points p, q, r, the function checks if
	 * point q lies on line segment 'pr'
	 */
	private static boolean onSegment(Point p, Point q, Point r) {
		return
			q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
			q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
	}
}
