package monopolyetse_entidades;

import monopolyetse_funcionalidades.Valor;

public class GrupoSolar extends Grupo
{
    private final float prezoHipotecaEdificio;
    private final float prezoCasa;
    private final float prezoHotel;
    private final float prezoPiscina;
    private final float prezoPista;
    
    //ESTADISTICA
    private float alquilerAcumulado;

    public GrupoSolar(String color) {
        
        super(color);
        
        prezoHipotecaEdificio = Valor.multiHipotecaEdificio * this.getPrezo();
        
        prezoCasa = Valor.multiInicialCasa * this.getPrezo();
        prezoHotel = Valor.multiInicialHotel * this.getPrezo();
        prezoPiscina = Valor.multiInicialPiscina * this.getPrezo();
        prezoPista = Valor.multiInicialPista * this.getPrezo();
        
    }

    public float getPrezoCasa() {
        return this.prezoCasa;
    }

    public float getPrezoHotel() {
        return this.prezoHotel;
    }

    public float getPrezoPiscina() {
        return this.prezoPiscina;
    }

    public float getPrezoPista() {
        return this.prezoPista;
    }
}
