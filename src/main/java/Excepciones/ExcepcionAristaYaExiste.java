/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author BRAYAN
 */
public class ExcepcionAristaYaExiste extends RuntimeException {

    public ExcepcionAristaYaExiste() {
        super("Arista ya existe");
    }

    public ExcepcionAristaYaExiste(String message) {
        super(message);
    }
}
