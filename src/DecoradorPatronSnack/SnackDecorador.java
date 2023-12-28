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
public class SnackDecorador implements ISnack{
    ISnack snacks;

    public SnackDecorador(ISnack snacks) {
        this.snacks = snacks;
    }

    @Override
    public String getDescripcion() {
        return snacks.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return  snacks.getPrecio();
    }
    
}
