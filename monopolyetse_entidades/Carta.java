package monopolyetse_entidades;

/*Esta clase debe ser abstracta xa que empregaremos unicamente
os seus subtipos, isto e, comunidade e sorte, de modo que non
será necesario instanciala en ningun momento
*/
public abstract class Carta 
{
    private final int numeroCarta;
    
    public Carta(int numeroCarta)
    {
        this.numeroCarta = numeroCarta;
    }
    
    public int getNumeroCarta()
    {
        return this.numeroCarta;
    }
    
    public abstract void accion();
}
