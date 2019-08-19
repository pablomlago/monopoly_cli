package monopolyetse_excepcions;

import monopolyetse_entidades.Xogador;

public final class NonHaiTratos extends ExcTrato
{
    public NonHaiTratos(Xogador xogador)
    {
        super("Non hai tratos!"
            + "\n\t\t" + xogador.getNomeXogador() + " non ten  trato recibido");
    }
}
