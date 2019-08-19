package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Xogador;

public final class ConPropietario extends ExcCompra
{
    public ConPropietario(Xogador propietario, Propiedade casilla)
    {
        super("A casilla xa ten propietario!"
                + "\n\t\tA propiedade " + casilla.getNome()
                + " pertence a " + propietario.getNomeXogador());
    }
}
