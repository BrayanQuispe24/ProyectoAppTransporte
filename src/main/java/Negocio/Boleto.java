/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author BRAYAN
 */
public class Boleto implements Comparable<Boleto> {

    private int numeroBoleto;
    private String nombreCliente;
    private String fecha;
    private double precio;
    private double distancia;
    private String nombreCiudadOrigen;
    private String nombreCiudadDestino;
    private String ciudadesIntermedias;

    public Boleto(int numeroBoleto, String nombreCliente, String fecha,
            double precio, double distancia, String nombreCiudadOrigen,
            String nombreCiudadDestino, String ciudadesIntermedias) {
        this.numeroBoleto = numeroBoleto;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.precio = precio;
        this.distancia = distancia;
        this.nombreCiudadOrigen = nombreCiudadOrigen;
        this.nombreCiudadDestino = nombreCiudadDestino;
        this.ciudadesIntermedias = ciudadesIntermedias;
    }

    public Boleto() {
    }

    public int getNumeroBoleto() {
        return numeroBoleto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public double getDistancia() {
        return distancia;
    }

    public String getNombreCiudadOrigen() {
        return nombreCiudadOrigen;
    }

    public String getNombreCiudadDestino() {
        return nombreCiudadDestino;
    }

    public String getCiudadesIntermedias() {
        return ciudadesIntermedias;
    }

    public void setNumeroBoleto(int numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setNombreCiudadOrigen(String nombreCiudadOrigen) {
        this.nombreCiudadOrigen = nombreCiudadOrigen;
    }

    public void setNombreCiudadDestino(String nombreCiudadDestino) {
        this.nombreCiudadDestino = nombreCiudadDestino;
    }

    public void setCiudadesIntermedias(String ciudadesIntermedias) {
        this.ciudadesIntermedias = ciudadesIntermedias;
    }

    @Override
    public int compareTo(Boleto o) {
        if (this.numeroBoleto > o.getNumeroBoleto()) {
            return 1;
        } else if (this.numeroBoleto < o.getNumeroBoleto()) {
            return -1;
        } else {
            return 0;
        }
    }

}
