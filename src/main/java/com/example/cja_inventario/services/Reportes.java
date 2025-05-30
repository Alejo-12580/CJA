package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_productoServices;
import com.example.cja_inventario.models.DetalleOrden;
import com.example.cja_inventario.models.Producto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Reportes {

    @Autowired
     private I_productoServices servP;
    public byte[] exportToPdfProducto(List<Producto> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportProd(list));
    }
    public byte[] exportToPdfEntrada(List<DetalleOrden> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportEntrada(list));
    }
    public byte[] exportToPdfOrdenPedido(List<DetalleOrden> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportOrdenPedido(list));
    }

    /*    public byte[] exportToXls(List<Producto> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }*/

    private JasperPrint getReportProd(List<Producto> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();


       //    List<Producto> lista= servP.listar();
       params.put("petsData", new JRBeanCollectionDataSource(list));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:reporte.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;
    }
    private JasperPrint getReportEntrada(List<DetalleOrden> listDetalle) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        List<DetalleOrden> dtList = new ArrayList<>();
        for (DetalleOrden dt:listDetalle) {
            if(dt.getOrden().getFechaRecibida()!=null){
                dtList.add(dt);
            }

        }

        //    List<Producto> lista= servP.listar();
        params.put("petsData", new JRBeanCollectionDataSource(dtList));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:EntradasReport.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;
    }
    private JasperPrint getReportOrdenPedido(List<DetalleOrden> listDetalle) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("petsData", new JRBeanCollectionDataSource(listDetalle));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:OrdenPedido.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;

        }
}
