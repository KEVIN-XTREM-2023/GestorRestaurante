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
public class Arroz implements IComida {

    double precio;

    @Override
    public String descripcion() {
        return "Usted ha ordenado ARROZ";

    }

    @Override
    public String comer() {
        return "Buen provecho espero que le guste su Arroz" + "\n" + "Usted ha terminado de comer su Arroz";
    }

    @Override
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public double getprecio() {
        return this.precio;
    }
}
