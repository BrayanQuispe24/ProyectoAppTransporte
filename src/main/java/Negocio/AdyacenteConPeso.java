/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 */
public class AdyacenteConPeso implements Comparable<AdyacenteConPeso> {

    private int nroVertice;
    private double peso;

    public AdyacenteConPeso(int nroVertice) {
        this.nroVertice = nroVertice;
    }

    public AdyacenteConPeso(int nroVertice, double peso) {
        this.nroVertice = nroVertice;
        this.peso = peso;
    }

    public int getNroVertice() {
        return nroVertice;
    }

    public void setNroVertice(int nroVertice) {
        this.nroVertice = nroVertice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(AdyacenteConPeso o) {
        if (this.nroVertice > o.nroVertice) {
            return 1;
        }
        if (this.nroVertice < o.nroVertice) {
            return -1;
        }
        return 0;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Referencias iguales
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Tipo y null-check
        }
        AdyacenteConPeso o = (AdyacenteConPeso) obj; // Cast seguro
        return this.nroVertice == o.nroVertice ; // Comparación
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.nroVertice;
        return hash;
    }

   

}
