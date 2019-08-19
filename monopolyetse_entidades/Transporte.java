package monopolyetse_entidades;

import java.util.HashMap;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;

/*As diferentes estacions so se distinguen no seu nome
non posúen caracteristicas diferenciadoras que xustifiquen
unha posterior subdivision desta clase
*/
public final class Transporte extends Propiedade
{
    public Transporte(Grupo grupo, String nome, int[] posCasilla)
    {
        super(grupo,nome, posCasilla);
    }
    
    @Override
    public float alquiler()
    {
        int casillasTransporte = 0;
        float novoAlquiler;
        
        HashMap<String, Propiedade> propiedadesPropietario = this.getPropietario().getPropiedades();
        
        //Habería que intentar eliminar este bucle
        for(String clave : propiedadesPropietario.keySet())
        {
            if(propiedadesPropietario.get(clave) instanceof Transporte)
            {
                casillasTransporte++;
            }
        }
        
        novoAlquiler = (casillasTransporte*0.25f)*Valor.prezoTransporte;
        
        this.setPrezoAlquiler(novoAlquiler);
        
        return super.alquiler();
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        super.cobroRealizado(cantidade);
        consola.imprimir("Pagaronse "+ cantidade+ "$ de transporte a " + this.getPropietario().getNomeXogador() + "!");
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
