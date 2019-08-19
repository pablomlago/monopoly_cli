/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_entidades;

/**
 *
 * @author manu
 */

/*Os tipos de edificios que se empregaran están ben definidos,
e non presentan características particulares que xustifiquen
a división dos mesmos en diferentes subtipos. Así, de querer
introducir un novo edificio, probablemente se faría isto como unha 
subclase diferenciada de Edificio
*/
public class Hotel extends Edificio {

    public Hotel(Casilla casilla) {
        super(casilla);
    }

    @Override
    public float getPrezo() {
        return this.getCasilla().getGrupo().getPrezoHotel();
    }
}
