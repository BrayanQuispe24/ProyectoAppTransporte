/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class Kruskal<G extends Comparable<G>> {

    private GrafoPesado grafoAux;
    private GrafoPesado grafoOriginal;
    private final List<G> listaVertices;
    private List<Arista> listaDeAristas;

    public Kruskal(GrafoPesado unGrafo) {
        this.grafoOriginal = unGrafo;
        this.listaVertices = (List<G>) grafoOriginal.getVertices();
        this.grafoAux = new GrafoPesado(this.listaVertices);
        this.listaDeAristas = new ArrayList<>();
        this.copiarAristas();
        this.ejecutarKruskal();
    }

    private void ejecutarKruskal() {
        for (Arista aristaActual : this.listaDeAristas) {
            G verticeOrigen = (G) this.grafoAux.listaDeVertices.get(aristaActual.getNroVerticeOrigen());
            G verticeDestino = (G) this.grafoAux.listaDeVertices.get(aristaActual.getNroVerticeDestino());
            double peso = aristaActual.getPeso();
            this.grafoAux.insertarArista(verticeOrigen, verticeDestino, peso);
            if (this.hayCiclo(verticeOrigen)) {
                grafoAux.eliminarArista(verticeOrigen, verticeDestino);
            }
        }
    }

    private void copiarAristas() {
        int cantidadVertices = this.grafoOriginal.cantidadDeVertices();
        ControlMarcados control = new ControlMarcados(cantidadVertices);
        for (int i = 0; i < cantidadVertices; i++) {
            control.marcarVertice(i);
            List<AdyacenteConPeso> listaActual = (List<AdyacenteConPeso>) this.grafoOriginal.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso AdyacenteActual : listaActual) {
                if (!control.estaMarcadoVertice(AdyacenteActual.getNroVertice())) {
                    Arista nuevaArista = new Arista(i, AdyacenteActual.getNroVertice(), AdyacenteActual.getPeso());
                    this.listaDeAristas.add(nuevaArista);
                }
            }
        }
        Collections.sort(listaDeAristas);
    }

    public boolean hayCiclo(G verticeOrigen) {
        ControlMarcados control = new ControlMarcados(this.grafoOriginal.cantidadDeVertices());
        Queue<G> colaDeVertices = new LinkedList<>();
        int[] padres = new int[this.grafoOriginal.cantidadDeVertices()]; // Arreglo para padres

        for (int i = 0; i < padres.length; i++) {
            padres[i] = -1; // Inicializar los padres como -1 (sin padre)
        }

        colaDeVertices.offer(verticeOrigen);
        control.marcarVertice(grafoAux.nroVertice(verticeOrigen));

        while (!colaDeVertices.isEmpty()) {
            G verticeAux = colaDeVertices.poll();
            int nroVerticeActual = this.grafoAux.nroVertice(verticeAux);
            List<G> adyacentes = (List<G>) this.grafoAux.getAdyacentesDeVertice(verticeAux);

            for (G adyacenteActual : adyacentes) {
                int nroVerticeAdyacente = this.grafoAux.nroVertice(adyacenteActual);

                if (!control.estaMarcadoVertice(nroVerticeAdyacente)) {
                    colaDeVertices.offer(adyacenteActual);
                    control.marcarVertice(nroVerticeAdyacente);
                    padres[nroVerticeAdyacente] = nroVerticeActual; // Registrar el padre
                } else if (padres[nroVerticeActual] != nroVerticeAdyacente) {
                    // Detectar ciclo: vecino visitado que no es el padre
                    return true;
                }
            }
        }

        return false;
    }

    public String mostrarListaAristas() {
        String o = "";
        for (Arista aristaActual : this.listaDeAristas) {
            o += "|" + aristaActual.getNroVerticeOrigen() + ","
                    + aristaActual.getNroVerticeDestino() + ","
                    + aristaActual.getPeso() + "|" + "  ";
        }
        return o;
    }
    public GrafoPesado<G> obtenerGrafoResultado(){
     return this.grafoAux;
    }

    @Override
    public String toString() {
        return grafoAux.toString();
    }

}
