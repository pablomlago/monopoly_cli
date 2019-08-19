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
public class ExcVender extends MonopolyExcepcion
{
    public ExcVender(String mensaxe)
    {
        super("\n\tVender: " + mensaxe);
    }
}
