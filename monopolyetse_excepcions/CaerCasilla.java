package monopolyetse_excepcions;

import monopolyetse_entidades.Casilla;
import monopolyetse_entidades.Xogador;

public final class CaerCasilla extends RestriccionAcciones
{
    public CaerCasilla(Xogador xogador, Casilla casilla)
    {
        super("O xogador non esta na casilla!"
            + "\n\t\t" + xogador.getNomeXogador() + " esta en "
            + xogador.getAvatar().getCasilla().getNome() + " en lugar de en "
            + casilla.getNome());
    }
}
