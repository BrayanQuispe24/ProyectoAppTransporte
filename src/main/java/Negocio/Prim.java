/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class Prim<G extends Comparable<G>> {

    private GrafoPesado grafoOriginal;
    private GrafoPesado grafoAux;
    private ControlMarcados control;
    private List<Arista> listaAristas;
    //List<List<AdyacenteConPeso>> listaDeAdyacentes;
    private final int cantidadVertices;
    private double pesoTotal;

    public Prim(GrafoPesado unGrafo) {
        this.grafoOriginal = unGrafo;
        this.grafoAux = new GrafoPesado();
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        //this.listaDeAdyacentes=new LinkedList<>();
        this.control = new ControlMarcados(cantidadVertices);
        this.listaAristas = new LinkedList<>();
        this.pesoTotal = 0;
        this.ejecutarPrism();
    }

    private void ejecutarPrism() {
        List<G> listaVertices = (List<G>) this.grafoOriginal.getVertices();
        G verticeInicial = listaVertices.get(0);
        this.grafoAux.insertarVertice(verticeInicial);
        this.obtenerAristasVertice(verticeInicial);
        this.control.marcarVertice(this.grafoAux.nroVertice(verticeInicial));
        while (!control.estanTodosLosVerticesMarcados()) {
            Arista aristaActual = this.obtenerAristaDeMenorPeso();
            if (!control.estaMarcadoVertice(aristaActual.nroVerticeDestino)) {
                G verticeOrigen = listaVertices.get(aristaActual.getNroVerticeOrigen());
                G verticeAInsertar = listaVertices.get(aristaActual.getNroVerticeDestino());
                this.grafoAux.insertarVertice(verticeAInsertar);
                this.grafoAux.insertarArista(verticeOrigen, verticeAInsertar, aristaActual.getPeso());
                this.pesoTotal += aristaActual.getPeso();
                this.obtenerAristasVertice(verticeAInsertar);
                this.control.marcarVertice(aristaActual.getNroVerticeDestino());
            }
        }
    }

    private void obtenerAristasVertice(G verticeActual) {
        int nroVerticeActual = this.grafoOriginal.nroVertice(verticeActual);
        List<AdyacenteConPeso> adyacentes = (List<AdyacenteConPeso>) grafoOriginal.listaDeAdyacencias.get(nroVerticeActual);
        for (AdyacenteConPeso adyacenteActual : adyacentes) {
            if (!control.estaMarcadoVertice(adyacenteActual.getNroVertice())) {
                Arista aristaActual = new Arista(nroVerticeActual, adyacenteActual.getNroVertice(),
                        adyacenteActual.getPeso());
                this.listaAristas.add(aristaActual);
            }
        }
        Collections.sort(listaAristas);
    }

    private Arista obtenerAristaDeMenorPeso() {
        Arista aristaDeMenorPeso = this.listaAristas.removeFirst();
        return aristaDeMenorPeso;
    }

    public GrafoPesado obtenerGrafoResultado() {
        return this.grafoAux;
    }

    public double obtenerPesoTotal() {
        return this.pesoTotal;
    }
}
