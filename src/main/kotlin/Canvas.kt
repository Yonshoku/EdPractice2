import java.awt.*
import javax.swing.JPanel

class Canvas(private val mWidth: Int, private val mHeight: Int): JPanel() {
    private val max = 3 * Math.PI / 2
    private val min = -max

    private val updateRate = 100
    private val a = 0.5
    private var curPointIndex: Int = 0

    private var intPointList: MutableList<IntPoint> = mutableListOf ()
    private var pointList: MutableList<Point> = mutableListOf ()

    var canvasColor: Color = Color(220, 220, 220)
    var contourColor: Color = Color(0, 0, 0)
    var shapeColor: Color = Color(0, 0, 0)

    private val thread = Thread {
        while (true) {
            if (curPointIndex >= pointList.size - 1)
                curPointIndex = 0

            repaint()
            curPointIndex += 2

            Thread.sleep((1000 / updateRate).toLong())
        }
    }

    init {
        preferredSize = Dimension(mWidth, mHeight)
        minimumSize = Dimension(mWidth, mHeight)
        maximumSize = Dimension(mWidth, mHeight)

        createPointLists()
        thread.start()
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = canvasColor

        (g as Graphics2D).stroke = BasicStroke(2f)
        initCoordinatePlane(g)

        (g as Graphics2D).stroke = BasicStroke(1f)
        createPointLists()

        g.color = contourColor
        drawGraph(g)

        g.color = shapeColor
        drawTriangle(g, pointList!![curPointIndex])

    }

    private fun initCoordinatePlane(g: Graphics?) {
        // Abscissa (x)
        g!!.drawLine(0, mHeight / 2, mWidth, mHeight / 2)

        // Ordinate(y)
        g.drawLine(mWidth / 2, 0, mWidth / 2, mHeight)
    }

    private fun convert(x: Double, y: Double): IntPoint {
        val newX: Double = ((x + max) / (max * 2)) * mWidth
        val newY: Double = mHeight - ((y + max) / (max * 2)) * mHeight

        return IntPoint(newX.toInt(), newY.toInt())
    }

    private fun drawGraph(g: Graphics?) {
        for (i in 0 until intPointList.size - 1) {
            g!!.drawLine(intPointList[i].x, intPointList[i].y, intPointList[i + 1].x, intPointList[i + 1].y)
        }
    }

    private fun createPointLists() {
        val step = 0.005
        var x: Double = min

        intPointList.clear()
        pointList.clear()

        while (x < max) {
            intPointList.add(convert(x, Math.sin(x)))
            pointList.add(Point(x, Math.sin(x)))

            x += step
        }
    }

    private fun drawTriangle(g: Graphics?, point: Point) {
        var x0: Double = point.x
        var y0: Double = point.y
        var x1: Double = x0
        var y1: Double = y0 + a
        var x2: Double = x1 + a * Math.sqrt(3.0) / 2
        var y2: Double = y1 - (a / 2)

        g!!.drawLine(convert(x0, y0).x, convert(x0, y0).y, convert(x1, y1).x, convert(x1, y1).y)
        g.drawLine(convert(x1, y1).x, convert(x1, y1).y, convert(x2, y2).x, convert(x2, y2).y)
        g.drawLine(convert(x0, y0).x, convert(x0, y0).y, convert(x2, y2).x, convert(x2, y2).y)

        var p = Polygon()

        p.addPoint(convert(x0, y0).x, convert(x0, y0).y)
        p.addPoint(convert(x1, y1).x, convert(x1, y1).y)
        p.addPoint(convert(x2, y2).x, convert(x2, y2).y)
        p.addPoint(convert(x1, y1).x, convert(x2, y2).y)
        g.fillPolygon(p)

    }

    data class IntPoint(var x: Int, var y: Int)
}