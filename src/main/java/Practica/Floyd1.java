/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica;

import Negocio.DigrafoPesado;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class Floyd1<G extends Comparable<G>> {

    private DigrafoPesado unGrafo;
    private int cantidadVertices;
    private List<G> vertices;
    private int[][] matrizPredecesores;
    private double[][] matrizPesos;

    public Floyd1(DigrafoPesado unGrafo) {
        this.unGrafo = unGrafo;
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.vertices = (List<G>) unGrafo.getVertices();
        this.llenarValoresMP();
        this.iniciarMatrizPesos();
        this.iniciarMaPre();
        this.ejecutarFloyd();
    }

    private void llenarValoresMP() {
        this.matrizPesos = new double[this.cantidadVertices][this.cantidadVertices];
        for (int i = 0; i < this.cantidadVertices; i++) {
            for (int j = 0; j < this.cantidadVertices; j++) {
                if (i != j) {
                    this.matrizPesos[i][j] = Double.MAX_VALUE;
                } else {
                    this.matrizPesos[i][j] = (double) 0;
                }
            }
        }
    }

    private void iniciarMatrizPesos() {
        for (int i = 0; i < this.cantidadVertices; i++) {
            G verticeActual = this.vertices.get(i);
            List<G> adyacentes = (List<G>) this.unGrafo.getAdyacentesDeVertice(verticeActual);
            for (G adyacente : adyacentes) {
                int nroAdyacente = this.unGrafo.nroVertice(adyacente);
                double peso = this.unGrafo.getPesoArista(verticeActual, adyacente);
                this.matrizPesos[i][nroAdyacente] = peso;
            }
        }
    }

    private void iniciarMaPre() {
        for (int i = 0; i < this.cantidadVertices; i++) {
            for (int j = 0; j < this.cantidadVertices; j++) {
                if (i != j) {
                    this.matrizPredecesores[i][j] = j;
                } else {
                    this.matrizPredecesores[i][j] = -1;
                }
            }
        }
    }

    private void ejecutarFloyd() {
        for (int k = 0; k < this.cantidadVertices; k++) {
            for (int i = 0; i < this.cantidadVertices; i++) {
                for (int j = 0; j < this.cantidadVertices; j++) {
                    if ((this.matrizPesos[i][k] != Double.MAX_VALUE) && (this.matrizPesos[k][j] != Double.MAX_VALUE)) {
                        if (this.matrizPesos[i][j] > (this.matrizPesos[i][k] + this.matrizPesos[k][j])) {
                            this.matrizPesos[i][j] = this.matrizPesos[i][k] + this.matrizPesos[k][j];
                            this.matrizPredecesores[i][j] = k;
                        }
                    }
                }
            }
        }
    }
}
