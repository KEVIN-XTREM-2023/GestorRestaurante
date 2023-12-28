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
public class MalteadasDecorador  implements IMalteadas{
    IMalteadas malteadas;

    public MalteadasDecorador(IMalteadas malteadas) {
        this.malteadas = malteadas;
    }

    @Override
    public String getDescripcion() {
        return malteadas.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return  malteadas.getPrecio();
    }
    
}
