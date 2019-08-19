package monopolyetse_excepcions;

import java.util.ArrayList;
import monopolyetse_entidades.Propiedade;

public final class HipotecadaGrupo extends ExcHipoteca
{
    public HipotecadaGrupo(ArrayList<Propiedade> casillasHipotecadas, String colorGrupo)
    {
        super("Hai casillas hipotecadas no grupo!"
         + "\n\n\tAs casillas " + casillasHipotecadas
        + "atopanse hipotecadas dentro do grupo " + colorGrupo);
    }
}
