/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.awt.Color;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;

/**
 *
 * @author RAMSES
 */
public class GeneradorReporte {

    public GeneradorReporte() {
    }
    
    public static void generarPDFDesdeTabla(DefaultTableModel modelo, String titulo_reporte) {
        try {
            JasperDesign jasper_design = new JasperDesign();
            // ... (Configuración inicial de márgenes y tamaño igual)
            jasper_design.setName("ReporteDinamico");
            jasper_design.setPageWidth(595);
            jasper_design.setPageHeight(842);
            jasper_design.setColumnWidth(515);
            jasper_design.setLeftMargin(40);
            jasper_design.setRightMargin(40);
            jasper_design.setTopMargin(40);
            jasper_design.setBottomMargin(40);

            // --- 1. TÍTULO Y FECHA ---
            JRDesignBand banda_titulo = new JRDesignBand();
            banda_titulo.setHeight(80);
            // (Aquí va tu código de txt_titulo y txt_fecha...)

            // [CÓDIGO DE TÍTULO Y FECHA MANTENIDO]
            JRDesignStaticText txt_titulo = new JRDesignStaticText();
            txt_titulo.setText(titulo_reporte);
            txt_titulo.setX(0); txt_titulo.setY(0);
            txt_titulo.setWidth(515); txt_titulo.setHeight(30);
            txt_titulo.setFontSize(18f);
            txt_titulo.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            banda_titulo.addElement(txt_titulo);

            JRDesignTextField txt_fecha = new JRDesignTextField();
            JRDesignExpression expr_fecha = new JRDesignExpression();
            expr_fecha.setText("new java.text.SimpleDateFormat(\"dd/MM/yyyy HH:mm\").format(new java.util.Date())");
            txt_fecha.setExpression(expr_fecha);
            txt_fecha.setX(0); txt_fecha.setY(35);
            txt_fecha.setWidth(515); txt_fecha.setHeight(20);
            txt_fecha.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            banda_titulo.addElement(txt_fecha);
            jasper_design.setTitle(banda_titulo);

            // --- 2. CONFIGURACIÓN DE COLUMNAS Y VARIABLE DE SUMA ---
            JRDesignBand banda_cabecera = new JRDesignBand();
            banda_cabecera.setHeight(20);
            JRDesignBand banda_detalle = new JRDesignBand();
            banda_detalle.setHeight(20);

            int num_columnas = modelo.getColumnCount();
            int ancho_col = 515 / num_columnas;

            // Si es reporte de comandas, creamos una variable para sumar la última columna
            if (titulo_reporte.contains("COMANDAS")) {
                JRDesignVariable varSuma = new JRDesignVariable();
                varSuma.setName("TOTAL_ACUMULADO");
                varSuma.setValueClass(Double.class);
                varSuma.setCalculation(CalculationEnum.SUM); // Indicamos que debe sumar
                varSuma.setResetType(ResetTypeEnum.REPORT);

                // Expresión para extraer el valor numérico de la columna "Total" (índice 6)
                // Quitamos el "$" y convertimos a Double
                JRDesignExpression exprSuma = new JRDesignExpression();
                exprSuma.setText("Double.valueOf($F{COLUMN_6}.toString().replace(\"$\", \"\"))");
                varSuma.setExpression(exprSuma);
                jasper_design.addVariable(varSuma);
            }

            for (int i = 0; i < num_columnas; i++) {
                String nombre_campo = "COLUMN_" + i;
                JRDesignField campo = new JRDesignField();
                campo.setName(nombre_campo);
                campo.setValueClass(Object.class);
                jasper_design.addField(campo);

                // Cabeceras
                JRDesignStaticText lbl_cabecera = new JRDesignStaticText();
                lbl_cabecera.setText(modelo.getColumnName(i));
                lbl_cabecera.setX(i * ancho_col); lbl_cabecera.setWidth(ancho_col); lbl_cabecera.setHeight(20);
                lbl_cabecera.setBackcolor(new Color(255, 184, 77));
                lbl_cabecera.setMode(ModeEnum.OPAQUE);
                lbl_cabecera.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                banda_cabecera.addElement(lbl_cabecera);

                // Celdas de datos
                JRDesignTextField txt_detalle = new JRDesignTextField();
                JRDesignExpression expr_det = new JRDesignExpression();
                expr_det.setText("$F{" + nombre_campo + "}");
                txt_detalle.setExpression(expr_det);
                txt_detalle.setX(i * ancho_col); txt_detalle.setWidth(ancho_col); txt_detalle.setHeight(20);
                txt_detalle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                banda_detalle.addElement(txt_detalle);
            }
            jasper_design.setColumnHeader(banda_cabecera);
            ((JRDesignSection) jasper_design.getDetailSection()).addBand(banda_detalle);

            // --- 3. BANDA DE RESUMEN (SUMMARY) - AQUÍ VA EL TOTAL ---
            if (titulo_reporte.contains("COMANDAS")) {
                JRDesignBand banda_resumen = new JRDesignBand();
                banda_resumen.setHeight(40);

                // Etiqueta "TOTAL VENTAS:"
                JRDesignStaticText lbl_total_texto = new JRDesignStaticText();
                lbl_total_texto.setText("TOTAL VENTAS DEL PERIODO:");
                lbl_total_texto.setX(250); lbl_total_texto.setY(15);
                lbl_total_texto.setWidth(150); lbl_total_texto.setHeight(20);
                lbl_total_texto.setBold(true);
                lbl_total_texto.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
                banda_resumen.addElement(lbl_total_texto);

                // Campo con el valor de la variable
                JRDesignTextField txt_total_valor = new JRDesignTextField();
                JRDesignExpression expr_total_valor = new JRDesignExpression();
                expr_total_valor.setText("\"$\" + String.format(\"%.2f\", $V{TOTAL_ACUMULADO})");
                txt_total_valor.setExpression(expr_total_valor);
                txt_total_valor.setX(400); txt_total_valor.setY(15);
                txt_total_valor.setWidth(115); txt_total_valor.setHeight(20);
                txt_total_valor.setBold(true);
                txt_total_valor.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                banda_resumen.addElement(txt_total_valor);

                jasper_design.setSummary(banda_resumen);
            }

            // --- 4. PIE DE PÁGINA ---
            JRDesignBand banda_pie = new JRDesignBand();
            banda_pie.setHeight(30);
            // (Tu código de numeración de página...)
            JRDesignTextField txt_pagina = new JRDesignTextField();
            JRDesignExpression expr_pagina = new JRDesignExpression();
            expr_pagina.setText("\"Página \" + $V{PAGE_NUMBER}");
            txt_pagina.setExpression(expr_pagina);
            txt_pagina.setX(315); txt_pagina.setY(10);
            txt_pagina.setWidth(200); txt_pagina.setHeight(20);
            txt_pagina.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
            banda_pie.addElement(txt_pagina);
            jasper_design.setPageFooter(banda_pie);

            // --- 5. COMPILAR Y MOSTRAR ---
            jasper_design.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            JasperReport jr = JasperCompileManager.compileReport(jasper_design);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, new JRTableModelDataSource(modelo));
            JasperViewer.viewReport(jp, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    
}
