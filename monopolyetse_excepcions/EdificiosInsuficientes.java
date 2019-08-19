/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_excepcions;

import monopolyetse_entidades.Xogador;

/**
 *
 * @author pablo
 */
public class EdificiosInsuficientes extends ExcVender
{
    public EdificiosInsuficientes(Xogador xogador, String tipo, String nome, int num, int numEdificios)
    {
        super("Non hai suficientes edificios"
            + "\n\t\t" + xogador.getNomeXogador() + " quere vender " + num +
                    " " + tipo + " en " + nome + " pero so ten " + numEdificios);
    }
}
