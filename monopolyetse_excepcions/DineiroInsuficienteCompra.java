package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Xogador;

public final class DineiroInsuficienteCompra extends ExcCompra
{
    public DineiroInsuficienteCompra(Xogador xogador, Propiedade propiedade, float cantidadePrecisada)
    {
        super("Dinero insuficiente!" + 
                "\n\t\t" + xogador.getNomeXogador() + " precisa "
                + cantidadePrecisada + "$ para poder comprar "
                + propiedade.getNome());
    }
}
