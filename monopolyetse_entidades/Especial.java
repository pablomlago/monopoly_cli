package monopolyetse_entidades;

import monopolyetse_funcionalidades.Valor;

public class Especial extends Casilla //Finalizar
{
    public Especial(String nome, int[] posCasilla)
    {
        super(nome, posCasilla);
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        return "";
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        
    }
    
    @Override
    public String getCodigoColor()
    {
        return Valor.cNegro;
    }
}
