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
import java.util.List;

/**
 *
 * @author BRAYAN
 */
public class Prim1<G extends Comparable<G>> {

    private GrafoPesado grafoOriginal;
    private GrafoPesado grafoAux;
    private ControlMarcados control;
    private List<Arista> listaArista;
    private List<G> listaVertice;
    private int cantidadVertices;
    private double pesoTotal;

    public Prim1(GrafoPesado unGrafo) {
        this.grafoOriginal = unGrafo;
        this.grafoAux = new GrafoPesado();
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.control = new ControlMarcados(this.cantidadVertices);
        this.listaArista = new ArrayList<>();
        this.listaVertice = (List<G>) unGrafo.getVertices();
        this.pesoTotal = 0;
        this.ejecutarPrim1();
    }

    private void ejecutarPrim1() {
        G verticeInicial = this.listaVertice.get(0);
        int nroVerticeInicial = this.grafoOriginal.nroVertice(verticeInicial);
        this.grafoAux.insertarVertice(verticeInicial);
        this.obtenerAristas(verticeInicial);
        control.marcarVertice(nroVerticeInicial);
        while (!control.estanTodosLosVerticesMarcados()) {
            Arista menorPeso = this.obtenerAristaMenorPeso();
            if (!control.estaMarcadoVertice(menorPeso.getNroVerticeDestino())) {
                G verticeOrigen = this.listaVertice.get(menorPeso.getNroVerticeOrigen());
                G verticeDestino = this.listaVertice.get(menorPeso.getNroVerticeDestino());
                double peso = menorPeso.getPeso();
                //insertamos vertice
                this.grafoAux.insertarVertice(verticeDestino);
                //insertamos arista
                this.grafoAux.insertarArista(verticeOrigen, verticeDestino, peso);
                this.pesoTotal += peso;
                this.obtenerAristas(verticeDestino);
                control.marcarVertice(menorPeso.getNroVerticeDestino());
            }
        }
    }

    private void obtenerAristas(G verticeActual) {
        int nroVerticeActual = this.grafoOriginal.nroVertice(verticeActual);
        List<G> adyacentes = (List<G>) this.grafoOriginal.getAdyacentesDeVertice(verticeActual);
        for (G adyacente : adyacentes) {
            int nroAdyacente = this.grafoOriginal.nroVertice(adyacente);
            if (!control.estaMarcadoVertice(nroAdyacente)) {
                double peso = this.grafoOriginal.getPesoArista(verticeActual, adyacente);
                Arista nuevaArista = new Arista(nroVerticeActual, nroAdyacente, peso);
                this.listaArista.add(nuevaArista);
            }
        }
        Collections.sort(this.listaArista);
    }

    private Arista obtenerAristaMenorPeso() {
        Arista menorPeso = this.listaArista.removeFirst();
        return menorPeso;
    }

    public GrafoPesado obtenerGrafoResultado() {
        return this.grafoAux;
    }

    public double obtenerPesoTotal() {
        return this.pesoTotal;
    }
}
