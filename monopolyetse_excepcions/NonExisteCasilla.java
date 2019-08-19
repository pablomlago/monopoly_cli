/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_excepcions;

/**
 *
 * @author pablo
 */
public class NonExisteCasilla extends MonopolyExcepcion
{
    public NonExisteCasilla(String nomeCasilla)
    {
        super("\n\tNon existe a casilla: A casilla requerida non existe!"
            +"\n\t\t" + nomeCasilla + " non e unha casilla do tableiro");
        
    }
}
