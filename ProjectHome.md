# GWT Graphics #

The goal of the GWT Graphics library is to provide a consistent cross-browser vector graphics library for [Google Web Toolkit](http://code.google.com/webtoolkit/). GWT Graphics uses SVG and VML for creating graphics.

## Supported browsers ##

The library has been tested to work with the following browsers:
  * Internet Explorer 6 and newer (6,7 and 8 use VML rendering, 9 uses SVG)
  * Firefox 3.0 and newer
  * Safari 3.2 and newer
  * Opera 9.6 and newer
  * Google Chrome

## How to use it? ##

Download [gwt-graphics.jar](http://vaadin.com/directory#addon/7) (see [release notes](ReleaseNotes.md) too) and add it to the classpath of your project. In addition, you have to add the following line to the Module XML file of the project:

```
<inherits name='org.vaadin.gwtgraphics.GWTGraphics'/>
```

After the above two steps, you can use GWT Graphics in your project. To draw something with the library, you have to add an instance of `DrawingArea` to your application:
```
DrawingArea canvas = new DrawingArea(400, 400);
panel.add(canvas);
```

After that you can start drawing. For example, the following code draws a red circle :
```
Circle circle = new Circle(100, 100, 50);
circle.setFillColor("red");
canvas.add(circle);
```

## Discussion ##
If you have any questions or problems with the library, feel free to use the [discussion group](http://groups.google.com/group/gwt-graphics) of the project.

## Demos & examples ##
  * [Examples](http://hene.virtuallypreinstalled.com/gwt-graphics-examples/)
    * Contains simple examples.
    * [Source codes](http://dev.vaadin.com/svn/incubator/gwt-graphics-examples/)
  * [Rocket](http://hene.virtuallypreinstalled.com/rocket/)
    * This is a GWT port of a [pygame](http://www.pygame.org/) application, which was created by [Henri](http://www.vaadin.com/hezamu).
  * [Interactive test application](http://hene.virtuallypreinstalled.com/gwt-graphics-test/)
    * This GWT application with unusable UI is used to test the library with different browsers during the development process.
    * [Source codes](http://code.google.com/p/gwt-graphics/source/browse/#hg/src/org/vaadin/gwtgraphics/testapp)
  * [Chart components for Vaadin](http://hene.virtuallypreinstalled.com/Charts)
    * I created a couple of chart components for [Vaadin](http://vaadin.com/) to test if i can use the library with Vaadin.
    * [Source codes](http://dev.vaadin.com/svn/incubator/ChartsWithGWTGraphics/)
  * Tomi's [ProcessingSVG](http://vaadin.com/directory/#addon/55)
    * [Demo #1](http://tomivirtanen.virtuallypreinstalled.com/processingsvg)
    * [Demo #2](http://tomivirtanen.virtuallypreinstalled.com/physics/physicssvg)
  * Fredu's [Vaadin Screensaver](http://vaadin.com/directory/#addon/31)
    * [Source codes](http://dev.vaadin.com/browser/contrib/Screensaver)