package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.DetalleOrden;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface I_reportesService {

    byte[] exportPdfprod() throws JRException, FileNotFoundException;

    byte[] exportPdfEntrada() throws JRException, FileNotFoundException;

    byte[] exportPdfOrdenPedido(List<DetalleOrden> list)  throws JRException, FileNotFoundException;
}
