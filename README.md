# ComputationalGeometry
This is a collection of all libraries I use for Computational Geometry.

## Point.java
This class is a representation of a 2d (although it somewhat supports 3d) point in space. It is the basis of Computational Geometry and Graph Theory.

## Angle.java
This class is the representation of an Angle (in degrees). It is very useful in Computational Geometry since you can find the angle between 2 points from another point.

## CoreGeom.java
This library uses both Point and Angle and contains a few useful functions that might often be used in Computational Geometry and Graph Theory.
As a dependency, `Core.java` is included. `Core.java` Is originally part of a different repo found at https://github.com/HeinrichWizardKreuser/KreuserCore

## Delaunay.java
This library is a java implementation of Delaunay triangulation and the original explenation of the algorithm can be found at http://www.geom.uiuc.edu/~samuelp/del_project.html. Note that this is simply my java implementation of it, but the algorithm's design is not my original work and is credited to the mentioned link.
