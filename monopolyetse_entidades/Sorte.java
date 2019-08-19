package monopolyetse_entidades;

import java.util.ArrayList;

/*As casillas fillas de AccionC estan asociadas a un tipo de carta
particular. Dado que dentro deste tipo non distinguiremos outros
subtipos, non sería lóxico poder facer esta distinción para as casillas
as que están asociadas. Ademais, no xogo esta constitue un tipo
claramente de casilla e non posúe características diferenciadoras
que podan xustificar a súa subdivisión en diferentes subclases.
*/
public final class Sorte extends AccionC
{
    public Sorte(String nome, int[] posCasilla, ArrayList<Carta> baralla)
    {
        super(nome, posCasilla, baralla);
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        super.accionCasilla(xogador);
        
        return "Sorte";
    }
}
