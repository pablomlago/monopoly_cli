package monopolyetse_excepcions;

import monopolyetse_entidades.Solar;

public final class RequisitosEdificios extends ExcEdificar
{
    private final String requerimento;
    private final String tipoRequerido;
    private final Solar solar;
    
    public RequisitosEdificios(Solar solar, String tipoRequerido, int numCasas, int numHoteis)
    {
        super("Non se cumplen os requisitos de construccion!");
        
        switch(tipoRequerido)
        {
            case "hotel":
                this.requerimento = "Precisanse 4 casas para construir un hotel. So hai " + numCasas;
                break;
            case "piscina":
                this.requerimento = "Precisanse 2 casas (hai " + numCasas
                        + ") e 1 hotel (hai " + numHoteis + ") para construir unha piscina";
                break;
            case "pista":
                this.requerimento = "Precisanse 2 hoteis para construir unha pista. So hai " + numHoteis;
                break;
            default:
                this.requerimento = "Non existe este edificio";
                break;
        }
        
        this.tipoRequerido = tipoRequerido;
        this.solar = solar;
    }
    
    public String getRequerimento()
    {
        return this.requerimento;
    }
    
    public String getTipoRequerido()
    {
        return this.tipoRequerido;
    }
    
    public Solar getSolar()
    {
        return this.solar;
    }
}
