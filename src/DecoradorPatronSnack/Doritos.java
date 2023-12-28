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
public class Doritos extends SnackDecorador{
    String descripcion;
    double costo;

    public Doritos(ISnack snack, String descripcion , double costo) {
        super(snack);
        this.descripcion = descripcion;
        this.costo = costo;
        
    }
    

    
    @Override
    public String getDescripcion() {
        return "Buen provecho de su " + snacks.getDescripcion() + " de " + this.descripcion + "\n" + "Usted ha termindo su snack";
    }
   

    @Override
    public double getPrecio() {
        return costo;
    }
}
