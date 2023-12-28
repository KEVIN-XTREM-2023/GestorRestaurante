/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronFactory.implementacion;

import PatronFactory.interfaz.IComida;

/**
 *
 * @author ===> kevin <=====
 */
public class ComidaNoOrdenada implements IComida {

    @Override
    public String descripcion() {
        return "Usted todavia no ha ordenado Nada";
    }

    @Override
    public String comer() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void setPrecio(double precio) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public double getprecio() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
