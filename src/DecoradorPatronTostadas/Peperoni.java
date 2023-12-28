/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecoradorPatronTostadas;

/**
 *
 * @author ===> kevin <=====
 */
public class Peperoni extends TostadaDecorador{
    String descripcion;
    double costo;
    
    public Peperoni(ITostadas tostadas, String descripcion , double costo) {
        super(tostadas);
        this.descripcion = descripcion;
        this.costo = costo;
        
    }
    
    @Override
    public String getDescripcion() {
        return "Buen provecho de su " + tostadas.getDescripcion() + " de " + this.descripcion + "\n" + "Usted ha terminado su Tostada";

    }

    

    @Override
    public double getPrecio() {
        return costo;
    }
}
