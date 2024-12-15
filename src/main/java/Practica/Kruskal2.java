/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica;

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
 */
public class Kruskal2<G extends Comparable<G>> {

    private GrafoPesado grafoOrigen;
    private GrafoPesado grafoAux;
    private List<Arista> listaArista;
    private int cantidadVertices;
    private List<G> listaVertices;

    public Kruskal2(GrafoPesado unGrafo) {
        this.grafoOrigen = unGrafo;
        this.listaVertices = (List<G>) unGrafo.getVertices();
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.grafoAux = new GrafoPesado(this.listaVertices);
        this.obtenerAristas();
        this.ejecutarkruskal();
    }

    public void ejecutarkruskal() {
        for (Arista aristaActual : this.listaArista) {
            G verticeOrigen = this.listaVertices.get(aristaActual.getNroVerticeOrigen());
            G verticeDestino = this.listaVertices.get(aristaActual.getNroVerticeDestino());
            double peso = aristaActual.getPeso();
            this.grafoAux.insertarArista(verticeOrigen, verticeDestino, peso);
            if (this.hayCiclo(verticeOrigen)) {
                this.grafoAux.eliminarArista(verticeOrigen, verticeDestino);
                System.out.println("se elimino una arista" + verticeOrigen + verticeDestino);
            }
        }
    }

    private boolean hayCiclo(G verticeOrigen) {
        ControlMarcados control = new ControlMarcados(this.cantidadVertices);
        Queue<G> colaVertices = new LinkedList<>();
        int[] padres = new int[this.cantidadVertices];
        int nroOrigen = this.grafoAux.nroVertice(verticeOrigen);
        colaVertices.offer(verticeOrigen);
        control.marcarVertice(nroOrigen);
        while (!colaVertices.isEmpty()) {
            G verticeActual = colaVertices.poll();
            int nroVerticeActual = this.grafoAux.nroVertice(verticeActual);
            List<G> verticesAdyacentes = (List<G>) this.grafoAux.getAdyacentesDeVertice(verticeActual);
            for (G verticeAdyacente : verticesAdyacentes) {
                int nroAdyacente = this.grafoAux.nroVertice(verticeAdyacente);
                if (!control.estaMarcadoVertice(nroAdyacente)) {
                    colaVertices.offer(verticeAdyacente);
                    control.marcarVertice(nroAdyacente);
                    padres[nroAdyacente] = nroVerticeActual;
                } else if (padres[nroVerticeActual] != nroAdyacente) {
                    return true;
                }
            }
        }
        return false;
    }

    private void obtenerAristas() {
        this.listaArista = new ArrayList<>();
        //vamos a necesitar un controlMarcados
        ControlMarcados control = new ControlMarcados(this.cantidadVertices);
        for (int i = 0; i < this.cantidadVertices; i++) {
            G verticeActual = this.listaVertices.get(i);
            control.marcarVertice(i);
            List<G> adyacentes = (List<G>) this.grafoOrigen.getAdyacentesDeVertice(verticeActual);
            for (G adyacente : adyacentes) {
                int nroAdyacente = this.grafoOrigen.nroVertice(adyacente);
                if (!control.estaMarcadoVertice(nroAdyacente)) {
                    double peso = this.grafoOrigen.getPesoArista(verticeActual, adyacente);
                    Arista aristaActual = new Arista(i, nroAdyacente, peso);
                    this.listaArista.add(aristaActual);
                }
            }
        }
        Collections.sort(this.listaArista);
    }

    @Override
    public String toString() {
        return this.grafoAux.toString();
    }

}
