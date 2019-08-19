package monopolyetse_entidades;

import monopolyetse_funcionalidades.AccionEspecial;

/*As cartas de sorte constituen un subtipo de cartas
diferenciado, e non se contempla distinguir dentro desta
outros subtipos, xa que non presenta atributos ou funcionalidades
que puideran ser propios unicamente dun subtipo dentro das
mesmas.
*/
public class CartaSorte extends Carta
{
    private final AccionEspecial accionSorte;
    
    public CartaSorte(int numeroCarta, AccionEspecial accionSorte)
    {
        super(numeroCarta);
        
        this.accionSorte = accionSorte;
    }
    
    @Override
    public void accion()
    {
        this.accionSorte.realizarAccionSorte(this.getNumeroCarta());
    }
}
