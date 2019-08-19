package monopolyetse_funcionalidades;

import monopolyetse_entidades.Xogador;

import java.util.ArrayList;
import static monopolyetse_funcionalidades.Juego.consola;

public class Turno 
{
    private  final ArrayList<Xogador> xogadores;
    //Non precisaremos un setter para xogadorTurno
    //xa que todas as funcións asociadas ao mesmo se deben 
    //realizar de maneira opaca dentro desta clase
    private Xogador xogadorTurno;
    //O índice correspondente ao xogador do cal é o turno
    //non debería ser modificado por funcións externas, polo
    //que non precisamos un setter. Ademais, por tratarse dunha
    //variable auxiliar para obter o xogador do cal é o turno
    //non é preciso que fóra da clase se coñoza o seu valor, 
    //xa que podemos acceder ao xogador do cal é o turno.
    //Polo tanto, non crearemos un getter
    private int pos;
    
    public Turno()
    {
        this.xogadores = new ArrayList<>();
        this.xogadorTurno = null;
        this.pos = 0;
    }
    
    //Pode resultar necesario no caso de que os xogadores non se podan unir durante o xogo
    public Turno(ArrayList<Xogador> xogadores)
    {
        this.pos = 0;
        
        if(xogadores != null)
        {
            this.xogadores = xogadores;
            if(xogadores.isEmpty())
            {
                xogadorTurno = null;
            }
            else
            {
                xogadorTurno = this.xogadores.get(pos);
            }
        }
        else
        {
           this.xogadores = new ArrayList<>();
           xogadorTurno = null;
        }
    }
    
    public Xogador getXogadorTurno()
    {
        if(this.xogadorTurno == null)
        {
           consola.imprimir("Non hai xogadores");
        }
        return this.xogadorTurno;
    }
    
    public ArrayList<Xogador> getXogadores()
    {
        return xogadores;
    }
    
    public void pasarTurno()
    {
        if(this.xogadorTurno != null)
        {
            this.xogadorTurno.getCambios().borrarCambios();
            
            //Ao chegar a n volvemos o principio
            this.pos = (this.pos+1) % this.xogadores.size();
            this.xogadorTurno = this.xogadores.get(this.pos);
            
            this.xogadorTurno.getCambios().borrarCambios();
        }
        else
        {
            consola.imprimir("Non hai xogadores");
        }
    }
    
    public void engadirXogador(Xogador xogador)
    {
        this.xogadores.add(xogador);
        
        if(xogadorTurno == null)
        {
            this.xogadorTurno = xogador;
        }
    }
    
    public boolean haiXogadores()
    {
        return !this.xogadores.isEmpty();
    }

    public int numeroXogadores()
    {
        return this.xogadores.size();
    }
    
    public void eliminarXogador(Xogador xogador)
    {
        this.xogadores.remove(xogador);
        
        if(this.xogadores.isEmpty())
        {
            this.xogadorTurno = null;
        }
        else
        {
            this.pos--;
        }
    }
}