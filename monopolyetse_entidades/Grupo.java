package monopolyetse_entidades;

import java.util.ArrayList;
import monopolyetse_funcionalidades.Valor;
 //Adaptar toString
public class Grupo {
    
    private final ArrayList<Propiedade> propiedadesGrupo;
    
    private final String subtipo;
    private final String codigoColor;
    private final String tipo;
    
    private final float prezoGrupo;
    private final float prezoAlquiler;
    private final float prezoHipotecaCasilla;
    
    //ESTADISTICA
    private float alquilerAcumulado;

    public Grupo(String subtipo) {
        this.subtipo = subtipo;
        switch (subtipo) {
            case "Marron":
                prezoGrupo = Valor.prezoMarron;
                tipo = "Solar";
                codigoColor = Valor.cNegro;
                break;
            case "Cian":
                prezoGrupo = Valor.prezoCian;
                tipo = "Solar";
                codigoColor = Valor.cCian;
                break;
            case "Rosa":
                prezoGrupo = Valor.prezoRosa;
                tipo = "Solar";
                codigoColor = Valor.cRosa;
                break;
            case "Laranxa":
                prezoGrupo = Valor.prezoLaranxa;
                tipo = "Solar";
                codigoColor = Valor.cVermello;
                break;
            case "Vermello":
                prezoGrupo = Valor.prezoVermello;
                tipo = "Solar";
                codigoColor = Valor.cVermello;
                break;
            case "Amarelo":
                prezoGrupo = Valor.prezoAmarelo;
                tipo = "Solar";
                codigoColor = Valor.cAmarelo;
                break;
            case "Verde":
                prezoGrupo = Valor.prezoVerde;
                tipo = "Solar";
                codigoColor = Valor.cVerde;
                break;
            case "Azul":
                prezoGrupo = Valor.prezoAzul;
                tipo = "Solar";
                codigoColor = Valor.cAzul;
                break;
            case "Transporte":
                prezoGrupo = Valor.prezoTransporte;
                tipo = "Transporte";
                codigoColor = Valor.cNegro;
                break;
            case "Servicio":
                prezoGrupo = Valor.prezoServicio;
                tipo = "Servicio";
                codigoColor = Valor.cNegro;
                break;

            default:
                prezoGrupo = 0;
                tipo = "";
                codigoColor = Valor.cNegro;
                break;
                
        }
        this.propiedadesGrupo = new ArrayList<>();
        
        prezoAlquiler = Valor.multiAlquiler * prezoGrupo;
        prezoHipotecaCasilla = Valor.multiHipotecaCasilla * prezoGrupo;
        
        //ESTADISTICA
        this.alquilerAcumulado = 0;
    }

    public final float getPrezo() {
        return prezoGrupo;
    }

    public float getPrezoHipotecaCasilla() {
        return prezoHipotecaCasilla;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getSubtipo() {
        return this.subtipo;
    }

    public String getCodigoColor() {
        return this.codigoColor;
    }

    public float getPrezoAlquiler() {
        return this.prezoAlquiler;
    }
    
    public float getAlquilerAcumulado()
    {
        return this.alquilerAcumulado;
    }
    
    public ArrayList<Propiedade> getPropiedadesGrupo()
    {
        return this.propiedadesGrupo;
    }
    
    public void engadirPropiedade(Propiedade propiedade)
    {
        if(propiedade != null)
        {
            this.propiedadesGrupo.add(propiedade);
        }
    }
    
    public void sumarAlquilerAcumulado(float cantidade)
    {
        this.alquilerAcumulado += cantidade;
    }
}