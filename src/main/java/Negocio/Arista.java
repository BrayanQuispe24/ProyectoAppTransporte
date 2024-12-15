/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 */
public class Arista implements Comparable<Arista> {

    int nroVerticeOrigen;
    int nroVerticeDestino;
    double peso;

    public Arista(int origen, int destino, double peso) {
        this.nroVerticeOrigen = origen;
        this.nroVerticeDestino = destino;
        this.peso = peso;
    }

    public void setNroVerticeOrigen(int nroVerticeOrigen) {
        this.nroVerticeOrigen = nroVerticeOrigen;
    }

    public void setNroVerticeDestino(int nroVerticeDestino) {
        this.nroVerticeDestino = nroVerticeDestino;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getNroVerticeOrigen() {
        return nroVerticeOrigen;
    }

    public int getNroVerticeDestino() {
        return nroVerticeDestino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public int compareTo(Arista o) {
        if (this.peso > o.getPeso()) {
            return 1;
        }
        if (this.peso < o.getPeso()) {
            return -1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Arista other = (Arista) obj;
        return this.peso == other.peso;
    }

}
