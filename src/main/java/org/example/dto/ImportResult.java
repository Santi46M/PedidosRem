package org.example.dto;

public class ImportResult {

    private final int nuevos;
    private final int duplicados;

    public ImportResult(int nuevos, int duplicados) {
        this.nuevos = nuevos;
        this.duplicados = duplicados;
    }

    public int getNuevos() {
        return nuevos;
    }

    public int getDuplicados() {
        return duplicados;
    }
}