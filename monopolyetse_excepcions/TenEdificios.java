package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;

public final class TenEdificios extends ExcHipoteca
{
    public TenEdificios(Propiedade propiedade)
    {
        super("A casilla ten edificios!" 
            + "\n\t\tEn " + propiedade.getNome() + " ten edificios construidos");
    }
}
