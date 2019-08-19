package monopolyetse_funcionalidades;

import java.util.ArrayList;
import monopolyetse_entidades.Casa;
import monopolyetse_entidades.Edificio;
import monopolyetse_entidades.Hotel;
import monopolyetse_entidades.Piscina;
import monopolyetse_entidades.Pista;
import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Solar;
import monopolyetse_entidades.Xogador;
import static monopolyetse_funcionalidades.Juego.consola;

public class Cambios 
{
    private final ArrayList<Float> cantidades;
    private final ArrayList<Integer> conceptos;
    private final ArrayList<Xogador> xogadores;
    
    private final ArrayList<Propiedade> propiedades;
    
    private final ArrayList<Edificio> edificios;
    
    public Cambios()
    {
        this.cantidades = new ArrayList<>();
        this.conceptos = new ArrayList<>();
        this.xogadores = new ArrayList<>();
        
        this.propiedades = new ArrayList<>();
        
        this.edificios = new ArrayList<>();
    }
    
    public void borrarCambios()
    {
        this.cantidades.clear();
        this.xogadores.clear();
        this.conceptos.clear();
        this.propiedades.clear();
        
        this.edificios.clear();
    }
    
    public void revertirCambios(Xogador xogador)
    {
        float tempCantidade;
        int tempConcepto;
        Xogador tempXogador;
        int tamano = this.xogadores.size();
        //Engadiranse pagos adicionales
        for(int i = 0; i < tamano; i++)
        {
            tempCantidade = this.cantidades.get(i);
            tempConcepto = this.conceptos.get(i);
            tempXogador = this.xogadores.get(i);
            if(tempCantidade < 0)
            {
                consola.imprimir(tempXogador.getNomeXogador() + " devolveche " + (-tempCantidade));
                xogador.sumarCantidade(tempXogador,-tempCantidade, tempConcepto);
                tempXogador.restarCantidade(xogador, -tempCantidade, tempConcepto);
            }
            else
            {
                consola.imprimir("Devolverse " + tempCantidade + " a " + tempXogador.getNomeXogador());
                tempXogador.sumarCantidade(xogador, tempCantidade, tempConcepto);
                xogador.restarCantidade(tempXogador, tempCantidade, tempConcepto);
            }
        }
        
        for(Propiedade propiedade : propiedades)
        {
            consola.imprimir(propiedade.getNome() + " volve a ser propiedade da Banca!");
            xogador.transferirPropiedade(xogador.getBanca(), propiedade);
        }
        
        for(Edificio edificio : edificios)
        {
            //Comprobamos que non se eliminara ao construir 
            if(edificio.getCasilla().getEdificios().contains(edificio))
            {
                String tipo = "";
                if(edificio instanceof Casa)
                {
                    tipo = "casa";
                }
                else if(edificio instanceof Hotel)
                {
                    tipo = "hotel";
                }
                else if(edificio instanceof Piscina)
                {
                    tipo = "piscina";
                }
                else if(edificio instanceof Pista)
                {
                    tipo = "pista";
                }
                
                xogador.eliminarEdificio(tipo, 1, edificio.getCasilla());
                edificio.getCasilla().eliminarEdificio(tipo, 1);
                
                consola.imprimir("Eliminamos 1 " + tipo + " de " + edificio.getCasilla().getNome());
            }
        }
        
        this.borrarCambios();
        xogador.getCambios().borrarCambios();
    }
    
    public void engadirPago(Xogador receptor, float cantidade, int concepto)
    {
        this.xogadores.add(receptor);
        this.cantidades.add(cantidade);
        this.conceptos.add(concepto);
    }
    
    public void engadirPropiedade(Propiedade propiedade)
    {
        this.propiedades.add(propiedade);
    }
    
    public void engadirEdificio(Edificio edificio)
    {
        this.edificios.add(edificio);
    }
}
