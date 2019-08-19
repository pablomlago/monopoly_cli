package monopolyetse_excepcions;

import monopolyetse_entidades.Accions;

public final class AccionNonPermitida extends RestriccionAcciones
{
    private final String accionRequerida;
    private final String accionsPermitidas;
    
    public AccionNonPermitida(String accionRequerida, int accion)
    {
        super("O xogador non pode realizar a accion");
        
        switch(accion)
        {
            case Accions.TODAS:
                this.accionsPermitidas = "Todas";
                break;
            case Accions.SO_EDF:
                this.accionsPermitidas = "So edificar";
                break;
            case Accions.SO_COMPR:
                this.accionsPermitidas = "So comprar";
                break;
            case Accions.NINGUNA:
                this.accionsPermitidas = "Ningunha";
                break;
            default:
                this.accionsPermitidas = "Outros";
                break;
        }
        
        this.accionRequerida = accionRequerida;
    }
    
    public String getAccionRequerida()
    {
        return this.accionRequerida;
    }
    
    public String getAccionsPermitidas()
    {
        return this.accionsPermitidas;
    }
}
