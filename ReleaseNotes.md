## 1.0.0 ##
  * 08/31/2011
  * IE9 uses SVG for rendering now
  * JavaDoc enhancements
  * GWT Graphics' version can be read from GWT Java code
  * Added Animate.onComplete() that is fired when animation completes
  * Bug fixes

## 0.9.7 ##
  * 06/28/2010
  * Added `VectorObjectContainer.insert(VectorObject vo, int beforeIndex)`
  * Added support for measuring Text's width and height (`getTextWidth()` & `getTextHeight()`)
  * Renamed `VectorObjectContainer.pop()` -> `VectorObjectContainer.bringToFront()`

## 0.9.6 ##
  * 04/20/2010
  * Text, Line, Circle and Ellipse were mispositioned with GWT 2.0

## 0.9.5 ##
  * 04/17/2010
  * Added support for `DoubleClickHandlers`
  * Fixed positioning of Text in IE
  * Moving a rotated element should work now similarly in all browsers

## 0.9.4 ##
  * 02/21/2010
  * Renamed Java package: com.vaadin.contrib.gwtgraphics. -> org.vaadin.gwtgraphics.
  * Fixed Text element positioning in IE

## 0.9.3 ##
  * 01/22/2010
  * Removed an invalid CSS style definition.

## 0.9.2 ##
  * 11/25/2009
  * Added `VectorObjectContainer.getVectorObjectCount()` and `VectorObjectContainer.getVectorObject(int index)` methods
  * Added `Path.getStepCount()` and `Path.getStep(int index)` methods
  * Fixed stroke and fill issues in VML

## 0.9.1 ##
  * 10/22/2009
  * Propagate `onAttach()` and `onDetach()` to the child `VectorObjects` too. This fixes an issue where event handlers don't work when `VectorObjects` is added to its parent before the parent is attached to its parent.
  * Fixed bugs in rotation of elements.
  * Added initial support for curves (both SVG and VML) and arcs (only SVG currently).

## 0.9 ##
  * 10/02/2009
  * First public version