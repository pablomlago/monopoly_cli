package monopolyetse_entidades;

import java.util.HashMap;
import monopolyetse_funcionalidades.Dado;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;

/*Tipo particular de Propiedade, pese a haber dous tipos de
servicios (electricidade e auga) a sua funcionalidade e a mesma
diferenciandose unicamente no nome de modo que non sera
necesario distinguir subtipos dentro desta
*/
public final class Servicio extends Propiedade
{
    private final Dado dado;
    
    public Servicio(Grupo grupo, String nome, Dado dado, int[] posCasilla)
    {
        super(grupo, nome, posCasilla);
        
        this.dado = dado;
    }
    
    @Override
    public float alquiler()
    {
        int casillasServicio = 0;
        float novoAlquiler;
        
        //Este fragmento podería reducirse
        HashMap<String, Propiedade> propiedadesPropietario = this.getPropietario().getPropiedades();
        
        for(String clave : propiedadesPropietario.keySet())
        {
            if(propiedadesPropietario.get(clave) instanceof Servicio)
            {
                casillasServicio++;
            }
        }
        //
        
        novoAlquiler = (2 + 8*(casillasServicio - 1))*this.dado.getResultado()*Valor.factorServicio;
        
        this.setPrezoAlquiler(novoAlquiler);
        
        return super.alquiler();
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        super.cobroRealizado(cantidade);
        consola.imprimir("Pagaronse "+ cantidade+ "$ de servicio a " + this.getPropietario().getNomeXogador() + "!");
    }
    
    @Override
    public String toString()
    {
        String s = "{\n\t"
                            + "tipo:"+this.getGrupo().getTipo()
                            +"\n\tpropietario:"+this.getPropietario().getNomeXogador()
                    +"\n}";
        
        return s;
    }
}
