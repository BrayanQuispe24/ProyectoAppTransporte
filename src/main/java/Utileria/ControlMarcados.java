/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utileria;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BRAYAN
 */
public class ControlMarcados {

    private final List<Boolean> listaDeMarcados;

    public ControlMarcados(int nroDeVertices) {//es la cantidad de vertices
        this.listaDeMarcados = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            listaDeMarcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodosVertices() {
        for (int i = 0; i < listaDeMarcados.size(); i++) {
            listaDeMarcados.set(i, Boolean.FALSE);
        }
    }

    public boolean estaMarcadoVertice(int nroVertice) {//es el numero del vertice
        return listaDeMarcados.get(nroVertice);
    }

    public void marcarVertice(int nroVertice) {
        listaDeMarcados.set(nroVertice, true);
    }

    public void desmarcarVertice(int nroVertice) {
        listaDeMarcados.set(nroVertice, false);
    }

    public boolean estanTodosLosVerticesMarcados() {
        for (int i = 0; i < listaDeMarcados.size(); i++) {
            if (!listaDeMarcados.get(i)) {
                return false;
            }
        }

        return true;
    }
}
