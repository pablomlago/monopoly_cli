package monopolyetse_entidades;

import monopolyetse_funcionalidades.AccionEspecial;

/*As cartas de comunidade constituen un subtipo de cartas
diferenciado, e non se contempla distinguir dentro desta
outros subtipos, xa que non presenta atributos ou funcionalidades
que puideran ser propios unicamente dun subtipo dentro das
mesmas.
*/
public final class CartaComunidade extends Carta
{
    private final AccionEspecial accionComunidade;
    
    public CartaComunidade(int numeroCarta, AccionEspecial accionComunidade)
    {
        super(numeroCarta);
        
        this.accionComunidade = accionComunidade;
    }
    
    @Override
    public void accion()
    {
        this.accionComunidade.realizarAccionComunidade(this.getNumeroCarta());
    }
}
