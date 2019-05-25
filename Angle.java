/*******************************************************************************
 * Represents some angle in degrees with value 0 inclusive to 360 disclusive.
 * It offers valuable operations on the angle, most notably the the degrees at
 * which a point lies relative to another, the difference in angles and whether
 * an angle is greater/less than another
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
public class Angle implements Comparable<Angle> {

  /** Stores the double value of this Angle element of [0, 360) */
  public final double angle;

  /*****************************************************************************
   *                           CONSTRUCTORS
   ****************************************************************************/
  /**
   * Constructs a new Angle equal to the angle of point p relative to point
   * relativeTo
   *
   * @param p the point we are looking at
   * @param relativeTo the point p's relative angle is to be calculated to.
   */
  public Angle(Point p, Point relativeTo) {
    double x = p.x-relativeTo.x;
    double y = p.y-relativeTo.y;
    this.angle = regulate(getAngle(x, y));
  }

  /**
   * Constructs an angle with teh given degrees
   */
  public Angle(double degrees) {
    this.angle = regulate(degrees);
  }

  /*****************************************************************************
   *                           TRIGONOMETRY
   ****************************************************************************/
  /**
   * Converts this angle's degrees to radians.
   *
   * @return the converted angle in radians
   */
  public double toRadians() {
    return Math.toRadians(this.angle);
  }

  /** The value of PI */
  public static final double PI = 3.14159265359;

  /**
   * @return the sine of the given degrees
   */
  public static double sin(double degrees) {
    return Math.sin(Math.toRadians(degrees));
  }

  /**
   * @return the asin of the given double
   */
  public static double asin(double d) {
    return Math.toDegrees(Math.asin(d));
  }

  /**
   * @return the cos of the given degrees
   */
  public static double cos(double degrees) {
    return Math.cos(Math.toRadians(degrees));
  }

  /**
   * @return the acos of the given double
   */
  public static double acos(double d) {
    return Math.toDegrees(Math.acos(d));
  }

  /**
   * @return the tan of the given degrees
   */
  public static double tan(double degrees) {
    return Math.tan(Math.toRadians(degrees));
  }

  /**
   * @return the atan of the given double
   */
  public static double atan(double d) {
    return Math.toDegrees(Math.atan(d));
  }

  /*****************************************************************************
   *                           ANGLE MATHS
   ****************************************************************************/
  /**
   * Calculates the angle (degrees) of a vector with x and y coordinates.
   *
   * @param x the x coordinate of the vector.
   * @param y the y coordinate of the vector.
   * @return the degree in angles of the vector.
   */
  public static double getAngle(double x, double y) {
    return Math.toDegrees(Math.atan2(y, x));
  }

  /**
   * Regulates the given angle in degrees so that it lies between 0 and 360.
   * e.g. 370 -> 10, -10 -> 350, 720 -> 0
   *
   * @param angle is the given angle to regulate
   * @return the regulated angle
   */
  public static double regulate(double angle) {
    angle %= 360;
    while (angle < 0) {
      angle += 360;
    }
    return angle;
  }

  /**
   * @param a is the angle to compare this angle to
   * @return -1 if this angle is clockwise left of the given angle, +1 otherwise
   */
  @Override
  public int compareTo(Angle a) {
    double sin = sin(a.angle-this.angle);
    if (sin == 0) {
      return 0;
    }
    return sin < 0 ? -1 : +1;
  }

  /**
   * a < b if sin(b-a) > 0
   * @param b is the angle to check whether we are clockwise right of
   * @return true if this angle is clockwise right of the given angle
   */
  public boolean lessThan(Angle b) {
    return lessThan(b.angle);
  }
  /** double parameter version of the above */
  public boolean lessThan(double b) {
    return sin(b-angle) > 0;
  }

  /**
   * a < b if sin(b-a) > 0
   * @param b is the angle to check whether we are clockwise left of
   * @return true if this angle is clockwise left of the given angle
   */
  public boolean greaterThan(Angle b) {
    return greaterThan(b.angle);
  }
  /** double parameter version of the above */
  public boolean greaterThan(double b) {
    return sin(b-angle) < 0;
  }

  /**
   * Checks whether this angle is between the given angles, thus if it is greater
   * than a and less than b.
   * Makes sure that the angle between a and b is acute, else swaps them
   * @param a is the angle that we must check whether we are greater than
   * @param b is the angle that we must check whether we are less than
   * @return true if this angle lies between the given angles
   */
  public boolean isBetween(Angle a, Angle b) {
    Angle c = a.copy();
    Angle d = b.copy();
    if (a.greaterThan(b)) {
      c = b.copy();
      d = a.copy();
    }
    return this.greaterThan(c) && this.lessThan(d);
  }
  /** double parameter version of the above */
  public boolean isBetween(double a, double b) {
    return isBetween(new Angle(a), new Angle(b));
  }

  /**
   * Checks whether the given angle and this angle is colinear
   */
  public boolean colinearWith(Angle a) {
    return colinearWith(a.angle);
  }
  /** double parameter version of the above */
  public boolean colinearWith(double a) {
    return this.angle % 180 == a % 180;
  }

  /**
   * Subtracts the given angle from this angle
   * @param theta is the angle to subtract
   * @return the angle (degrees) if this angle minus the given angle
   */
  public double minus(Angle theta) {
    return regulate(angle-theta.angle);
  }
  /** double parameter version of the above */
  public double minus(double a) {
    return minus(new Angle(a));
  }

  /**
   * Adds the given angle to this angle
   * @param theta is the angle to add
   * @return the angle (degrees) if this angle plus the given angle
   */
  public double plus(Angle theta) {
    return regulate(angle+theta.angle);
  }
  /** double parameter version of the above */
  public double plus(double a) {
    return plus(new Angle(a));
  }

  /**
   * gets the angle of the acute angle between this angle and the given angle a
   * @param a is the angle to get the abs difference of angle with
   * @return the absolute difference between this angle and the given
   */
  public double absDiff(Angle a) {
    if (colinearWith(a)) {
      return 180d;
    }
    if (this.greaterThan(a)) {
      return -this.minus(a);
    }
    return a.minus(this);
  }
  /** double parameter version of the above */
  public double absDiff(double d) {
    return absDiff(new Angle(d));
  }

  /*****************************************************************************
   *                            POINT INTERACTION
   ****************************************************************************/
  /**
   * Rotates the given point clockwise by the given degrees. For instance, if p = (2, 3),
   * then rotate(p, 90) will return new point (3, -2).
   *
   * @param p the point to rotate
   * @param degres the angle to rotate the point by.
   * @return a new rotated point
   */
  public static Point rotate(Point p, double degrees) {
    double cos = cos(degrees);
    double sin = sin(degrees);
    return new Point(
      p.x * cos - p.y * sin,
      p.x * sin + p.y * cos
    );
  }

  /**
   * Reflects the given point over the given axis. For instance, if p = (2, 3),
   * then reflect(p, 90) will return new point (-2, 3) (reflected over y-axis)
   *
   * @param p the point to reflect
   * @param axis the axis (in degrees) to reflect the point.
   * @return the new reflected point
   */
  public static Point reflect(Point p, double axis) {
    axis *= 2;
    double cos = cos(axis);
    double sin = sin(axis);
    return new Point(
      p.x * cos + p.y * sin,
      p.x * sin - p.y * cos
    );
  }

  /*****************************************************************************
   *                            OBJECT INTERACTIONS
   ****************************************************************************/
  /** for println calls */
  @Override
  public String toString() {
    return angle +"";
  }

  /** Retrieves a deep copy of this angle */
  public Angle copy() {
    return new Angle(this.angle);
  }

  /** Checks whether this angle and the given angle have the same degrees */
  public boolean equals(Angle theta) {
    return theta.angle == this.angle;
  }
  /** double parameter version of the above */
  public boolean equals(double theta) {
    return angle == theta;
  }
}