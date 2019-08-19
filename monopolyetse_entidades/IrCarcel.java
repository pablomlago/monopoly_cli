package monopolyetse_entidades;

/*Este e un tipo moi particular de casilla, cunha funcionalidade
claramente diferenciada polo que non será nunca necesario
distinguir subtipos dentro desta
*/
public final class IrCarcel extends Especial
{
    public IrCarcel(String nome, int[] posCasilla)
    {
        super(nome, posCasilla);
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        super.accionCasilla(xogador);
        
        return "IrCarcel";
    }
}
