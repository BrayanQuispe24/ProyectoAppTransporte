/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class GrafoPesado<G extends Comparable<G>> {

    protected List<G> listaDeVertices;
    protected List<List<AdyacenteConPeso>> listaDeAdyacencias;
    public static final int NRO_DE_VERTICE_INVALIDO = -1;

    //constructor sin parametros
    public GrafoPesado() {
        this.listaDeVertices = new ArrayList<>();
        this.listaDeAdyacencias = new ArrayList<>();
    }

    public GrafoPesado(Iterable<G> vertices) {
        this();
        for (G vertice : vertices) {
            this.insertarVertice(vertice);
        }
    }

    public int nroVertice(G vertice) {
        for (int i = 0; i < listaDeVertices.size(); i++) {
            G verticeEnTurno = listaDeVertices.get(i);
            if (vertice.compareTo(verticeEnTurno) == 0) {
                return i;
            }
        }
        return NRO_DE_VERTICE_INVALIDO;
    }

    public void validarVertice(G vertice) {
        int nroDelVertice = nroVertice(vertice);
        if (nroDelVertice == NRO_DE_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("Vertice no pertenece al grafo");
        }
    }

    public void insertarVertice(G vertice) {
        if (this.nroVertice(vertice) != NRO_DE_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("Vertice ya pertenece al grafo");
        }
        listaDeVertices.add(vertice);
        listaDeAdyacencias.add(new ArrayList<>());
    }

    public int cantidadDeVertices() {
        return listaDeVertices.size();
    }

    public boolean existeAdyacencia(G verticeOrigen, G verticeDestino) {
        this.validarVertice(verticeOrigen);
        this.validarVertice(verticeDestino);
        int nroDelVerticeOrigen = this.nroVertice(verticeOrigen);
        int nroDelVerticeDestino = this.nroVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesOrigen = this.listaDeAdyacencias.get(nroDelVerticeOrigen);
        AdyacenteConPeso adyacenteDestino = new AdyacenteConPeso(nroDelVerticeDestino);
        return adyacentesOrigen.contains(adyacenteDestino);
    }

    public void insertarArista(G verticeOrigen, G verticeDestino, double peso) {
        if (existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int nroDelVerticeOrigen = nroVertice(verticeOrigen);
        int nroDelVerticeDestino = nroVertice(verticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = listaDeAdyacencias.get(nroDelVerticeOrigen);
        AdyacenteConPeso adyacenteDestino = new AdyacenteConPeso(nroDelVerticeDestino, peso);
        adyacentesDelOrigen.add(adyacenteDestino);
        Collections.sort(adyacentesDelOrigen);
        if (nroDelVerticeOrigen != nroDelVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = listaDeAdyacencias.get(nroDelVerticeDestino);
            AdyacenteConPeso adyacenteOrigen = new AdyacenteConPeso(nroDelVerticeOrigen, peso);
            adyacentesDelDestino.add(adyacenteOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public int cantidadDeAristas() {
        int cantidad = 0;
        for (List<AdyacenteConPeso> listaDeAdyacentes : listaDeAdyacencias) {
            cantidad = cantidad + listaDeAdyacentes.size();
        }
        return cantidad / 2;
    }

    public Iterable<G> getVertices() {
        return listaDeVertices;
    }

    public Iterable<G> getAdyacentesDeVertice(G vertice) {
        this.validarVertice(vertice);
        int nroDelVertice = nroVertice(vertice);
        List<AdyacenteConPeso> adyacentesDelVerticeXNro = listaDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdyacentesDelVertice = new ArrayList<>();
        for (AdyacenteConPeso adyacenteEnTurno : adyacentesDelVerticeXNro) {
            listaDeAdyacentesDelVertice.add(listaDeVertices.get(adyacenteEnTurno.getNroVertice()));
        }
        return listaDeAdyacentesDelVertice;
    }

    public void eliminarVertice(G vertice) {
        validarVertice(vertice);
        int nroDelVertice = nroVertice(vertice);
        listaDeVertices.remove(nroDelVertice);
        listaDeAdyacencias.remove(nroDelVertice);
        AdyacenteConPeso verticeEliminar = new AdyacenteConPeso(nroDelVertice);
        for (List<AdyacenteConPeso> adyacentesDeunVertice : listaDeAdyacencias) {
            adyacentesDeunVertice.remove(verticeEliminar);
            for (int i = 0; i < adyacentesDeunVertice.size(); i++) {
                AdyacenteConPeso adyacenteActual = adyacentesDeunVertice.get(i);
                int nroAdyacenteEnTurno = adyacenteActual.getNroVertice();
                if (nroAdyacenteEnTurno > nroDelVertice) {
                    nroAdyacenteEnTurno--;
                    adyacenteActual.setNroVertice(nroAdyacenteEnTurno);
                    adyacentesDeunVertice.set(i, adyacenteActual);
                }
            }
        }
    }

    public void eliminarArista(G verticeOrigen, G verticeDestino) {
        // Verifica si existe la adyacencia antes de proceder
        if (!existeAdyacencia(verticeOrigen, verticeDestino)) {
            throw new IllegalArgumentException("La arista no existe en el grafo");
        }

        int nroOrigen = nroVertice(verticeOrigen);
        int nroDestino = nroVertice(verticeDestino);
        AdyacenteConPeso AdyacenteOrigen = new AdyacenteConPeso(nroOrigen);
        AdyacenteConPeso AdyacenteDestino = new AdyacenteConPeso(nroDestino);

        // Eliminar la arista de la lista de adyacencias de verticeOrigen
        List<AdyacenteConPeso> listaAdyacentesOrigen = listaDeAdyacencias.get(nroOrigen);
        listaAdyacentesOrigen.remove(AdyacenteDestino); // Eliminar por valor, no por índice

        // Eliminar la arista de la lista de adyacencias de verticeDestino
        List<AdyacenteConPeso> listaAdyacentesDestino = listaDeAdyacencias.get(nroDestino);
        listaAdyacentesDestino.remove(AdyacenteOrigen); // Eliminar por valor, no por índice
    }

    public int gradoDelVertice(G vertice) {
        validarVertice(vertice);
        int nroDelVertice = nroVertice(vertice);
        List<AdyacenteConPeso> adyacentesDelVertice = listaDeAdyacencias.get(nroDelVertice);
        return adyacentesDelVertice.size();
    }

    public GrafoPesado<G> obtenerGrafoTranspuesto() {
        // Crear un nuevo grafo vacío
        GrafoPesado<G> grafoTranspuesto = new GrafoPesado<>();

        // Agregar todos los vértices al nuevo grafo
        for (G vertice : listaDeVertices) {
            grafoTranspuesto.insertarVertice(vertice);
        }

        // Recorrer las listas de adyacencia para invertir las aristas
        for (int i = 0; i < listaDeAdyacencias.size(); i++) {
            G verticeOrigen = listaDeVertices.get(i);
            List<AdyacenteConPeso> adyacentes = listaDeAdyacencias.get(i);
            for (AdyacenteConPeso indiceDestino : adyacentes) {
                G verticeDestino = listaDeVertices.get(indiceDestino.getNroVertice());
                // Insertar la arista invertida en el grafo transpuesto
                grafoTranspuesto.insertarArista(verticeDestino, verticeOrigen, indiceDestino.getPeso());
            }
        }

        return grafoTranspuesto;
    }

    //vamos a implementar un metodo que me devuelva el peso de una arista 
    public double getPesoArista(G verticeorigen, G verticeDestino) {
        double pesoArista = -1;
        int nroVerticeOrigen = this.nroVertice(verticeorigen);
        int nroVerticeDestino = this.nroVertice(verticeDestino);
        AdyacenteConPeso AdycenteDestino = new AdyacenteConPeso(nroVerticeDestino);
        List<AdyacenteConPeso> adyacentesOrigen = this.listaDeAdyacencias.get(nroVerticeOrigen);
        for (AdyacenteConPeso adyacenteActual : adyacentesOrigen) {
            if (adyacenteActual.compareTo(AdycenteDestino) == 0) {
                pesoArista = adyacenteActual.getPeso();
            }
        }
        return pesoArista;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mapa de Bolivia:\n");
        for (int i = 0; i < listaDeVertices.size(); i++) {
            G vertice = listaDeVertices.get(i);
            List<AdyacenteConPeso> adyacentes = listaDeAdyacencias.get(i);
            for (AdyacenteConPeso adyacente : adyacentes) {
                G destino = listaDeVertices.get(adyacente.getNroVertice());
                builder.append("Origen=>").append(vertice)
                        .append(" ---->")
                        .append(adyacente.getPeso())
                        .append(" ---->Destino=>")
                        .append(destino).append("\n");
            }
        }
        return builder.toString();
    }

}
