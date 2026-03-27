package org.example.controller;

import javafx.collections.ObservableList;
import org.example.dto.ImportResult;
import org.example.factory.PedidoFactory;
import org.example.model.EstadoPedido;
import org.example.model.Pedido;
import org.example.service.CsvImportService;
import org.example.service.PedidoService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {

    private final PedidoService pedidoService;
    private final PedidoFactory pedidoFactory;

    public MainController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
        this.pedidoFactory = new PedidoFactory();
    }

    public ObservableList<Pedido> getPedidos() {
        return pedidoService.getPedidos();
    }

    public void cargarDemoInicial() {
        pedidoService.agregarPedidoSiNoExiste(pedidoFactory.crearDemo1());
        pedidoService.agregarPedidoSiNoExiste(pedidoFactory.crearDemo2());
    }

    public ImportResult importarCsv(File file) throws IOException {
        return CsvImportService.importar(file, pedidoService, pedidoFactory);
    }

    public void marcarComoImpreso(Pedido pedido) {
        pedidoService.cambiarEstado(pedido, EstadoPedido.IMPRESO);
    }

    public int[] cargarDemoImportacion() {
        List<Pedido> demo = List.of(
                pedidoFactory.crearPedido("3", "Óptica Florida", "210009990011", "Lucía Acosta", "OD", -1.75, -0.50, 10, 7.90, null, 9.6, "Boston Es C", "Torico Externo"),
                pedidoFactory.crearPedido("4", "Óptica Florida", "210009990011", "Martín Sosa", "OI", -4.25, null, null, 7.90, 8.27, 10.1, "Boston EO", "Torico Interno"),
                pedidoFactory.crearPedido("5", "Garese Geant", "210009990012", "Ana Pereira", "", -2.00, -2.25, 90, 8.40, null, 9.4, "Boston ES Verde", "Borde grueso")
        );

        int nuevos = 0;
        int duplicados = 0;

        for (Pedido pedido : demo) {
            if (pedidoService.agregarPedidoSiNoExiste(pedido)) {
                nuevos++;
            } else {
                duplicados++;
            }
        }

        return new int[]{nuevos, duplicados};
    }
}