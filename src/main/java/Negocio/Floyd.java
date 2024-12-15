package Negocio;

import java.util.ArrayList;
import java.util.List;


public class Floyd<G extends Comparable<G>> {

    private GrafoPesado<G> unGrafo;
    private double[][] matrizDePesos;
    private int[][] matrizDePredecesores;
    private final int cantidadVertices;

    public Floyd(GrafoPesado<G> unGrafo) {
        this.unGrafo = unGrafo;
        this.cantidadVertices = unGrafo.cantidadDeVertices();
        this.matrizDePesos = new double[cantidadVertices][cantidadVertices];
        this.matrizDePredecesores = new int[cantidadVertices][cantidadVertices];
        this.llenarMatrizPesosValoresIniciales();
        this.iniciarMatrizPesos();
        this.iniciarMatrizDePredecesores();
        this.ejecutarFloyd();
    }

    private void llenarMatrizPesosValoresIniciales() {
        for (int i = 0; i < cantidadVertices; i++) {
            for (int j = 0; j < cantidadVertices; j++) {
                if (i != j) {
                    this.matrizDePesos[i][j] = Double.MAX_VALUE; // Valor infinito si no hay conexión
                } else {
                    this.matrizDePesos[i][j] = 0; // Costo cero para ir a sí mismo
                }
            }
        }
    }

    private void iniciarMatrizPesos() {
        for (int i = 0; i < cantidadVertices; i++) {
            List<AdyacenteConPeso> adyacentes = this.unGrafo.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso adyacenteActual : adyacentes) {
                int nroVerticeAdyacente = adyacenteActual.getNroVertice();
                this.matrizDePesos[i][nroVerticeAdyacente] = adyacenteActual.getPeso();
            }
        }
    }

    private void iniciarMatrizDePredecesores() {
        for (int i = 0; i < cantidadVertices; i++) {
            for (int j = 0; j < cantidadVertices; j++) {
                if (i != j && this.matrizDePesos[i][j] != Double.MAX_VALUE) {
                    this.matrizDePredecesores[i][j] = i; // El predecesor inicial es el origen
                } else {
                    this.matrizDePredecesores[i][j] = -1; // No hay predecesor
                }
            }
        }
    }

    private void ejecutarFloyd() {
        for (int k = 0; k < cantidadVertices; k++) {
            for (int i = 0; i < cantidadVertices; i++) {
                for (int j = 0; j < cantidadVertices; j++) {
                    if (i != j) {
                        if (this.matrizDePesos[i][k] != Double.MAX_VALUE && this.matrizDePesos[k][j] != Double.MAX_VALUE) {
                            if (this.matrizDePesos[i][j] > this.matrizDePesos[i][k] + this.matrizDePesos[k][j]) {
                                this.matrizDePesos[i][j] = this.matrizDePesos[i][k] + this.matrizDePesos[k][j];
                                this.matrizDePredecesores[i][j] = this.matrizDePredecesores[k][j];
                            }
                        }
                    }
                }
            }
        }
    }

    public double obtenerCaminoCostoMinimo(G verticeInicio, G verticeDestino) {
        int nroVerticeInicio = this.unGrafo.nroVertice(verticeInicio);
        int nroVerticeDestino = this.unGrafo.nroVertice(verticeDestino);
        return this.matrizDePesos[nroVerticeInicio][nroVerticeDestino];
    }

    public List<G> obtenerRecorrido(G origen, G destino) {
        int indiceOrigen = this.unGrafo.nroVertice(origen);
        int indiceDestino = this.unGrafo.nroVertice(destino);

        if (indiceOrigen == -1 || indiceDestino == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        if (this.matrizDePesos[indiceOrigen][indiceDestino] == Double.MAX_VALUE) {
            return new ArrayList<>(); // Si no hay camino, retorna lista vacía
        }

        List<Integer> caminoIndices = new ArrayList<>();
        reconstruirCamino(indiceOrigen, indiceDestino, caminoIndices);

        List<G> caminoVertices = new ArrayList<>();
        for (int indice : caminoIndices) {
            caminoVertices.add(this.unGrafo.listaDeVertices.get(indice));
        }

        return caminoVertices;
    }

    private void reconstruirCamino(int origen, int destino, List<Integer> camino) {
        if (origen == destino) {
            camino.add(origen);
        } else {
            int predecesor = this.matrizDePredecesores[origen][destino];

            if (predecesor == -1) {
                throw new IllegalStateException("No hay un camino válido entre los vértices proporcionados.");
            }

            reconstruirCamino(origen, predecesor, camino);
            camino.add(destino);
        }
    }

    public void imprimirMatrizPesos() {
        System.out.println("Matriz de Pesos:");
        for (int i = 0; i < matrizDePesos.length; i++) {
            for (int j = 0; j < matrizDePesos[i].length; j++) {
                if (matrizDePesos[i][j] == Double.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.printf("%.2f ", matrizDePesos[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void imprimirMatrizPredecesores() {
        System.out.println("Matriz de Predecesores:");
        for (int i = 0; i < matrizDePredecesores.length; i++) {
            for (int j = 0; j < matrizDePredecesores[i].length; j++) {
                System.out.print(matrizDePredecesores[i][j] + " ");
            }
            System.out.println();
        }
    }
}
