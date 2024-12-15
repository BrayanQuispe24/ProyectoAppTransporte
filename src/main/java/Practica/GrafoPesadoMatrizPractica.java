/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 */
public class GrafoPesadoMatrizPractica<G extends Comparable<G>> {

    private List<G> listaDeVertices;
    private double[][] matrizAdyacencias;
    private final int indice_invalido = -1;
    private final double infinito = Double.MAX_VALUE;

    public GrafoPesadoMatrizPractica() {
        this.listaDeVertices = new ArrayList<>();
        this.matrizAdyacencias = new double[0][0];
    }

    public GrafoPesadoMatrizPractica(Iterable<G> listaVertices) {
        this();
        for (G verticeActual : listaVertices) {
            this.insertarVertice(verticeActual);
        }
    }

    public int cantidadVertices() {
        return this.listaDeVertices.size();
    }

    public int nroVertice(G vertice) {
        int cantidadVertices = this.cantidadVertices();
        for (int i = 0; i < cantidadVertices; i++) {
            G verticeActual = this.listaDeVertices.get(i);
            if (verticeActual.compareTo(vertice) == 0) {
                return i;
            }
        }
        return this.indice_invalido;
    }

    public void validarVertice(G vertice) {
        int nroVertice = this.nroVertice(vertice);
        if (nroVertice == this.indice_invalido) {
            throw new IllegalArgumentException("Error vertice no existe en el grafo");
        }
    }

    public void insertarVertice(G verticeActual) {
        int nroVertice = this.nroVertice(verticeActual);
        if (nroVertice != this.indice_invalido) {
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

    public void eliminarVertice(G verticeAEliminar) {
        int nroEliminar = this.nroVertice(verticeAEliminar);
        this.listaDeVertices.remove(nroEliminar);
        int nuevaCantidad = this.cantidadVertices();
        double[][] nuevaMatriz = new double[nuevaCantidad][nuevaCantidad];
        int filas = 0;
        for (int i = 0; i < this.matrizAdyacencias.length; i++) {
            if (i != nroEliminar) {
                int columnas = 0;
                for (int j = 0; j < this.matrizAdyacencias.length; j++) {
                    if (j != nroEliminar) {
                        nuevaMatriz[filas][columnas] = this.matrizAdyacencias[i][j];
                        columnas++;
                    }
                }

            }
            filas++;
        }
        this.matrizAdyacencias = nuevaMatriz;
    }

    public void eliminarArista(G verticeOrigen, G verticeDestino) {
        this.validarVertice(verticeOrigen);
        this.validarVertice(verticeDestino);
        int nroOrigen = this.nroVertice(verticeOrigen);
        int nroDestino = this.nroVertice(verticeDestino);
        this.matrizAdyacencias[nroOrigen][nroDestino] = Double.MAX_VALUE;
    }

    private void expandirMatriz() {
        int nuevaLongitud = this.cantidadVertices();
        double[][] nuevaMatriz = new double[nuevaLongitud][nuevaLongitud];
        for (int i = 0; i < nuevaLongitud; i++) {
            for (int j = 0; j < nuevaLongitud; j++) {
                if (i < nuevaLongitud && j < nuevaLongitud) {
                    nuevaMatriz[i][j] = this.matrizAdyacencias[i][j];
                } else {
                    nuevaMatriz[i][j] = this.infinito;
                }
            }
        }
    }

}
