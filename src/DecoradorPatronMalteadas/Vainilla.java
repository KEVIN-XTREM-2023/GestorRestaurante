/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecoradorPatronMalteadas;

/**
 *
 * @author ===> kevin <=====
 */
public class Vainilla extends MalteadasDecorador{
    String descripcion;
    double costo;
    
    public Vainilla(IMalteadas malteadas, String descripcion , double costo) {
        super(malteadas);
        this.descripcion = descripcion;
        this.costo = costo;
        
    }
    
    @Override
    public String getDescripcion() {
        return "Buen provecho de su "+malteadas.getDescripcion() + " de " + this.descripcion + "\n" + "Usted ha termindo su malteada";
    }

    

    @Override
    public double getPrecio() {
        return costo;
    }
}
