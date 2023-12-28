/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecoradorPatronJugos;

/**
 *
 * @author ===> kevin <=====
 */
public class Pinia extends JugosDecorador{
    String descripcion;
    double costo;

    public Pinia(IJugos jugos, String descripcion , double costo) {
        super(jugos);
        this.descripcion = descripcion;
        this.costo = costo;
        
    }
    

    
    @Override
    public String getDescripcion() {
        return  "Buen provecho de su "+ jugos.getDescripcion() + " de " + this.descripcion + "\n"+ "Usted ha terminada su jugo";
    }

    

    @Override
    public double getPrecio() {
        return costo;
    }
}
