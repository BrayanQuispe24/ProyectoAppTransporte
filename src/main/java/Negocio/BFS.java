/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class BFS<G extends Comparable<G>> {

    private final GrafoPesado<G> elGrafo;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

    public BFS(GrafoPesado<G> unGrafo, G verticeDePartida) {
        elGrafo = unGrafo;
        elGrafo.validarVertice(verticeDePartida);
        controlMarcados = new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido = new ArrayList<>();
        this.ejecutarBFS(verticeDePartida);
    }

    private void ejecutarBFS(G verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        Queue<G> colaDeVertices = new LinkedList<>();
        colaDeVertices.add(verticeEnTurno);
        controlMarcados.marcarVertice(elGrafo.nroVertice(verticeEnTurno));
        while (!colaDeVertices.isEmpty()) {
            G vertice = colaDeVertices.poll();
            recorrido.add(vertice);
            Iterable<G> adyacentesDelVertice = elGrafo.getAdyacentesDeVertice(vertice);
            for (G adyacente : adyacentesDelVertice) {
                int nroDelAdyacente = elGrafo.nroVertice(adyacente);
                if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                    colaDeVertices.add(adyacente);
                    controlMarcados.marcarVertice(nroDelAdyacente);
                }
            }
        }
    }

    public List<G> getRecorrido() {
        return recorrido;
    }

    public boolean seVisitoVertice(G vertice) {
        elGrafo.validarVertice(vertice);
        int nroVertice = elGrafo.nroVertice(vertice);
        return controlMarcados.estaMarcadoVertice(nroVertice);
    }

    public boolean seVisitoTodosLosVertice() {
        return controlMarcados.estanTodosLosVerticesMarcados();
    }
//probar metodo
    public int cantidadDeIslas() {
        int cantidad = 1;
        for (G verticeActual : elGrafo.getVertices()) {
            if (!this.seVisitoVertice(verticeActual)) {
                cantidad++;
                this.ejecutarBFS(verticeActual);
            }
        }
        return cantidad;
    }
    

}
