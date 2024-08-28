package com.codinsa.farmacovigilancia.admin.reportes


import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.DeviceGray
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.Line
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Div
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.OutputStream
import kotlin.collections.List

class PdfGenerator(private val outputStream: OutputStream) {
        private val pdf = PdfDocument(PdfWriter(outputStream))
        private val document: Document = Document(pdf)
        private val tableWidthPercentage = 90f
        init {
            document.setMargins(36f, 36f, 36f, 36f)
        }

        fun addTitle(title: String) {
            val titleParagraph = Paragraph(title)
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(18f)
                .setMarginBottom(10f)
                .setTextAlignment(TextAlignment.CENTER)
            document.add(titleParagraph)
        }
        fun addCenteredText(text: String) {
            val paragraph = Paragraph(text)
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(12f) // Ajusta el tamaño de la fuente según sea necesario
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setMarginBottom(15f)
                //.setFixedLeading(12f) // Ajusta la altura de la línea según sea necesario

            document.add(paragraph)
        }
        fun addCenteredTextSinMargin(text: String) {
            val paragraph = Paragraph(text)
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(12f) // Ajusta el tamaño de la fuente según sea necesario
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
            //.setFixedLeading(12f) // Ajusta la altura de la línea según sea necesario

            document.add(paragraph)
        }
        fun addCustomTableRow(rowData: List<String>) {

            val table = Table(rowData.size)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(UnitValue.createPercentValue(tableWidthPercentage))

            // Agregar celdas a la fila personalizada
            rowData.forEachIndexed { index, cellData ->
                val cell = createCell(cellData,DeviceGray.WHITE)

                table.addCell(cell)
            }
            document.add(table)
        }
        fun addCustomTableRowSinSeparador(singleData: String) {

            val table = Table(1)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(UnitValue.createPercentValue(tableWidthPercentage))
                .setBackgroundColor(DeviceGray.GRAY)  // Fondo gris para la tabla

            val cell = Cell()
                .add(Paragraph(singleData).setBold())
                .setBackgroundColor(DeviceGray.WHITE)  // Fondo blanco para la celda
                .setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f))

            table.addCell(cell)

            // Agregar tabla al documento
            document.add(table)
        }
        fun addCheckboxTextRow() {

            val table = Table(5)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(UnitValue.createPercentValue(tableWidthPercentage))
                .setBackgroundColor(DeviceGray.GRAY)  // Fondo gris para la tabla

            // Combinar celdas para el texto
            val textCell = Cell(1, 5)
                .add(Paragraph("Marcar con 'X' si la notificación corresponde a:").setBold().setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f)))
                .setBackgroundColor(DeviceGray.WHITE)  // Fondo blanco para la celda
                .setBorder(Border.NO_BORDER)   // Sin bordes

            table.addCell(textCell)

            // Agregar opciones
            val options = listOf("Reacción Adversa", "Error de medicación", "Problema de calidad", "Otros Evento Adverso")
            options.forEach { option ->
                val optionCell = Cell(1, 1)
                    .add(Paragraph(option).setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f)))
                    .setBackgroundColor(DeviceGray.WHITE)  // Fondo blanco para la celda
                    .setBorder(Border.NO_BORDER)  // Sin bordes
                table.addCell(optionCell)
            }

            // Agregar tabla al documento
            document.add(table)
        }
        fun addCustomTable(data: List<String>,listaBolean :List<Boolean>) {
            val table = Table(2).apply {
                setHorizontalAlignment(HorizontalAlignment.CENTER)
                setWidth(UnitValue.createPercentValue(tableWidthPercentage))
                setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f))
            }

            // Primera columna con un solo string
            val firstColumnCell = Cell(1, 1).apply {
                add(Paragraph(data[0]))
                setWidth(UnitValue.createPercentValue(60f))
                setBorder(Border.NO_BORDER)
            }
            val secondColumnCell = Cell(4, 1).apply {
                add(Paragraph(data[1]).setBorder(Border.NO_BORDER))
                add(Paragraph().apply {
                    val options = listOf("Leve","Moderado", "Severo")
                    add("Gravedad de la RAM\n")
                    options.forEachIndexed { index, option ->
                        val checkbox = if(listaBolean[index]){
                            "✔" // Código Unicode para checkbox marcado
                        } else {
                            "■"  // Código Unicode para checkbox desmarcado
                        }
                        add("$checkbox $option\n")
                    }
                    setBorder(Border.NO_BORDER)
                })
            }.setBorder(Border.NO_BORDER)
            table.addCell(firstColumnCell)
            table.addCell(secondColumnCell)

            document.add(table)
        }
        fun addCustomTableRowMitadCuatro(data: List<String>) {

            val table = Table(5)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(UnitValue.createPercentValue(tableWidthPercentage))
                .setBackgroundColor(DeviceGray.WHITE)  // Fondo gris para la tabla

            // Calcular el ancho para la primera columna (izquierda) y las otras columnas
            val firstColumnWidth = tableWidthPercentage / 2
            val otherColumnsWidth = (tableWidthPercentage - firstColumnWidth) / (data.size - 1)

            // Primera columna (izquierda)
            val firstColumnCell = Cell(2, 1)
                .add(Paragraph(data[0]).setBold())  // Primer elemento en negrita
                .setBackgroundColor(DeviceGray.WHITE)  // Fondo blanco para la celda
                .setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f))  // Sin bordes
            table.addCell(firstColumnCell)

            // Columnas para data[1] a data[4]
            for (i in 1 until data.size-1) {
                val cell = Cell(1, 1)
                    .add(Paragraph(data[i]))
                    .setBackgroundColor(DeviceGray.WHITE)  // Fondo blanco para la celda
                    .setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f))  // Sin bordes
                    .setWidth(UnitValue.createPercentValue(otherColumnsWidth))
                table.addCell(cell)
            }

            // Agregar tabla al documento
            document.add(table)
        }

        fun createSeparatorLine(): Div {
            return Div()
                .setBackgroundColor(DeviceGray.BLACK)
                .setHeight(1f)
                .setWidth(UnitValue.createPercentValue(100f))
        }

        fun createCustomBorder(): Border {
            // Implementa la lógica para crear un borde personalizado según tus necesidades
            // Puedes consultar la documentación de iText para más opciones de bordes.
            return SolidBorder(1f)  // Ejemplo de un borde sólido de ancho 1
        }

        fun addParagraph(text: String) {
            val paragraph = Paragraph(text)
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(12f)
            document.add(paragraph)
        }
        fun addTablerReporte(headers: List<String>, data: List<List<String>>) {


            // Obtener el ancho de la página actual

            val pageWidth = pdf.defaultPageSize.width - document.leftMargin - document.rightMargin

            // Calcular el ancho de la tabla en puntos
            val tableWidthPoints = pageWidth * (tableWidthPercentage / 100)

            val table = Table(headers.size)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(UnitValue.createPointValue(tableWidthPoints))
                .setBackgroundColor(DeviceGray.WHITE)


            // Añadir encabezados
            headers.forEach { header ->
                table.addCell(createCell(header, DeviceGray.WHITE))
            }

            // Añadir datos
            data.forEach { rowData ->
                rowData.forEach { cellData ->
                    table.addCell(createCell(cellData, DeviceGray.WHITE))
                }
            }

            document.add(table)

        }
        fun addTable(headers: List<String>, data: List<List<String>>) {
            val table = Table(headers.size)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)

            // Añadir encabezados
            headers.forEach { header ->
                table.addCell(createCell(header, DeviceGray.WHITE))
            }

            // Añadir datos
            data.forEach { rowData ->
                rowData.forEach { cellData ->
                    table.addCell(createCell(cellData, DeviceGray.WHITE))
                }
            }

            document.add(table)

        }

        private fun createCell(content: String, backgroundColor: Color): Cell {
            return Cell()
                .add(Paragraph(content))
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(backgroundColor)
                .setBorder(SolidBorder(DeviceRgb(0, 0, 0), 1f))
        }

        fun addLineBreak() {
            document.add(AreaBreak())
        }
        fun close() {
            document.close()
            outputStream.close()
        }
    }
