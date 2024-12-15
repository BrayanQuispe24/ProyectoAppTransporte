/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.List;

/**
 *
 * @author BRAYAN
 */
public class HayCicloDFS<G extends Comparable<G>> {

    private GrafoPesado<G> unGrafo;
    private ControlMarcados control;
    private int cantidadVertices;
    private int[] padres;
    private boolean ciclo;

    public HayCicloDFS(GrafoPesado<G> grafo, G verticeInicial) {
        this.unGrafo = grafo;
        this.cantidadVertices = grafo.cantidadDeVertices();
        this.control = new ControlMarcados(this.cantidadVertices);
        this.padres = new int[this.cantidadVertices];
        this.iniciarVectorPadres(this.cantidadVertices);
        this.ciclo = this.detectarCiclo(verticeInicial);
    }

    private boolean detectarCiclo(G verticeInicial) {
        int nroVertice = this.unGrafo.nroVertice(verticeInicial);
        control.marcarVertice(nroVertice);
        List<G> adyacentes = (List<G>) this.unGrafo.getAdyacentesDeVertice(verticeInicial);
        for (G adyacente : adyacentes) {
            int nroAdyacente = this.unGrafo.nroVertice(adyacente);
            if (!control.estaMarcadoVertice(nroAdyacente)) {
                this.padres[nroAdyacente] = nroVertice;
                return this.detectarCiclo(adyacente);
            } else if (this.padres[nroVertice] != nroAdyacente) {
                return true;
            }
        }
        return false;
    }

    private void iniciarVectorPadres(int cantidadVertices) {
        for (int i = 0; i < cantidadVertices; i++) {
            this.padres[i] = -1;
        }
    }

    public boolean hayCiclo() {
        return this.ciclo;
    }
}
