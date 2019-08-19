package monopolyetse_excepcions;

import monopolyetse_entidades.Xogador;
import monopolyetse_funcionalidades.Trato;

public final class DineiroInsuficienteTrato extends ExcTrato
{
    public DineiroInsuficienteTrato(Xogador xogador, String rol, Trato trato, float cantidadeRestante)
    {
        super("O " + rol + " non ten diñeiro suficiente!"
            + "\n\t\t" + xogador.getNomeXogador() + " precisa "
            + cantidadeRestante + " para poder realizar o seguinte trato: " + trato.toString());
    }
}
