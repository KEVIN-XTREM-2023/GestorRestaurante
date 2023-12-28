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
public class TostadaDecorador implements ITostadas{
    ITostadas tostadas;

    public TostadaDecorador(ITostadas tostadas) {
        this.tostadas = tostadas;
    }

    @Override
    public String getDescripcion() {
        return tostadas.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return  tostadas.getPrecio();
    }
    
}
