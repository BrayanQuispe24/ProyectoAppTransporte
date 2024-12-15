/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica;

import Negocio.GrafoPesado;
import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BRAYAN
 */
public class Dijkstra<G extends Comparable<G>> {

    private GrafoPesado<G> unGrafo;
    private ControlMarcados control;
    private List<Double> listaCostos;
    private List<Integer> listaPredecesores;
    private double caminoDeMenorCosto;
    private int cantidadVertices;
    private List<G> listaVertices;
    private List<G> camino;

    public Dijkstra(GrafoPesado<G> unGrafo, G verticeOrigen, G verticeDestino) {
        this.unGrafo = unGrafo;
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.control = new ControlMarcados(this.cantidadVertices);
        this.caminoDeMenorCosto = -1;
        this.listaVertices = (List<G>) this.unGrafo.getVertices();
        this.camino = new ArrayList<>();
        this.iniciarListaCostos(verticeOrigen);
        this.iniciarListasPredecesores();
        this.ejecutarDijkstra(verticeDestino);
        this.getCamino(verticeDestino);
    }

    private void iniciarListaCostos(G verticeOrigen) {
        this.listaCostos = new ArrayList<>();
        this.unGrafo.validarVertice(verticeOrigen);
        int nroVerticeOrigen = this.unGrafo.nroVertice(verticeOrigen);
        for (int i = 0; i < this.cantidadVertices; i++) {
            if (i != nroVerticeOrigen) {
                listaCostos.add(Double.MAX_VALUE);
            } else {
                listaCostos.add((double) 0);
            }
        }
    }

    private void iniciarListasPredecesores() {
        this.listaPredecesores = new ArrayList<>();
        for (int i = 0; i < this.cantidadVertices; i++) {
            this.listaPredecesores.add(-1);
        }
    }

    private void ejecutarDijkstra(G verticeDestino) {
        int nroVerticeDestino = this.unGrafo.nroVertice(verticeDestino);
        while (!control.estaMarcadoVertice(nroVerticeDestino)) {
            int nroVerticeMenorCosto = this.verticeDeMenorCosto();
            if (nroVerticeMenorCosto != -1) {
                G verticeDeMenorCosto = this.listaVertices.get(nroVerticeMenorCosto);
                control.marcarVertice(nroVerticeMenorCosto);
                List<G> adyacentes = (List<G>) this.unGrafo.getAdyacentesDeVertice(verticeDeMenorCosto);
                for (G adyacente : adyacentes) {
                    int nroAdyacente = this.unGrafo.nroVertice(adyacente);
                    if (!control.estaMarcadoVertice(nroAdyacente)) {
                        double pesoOrigen = this.listaCostos.get(nroVerticeMenorCosto);
                        double pesoDestino = this.listaCostos.get(nroAdyacente);
                        double pesoArista = this.unGrafo.getPesoArista(verticeDeMenorCosto, adyacente);
                        if (pesoDestino > (pesoOrigen + pesoArista)) {
                            this.listaCostos.set(nroAdyacente, (pesoOrigen + pesoArista));
                            this.listaPredecesores.set(nroAdyacente, nroVerticeMenorCosto);
                        }
                    }
                }
            } else {
                this.caminoDeMenorCosto = -1;
                return;
            }
            this.caminoDeMenorCosto = this.listaCostos.get(nroVerticeDestino);
        }
    }

    private int verticeDeMenorCosto() {
        //vamos a devolver el indice del vertice de menor costo
        int indiceMenorCosto = -1;
        double menor = Double.MAX_VALUE;
        for (int i = 0; i < this.cantidadVertices; i++) {
            if ((menor > this.listaCostos.get(i)) && (!this.control.estaMarcadoVertice(i))) {
                indiceMenorCosto = i;
                menor = this.listaCostos.get(i);
            }
        }
        return indiceMenorCosto;
    }

    private void getCamino(G verticeDestino) {
        int nroDestino = this.unGrafo.nroVertice(verticeDestino);
        while (nroDestino != -1) {
            camino.add(this.listaVertices.get(nroDestino));
            nroDestino = this.listaPredecesores.get(nroDestino);
        }
        Collections.sort(camino);
    }

    public List<G> getCamino() {
        return this.camino;
    }

    public double getCostoCamino() {
        return this.caminoDeMenorCosto;
    }
}
