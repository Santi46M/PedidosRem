package org.example.factory;

import org.example.model.Pedido;

import java.util.List;

public class PedidoFactory {

    public Pedido crearPedido(String idExterno,
                              String optica,
                              String rut,
                              String paciente,
                              String ojo,
                              Double esfera,
                              Double cilindro,
                              Integer eje,
                              Double curvaBase,
                              Double curvaTorica,
                              double diametro,
                              String material,
                              String observaciones) {
        return new Pedido(
                idExterno, optica, rut, paciente, ojo,
                esfera, cilindro, eje, curvaBase, curvaTorica, diametro,
                material, observaciones
        );
    }

    public Pedido crearDesdeCsv(List<String> cols) {
        return new Pedido(
                get(cols, 0),
                get(cols, 1),
                get(cols, 2),
                get(cols, 3),
                get(cols, 4),
                parseDouble(get(cols, 5)),
                parseDouble(get(cols, 6)),
                parseInt(get(cols, 7)),
                parseDouble(get(cols, 8)),
                parseDouble(get(cols, 9)),
                parseDouble(get(cols, 10)),
                get(cols, 11),
                get(cols, 12)
        );
    }

    public Pedido crearDemo1() {
        return new Pedido(
                "2", "Óptica Americana", "210001110019", "María Gómez", "",
                -2.50, null, null, 7.80, null, 9.6, "ES Cel", ""
        );
    }

    public Pedido crearDemo2() {
        return new Pedido(
                "1", "Óptica Ruglio", "210001110020", "Carlos Díaz", "",
                -3.00, -0.75, 90, 7.90, null, 9.60, "Es Cel", "Torico Externo"
        );
    }

    private String get(List<String> cols, int index) {
        return index < cols.size() ? cols.get(index).trim() : "";
    }

    private double parseDouble(String value) {
        String normalized = value.trim().replace(',', '.');
        if (normalized.isBlank()) return 0.0;
        return Double.parseDouble(normalized);
    }

    private int parseInt(String value) {
        String normalized = value.trim();
        if (normalized.isBlank()) return 0;
        return Integer.parseInt(normalized);
    }
}