import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class Canvas(private val mWidth: Int, private val mHeight: Int): JPanel() {
    private val max = 6
    private val min = -max

    init {
        preferredSize = Dimension(mWidth, mHeight)
        minimumSize = Dimension(mWidth, mHeight)
        maximumSize = Dimension(mWidth, mHeight)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        initCoordinatePlane(g)
        print(convert(1, 3))

        /* Test code
        var p1 = convert(Point(2, 4))
        var p2 = convert(Point(-6, -5))
        g!!.drawLine(p1.x, p1.y, p2.x, p2.y)
        */

    }

    private fun initCoordinatePlane(g: Graphics?) {
        // Abscissa (x)
        g!!.drawLine(0, mHeight / 2, mWidth, mHeight / 2)

        // Ordinate(y)
        g.drawLine(mWidth / 2, 0, mWidth / 2, mHeight)
    }

    private fun convert(point: Point): Point {
        var xx = point.x.toFloat()
        var yy = point.y.toFloat()

        val newX: Float = ((xx + max) / (max * 2)) * mWidth
        val newY: Float = mHeight - ((yy + max) / (max * 2)) * mHeight

        return Point(newX.toInt(), newY.toInt())
    }

    private fun convert(x: Int, y: Int): Point {
        var xx = x.toFloat()
        var yy = y.toFloat()

        val newX: Float = ((xx + max) / (max * 2)) * mWidth
        val newY: Float = mHeight - ((yy + max) / (max * 2)) * mHeight
        return Point(newX.toInt(), newY.toInt())
    }

    data class Point(var x: Int, var y: Int)
}