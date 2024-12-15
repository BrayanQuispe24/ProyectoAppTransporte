/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Utileria.ControlMarcados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class Dijkstra<G extends Comparable<G>> {
    
    private GrafoPesado unGrafoPesado;//con este grafo vamos a trabajar
    private ControlMarcados vectorControl;//con este arreglo marcaremos los vertices
    public List<Double> vectorCostos;// este sera el vector de costos
    public List<Integer> predecesores;//estructura para guardar los predecesores de un vertice
    private double CostoCaminoMasCorto = -1;
    public List<G> CaminoMasCorto;
    private final int cantidadVertices;
    
    public Dijkstra(GrafoPesado unGrafo, G verticeOrigen, G verticeDestino) {
        this.unGrafoPesado = unGrafo;
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.vectorControl = new ControlMarcados(unGrafo.cantidadDeVertices());
        this.CaminoMasCorto = new ArrayList<>();
        this.iniciarVectorCostos(verticeOrigen);
        this.iniciarVectorPredecesores();
        this.ejecutarDijkstra(verticeOrigen, verticeDestino);
        this.construirCaminoMasCorto(verticeDestino);
    }
    
    private void ejecutarDijkstra(G verticeOrigen, G verticeDestino) {
        int nroVerticeDestino = this.unGrafoPesado.nroVertice(verticeDestino); 
        while (!vectorControl.estaMarcadoVertice(nroVerticeDestino)) {
            int indiceDelVerticeMenorCosto = this.obetenerVerticeDeMenorCosto();
            if (indiceDelVerticeMenorCosto != -1) {
                this.vectorControl.marcarVertice(indiceDelVerticeMenorCosto);
                List<AdyacenteConPeso> adyacentes = (List<AdyacenteConPeso>) this.unGrafoPesado.listaDeAdyacencias.get(indiceDelVerticeMenorCosto);
                for (AdyacenteConPeso adyacente : adyacentes) {
                    if (!vectorControl.estaMarcadoVertice(adyacente.getNroVertice())) {
                        G verticeMenorCosto = (G) this.unGrafoPesado.listaDeVertices.get(indiceDelVerticeMenorCosto);
                        G verticeAdyacente = (G) this.unGrafoPesado.listaDeVertices.get(adyacente.getNroVertice());
                        double pesoArista = this.unGrafoPesado.getPesoArista(verticeMenorCosto, verticeAdyacente);
                        double costoAdyacente = this.vectorCostos.get(adyacente.getNroVertice());
                        double costoVerticeMenorCosto = this.vectorCostos.get(indiceDelVerticeMenorCosto);
                        if (costoAdyacente > (costoVerticeMenorCosto + pesoArista)) {
                            this.vectorCostos.set(adyacente.getNroVertice(), costoVerticeMenorCosto + pesoArista);
                            this.predecesores.set(adyacente.getNroVertice(), indiceDelVerticeMenorCosto);  
                        }
                    }
                }
            } else {
                System.out.println("No se puede llegar al destino");
                this.CostoCaminoMasCorto = -1;
                return;
            }
            this.CostoCaminoMasCorto = this.vectorCostos.get(nroVerticeDestino);
        }
    }
    
    public double getCostoCaminoMasCorto() {
        return CostoCaminoMasCorto;
    }
    
    public List<G> getCaminoMasCorto() {
        return CaminoMasCorto;
    }
    
    private void iniciarVectorCostos(G verticeOrigen) {
        this.vectorCostos = new ArrayList<>();
        int nroVerticeorigen = unGrafoPesado.nroVertice(verticeOrigen);
        for (int i = 0; i < this.cantidadVertices; i++) {
            if (i == nroVerticeorigen) {
                vectorCostos.add((double) 0);
            } else {
                vectorCostos.add(Double.MAX_VALUE);
            }
            
        }
    }
    
    private void iniciarVectorPredecesores() {
        this.predecesores = new ArrayList<>();
        for (int i = 0; i < this.cantidadVertices; i++) {
            this.predecesores.add(-1);
        }
    }

    //Vamos a devolver el numero de vertice de menor costo y no marcado, en el aso de que el menor este marcado devolveremos -1
    private int obetenerVerticeDeMenorCosto() {
        double menor = Double.MAX_VALUE;
        int indice = -1;
        for (int i = 0; i < this.vectorCostos.size(); i++) {
            if ((this.vectorCostos.get(i) < menor) && (!vectorControl.estaMarcadoVertice(i))) {
                menor = this.vectorCostos.get(i);
                indice = i;
            }
        }
        return indice;
    }
    
    public void construirCaminoMasCorto(G verticeDestino) {
        
        int direccion = this.unGrafoPesado.nroVertice(verticeDestino);
        
        if (this.vectorCostos.get(direccion) == Double.MAX_VALUE) {
            System.out.println("No hay camino desde el origen al destino.");
            this.CaminoMasCorto = null;
            return;
        }
        
        while (direccion != -1) {
            this.CaminoMasCorto.add((G) this.unGrafoPesado.listaDeVertices.get(direccion));
            direccion = this.predecesores.get(direccion);
        }

        // Invertir la lista para obtener el camino en orden correcto
        Collections.reverse(this.CaminoMasCorto);
    }
    
}
