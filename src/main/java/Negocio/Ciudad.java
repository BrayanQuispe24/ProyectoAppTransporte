/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 */
public class Ciudad implements Comparable<Ciudad> {

    private String ciudad;

    public Ciudad(String nombre/*, int distancia, double costo*/) {
        this.ciudad = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "ciudad=" + ciudad;
    }

    @Override
    public int compareTo(Ciudad otra) {
        // Comparar nombres de las ciudades ignorando mayúsculas/minúsculas
        return this.ciudad.equalsIgnoreCase(otra.ciudad) ? 0 : 1;
    }

}
