package monopolyetse_excepcions;

import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Xogador;

public final class DineiroInsuficienteDeshipotecar extends ExcDeshipotecar
{
    public DineiroInsuficienteDeshipotecar(Xogador xogador, Propiedade propiedade, float cantidadeRequerida)
    {
        super("O xogador non ten dineiro suficiente para deshipotecar!"
            + "\n\t\t" + xogador.getNomeXogador() + " precisa " + cantidadeRequerida
                    + "$ mais para poder deshipotecar " + propiedade.getNome());
    }
}
