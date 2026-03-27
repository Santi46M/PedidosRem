package org.example.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.EstadoPedido;
import org.example.model.Pedido;

import java.util.Comparator;
import java.util.Objects;

public class PedidoService {

    private final ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

    public ObservableList<Pedido> getPedidos() {
        return pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        ordenar();
    }

    public boolean agregarPedidoSiNoExiste(Pedido pedido) {
        boolean existe = pedidos.stream()
                .anyMatch(p -> Objects.equals(normalize(p.getIdExterno()), normalize(pedido.getIdExterno())));

        if (existe) {
            return false;
        }

        pedidos.add(pedido);
        ordenar();
        return true;
    }

    public void cambiarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        pedido.setEstado(nuevoEstado);
    }

    public void cargarDatosDemo() {
        agregarPedido(new Pedido(
                "q", "Garese Centro", "210001110019", "Roberto Camacho", "",
                -2.50, null, null, 6.90, null, 8.4, "Boston XO", "Camacho se la come"
        ));

        agregarPedido(new Pedido(
                "w", "Guzman Echave", "210001110020", "Federico", "",
                +3.00, null, null, 8.30, null, 9.9, "Boston EO", "Diseño Asferico"
        ));
    }

    private void ordenar() {
        FXCollections.sort(pedidos, Comparator.comparing(Pedido::getFechaAlta).reversed());
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }
}