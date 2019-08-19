/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_entidades;

/**
 *
 * @author pablo
 */
public interface ICasilla {
    public abstract boolean estaAvatar(String codigo);
    public abstract int frecuenciaVisita();
    @Override
    public abstract String toString();
}
