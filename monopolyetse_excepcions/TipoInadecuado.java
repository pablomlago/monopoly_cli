package monopolyetse_excepcions;

import monopolyetse_entidades.Casilla;
import monopolyetse_entidades.Comunidade;
import monopolyetse_entidades.Especial;
import monopolyetse_entidades.Imposto;
import monopolyetse_entidades.IrCarcel;
import monopolyetse_entidades.Parking;
import monopolyetse_entidades.Servicio;
import monopolyetse_entidades.Solar;
import monopolyetse_entidades.Sorte;
import monopolyetse_entidades.Transporte;

public final class TipoInadecuado extends MonopolyExcepcion
{
    private final String tipoPrecisado;
    private final String tipoCasilla;
    
    public TipoInadecuado(String tipoPrecisado, Casilla casilla)
    {
        super("Tipo inadecuado!");
        
        if(casilla instanceof Solar)
        {
            this.tipoCasilla = "Casilla -> Propiedade -> Solar";
        }
        else if(casilla instanceof Servicio)
        {
            this.tipoCasilla = "Casilla -> Propiedade -> Servicio";
        }
        else if(casilla instanceof Transporte)
        {
            this.tipoCasilla = "Casilla -> Propiedade -> Transporte";
        }
        else if(casilla instanceof Imposto)
        {
            this.tipoCasilla = "Casilla -> Imposto";
        }
        else if(casilla instanceof Parking)
        {
            this.tipoCasilla = "Casilla -> Especial -> Parking";
        }
        else if(casilla instanceof IrCarcel)
        {
            this.tipoCasilla = "Casilla -> Especial -> IrCarcel";
        }
        else if(casilla instanceof Sorte)
        {
            this.tipoCasilla = "Casilla -> Accion -> Sorte";
        }
        else if(casilla instanceof Comunidade)
        {
            this.tipoCasilla = "Casilla -> Accion -> Comunidade";
        }
        else if(casilla instanceof Especial)
        {
            this.tipoCasilla = "Casilla -> Especial";
        }
        else
        {
            this.tipoCasilla = "Tipo desconocido";
        }
        
        this.tipoPrecisado = tipoPrecisado;
    }
    
    public String getTipoCasilla()
    {
        return this.tipoCasilla;
    }
    
    public String getTipoPrecisado()
    {
        return this.tipoPrecisado;
    }
}
