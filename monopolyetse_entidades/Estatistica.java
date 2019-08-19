package monopolyetse_entidades;

import java.util.HashMap;
import java.util.Set;

public class Estatistica 
{
    private float dineiroInvertido;
    private float pagoTasasImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasoCasillaSaida;
    private float premiosInversiones;
    private int vecesNaCarcel;
    private int lanzamentosDados;
    
    private float valorAcumulado;
    
    private final Xogador xogador;
    
    public Estatistica(Xogador xogador)
    {
        this.dineiroInvertido = 0.0f;
        this.pagoTasasImpuestos = 0.0f;
        this.pagoDeAlquileres = 0.0f;
        this.cobroDeAlquileres  = 0.0f;
        this.pasoCasillaSaida = 0.0f;
        this.premiosInversiones = 0.0f;
        this.vecesNaCarcel = 0;
        this.lanzamentosDados = 0;
        
        this.valorAcumulado = 0.0f;
        
        this.xogador = xogador;
    }
    
    public int getLanzamentosDados()
    {
        return this.lanzamentosDados;
    }
    
    public float getValorAcumulado()
    {
        return this.valorAcumulado;
    }
    
    public void sumarInvertido(float cantidade)
    {
        this.dineiroInvertido += cantidade;
    }
    
    public void sumarPagoTasasImpuestos(float cantidade)
    {
        this.pagoTasasImpuestos += cantidade;
    }
    
    public void sumarPagoDeAlquileres(float cantidade)
    {
        this.pagoDeAlquileres += cantidade;
    }
    
    public void sumarCobroDeAlquileres(float cantidade)
    {
        this.cobroDeAlquileres += cantidade;
    }
    
    public void sumarPasoCasillaSaida(float cantidade)
    {
        this.pasoCasillaSaida += cantidade;
    }
    
    public void sumarPremiosInversiones(float cantidade)
    {
        this.premiosInversiones += cantidade;
    }
    
    public void sumarVecesNaCarcel()
    {
        this.vecesNaCarcel++;
    }
    
    public void sumarLanzamentosDados()
    {
        this.lanzamentosDados++;
    }
    
    public void calcularValorAcumulado()
    {
        float valorAcumuladoEdificios = 0.0f;
        float valorAcumuladoPropiedades = 0.0f;
        
        HashMap<String, Propiedade> propiedades = this.xogador.getPropiedades();
        Set<String> claves = propiedades.keySet();
        
        for(String clave :  claves)
        {
            valorAcumuladoPropiedades += propiedades.get(clave).valor();
        }
        
        for(Edificio ed : this.xogador.getEdificios())
        {
            valorAcumuladoEdificios += ed.getPrezo();
            //Sumar o valor acumulado dos edificios
        }
        
        this.valorAcumulado = this.xogador.getFortuna() + valorAcumuladoEdificios + valorAcumuladoPropiedades;
    }
    
    @Override
    public String toString()
    {
        String s = "";
        s += "{\n"
                + "\tdineroInvertido:" + this.dineiroInvertido
                + "\n\tpagoTasasEImpuestos:" + this.pagoTasasImpuestos
                + "\n\tpagoDeAlquileres:" + this.pagoDeAlquileres
                + "\n\tcobroDeAlquileres:" + this.cobroDeAlquileres
                + "\n\tpasoPorCasillaDeSalida:" + this.pasoCasillaSaida
                + "\n\tpremiosInversionesOBote:" + this.premiosInversiones
                + "\n\tvecesEnLaCarcel:" + this.vecesNaCarcel + "\n}";
        
        return s;
    }
}
