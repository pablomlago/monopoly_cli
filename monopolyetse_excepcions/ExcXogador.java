package monopolyetse_excepcions;

public class ExcXogador extends MonopolyExcepcion
{
    public ExcXogador(String mensaxe)
    {
        super("\n\tXogador: " + mensaxe);
    }
}
