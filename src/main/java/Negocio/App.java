/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 * @param <G>
 */
public class App<G extends Comparable<G>> {

    private GrafoPesado<Ciudad> grafoCiudad;
    private DigrafoPesado<Ciudad> grafoFlujos;
    private List<Boleto> listaBoletos;
    private Dijkstra<Ciudad> caminoMasCorto1;
    private Kruskal<Ciudad> caminosMasCortos1;
    private Prim<Ciudad> caminosMasCortos2;
    private Floyd<Ciudad> caminosMasCortos3;
    //aqui va ir el de ford fulkerson
    //private FordFulkerson flujoEncomiendas;

    public App() {
        this.grafoCiudad = new GrafoPesado();
        this.listaBoletos = new ArrayList<>();
        this.grafoFlujos = new DigrafoPesado();
    }

    public App(GrafoPesado<Ciudad> unGrafo) {
        this.grafoCiudad = unGrafo;
        this.listaBoletos = new ArrayList<>();

    }

    public App(List<Ciudad> listaDeCiudades) {
        for (Ciudad ciudadActual : listaDeCiudades) {
            this.grafoCiudad.insertarVertice(ciudadActual);
        }
    }

    public void iniciarAlgoritmo(int indice, Ciudad origen, Ciudad destino) {
        // Validación de los parámetros
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Origen y destino no pueden ser nulos.");
        }

        // Iniciar algoritmo según el índice
        switch (indice) {
            case 1:
                System.out.println("Iniciando Dijkstra...");
                this.caminoMasCorto1 = new Dijkstra(this.grafoCiudad, origen, destino);
                break;

            case 2:
                System.out.println("Iniciando Kruskal...");
                this.caminosMasCortos1 = new Kruskal(this.grafoCiudad);
                break;

            case 3:
                System.out.println("Iniciando Prim...");
                this.caminosMasCortos2 = new Prim(this.grafoCiudad);
                break;

            case 4:
                System.out.println("Iniciando Floyd...");
                this.caminosMasCortos3 = new Floyd(this.grafoCiudad);
                break;

            default:
                System.out.println("Algoritmo no implementado para el índice: " + indice);
                break;
        }
    }

    public void iniciarAlgoritmo2(int indice) {
        switch (indice) {
            case 2:
                System.out.println("Iniciando Kruskal...");
                this.caminosMasCortos1 = new Kruskal(this.grafoCiudad);
                break;

            case 3:
                System.out.println("Iniciando Prim...");
                this.caminosMasCortos2 = new Prim(this.grafoCiudad);
                break;

            default:
                System.out.println("Algoritmo no implementado para el índice: " + indice);
                break;
        }
    }

    public void insertarVertice(Ciudad ciudad) {
        this.grafoCiudad.insertarVertice(ciudad);
    }

    public void grafoFlujosinsertarArista(Ciudad ciudadDestino, Ciudad ciudadOrigen, double distancia) {
        this.grafoCiudad.insertarArista(ciudadOrigen, ciudadDestino, distancia);
    }

    public List<Ciudad> obtenerCaminoMasCorto(int indice, Ciudad origen, Ciudad destino) {
        if (indice == 1) {
            return this.caminoMasCorto1.getCaminoMasCorto();
        } else if (indice == 4) {
            return this.caminosMasCortos3.obtenerRecorrido(origen, destino);//falta implementar el obtener camino de predecesores 
        }
        return null;

    }

    public double obtenerDistanciaCaminoMasCorto(int indice, Ciudad origen, Ciudad destino) {
        if (indice == 1) {
            return this.caminoMasCorto1.getCostoCaminoMasCorto();
        } else if (indice == 4) {
            return this.caminosMasCortos3.obtenerCaminoCostoMinimo(origen, destino);
        }
        return 0;
    }

    public GrafoPesado<Ciudad> obtenerCaminosMasCortos(int opcion) {
        if (opcion == 2) {
            return this.caminosMasCortos1.obtenerGrafoResultado();
        } else if (opcion == 3) {
            return this.caminosMasCortos2.obtenerGrafoResultado();
        }
        return null;
    }

    public String mostrarTodasLasRutas(int indice) {
        return "";
    }

    public void crearBoleto(int numeroBoleto, String nombreCliente, String fecha,
            double precio, int distancia, String nombreCiudadOrigen,
            String nombreCiudadDestino, String ciudadesIntermedias) {
        Boleto nuevoBoleto = new Boleto(numeroBoleto, nombreCliente, fecha,
                precio, distancia, nombreCiudadOrigen,
                nombreCiudadDestino, ciudadesIntermedias);
        this.listaBoletos.add(nuevoBoleto);
    }

    public Boleto getBoleto(int numeroBoleto) {
        return null;
    }

    //esto no lo usaremos 
    public String mostrarMapa() {
        return "";
    }

    public String mostrarGrafoCiudad() {
        return this.grafoCiudad.toString();
    }

    public void verificarCiudad(Ciudad ciudad) {
        this.grafoCiudad.validarVertice(ciudad);
    }

    public double getDistancia(Ciudad origen, Ciudad destino) {
        return this.grafoCiudad.getPesoArista(origen, destino);
    }

    public void eliminarCiudad(Ciudad ciudadAEliminar) {
        this.grafoCiudad.eliminarVertice(ciudadAEliminar);
    }

    public void eliminarTramo(Ciudad origen, Ciudad destino) {
        this.grafoCiudad.eliminarArista(origen, destino);
    }

    public void llenarDatosFord() {

        Ciudad SantaCruz = new Ciudad("Santa Cruz");
        Ciudad Beni = new Ciudad("Beni");
        Ciudad Cochabamba = new Ciudad("Cochabamba");
        Ciudad Chuquisaca = new Ciudad("Chuquisaca");
        Ciudad Pando = new Ciudad("Pando");
        Ciudad LaPaz = new Ciudad("La Paz");
        Ciudad Oruro = new Ciudad("Oruro");
        Ciudad Potosi = new Ciudad("Potosi");
        Ciudad Tarija = new Ciudad("Tarija");
        this.grafoFlujos.insertarVertice(SantaCruz);
        this.grafoFlujos.insertarVertice(Beni);
        this.grafoFlujos.insertarVertice(Cochabamba);
        this.grafoFlujos.insertarVertice(Chuquisaca);
        this.grafoFlujos.insertarVertice(Pando);
        this.grafoFlujos.insertarVertice(LaPaz);
        this.grafoFlujos.insertarVertice(Oruro);
        this.grafoFlujos.insertarVertice(Potosi);
        this.grafoFlujos.insertarVertice(Tarija);
        this.grafoFlujos.insertarArista(LaPaz, Pando, 1400);
        this.grafoFlujos.insertarArista(LaPaz, Beni, 680);
        this.grafoFlujos.insertarArista(LaPaz, Cochabamba, 900);
        this.grafoFlujos.insertarArista(LaPaz, Oruro, 870);
        this.grafoFlujos.insertarArista(Oruro, Cochabamba, 1890);
        this.grafoFlujos.insertarArista(Oruro, Potosi, 600);
        this.grafoFlujos.insertarArista(Oruro, Tarija, 417);
        this.grafoFlujos.insertarArista(Potosi, Cochabamba, 3652);
        this.grafoFlujos.insertarArista(Potosi, Chuquisaca, 50);
        this.grafoFlujos.insertarArista(Potosi, Tarija, 692);
        this.grafoFlujos.insertarArista(Chuquisaca, Cochabamba, 6333);
        this.grafoFlujos.insertarArista(Chuquisaca, Tarija, 5810);
        this.grafoFlujos.insertarArista(Chuquisaca, SantaCruz, 2140);
        this.grafoFlujos.insertarArista(Cochabamba, Beni, 600);
        this.grafoFlujos.insertarArista(Cochabamba, SantaCruz, 8000);
        this.grafoFlujos.insertarArista(LaPaz, SantaCruz, 862);
        this.grafoFlujos.insertarArista(LaPaz, Potosi, 5963);
        this.grafoFlujos.insertarArista(LaPaz, Chuquisaca, 1455);
        this.grafoFlujos.insertarArista(LaPaz, Tarija, 6982);
        //this.insertarTramo(Oruro,Tarija,796);
        this.grafoFlujos.insertarArista(Oruro, Chuquisaca, 555);
        this.grafoFlujos.insertarArista(Oruro, SantaCruz, 4820);
        this.grafoFlujos.insertarArista(Oruro, Beni, 369);
        this.grafoFlujos.insertarArista(Oruro, Pando, 250);
        this.grafoFlujos.insertarArista(Tarija, Pando, 1753);
        this.grafoFlujos.insertarArista(Tarija, Beni, 1116);
        this.grafoFlujos.insertarArista(Tarija, SantaCruz, 718);
        this.grafoFlujos.insertarArista(SantaCruz, Pando, 1395);
        this.grafoFlujos.insertarArista(Potosi, SantaCruz, 9000);
    }

    public void llenarDatos() {
        Ciudad SantaCruz = new Ciudad("Santa Cruz");
        Ciudad Beni = new Ciudad("Beni");
        Ciudad Cochabamba = new Ciudad("Cochabamba");
        Ciudad Chuquisaca = new Ciudad("Chuquisaca");
        Ciudad Pando = new Ciudad("Pando");
        Ciudad LaPaz = new Ciudad("La Paz");
        Ciudad Oruro = new Ciudad("Oruro");
        Ciudad Potosi = new Ciudad("Potosi");
        Ciudad Tarija = new Ciudad("Tarija");
        this.insertarVertice(SantaCruz);
        this.insertarVertice(Beni);
        this.insertarVertice(Cochabamba);
        this.insertarVertice(Chuquisaca);
        this.insertarVertice(Pando);
        this.insertarVertice(LaPaz);
        this.insertarVertice(Oruro);
        this.insertarVertice(Potosi);
        this.insertarVertice(Tarija);
        this.grafoFlujosinsertarArista(LaPaz, Pando, 1162);
        this.grafoFlujosinsertarArista(LaPaz, Beni, 824);
        this.grafoFlujosinsertarArista(LaPaz, Cochabamba, 376);
        this.grafoFlujosinsertarArista(LaPaz, Oruro, 224);
        this.grafoFlujosinsertarArista(Oruro, Cochabamba, 223);
        this.grafoFlujosinsertarArista(Oruro, Potosi, 373);
        this.grafoFlujosinsertarArista(Oruro, Tarija, 796);
        this.grafoFlujosinsertarArista(Potosi, Cochabamba, 493);
        this.grafoFlujosinsertarArista(Potosi, Chuquisaca, 156);
        this.grafoFlujosinsertarArista(Potosi, Tarija, 589);
        this.grafoFlujosinsertarArista(Chuquisaca, Cochabamba, 332);
        this.grafoFlujosinsertarArista(Chuquisaca, Tarija, 447);
        this.grafoFlujosinsertarArista(Chuquisaca, SantaCruz, 511);
        this.grafoFlujosinsertarArista(Cochabamba, Beni, 571);
        this.grafoFlujosinsertarArista(Cochabamba, SantaCruz, 474);
        this.grafoFlujosinsertarArista(LaPaz, SantaCruz, 862);
        this.grafoFlujosinsertarArista(LaPaz, Potosi, 533);
        this.grafoFlujosinsertarArista(LaPaz, Chuquisaca, 695);
        this.grafoFlujosinsertarArista(LaPaz, Tarija, 1023);
        //this.insertarTramo(Oruro,Tarija,796);
        this.grafoFlujosinsertarArista(Oruro, Chuquisaca, 555);
        this.grafoFlujosinsertarArista(Oruro, SantaCruz, 770);
        this.grafoFlujosinsertarArista(Oruro, Beni, 634);
        this.grafoFlujosinsertarArista(Oruro, Pando, 1263);
        this.grafoFlujosinsertarArista(Tarija, Pando, 1753);
        this.grafoFlujosinsertarArista(Tarija, Beni, 1116);
        this.grafoFlujosinsertarArista(Tarija, SantaCruz, 718);
        this.grafoFlujosinsertarArista(SantaCruz, Pando, 1395);
        this.grafoFlujosinsertarArista(Potosi, SantaCruz, 699);
    }

    public DigrafoPesado<Ciudad> getGrafoFlujos() {
        return this.grafoFlujos;
    }
}
