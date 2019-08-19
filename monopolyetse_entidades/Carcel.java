package monopolyetse_entidades;

import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;

/*Non se contempla crear subclases desta casilla,
en parte debido a que só se precisa unha instancia desta clase
*/
public final class Carcel extends Especial
{
    private final float multaCarcel;
    
    public Carcel(String nome, int[] posCasilla)
    {
        super(nome, posCasilla);
        
        this.multaCarcel = Valor.prezoSalirCarcel;
    }
    
    public float getMultaCarcel()
    {
        return this.multaCarcel;
    }
    
    @Override
    public String toString()
    {
        String s = "{\n\t"
                            + "salir:" + this.multaCarcel
                            +"\n\tjugadores:"
                            +this.xogadoresEnCarcel()
                    +"\n}";
        
        return s;
    }
    
    public String xogadoresEnCarcel()
    {
        String s = "";
        if(!this.getAvatares().isEmpty())
        {
            for(String clave : this.getAvatares().keySet())
            {
                if(this.getAvatares().get(clave).getEstarCarcel())
                {
                    s += " [" + this.getAvatares().get(clave).getXogador().getNomeXogador() + ", "
                            + this.getAvatares().get(clave).getTurnosCarcel() + "]";
                }
            }
        }
        return s;
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        consola.imprimir("Pagaronse "+ cantidade+ "$ para salir da carcel!");
    }
}
