/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica;

import Negocio.AdyacenteConPeso;
import Negocio.Arista;
import Negocio.GrafoPesado;
import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class Kruskal<G extends Comparable<G>> {

    private GrafoPesado<G> grafoOriginal;
    private GrafoPesado<G> grafoAux;
    private List<Arista> listaAristas;
    private List<G> vertices;
    private int cantidadVertices;

    public Kruskal(GrafoPesado unGrafo) {
        this.grafoOriginal = unGrafo;
        this.listaAristas = new ArrayList<>();
        this.vertices = (List<G>) unGrafo.getVertices();
        this.grafoAux = new GrafoPesado(this.vertices);
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.obtenerAristas();
        this.ejecutarKruskal();
    }

    private void obtenerAristas() {
        ControlMarcados control = new ControlMarcados(this.grafoOriginal.cantidadDeVertices());
        for (int i = 0; i < this.cantidadVertices; i++) {
            control.marcarVertice(i);
            List<G> adyacentes = (List<G>) this.grafoOriginal.getAdyacentesDeVertice(this.vertices.get(i));
            for (G adyacente : adyacentes) {
                int nroAdyacente = this.grafoOriginal.nroVertice(adyacente);
                if (!control.estaMarcadoVertice(nroAdyacente)) {
                    double peso = this.grafoOriginal.getPesoArista(this.vertices.get(i), adyacente);
                    Arista nuevaArista = new Arista(i, nroAdyacente, peso);
                    listaAristas.add(nuevaArista);
                }
            }
        }
        Collections.sort(this.listaAristas);
    }

    public boolean hayCiclo(G verticeOrigen) {
        ControlMarcados control = new ControlMarcados(this.cantidadVertices);
        Queue<G> colaVertices = new LinkedList<>();
        int[] padres = new int[this.cantidadVertices];

        colaVertices.offer(verticeOrigen);
        control.marcarVertice(this.grafoAux.nroVertice(verticeOrigen));
        while (!colaVertices.isEmpty()) {
            G verticeActual = colaVertices.poll();
            int nroVerticeActual = this.grafoAux.nroVertice(verticeActual);
           List<G> adyacentes = (List<G>) this.grafoAux.getAdyacentesDeVertice(verticeActual);
            for (G adyacente : adyacentes) {
                int nroAdyacente = this.grafoAux.nroVertice(adyacente);
                if (!control.estaMarcadoVertice(nroAdyacente)) {
                    colaVertices.offer(adyacente);
                    control.marcarVertice(nroAdyacente);
                    padres[nroAdyacente] = nroVerticeActual;
                } else if (padres[nroVerticeActual] != nroAdyacente) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.grafoAux.toString();
    }

    private void ejecutarKruskal() {
        for (Arista aristaActual : this.listaAristas) {
            G verticeOrigen = this.vertices.get(aristaActual.getNroVerticeOrigen());
            G verticeDestino = this.vertices.get(aristaActual.getNroVerticeDestino());
            double peso = aristaActual.getPeso();
            this.grafoAux.insertarArista(verticeOrigen, verticeDestino, peso);
            if (this.hayCiclo(verticeOrigen)) {
                this.grafoAux.eliminarArista(verticeOrigen, verticeDestino);
            }
        }
    }
}
