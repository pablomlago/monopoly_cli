package monopolyetse_excepcions;

import monopolyetse_entidades.Xogador;

public final class NonPosue extends ExcXogador
{
    public NonPosue(Xogador xogador, String nomePropiedade)
    {
        super("Non posue a propiedade!" +
                "\n\t\t" + xogador.getNomeXogador()
                + " non posue " + nomePropiedade);
    }
    
}
