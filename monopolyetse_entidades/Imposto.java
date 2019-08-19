package monopolyetse_entidades;

import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;


public class Imposto extends Casilla
{
    private final float valorImposto;
    
    public Imposto(String nome, float valorImposto, int[] posCasilla)
    {
        super(nome, posCasilla);
        //falta inicializar valor imposto
        
        this.valorImposto = valorImposto;
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        String s = "Imposto";
        
        return s;
    }
    
    public float getValorImposto()
    {
        return this.valorImposto;
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        consola.imprimir("Pagaronse "+ cantidade+ "$ de imposto!");
    }
    
    @Override
    public String toString()
    {
        String s = "{\n\t"
                            + "tipo: Imposto"
                            +"\n\tapagar:"+ this.valorImposto
                    +"\n}";
        
        return s;
    }
    
    public String xogadoresImposto()
    {
        String s = "";
        if(!this.getAvatares().isEmpty())
        {
           for(String clave : this.getAvatares().keySet())
            {
                s += " " + this.getAvatares().get(clave).getXogador().getNomeXogador() + " ";
            }
        }
        return s;
    }
    
    @Override
    public String getCodigoColor()
    {
        return Valor.cAmarelo;
    }
}
