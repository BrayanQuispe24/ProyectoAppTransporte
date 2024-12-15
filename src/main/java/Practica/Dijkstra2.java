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
public class Dijkstra2<G extends Comparable<G>> {

    private GrafoPesado unGrafo;
    private List<G> vertices;
    private List<Integer> listaPredecesores;
    private List<Double> listaCostos;
    private List<G> listaCaminoMasCorto;
    private int cantidadVertices;
    private ControlMarcados control;
    private double costoCaminoMasCorto;
    private G verticeDestino;

    public Dijkstra2(GrafoPesado unGrafo, G verticeOrigen, G verticeDestino) {
        this.costoCaminoMasCorto = -1;
        this.unGrafo = unGrafo;
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.vertices = (List<G>) unGrafo.getVertices();
        this.iniciarListaPredecesores();
        this.iniciarListaCostos(verticeOrigen);
        this.control = new ControlMarcados(this.cantidadVertices);
        this.ejecutarDijkstra2(verticeDestino);
        this.verticeDestino = verticeDestino;
    }

    private void iniciarListaPredecesores() {
        this.listaPredecesores = new ArrayList<>();
        for (int i = 0; i < this.cantidadVertices; i++) {
            this.listaPredecesores.add(-1);
        }
    }

    private void iniciarListaCostos(G verticeOrigen) {
        this.listaCostos = new ArrayList<>();
        int nroVertice = this.unGrafo.nroVertice(verticeOrigen);
        for (int i = 0; i < this.cantidadVertices; i++) {
            if (i != nroVertice) {
                this.listaCostos.add(Double.MAX_VALUE);
            } else {
                this.listaCostos.add((double) 0);
            }
        }
    }

    private void ejecutarDijkstra2(G verticeDestino) {
        int nroVerticeDestino = this.unGrafo.nroVertice(verticeDestino);
        while (!control.estaMarcadoVertice(nroVerticeDestino)) {
            int nroVerticeMenorCosto = this.obtenerVerticeMernorCosto();
            this.control.marcarVertice(nroVerticeMenorCosto);
            if (nroVerticeMenorCosto != -1) {
                G verticeMenorCosto = this.vertices.get(nroVerticeMenorCosto);
                List<G> adyacentes = (List<G>) this.unGrafo.getAdyacentesDeVertice(verticeMenorCosto);
                for (G adyacente : adyacentes) {
                    int nroAdyacente = this.unGrafo.nroVertice(adyacente);
                    if (!control.estaMarcadoVertice(nroAdyacente)) {
                        double pesoOrigen = this.listaCostos.get(nroVerticeMenorCosto);
                        double pesoDestino = this.listaCostos.get(nroAdyacente);
                        double pesoArista = this.unGrafo.getPesoArista(verticeMenorCosto, adyacente);
                        if (pesoDestino > (pesoOrigen + pesoArista)) {
                            this.listaCostos.set(nroAdyacente, (pesoOrigen + pesoArista));
                            this.listaPredecesores.set(nroAdyacente, nroVerticeMenorCosto);
                        }
                    }
                }
            } else {
                System.out.println("No se puede ");
                this.costoCaminoMasCorto = -1;
                return;
            }
        }
        this.costoCaminoMasCorto = this.listaCostos.get(nroVerticeDestino);
    }

    private int obtenerVerticeMernorCosto() {
        int indice = -1;
        double menor = Double.MAX_VALUE;
        for (int i = 0; i < this.cantidadVertices; i++) {
            if (menor > this.listaCostos.get(i) && !this.control.estaMarcadoVertice(i)) {
                indice = i;
                menor = this.listaCostos.get(i);
            }
        }
        return indice;
    }

    public double costoMinimo() {
        int nroVerticeDestino = this.unGrafo.nroVertice(this.verticeDestino);
        return this.listaCostos.get(nroVerticeDestino);
    }

    public List<G> obtenerCamino() {
        this.listaCaminoMasCorto = new ArrayList<>();
        int nroVerticeDestino = this.unGrafo.nroVertice(this.verticeDestino);

        // Verificar si hay camino
        if (this.listaPredecesores.get(nroVerticeDestino) == -1) {
            // No hay camino válido
            return Collections.emptyList();
        }

        // Construir el camino desde el destino al origen
        this.listaCaminoMasCorto.add(this.verticeDestino);
        while (nroVerticeDestino != -1) {
            int nroPredecesor = this.listaPredecesores.get(nroVerticeDestino);
            if (nroPredecesor == -1) {
                break; // No hay más predecesores
            }
            G verticePredecesor = this.vertices.get(nroPredecesor);
            this.listaCaminoMasCorto.add(verticePredecesor);
            nroVerticeDestino = nroPredecesor;
        }

        // Invertir la lista para obtener el orden correcto (origen -> destino)
        Collections.reverse(this.listaCaminoMasCorto);
        return this.listaCaminoMasCorto;
    }

}
