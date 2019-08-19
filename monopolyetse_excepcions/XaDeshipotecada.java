package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;

public final class XaDeshipotecada extends ExcDeshipotecar
{
    public XaDeshipotecada(Propiedade propiedade)
    {
        super("A casilla xa está deshipotecada!"
            + "\n\t\t" + propiedade.getNome() + " non esta hipotecada");
    }
}
