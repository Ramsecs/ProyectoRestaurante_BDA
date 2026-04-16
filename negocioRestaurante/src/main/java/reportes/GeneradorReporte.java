/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.awt.Color;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.DefaultTableModel;
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
            jasper_design.setName("ReporteDinamico");
            jasper_design.setPageWidth(595);
            jasper_design.setPageHeight(842);
            jasper_design.setColumnWidth(515);
            jasper_design.setLeftMargin(40);
            jasper_design.setRightMargin(40);
            jasper_design.setTopMargin(40);
            jasper_design.setBottomMargin(40);

            // --- 1. TÍTULO Y FECHA (TITLE BAND) ---
            JRDesignBand banda_titulo = new JRDesignBand();
            banda_titulo.setHeight(80); // Aumentamos altura para la fecha

            // Título Principal
            JRDesignStaticText txt_titulo = new JRDesignStaticText();
            txt_titulo.setText(titulo_reporte);
            txt_titulo.setX(0); 
            txt_titulo.setY(0);
            txt_titulo.setWidth(515); 
            txt_titulo.setHeight(30);
            txt_titulo.setFontSize(18f);
            txt_titulo.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            banda_titulo.addElement(txt_titulo);

            // Fecha de creación (Justo abajo del título)
            JRDesignTextField txt_fecha = new JRDesignTextField();
            JRDesignExpression expr_fecha = new JRDesignExpression();
            // Usamos una expresión simple de Java para la fecha actual
            expr_fecha.setText("new java.text.SimpleDateFormat(\"dd/MM/yyyy HH:mm\").format(new java.util.Date())");
            txt_fecha.setExpression(expr_fecha);
            txt_fecha.setX(0); 
            txt_fecha.setY(35);
            txt_fecha.setWidth(515); 
            txt_fecha.setHeight(20);
            txt_fecha.setFontSize(10f);
            txt_fecha.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            banda_titulo.addElement(txt_fecha);

            jasper_design.setTitle(banda_titulo);

            // --- 2. CONFIGURACIÓN DE COLUMNAS ---
            JRDesignBand banda_cabecera = new JRDesignBand();
            banda_cabecera.setHeight(20);
            JRDesignBand banda_detalle = new JRDesignBand();
            banda_detalle.setHeight(20);

            int num_columnas = modelo.getColumnCount();
            int ancho_col = 515 / num_columnas;

            for (int i = 0; i < num_columnas; i++) {
                String nombre_campo = "COLUMN_" + i;
                JRDesignField campo = new JRDesignField();
                campo.setName(nombre_campo);
                campo.setValueClass(Object.class);
                jasper_design.addField(campo);

                JRDesignStaticText lbl_cabecera = new JRDesignStaticText();
                lbl_cabecera.setText(modelo.getColumnName(i));
                lbl_cabecera.setX(i * ancho_col); 
                lbl_cabecera.setWidth(ancho_col); 
                lbl_cabecera.setHeight(20);
                lbl_cabecera.setBackcolor(new Color(255, 184, 77));
                lbl_cabecera.setMode(ModeEnum.OPAQUE);
                lbl_cabecera.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                banda_cabecera.addElement(lbl_cabecera);

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

            // --- 3. NUMERACIÓN DE PÁGINAS (PAGE FOOTER) ---
            JRDesignBand banda_pie = new JRDesignBand();
            banda_pie.setHeight(30);

            JRDesignTextField txt_pagina = new JRDesignTextField();
            JRDesignExpression expr_pagina = new JRDesignExpression();
            // Expresión integrada de Jasper para numeración "Página X de Y"
            expr_pagina.setText("\"Página \" + $V{PAGE_NUMBER}");
            txt_pagina.setExpression(expr_pagina);
            txt_pagina.setX(315); 
            txt_pagina.setY(10); // Posicionado a la derecha
            txt_pagina.setWidth(200); 
            txt_pagina.setHeight(20);
            txt_pagina.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
            banda_pie.addElement(txt_pagina);

            jasper_design.setPageFooter(banda_pie);

            // --- 4. COMPILAR Y MOSTRAR ---
            jasper_design.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            JasperReport jr = JasperCompileManager.compileReport(jasper_design);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, new JRTableModelDataSource(modelo));
            JasperViewer.viewReport(jp, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    
}
