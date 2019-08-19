/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_funcionalidades;

import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Xogador;

/**
 *
 * @author pablo
 */
public class NoAlquiler {

    private int turnosRestantes;
    private final Propiedade casilla;
    private final Xogador xogador;

    public NoAlquiler(Xogador xogador, Propiedade casilla, int turnos) {
        this.turnosRestantes = turnos;
        this.casilla = casilla;
        this.xogador = xogador;
    }

    public int getTurnosRestantes() {
        return this.turnosRestantes;
    }

    public Propiedade getCasillaNoAlquiler() {
        return this.casilla;
    }

    public Xogador getXogador() {
        return this.xogador;
    }

    public void restarTurno() {
        this.turnosRestantes--;
        if (this.turnosRestantes <= 0) {
            this.xogador.eliminarNoAlquiler(this);
        }
    }

    @Override
    public String toString() {
        return "Casilla: " + this.casilla.getNome()
                + "\nTurnos Restantes: " + this.turnosRestantes;
    }

}
