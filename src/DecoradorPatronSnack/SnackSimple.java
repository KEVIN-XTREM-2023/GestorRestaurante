/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecoradorPatronSnack;


/**
 *
 * @author ===> kevin <=====
 */
public class SnackSimple implements ISnack{

    private String descripcion;
    private double costo ;

    public SnackSimple(String descripcion, double costo) {
        this.descripcion = descripcion;
        this.costo = costo;
    }
    
    @Override
    public String getDescripcion() {
        return  descripcion;
    }
    

    @Override
    public double getPrecio() {
        return costo;
    }
    
}
