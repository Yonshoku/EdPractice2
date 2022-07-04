import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*
import javax.swing.border.Border

class MFrame: JFrame() {
    private val frameWidth = 1000
    private val frameHeight = 1000
    private val canvasWidth = 800
    private val canvasHeight = 800

    private val canvas = Canvas(canvasWidth, canvasHeight)
    private val canvasBox = Box(BoxLayout.Y_AXIS)
    private val contourColorButton = JButton("Contour color")
    private val shapeColorButton = JButton("Shape color")
    private val canvasColorButton = JButton("Canvas color")
    private val buttonsPanel = JPanel(FlowLayout(FlowLayout.CENTER))

    init {
        initFrame()

        // Frame
        contentPane.layout = BorderLayout()

        // Canvas

        canvas.background = Color(200, 200, 200)

        canvasBox.add(Box.createVerticalGlue())
        canvasBox.add(canvas)
        canvasBox.add(Box.createVerticalGlue())

        // Buttons
        contourColorButton.alignmentX = Component.CENTER_ALIGNMENT
        shapeColorButton.alignmentX = Component.CENTER_ALIGNMENT
        buttonsPanel.alignmentX = Component.CENTER_ALIGNMENT

        buttonsPanel.add(contourColorButton)
        buttonsPanel.add(shapeColorButton)
        buttonsPanel.add(canvasColorButton)

        contentPane.add(canvasBox, BorderLayout.PAGE_START)
        contentPane.add(buttonsPanel, BorderLayout.PAGE_END)

        isResizable = false
        isVisible = true
    }

    fun initFrame() {
        title = "Shape moving"
        setSize(frameWidth, frameHeight)

        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }
}