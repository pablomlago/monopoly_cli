/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_funcionalidades;

import monopolyetse_excepcions.NonPosue;
import monopolyetse_excepcions.DineiroInsuficienteTrato;
import monopolyetse_excepcions.ExcTrato;
import monopolyetse_excepcions.ExcXogador;
import java.util.ArrayList;
import monopolyetse_entidades.Casilla;
import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Xogador;
import static monopolyetse_funcionalidades.Juego.consola;

/**
 *
 * @author manu
 */
public class Trato {

    private final Xogador emisor;
    private final Xogador receptor;
    private final ArrayList<Propiedade> propiedadesPropostas;
    private final ArrayList<Propiedade> propiedadesRecibidas;
    private final ArrayList<Propiedade> propiedadesNoAlquiler;
    private final float cartosRecibidos;
    private final float cartosPropostos;
    private final int turnosNoAlquiler;

    public Trato(Xogador emisor, Xogador receptor, ArrayList<Propiedade> propiedadesPropostas, float cartosPropostos, ArrayList<Propiedade> propiedadesRecibidas, float cartosRecibidos, ArrayList<Propiedade> propiedadesNoAlquiler, int turnosNoAlquiler) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.propiedadesPropostas = propiedadesPropostas;
        this.propiedadesRecibidas = propiedadesRecibidas;
        this.cartosPropostos = cartosPropostos;
        this.cartosRecibidos = cartosRecibidos;
        this.propiedadesNoAlquiler = propiedadesNoAlquiler;
        this.turnosNoAlquiler = turnosNoAlquiler;
    }

    public Xogador getEmisor() {
        return this.emisor;
    }

    public Xogador getReceptor() {
        return this.receptor;
    }

    public ArrayList<Propiedade> getPropiedadesPropostas() {
        return this.propiedadesPropostas;
    }

    public ArrayList<Propiedade> getPropiedadesRecibidas() {
        return this.propiedadesRecibidas;
    }

    public ArrayList<Propiedade> getPropiedadesNoAlquiler() {
        return this.propiedadesNoAlquiler;
    }

    public float getCartosPropostos() {
        return this.cartosPropostos;
    }

    public float getCartosRecibidos() {
        return this.cartosRecibidos;
    }

    public int getTurnosNoAlquiler() {
        return this.turnosNoAlquiler;
    }
    
    public int Identificador()
    {
        return this.receptor.getTratosRecibidos().indexOf(this)+1;
    }

    public void eliminarTrato() {
        this.emisor.getTratosEmitidos().remove(this);
        this.receptor.getTratosRecibidos().remove(this);
    }

    public boolean posible() throws ExcTrato, ExcXogador{
        if (!emisor.comprobarFortuna(this.getCartosPropostos())) {
            throw new DineiroInsuficienteTrato(this.emisor, "emisor", this, this.getCartosPropostos() - this.emisor.getFortuna());
        }
        if (!receptor.comprobarFortuna(this.getCartosRecibidos())) {
            throw new DineiroInsuficienteTrato(this.receptor, "receptor", this, this.getCartosPropostos() - this.receptor.getFortuna());
        }
        for (Propiedade propiedade : this.getPropiedadesPropostas()) {//comprobamos que o emisor ten todas estas propiedades
            if (!emisor.getPropiedades().containsKey(propiedade.getNome())) {
                throw new NonPosue(this.emisor, propiedade.getNome());
            }
        }
        for (Propiedade propiedade : this.getPropiedadesRecibidas()) {//comprobamos que o emisor ten todas estas propiedades
            if (!receptor.getPropiedades().containsKey(propiedade.getNome())) {
                throw new NonPosue(this.receptor, propiedade.getNome());
            }
        }
        for (Propiedade propiedade : this.getPropiedadesNoAlquiler()) {
            if (!receptor.getPropiedades().containsKey(propiedade.getNome())) {
                throw new NonPosue(this.receptor, propiedade.getNome());
            }

        }
        return true;
    }

    public void facerIntercambios() {
        for (Casilla casilla : this.getPropiedadesRecibidas()) {
            receptor.transferirPropiedade(emisor, (Propiedade) casilla);
        }
        for (Casilla casilla : this.getPropiedadesPropostas()) {
            emisor.transferirPropiedade(receptor, (Propiedade) casilla);
        }
        emisor.sumarCantidade(null, this.getCartosRecibidos(), 1); //esta cantidade está engadida a cartos de alquileres para as estadisticas
        receptor.sumarCantidade(null, this.getCartosPropostos(), 1);

    }

    public void enunciarTrato() {
        String s = "";
        s += this.receptor.getNomeXogador() + ", ¿te doy ";
        if (!this.propiedadesPropostas.isEmpty()) {
            for (Casilla casilla : propiedadesPropostas) {
                s += casilla.getNome() + ", ";
            }

            if (this.cartosPropostos != 0) {
                s += "y ";
            }

        }
        if (this.cartosPropostos != 0) {
            s += this.cartosPropostos + " $, ";
        }
        s += "y tú me das ";
        if (!this.propiedadesRecibidas.isEmpty()) {
            for (Casilla casilla : propiedadesRecibidas) {
                s += casilla.getNome() + ", ";
            }

            if (this.cartosRecibidos != 0) {
                s += "y ";
            }
        }
        if (this.cartosRecibidos != 0) {
            s += this.cartosRecibidos + " $";
        }
        s += "?";
        if (!this.propiedadesNoAlquiler.isEmpty()) {
            s += " Además, no pagaría el alquiler en: ";
            for (Casilla casilla : propiedadesNoAlquiler) {
                s += casilla.getNome() + ", ";
            }
            s += "durante " + this.turnosNoAlquiler + " turnos.";
        }
        consola.imprimir(s);
    }

    @Override

    public String toString() {
        String s = "";
        s += ("{"
                + "\n\ttrato numero " + this.Identificador()
                + "\n\tjugadorPropone: " + this.emisor.getNomeXogador()
                + "\n\ttrato: cambiar (");
        if (!this.propiedadesPropostas.isEmpty()) {

            for (Casilla casilla : propiedadesPropostas) {
                s += casilla.getNome() + ", ";
            }
            s += "propiedades propostas, ";
        }

        if (this.cartosPropostos != 0) {
            s += this.cartosPropostos + "$ propostos, ";
        }
        if (!this.propiedadesRecibidas.isEmpty()) {
            for (Casilla casilla : propiedadesRecibidas) {
                s += casilla.getNome() + " ";
            }
            s += "propiedades recibidas, ";
        }
        if (this.cartosRecibidos != 0) {
            s += this.cartosRecibidos + "$ recibidos";
        }
				s+=")";				
        if (!this.propiedadesNoAlquiler.isEmpty()) {
            s += "\n\t\tno pagar alquiler en ";
            for (Casilla casilla : this.propiedadesNoAlquiler) {
                s += casilla.getNome() + ", ";
            }
            s += " durante " + this.turnosNoAlquiler + " turnos.";
        }
        s += "\n}";
        return s;
    }

}