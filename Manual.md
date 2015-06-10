

# Introduction #


GWT Graphics is a cross-browser vector graphics library for [Google Web Toolkit](http://code.google.com/webtoolkit/). It's open-source and licensed under the [Apache License 2.0](http://dev.vaadin.com/svn/contrib/gwtgraphics/LICENSE.txt).


Under the hood, the library uses to XML-based languages: [SVG](http://www.w3.org/Graphics/SVG/) and [VML](http://www.w3.org/TR/NOTE-VML). VML implementation is for Internet Explorer. Other browsers use SVG implementation. GWT Graphics uses GWT's [Deferred Binding](http://code.google.com/intl/fi/webtoolkit/doc/1.6/FAQ_Client.html#Deferred_Binding) feature to provide VML implementation for IE and SVG implementation for other browsers.

The library has been tested to work with the following browsers:
  * Internet Explorer 6 and newer
  * Firefox 3.0 and newer
  * Safari 3.2 and newer
  * Opera 9.6 and newer
  * Google Chrome

# Setting up the library in your project #


Download [gwt-graphics.jar](http://vaadin.com/directory#addon/7) and add it to the class path of your GWT project. In addition, you have to add the following line to the GWT module file:
```
<inherits name='org.vaadin.gwtgraphics.GWTGraphics' />
```
After these two steps, you are ready to use GWT Graphics in your GWT project!


# Drawing canvas #


When user wants to draw with the library, a `DrawingArea` object need to be instantiated and added to the application. The actual drawing is done by adding drawing objects to this drawing canvas. The code below creates an instance of `DrawingArea` and adds it to the application. All examples given in this manual use this canvas.


```
DrawingArea canvas = new DrawingArea(400, 400);
RootPanel.get().add(canvas);
```


| `DrawingArea(int width, int height)` | Creates a `DrawingArea`. |
|:-------------------------------------|:-------------------------|
| `getWidth(): int / setWidth(int width): void` | The width of `DrawingArea` is defined in pixels. |
| `getHeight(): int / setHeight(int height): void` | The height of `DrawingArea` is defined in pixels. |
| `add(VectorObject vo): VectorObject` | Add a `VectorObject` to the `DrawingArea`. |
| `insert(VectorObject vo, int beforeIndex): VectorObject` | Insert the given `VectorObject` before the specified index. |
| `remove(VectorObject vo): VectorObject` | Remove a `VectorObject` from the `DrawingArea`. |
| `bringToFront(VectorObject vo): VectorObject` | Moves the given `VectorObject` on top of the other elements in the `DrawingArea`. |
| `clear(): void`                      | Remove all elements from `DrawingArea`. |
| `getVectorObjectCount(): int`        | Returns the number of `VectorObjects` in this `DrawingArea`. |
| `getVectorObject(int index): VectorObject` | Returns the `VectorObject` element at the specified position |


For instance, the following code adds three circles to the above created `DrawingArea`. When a circle is clicked, the circle is moved on top of the other circles (see [demo](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/#circles)).
```
ClickHandler handler = new ClickHandler() {
  public void onClick(ClickEvent event) {
    canvas.bringToFront((Circle) event.getSource());
  }
};
int xCoords[] = { 200, 225, 175 };
int yCoords[] = { 150, 200, 200 };
String fillColors[] = { "red", "blue", "green" };
for (int i = 0; i < xCoords.length; i++) {
  Circle circle = new Circle(xCoords[i], yCoords[i], 50);
  circle.setFillColor(fillColors[i]);
  circle.setFillOpacity(0.5);
  circle.addClickHandler(handler);
  canvas.add(circle);
}
```


# Drawing objects #


Classes that can be used to accomplish drawing (ie. can be added to `DrawingArea`) are subclasses of `VectorObject`. These are: `Group`, `Image`, `Line`, `Circle`, `Ellipse`, `Path`, `Rectangle` and `Text`.

## Group ##

The `Group` can contain one or more `VectorObjects` so it can be used to group elements as layers and groups.

| `Group()` | Creates an empty `Group`. |
|:----------|:--------------------------|
| `add(VectorObject vo): VectorObject` | Add a `VectorObject` to this `Group`. |
| `insert(VectorObject vo, int beforeIndex): VectorObject` | Insert the given `VectorObject` before the specified index. |
| `remove(VectorObject vo): VectorObject` | Remove a `VectorObject` from this Group. |
| `bringToFront(VectorObject vo): VectorObject` | Moves the given `VectorObject` on top of the other elements in this `Group`. |
| `clear(): void` | Remove all elements from this `Group`. |
| `getVectorObjectCount(): int` | Returns the number of `VectorObjects` in this `Group`. |
| `getVectorObject(int index): VectorObject` | Returns the `VectorObject` element at the specified position |


## Image ##

The `Image` class can be used to embed a raster image into `DrawingArea`.

| `Image(int x, int y, int width, int height, String href)` | Creates an `Image`. |
|:----------------------------------------------------------|:--------------------|
| `getX(): int / setX(int x): void`                         | The x-coordinate of the top-left corner of the `Image` is specified in pixels. |
| `getY(): int / setY(int y): void`                         | The y-coordinate of the top-left corner of the `Image` is specified in pixels. |
| `getHref(): String / setHref(String href): void`          | The URI of the raster image to be displayed is specified as a string. |
| `getWidth(): int / setWidth(int width): void`             | The width of the `Image` element is specified in pixels. |
| `getHeight(): int / setHeight(int height): void`          | The height of the `Image` element is specified in pixels. |
| `getRotation(): int / setRotation(int degree): void`      | The rotation of the `Image` is specified in degrees. |

## Line ##

The `Line` class represents a straight line.

| `Line(int x1, int y1, int x2, int y2)` | Creates a `Line`. |
|:---------------------------------------|:------------------|
| `getX1(): int / setX1(int x1): void`   | The x-coordinate position of the starting point of the `Line` is specified in pixels. |
| `getY1(): int / setY1(int y1): void`   | The y-coordinate position of the starting point of the `Line` is specified in pixels. |
| `getX2(): int / setX2(int x2): void`   | The x-coordinate position of the ending point of the `Line` is specified in pixels. |
| `getY2(): int / setY2(int y2): void`   | The y-coordinate position of the ending point of the `Line` is specified in pixels. |
| `getRotation(): int / setRotation(int degree): void` | The rotation of the `Line` is specified in degrees. |

## Circle ##

The `Circle` class represents a circle.

| `Circle(int x, int y, int radius)` | Creates a new `Circle`. |
|:-----------------------------------|:------------------------|
| `getX(): int / setX(int x): void`  | The x-coordinate of the center of the `Circle` is specified in pixels. |
| `getY(): int / setY(int y): void`  | The y-coordinate of the center of the `Circle` is specified in pixels. |
| `getRadius(): int / setRadius(int radius): void` | The radius of the `Circle` is defined in pixels. |
| `getRotation(): int / setRotation(int degree): void` | The rotation of the `Circle` is specified in degrees. |

## Ellipse ##

The `Ellipse` class represents an ellipse.

| `Ellipse(int x, int y, int radiusX, int radiusY)` | Creates a new `Ellipse`. |
|:--------------------------------------------------|:-------------------------|
| `getX(): int / setX(int x): void`                 | The x-coordinate of the center of the `Ellipse` is specified in pixels. |
| `getY(): int / setY(int y): void`                 | The y-coordinate of the center of the `Ellipse` is specified in pixels. |
| `getRadiusX(): int / setRadiusX(int radiusX): void` | The x-axis radius of the `Ellipse` is defined in pixels. |
| `getRadiusY(): int / setRadiusY(int radiusY): void` | The y-axis radius of the `Ellipse` is defined in pixels. |
| `getRotation(): int / setRotation(int degree): void` | The rotation of the `Ellipse` is specified in degrees. |

## Path ##

The `Path` represents a path. Paths are sets of pen movement commands, which define the geometry of the outline of a shape.

| `Path(int x, int y)` | Creates a new `Path`. |
|:---------------------|:----------------------|
| `setStep(int index, PathStep step): void` | Set a `PathStep` at the specified position. |
| `removeStep(int index): void` | Remove a `PathStep` at the specified position. |
| `getStepCount(): int` | Returns the number of `PathSteps` in this `Path`. |
| `getStep(int index): PathStep` | Returns the `PathStep` element at the specified position. |
| `moveTo(int x, int y): void / moveRelativelyTo(int x, int y): void` | Start a new sub-path at the given absolute or relative point. |
| `lineTo(int x, int y): void / lineRelativelyTo(int x, int y): void` | Draw a line from the current point to the given absolute or relative point. |
| `close(): void`      | Close the path.       |


For instance, the code below creates a path drawing a 100 x 100 pixels rectangle at the position (50, 50):
```
Path path = new Path(50, 50);
path.lineRelativelyTo(100, 0);
path.lineRelativelyTo(0, 100);
path.lineRelativelyTo(-100, 0);
path.close();
```
This rectangle is modified as a triangle with the following code:
```
path.setStep(2, new LineTo(true, -50, 100));
path.removeStep(3);
```

See a working [demo](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/#path) of this example.

## Rectangle ##

The `Rectangle` class represents a rectangle.

| `Rectangle(int x, int y, int width, int height)` | Creates a new `Rectangle`. |
|:-------------------------------------------------|:---------------------------|
| `getX(): int / setX(int x): void`                | The x-coordinate of the top-left corner of the `Rectangle` is specified in pixels. |
| `getY(): int / setY(int y): void`                | The y-coordinate of the top-left corner of the `Rectangle` is specified in pixels. |
| `getWidth(): int / setWidth(int width): void`    | The width of the `Rectangle` is defined in pixels. |
| `getHeight(): int / setHeight(int height): void` | The height of the `Rectangle` is defined in pixels. |
| `getRoundedCorners(): / int setRoundedCorners(int radius): void` | The radius of the rounded corners of the `Rectangle` is defined in pixels. |
| `getRotation(): int / setRotation(int degree): void` | The rotation of the `Rectangle` is specified in degrees. |

## Text ##

The `Text` class represents a block containing text.

| `Text(int x, int y, String text)` | Creates a new Text. |
|:----------------------------------|:--------------------|
| `getX(): int / setX(int x): void` | The x-coordinate of the top-left corner of the Text is specified in pixels. |
| `getY(): int / setY(int y): void` | The y-coordinate of the top-left corner of the Text is specified in pixels. |
| `getText(): String / setText(String text): void` | The text to be rendered is specified as a string. |
| `getFontFamily: String / setFontFamily(String family): void` | The font family specifies which font family is to be used to render the text. |
| `getFontSize(): int / setFontSize(int size): void` | The font size is specified in pixels. |
| `getTextWidth(): int`             | Returns the width of the rendered text in pixels. |
| `getTextHeight(): int`            | Returns the height of the rendered text in pixels. |
| `getRotation(): int / setRotation(int degree): void` | The rotation of the Text is specified in degrees. |



# Stroke and fill #


The `Line`, `Circle`, `Ellipse`, `Path`, `Rectangle` and `Text` classes support stroking. These support stroke with a solid color. Also, the opacity and width of the stroke can be specified.

| `getStrokeColor(): String / setStrokeColor(int width): void` | The stroke color is defined in CSS color formats. |
|:-------------------------------------------------------------|:--------------------------------------------------|
| `getStrokeWidth(): int / setStrokeWidth(int width): void`    | The stroke width is defined in pixels.            |
| `getStrokeOpacity(): double / setStrokeOpacity(double opacity): void` | The stroke opacity is a double value between 0 and 1. |



The `Circle`, `Ellipse`, `Path`, `Rectangle` and `Text` support filling. These drawing objects support fill with a solid color. Also, the opacity of the fill can be defined.

| `getFillColor(): String / setFillColor(String color): void` | The fill color is defined in CSS color formats. |
|:------------------------------------------------------------|:------------------------------------------------|
| `getFillOpacity(): double / setFillOpacity(double opacity): void` | The fill opacity is a double value between 0 and 1. |

## Color notations ##
The `setFillColor(String color)` and `setStrokeColor(String color)` methods support the following color notations:

| **Notation** | **Example** |
|:-------------|:------------|
| `colorname`  | `red`       |
| `#rrggbb`    | `#ff0000`   |
| `#rgb`       | `#f00`      |
| `rgb(x, x, x)` | `rgb(255, 0, 0)` |
| `rgb(x%, x%, x%)` | `rgb(100%, 0%, 0%)` |


GWT Graphics supports the following 16 keyword color names: `aqua`, `black`, `blue`, `fuchsia`, `gray`, `green`, `lime`, ` maroon`, `navy`, `olive`, `purple`, `red`, `silver`, `teal`, `white` and `yellow`. SVG-enabled browsers would support a wider range of color names but VML limits supported keywords to these 16 values.


# Mouse events #


The `DrawingArea` and `VectorObject` classes implement GWT's [HasClickHandlers](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/HasClickHandlers.html), [HasDoubleClickHandlers](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/HasDoubleClickHandlers.html) and [HasAllMouseHandlers](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/HasAllMouseHandlers.html) interfaces. This means that it's possible to add the following event handlers for drawing canvas and all the drawing objects: [ClickHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/ClickHandler.html), [DoubleClickHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/DoubleClickHandler.html), [MouseDownHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseDownHandler.html), [MouseUpHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseUpHandler.html), [MouseOutHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseOutHandler.html), [MouseOverHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseOverHandler.html), [MouseMoveHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseMoveHandler.html) and [MouseWheelHandler](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/event/dom/client/MouseWheelHandler.html).

For example, the following code creates a rectangle, which fill color changes every time the rectangle is clicked (see [demo](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/#mouseclick)):
```
Rectangle rect = new Rectangle(10, 10, 100, 50);
canvas.add(rect);
rect.setFillColor("blue");
rect.addClickHandler(new ClickHandler() {
  public void onClick(ClickEvent event) {
    Rectangle rect = (Rectangle) event.getSource();
    if (rect.getFillColor().equals("blue")) {
      rect.setFillColor("red";);
    } else {
      rect.setFillColor("blue");
    }
  }
});
```


The following example shows how to create a circle, which follows mouse withing the `DrawingArea` (see [demo](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/#mousemove)):
```
final Circle circle = new Circle(0, 0, 10);
canvas.add(circle);
canvas.addMouseMoveHandler(new MouseMoveHandler() {
  public void onMouseMove(MouseMoveEvent event) {
    circle.setX(event.getX());
    circle.setY(event.getY());
  }
});
```


# Animation #


The `Animate` class can be used to animate numeric properties of the drawing objects. For example, the following code animates circle's radius from value 0 to 100 in 3000ms (see [demo](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/#animation)):
```
Circle circle = new Circle(200, 200, 0);
canvas.add(circle);
new Animate(circle, "radius", 0, 100, 3000).start();
```


Drawing objects have the following animatable properties:
| `Image` | `x`, `y`, `width`, `height` and `rotation` |
|:--------|:-------------------------------------------|
| `Line`  | `x1`, `y1`, `x2`, `y2`, `strokeopacity`, `strokewidth` and `rotation` |
| `Circle` | `x`, `y`, `fillopacity`, `strokeopacity`, `strokewidth`, `rotation` and `radius` |
| `Ellipse` | `x`, `y`, `fillopacity`, `strokeopacity`, `strokewidth`, `rotation`, `radiusx` and `radiusy` |
| `Path`  | `x`, `y`, `fillopacity`, `strokeopacity`, `strokewidth` and `rotation` |
| `Rectangle` | `x`, `y`, `fillopacity`, `strokeopacity`, `strokewidth`, `rotation`, `width`, `height` and `roundedcorners` |
| `Text`  | `x`, `y`, `fillopacity`, `strokeopacity`, `strokewidth`, `rotation` and `fontsize` |


Another animation possibility is to utilize the [Animation](http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/animation/client/Animation.html) class of GWT.