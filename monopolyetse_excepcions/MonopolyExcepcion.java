package monopolyetse_excepcions;

/*Clase raiz das instancias do monopoly, dentro dela interesan
os seus subtipos polo que esta nunca se instanciara
*/
public abstract class MonopolyExcepcion extends Exception
{
    public MonopolyExcepcion(String mensaxe)
    {
        super("Monopoly: " + mensaxe);
    }
}
