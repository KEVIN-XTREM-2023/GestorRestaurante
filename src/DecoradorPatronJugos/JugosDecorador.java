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
public class JugosDecorador implements IJugos{
    IJugos jugos;

    public JugosDecorador(IJugos jugos) {
        this.jugos = jugos;
    }

    @Override
    public String getDescripcion() {
        return jugos.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return  jugos.getPrecio();
    }
    
}
