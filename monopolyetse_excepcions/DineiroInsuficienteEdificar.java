package monopolyetse_excepcions;

import monopolyetse_entidades.Solar;
import monopolyetse_entidades.Xogador;

public final class DineiroInsuficienteEdificar extends ExcEdificar
{
    public DineiroInsuficienteEdificar(Xogador xogador, String tipoEdificio, Solar solar, float cantidadePrecisada)
    {
        super("O xogador non ten dineiro insuficiente para edificar!"
            + xogador.getNomeXogador() + " precisa " + cantidadePrecisada
            + "$ mais para construir " + tipoEdificio + " en " + solar.getNome());
    }
}
