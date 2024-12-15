package Negocio;

import java.util.*;

public class FordFulkerson2<G extends Comparable<G>> {

    private static class DiGrafoPesadoFM<G extends Comparable<G>> extends DigrafoPesado<G> {

        public DiGrafoPesadoFM(Iterable<G> vertices) {
            super(vertices);
        }

        public void actualizarPesoArista(G origen, G destino, double nuevoPeso) {
            int nroOrigen = nroVertice(origen);
            int nroDestino = nroVertice(destino);

            List<AdyacenteConPeso> adyacentesOrigen = listaDeAdyacencias.get(nroOrigen);
            AdyacenteConPeso arista = new AdyacenteConPeso(nroDestino, 0);
            int indice = adyacentesOrigen.indexOf(arista);

            if (indice >= 0) {
                adyacentesOrigen.get(indice).setPeso(nuevoPeso);
            } else {
                adyacentesOrigen.add(new AdyacenteConPeso(nroDestino, nuevoPeso));
            }
        }
    }

    private final DiGrafoPesadoFM<G> grafoResidual;
    private double flujoMaximo;
    private final G fuente;
    private final G sumidero;

    // Para almacenar los caminos aumentadores
    private final List<List<G>> caminosAumentadores = new ArrayList<>();
    private final List<Double> flujosAumentadores = new ArrayList<>();

    public FordFulkerson2(DigrafoPesado<G> grafo, G fuente, G sumidero) {
        this.grafoResidual = new DiGrafoPesadoFM<>(grafo.getVertices());
        copiarGrafo(grafo);
        this.fuente = fuente;
        this.sumidero = sumidero;
        this.flujoMaximo = 0.0;
        ejecutarFordFulkerson();
    }

    private void copiarGrafo(DigrafoPesado<G> grafo) {
        for (G origen : grafo.getVertices()) {
            for (G destino : grafo.getAdyacentesDeVertice(origen)) {
                double peso = grafo.getPesoArista(origen, destino);
                grafoResidual.insertarArista(origen, destino, peso);
                grafoResidual.insertarArista(destino, origen, 0); // Arista residual inversa
            }
        }
    }

    private void ejecutarFordFulkerson() {
        List<G> camino = new ArrayList<>();

        while (existeCaminoAugmentador(fuente, sumidero, camino)) {
            double flujoCamino = calcularFlujoMinimo(camino);

            // Almacenar el camino y su flujo
            caminosAumentadores.add(new ArrayList<>(camino));
            flujosAumentadores.add(flujoCamino);

            // Actualizar las capacidades residuales en el grafo
            actualizarCapacidades(camino, flujoCamino);

            // Incrementar el flujo máximo
            flujoMaximo += flujoCamino;

            // Limpiar el camino para la próxima iteración
            camino.clear();
        }
    }

    private boolean existeCaminoAugmentador(G origen, G destino, List<G> camino) {
        Map<G, G> predecesores = new HashMap<>();
        Set<G> visitados = new HashSet<>();
        Queue<G> cola = new LinkedList<>();
        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            G actual = cola.poll();

            for (G adyacente : grafoResidual.getAdyacentesDeVertice(actual)) {
                if (!visitados.contains(adyacente) && grafoResidual.getPesoArista(actual, adyacente) > 0) {
                    predecesores.put(adyacente, actual);
                    visitados.add(adyacente);
                    cola.add(adyacente);

                    if (adyacente.equals(destino)) {
                        reconstruirCamino(predecesores, origen, destino, camino);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void reconstruirCamino(Map<G, G> predecesores, G origen, G destino, List<G> camino) {
        G actual = destino;
        while (!actual.equals(origen)) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }
        camino.add(0, origen);
    }

    private double calcularFlujoMinimo(List<G> camino) {
        double flujoMinimo = Double.MAX_VALUE;

        for (int i = 0; i < camino.size() - 1; i++) {
            G origen = camino.get(i);
            G destino = camino.get(i + 1);
            double peso = grafoResidual.getPesoArista(origen, destino);
            flujoMinimo = Math.min(flujoMinimo, peso);
        }

        return flujoMinimo;
    }

    private void actualizarCapacidades(List<G> camino, double flujoCamino) {
        for (int i = 0; i < camino.size() - 1; i++) {
            G origen = camino.get(i);
            G destino = camino.get(i + 1);

            // Reducir el flujo en la dirección directa
            double pesoActual = grafoResidual.getPesoArista(origen, destino);
            grafoResidual.actualizarPesoArista(origen, destino, pesoActual - flujoCamino);

            // Incrementar el flujo en la dirección inversa
            double pesoInverso = grafoResidual.getPesoArista(destino, origen);
            grafoResidual.actualizarPesoArista(destino, origen, pesoInverso + flujoCamino);
        }
    }

    public double getFlujoMaximo() {
        return flujoMaximo;
    }

    public String obtenerCaminosAumentadores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Caminos Aumentadores y sus Flujos:\n");
        for (int i = 0; i < caminosAumentadores.size(); i++) {
            sb.append("Camino ").append(i + 1).append(": ")
                    .append(caminosAumentadores.get(i))
                    .append(" | Flujo: ").append(flujosAumentadores.get(i))
                    .append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DigrafoPesado<Ciudad> grafoFlujos = new DigrafoPesado<>();

        // Definir vértices
        Ciudad SantaCruz = new Ciudad("Santa Cruz");
        Ciudad Beni = new Ciudad("Beni");
        Ciudad Cochabamba = new Ciudad("Cochabamba");
        Ciudad Chuquisaca = new Ciudad("Chuquisaca");
        Ciudad Pando = new Ciudad("Pando");
        Ciudad LaPaz = new Ciudad("La Paz");
        Ciudad Oruro = new Ciudad("Oruro");
        Ciudad Potosi = new Ciudad("Potosi");
        Ciudad Tarija = new Ciudad("Tarija");
        grafoFlujos.insertarVertice(Beni);
        grafoFlujos.insertarVertice(Cochabamba);
        grafoFlujos.insertarVertice(Chuquisaca);
        grafoFlujos.insertarVertice(Pando);
        grafoFlujos.insertarVertice(LaPaz);
        grafoFlujos.insertarVertice(SantaCruz);
        grafoFlujos.insertarVertice(Oruro);
        grafoFlujos.insertarVertice(Potosi);
        grafoFlujos.insertarVertice(Tarija);
        grafoFlujos.insertarArista(LaPaz, Pando, 1400);
        grafoFlujos.insertarArista(LaPaz, Beni, 680);
        grafoFlujos.insertarArista(LaPaz, Cochabamba, 900);
        grafoFlujos.insertarArista(LaPaz, Oruro, 870);
        grafoFlujos.insertarArista(Oruro, Cochabamba, 1890);
        grafoFlujos.insertarArista(Oruro, Potosi, 600);
        grafoFlujos.insertarArista(Oruro, Tarija, 417);
        grafoFlujos.insertarArista(Potosi, Cochabamba, 3652);
        grafoFlujos.insertarArista(Potosi, Chuquisaca, 50);
        grafoFlujos.insertarArista(Potosi, Tarija, 692);
        grafoFlujos.insertarArista(Chuquisaca, Cochabamba, 6333);
        grafoFlujos.insertarArista(Chuquisaca, Tarija, 5810);
        grafoFlujos.insertarArista(Chuquisaca, SantaCruz, 2140);
        grafoFlujos.insertarArista(Cochabamba, Beni, 600);
        grafoFlujos.insertarArista(Cochabamba, SantaCruz, 8000);
        grafoFlujos.insertarArista(LaPaz, SantaCruz, 862);
        grafoFlujos.insertarArista(LaPaz, Potosi, 5963);
        grafoFlujos.insertarArista(LaPaz, Chuquisaca, 1455);
        grafoFlujos.insertarArista(LaPaz, Tarija, 6982);
        //this.insertarTramo(Oruro,Tarija,796);
        grafoFlujos.insertarArista(Oruro, Chuquisaca, 555);
        grafoFlujos.insertarArista(Oruro, SantaCruz, 4820);
        grafoFlujos.insertarArista(Oruro, Beni, 369);
        grafoFlujos.insertarArista(Oruro, Pando, 250);
        grafoFlujos.insertarArista(Tarija, Pando, 1753);
        grafoFlujos.insertarArista(Tarija, Beni, 1116);
        grafoFlujos.insertarArista(Tarija, SantaCruz, 718);
        grafoFlujos.insertarArista(SantaCruz, Pando, 1395);
        grafoFlujos.insertarArista(Potosi, SantaCruz, 9000);
        // Ejecutar Ford-Fulkerson
        FordFulkerson2<Ciudad> fordFulkerson = new FordFulkerson2<>(grafoFlujos, SantaCruz, Pando);
        System.out.println("Flujo máximo: " + fordFulkerson.getFlujoMaximo());

        // Imprimir caminos del flujo máximo
        System.out.println(fordFulkerson.obtenerCaminosAumentadores());
    }

}
