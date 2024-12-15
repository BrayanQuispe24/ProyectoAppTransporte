/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class GrafoPesadoMatrizA<G extends Comparable<G>> {

    private List<G> listaDeVertices;
    private double[][] matrizAdyacencias;
    private final int INDICE_INVALIDO = -1;

    public GrafoPesadoMatrizA() {
        this.listaDeVertices = new ArrayList<>();
        this.matrizAdyacencias = new double[0][0];
    }

    public GrafoPesadoMatrizA(Iterable<G> listaVertices) {
        this();
        for (G verticeActual : listaVertices) {
            this.insertarVertice(verticeActual);
        }
    }

    public void insertarVertice(G verticeActual) {
        int nroVertice = this.nroVertice(verticeActual);
        if (nroVertice != this.INDICE_INVALIDO) {
            throw new IllegalArgumentException("Vertice ya existe en el grafo");
        }
        this.listaDeVertices.add(verticeActual);
        this.expandirMatriz();
    }

    public void insertarArista(G verticeOrigen, G verticeDestino, double peso) {
        this.validarVertice(verticeOrigen);
        this.validarVertice(verticeDestino);
        int nroOrigen = this.nroVertice(verticeOrigen);
        int nroDestino = this.nroVertice(verticeDestino);
        this.matrizAdyacencias[nroOrigen][nroDestino] = peso;

    }

    private void validarVertice(G vertice) {
        int nroVertice = this.nroVertice(vertice);
        if (nroVertice == this.INDICE_INVALIDO) {
            throw new IllegalArgumentException("El vertice no existe en el grafo");
        }
    }

    private int nroVertice(G verticeActual) {
        int cantidadVertices = this.cantidadVertices();
        for (int i = 0; i < cantidadVertices; i++) {
            G vertice = this.listaDeVertices.get(i);
            if (vertice.compareTo(verticeActual) == 0) {
                return i;
            }
        }
        return this.INDICE_INVALIDO;
    }

    private int cantidadVertices() {
        return this.listaDeVertices.size();
    }

    private void expandirMatriz() {
        int nuevaCantidad = this.cantidadVertices();
        double[][] nuevaMatriz = new double[nuevaCantidad][nuevaCantidad];

        for (int i = 0; i < nuevaCantidad; i++) {
            for (int j = 0; j < nuevaCantidad; j++) {
                if (i < matrizAdyacencias.length && j < matrizAdyacencias.length) {
                    nuevaMatriz[i][j] = matrizAdyacencias[i][j];
                } else {
                    nuevaMatriz[i][j] = Double.MAX_VALUE; // Inicializa las nuevas posiciones con infinito
                }
            }
        }
        this.matrizAdyacencias = nuevaMatriz;
    }

    public void eliminarVertice(G vertice) {
        int indice = this.nroVertice(vertice);

        if (indice == INDICE_INVALIDO) {
            throw new IllegalArgumentException("El vértice no existe en el grafo");
        }

        // Eliminar el vértice de la lista
        this.listaDeVertices.remove(indice);

        // Crear una nueva matriz sin la fila y columna del vértice eliminado
        int nuevaCantidad = this.cantidadVertices();
        double[][] nuevaMatriz = new double[nuevaCantidad][nuevaCantidad];

        int filaNueva = 0; // Índice para la nueva matriz
        for (int i = 0; i < matrizAdyacencias.length; i++) {
            if (i != indice) { // Solo copiar filas que no sean la del vértice eliminado
                int columnaNueva = 0; // Índice para las columnas de la nueva matriz
                for (int j = 0; j < matrizAdyacencias.length; j++) {
                    if (j != indice) { // Solo copiar columnas que no sean la del vértice eliminado
                        nuevaMatriz[filaNueva][columnaNueva] = matrizAdyacencias[i][j];
                        columnaNueva++;
                    }
                }
                filaNueva++;
            }
        }

        // Actualizar la matriz
        this.matrizAdyacencias = nuevaMatriz;
    }

    public void eliminarArista(G verticeOrigen, G verticeDestino) {
        this.validarVertice(verticeOrigen);
        this.validarVertice(verticeDestino);
        int nroOrigen = this.nroVertice(verticeOrigen);
        int nroDestino = this.nroVertice(verticeDestino);
        this.matrizAdyacencias[nroOrigen][nroDestino] = Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Grafo Pesado (Matriz de Adyacencia):\n");

        // Encabezado de la matriz con los nombres de los vértices
        builder.append("   ");
        for (G vertice : listaDeVertices) {
            builder.append(vertice).append("  ");
        }
        builder.append("\n");

        // Mostrar las filas de la matriz
        for (int i = 0; i < matrizAdyacencias.length; i++) {
            builder.append(listaDeVertices.get(i)).append(" "); // Etiqueta de fila
            for (int j = 0; j < matrizAdyacencias[i].length; j++) {
                if (matrizAdyacencias[i][j] == Double.MAX_VALUE) {
                    builder.append("∞ "); // Representar ausencia de arista como infinito
                } else {
                    builder.append(String.format("%.1f ", matrizAdyacencias[i][j])); // Mostrar el peso
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }

}
