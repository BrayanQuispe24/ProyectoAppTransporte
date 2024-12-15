/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Practica;

import Negocio.GrafoPesado;
import Negocio.GrafoPesadoMatrizA;

/**
 *
 * @author BRAYAN
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GrafoPesado unGrafo = new GrafoPesado();
        unGrafo.insertarVertice(5);
        unGrafo.insertarVertice(6);
        unGrafo.insertarVertice(7);
        unGrafo.insertarVertice(8);
        unGrafo.insertarVertice(9);
        unGrafo.insertarArista(5, 6, 10);
        unGrafo.insertarArista(5, 7, 15);
        unGrafo.insertarArista(6, 8, 20);
        unGrafo.insertarArista(8, 9, 30);
        unGrafo.insertarArista(7, 9, 14);
        System.out.println(unGrafo.toString());
        //unGrafo.eliminarVertice(5);
        //System.out.println(unGrafo.toString());
        Dijkstra2 nuevoA = new Dijkstra2(unGrafo, 5, 9);
        System.out.println(nuevoA.costoMinimo());
        System.out.println(nuevoA.obtenerCamino());
        // System.out.println((int)nuevoA.getCostoCamino());
        //System.out.println(nuevoA.getCamino());
    }

}
