package com.example.cja_inventario.services;


import com.example.cja_inventario.interfaceServices.I_reportesService;
import com.example.cja_inventario.models.DetalleOrden;
import com.example.cja_inventario.repository.detalleOrdenRepository;
import com.example.cja_inventario.repository.productoRepository;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ReportesServices  implements I_reportesService {

    @Autowired
    private productoRepository prodRepository;

    @Autowired
    private detalleOrdenRepository detalleRepository;

    @Autowired
    private Reportes report;

    @Override
    public byte[] exportPdfprod() throws JRException, FileNotFoundException {
        return report.exportToPdfProducto(prodRepository.findAll());
    }

    @Override
    public byte[] exportPdfEntrada() throws JRException, FileNotFoundException {
        return report.exportToPdfEntrada(detalleRepository.findAll());
    }

    @Override
    public byte[] exportPdfOrdenPedido(List<DetalleOrden> list)  throws JRException, FileNotFoundException {
        return report.exportToPdfOrdenPedido(list);
    }
}
