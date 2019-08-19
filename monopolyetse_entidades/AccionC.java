package monopolyetse_entidades;

import java.util.ArrayList;
import java.util.Collections;
import monopolyetse_funcionalidades.Valor;

public class AccionC extends Casilla
{
    private final ArrayList<Carta> baralla;
    
    public AccionC(String nome, int[] posCasilla, ArrayList<Carta> baralla)
    {
        super(nome, posCasilla);
        
        this.baralla = baralla;
    }
    
    public void barallar()
    {
        Collections.shuffle(baralla);
    }
    
    @Override
    public String getCodigoColor()
    {
        return Valor.cCian;
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        
    }
    
    public void accion(int numCarta)
    {
        this.baralla.get(numCarta).accion();
    }
}
