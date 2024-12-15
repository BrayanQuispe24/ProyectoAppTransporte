/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.grafospesados_2024_2;

import Negocio.App;
import Negocio.Ciudad;
import Negocio.DigrafoPesado;
import Negocio.Floyd;
import Negocio.GrafoPesado;

/**
 *
 * @author BRAYAN
 */
public class GrafosPesados_2024_2 {

    public static void main(String[] args) {
    
       GrafoPesado unGrafo= new GrafoPesado();
       unGrafo.insertarVertice(4);
       unGrafo.insertarVertice(5);
       unGrafo.insertarVertice(6);
       unGrafo.insertarVertice(7);
       unGrafo.insertarVertice(8);
       unGrafo.insertarVertice(9);
       unGrafo.insertarArista(5, 6, 15);
       unGrafo.insertarArista(4, 5, 20);
       unGrafo.insertarArista(8, 9, 10);
       unGrafo.insertarArista(9, 5, 56);
       unGrafo.insertarArista(4, 7, 89);
       unGrafo.insertarArista(8, 6, 5);
        System.out.println(unGrafo.toString());
        Floyd algo=new Floyd(unGrafo);
        algo.imprimirMatrizPesos();
        algo.imprimirMatrizPredecesores();
        System.out.println(algo.obtenerRecorrido(4, 9));
        System.out.println(algo.obtenerCaminoCostoMinimo(4, 9));
        //unGrafo.eliminarVertice(5);       unGrafo.insertarArista(8, 6, 5);
        //System.out.println(unGrafo.toString());
        // unGrafo.insertarArista(8, 9, 14);
        /* System.out.println(unGrafo.toString());
        DFS nuevoRecorrido = new DFS(unGrafo, 5);
        System.out.println(nuevoRecorrido.obtenerRecorrido());
        BFS nuevoRecorrido2 = new BFS(unGrafo, 5);
        System.out.println(nuevoRecorrido2.getRecorrido());
        System.out.println(unGrafo.getPesoArista(5, 9));
        Dijkstra algoritmo = new Dijkstra(unGrafo, 5, 7);
        System.out.println(algoritmo.vectorCostos);
        System.out.println(algoritmo.predecesores);
        System.out.println(algoritmo.CaminoMasCorto);
        System.out.println(algoritmo.getCostoCaminoMasCorto());*/
 /*Kruskal nuevoAlgoritmo=new Kruskal(unGrafo);
        System.out.println(nuevoAlgoritmo.mostrarListaAristas());
        System.out.println(nuevoAlgoritmo.toString());*/
 /*Prim algoritmoPrim=new Prim(unGrafo);
        System.out.println(algoritmoPrim.obtenerGrafoResultado().toString());
        System.out.println(algoritmoPrim.obtenerPesoTotal());*/
 /* Floyd algoritmo = new Floyd(unGrafo);
        algoritmo.imprimirMatrizPesos();
        algoritmo.imprimirMatrizPredecesores();*/
 /*HayCicloDFS ciclo= new HayCicloDFS(unGrafo,5);
        System.out.println(ciclo.hayCiclo());*/
    }
}
