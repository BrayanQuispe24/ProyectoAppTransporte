/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class DFS<G extends Comparable<G>> {

    private final GrafoPesado<G> elGrafo;
    private final ControlMarcados controlMarcados;
    private final List<G> recorrido;

    public DFS(GrafoPesado<G> unGrafo, G verticeDePartida) {
        elGrafo = unGrafo;
        elGrafo.validarVertice(verticeDePartida);
        controlMarcados = new ControlMarcados(elGrafo.cantidadDeVertices());
        recorrido = new ArrayList<>();
        ejecutarDFS(verticeDePartida);
    }

    private void ejecutarDFS(G verticeEnTurno) {
        elGrafo.validarVertice(verticeEnTurno);
        controlMarcados.marcarVertice(elGrafo.nroVertice(verticeEnTurno));
        recorrido.add(verticeEnTurno);
        Iterable<G> adyacentesDelVertice = elGrafo.getAdyacentesDeVertice(verticeEnTurno);
        for (G adyacente : adyacentesDelVertice) {
            int nroDelAdyacente = elGrafo.nroVertice(adyacente);
            if (!controlMarcados.estaMarcadoVertice(nroDelAdyacente)) {
                ejecutarDFS(adyacente);
            }
        }
    }

    public List<G> obtenerRecorrido() {
        return recorrido;
    }
}
