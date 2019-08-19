package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;

public final class XaHipotecada extends ExcHipoteca
{
    public XaHipotecada(Propiedade propiedade)
    {
        super("Xa hipotecada!"
            + "\n\t\tA propiedade " + propiedade.getNome()
            + " xa foi hipotecada previamente");
    }
}
