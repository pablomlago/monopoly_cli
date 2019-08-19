package monopolyetse_excepcions;

public class RestriccionAcciones extends MonopolyExcepcion
{
    public RestriccionAcciones(String mensaxe)
    {
        super("\n\tRestriccion accion: " + mensaxe);
    }
}