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
public class Prim2<G extends Comparable<G>> {

    private GrafoPesado grafoOriginal;
    private GrafoPesado grafoAux;
    private List<G> vertices;
    private List<Arista> listaAristas;
    private ControlMarcados control;
    private double costoTotal;

    public Prim2(GrafoPesado unGrafo) {
        this.costoTotal = 0;
        this.grafoOriginal = unGrafo;
        this.grafoAux = new GrafoPesado();
        this.vertices = (List<G>) unGrafo.getVertices();
        this.listaAristas = new ArrayList<>();
        this.control = new ControlMarcados(unGrafo.cantidadDeVertices());
        this.ejecutarPrim2();
    }

    private void ejecutarPrim2() {
        G verticeInicial = this.vertices.get(0);
        int nroVerticeI = this.grafoOriginal.nroVertice(verticeInicial);
        this.grafoAux.insertarVertice(verticeInicial);
        control.marcarVertice(nroVerticeI);
        this.obtenerAristas(verticeInicial);
        while (!this.control.estanTodosLosVerticesMarcados()) {
            Arista aristaMenorCosto = this.obtenerAristaMenorCosto();
            int nroVerticeOrigen = aristaMenorCosto.getNroVerticeOrigen();
            int nroVerticeDestino = aristaMenorCosto.getNroVerticeDestino();
            if (!control.estaMarcadoVertice(nroVerticeDestino)) {
                G verticeOrigen = this.vertices.get(nroVerticeOrigen);
                G verticeAInsertar = this.vertices.get(nroVerticeDestino);
                double peso = aristaMenorCosto.getPeso();
                this.grafoAux.insertarVertice(verticeAInsertar);
                this.grafoAux.insertarArista(verticeOrigen, verticeAInsertar, peso);
                this.costoTotal += peso;
                this.control.marcarVertice(nroVerticeDestino);
                this.obtenerAristas(verticeAInsertar);

            }
        }

    }

    private void obtenerAristas(G verticeInicial) {
        int nroVerticeInicial = this.grafoOriginal.nroVertice(verticeInicial);
        List<G> adyacentes = (List<G>) this.grafoOriginal.getAdyacentesDeVertice(verticeInicial);
        for (G adyacente : adyacentes) {
            int nroAdyacente = this.grafoOriginal.nroVertice(adyacente);
            if (!control.estaMarcadoVertice(nroAdyacente)) {
                double peso = this.grafoOriginal.getPesoArista(verticeInicial, adyacente);
                Arista nuevaArista = new Arista(nroVerticeInicial, nroAdyacente, peso);
                this.listaAristas.add(nuevaArista);
            }
        }
        Collections.sort(this.listaAristas);
    }

    private Arista obtenerAristaMenorCosto() {
        return this.listaAristas.removeFirst();
    }

    @Override
    public String toString() {
        return this.grafoAux.toString();
    }

}
