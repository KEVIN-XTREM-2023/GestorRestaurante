/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronFactory.fabrica;

import PatronFactory.implementacion.Arroz;
import PatronFactory.implementacion.ComidaNoOrdenada;
import PatronFactory.implementacion.Hamburguesas;
import PatronFactory.implementacion.Postre;
import PatronFactory.implementacion.Sopa;
import PatronFactory.interfaz.IComida;

/**
 *
 * @author ===> kevin <=====
 */
public class FabricaComida {

    public IComida obtnerComida(String comida) {

        if (comida.equalsIgnoreCase("SOPA")) {
            return new Sopa();
        } else if (comida.equalsIgnoreCase("ARROZ")) {
            return new Arroz();

        } else if (comida.equalsIgnoreCase("POSTRE")) {
            return new Postre();
        } else if (comida.equalsIgnoreCase("HAMBURGUESAS")) {
            return new Hamburguesas();
        }
        return new ComidaNoOrdenada();

    }

}
