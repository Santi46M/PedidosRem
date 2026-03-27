package org.example.service;

import org.example.dto.ImportResult;
import org.example.factory.PedidoFactory;
import org.example.model.Pedido;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvImportService {

    public static ImportResult importar(File file,
                                        PedidoService pedidoService,
                                        PedidoFactory pedidoFactory) throws IOException {
        int nuevos = 0;
        int duplicados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    String lower = line.toLowerCase();
                    if (lower.contains("id_externo") || lower.contains("optica")) {
                        continue;
                    }
                }

                List<String> cols = parseCsvLine(line);

                if (cols.size() < 11) {
                    throw new IOException("Fila inválida: se esperaban al menos 11 columnas. Línea: " + line);
                }

                Pedido pedido = pedidoFactory.crearDesdeCsv(cols);

                if (pedidoService.agregarPedidoSiNoExiste(pedido)) {
                    nuevos++;
                } else {
                    duplicados++;
                }
            }
        }

        return new ImportResult(nuevos, duplicados);
    }

    private static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());
        return result;
    }
}