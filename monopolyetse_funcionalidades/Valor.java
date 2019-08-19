/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_funcionalidades;

/**
 *
 * @author pablo
 */
public interface Valor {
    public static final float prezoMarron = 1200f;
    public static final float prezoCian = (float) (Valor.prezoMarron*1.3);
    public static final float prezoRosa = (float) (Valor.prezoCian*1.3);
    public static final float prezoLaranxa = (float) (Valor.prezoRosa*1.3);
    public static final float prezoVermello = (float) (Valor.prezoLaranxa*1.3);
    public static final float prezoAmarelo = (float) (Valor.prezoVermello*1.3);
    public static final float prezoVerde = (float) (Valor.prezoAmarelo*1.3);
    public static final float prezoAzul= (float) (Valor.prezoVerde*1.3);
    
    public static final int numMarron = 2;
    public static final int numCian = 3;
    public static final int numRosa = 3;
    public static final int numLaranxa = 3;
    public static final int numVermello = 3;
    public static final int numAmarelo = 3;
    public static final int numVerde = 3;
    public static final int numAzul = 2;
    
    public static final float prezoTotal = Valor.numMarron*Valor.prezoMarron + Valor.numCian*Valor.prezoCian
                + Valor.numRosa*Valor.prezoRosa + Valor.numLaranxa*Valor.prezoLaranxa
                + Valor.numVermello*Valor.prezoVermello + Valor.numAmarelo*Valor.prezoAmarelo
                + Valor.numVerde*Valor.prezoVerde + Valor.numAzul*Valor.prezoAzul;
    
   public static final int numeroSolares =Valor.numMarron + Valor.numCian + Valor.numRosa
                + Valor.numLaranxa + Valor.numVermello + Valor.numAmarelo
                + Valor.numVerde + Valor.numAzul;
    
    public static final float dineiroInicio = Valor.prezoTotal/3;
    public static final float dineiroSalida = Valor.prezoTotal/Valor.numeroSolares;
    
    public static final float prezoTransporte = Valor.dineiroSalida;
    public static final float prezoServicio = Valor.dineiroSalida*0.75f; 
    public static final float factorServicio = Valor.dineiroSalida*0.005f; //Cambiar
    public static final float prezoImposto1=Valor.dineiroSalida;
    public static final float prezoImposto2=Valor.dineiroSalida*0.50f;
    public static final float prezoSalirCarcel = Valor.dineiroSalida*0.25f;
    
    //Multiplicanse polo prezo do solar ao que corresponden
    public static final float multiAlquiler = 0.10f;
    public static final float multiInicialCasa = 0.60f;
    public static final float multiInicialHotel = 0.60f;
    public static final float multiInicialPista = 1.25f;
    public static final float multiInicialPiscina = 1.25f;
    
    //
    public static final float multiHipotecaCasilla = 0.5f;
    public static final float multiVentaEdificio = 0.5f;
    public static final float multiHipotecaEdificio= 0.5f;
    
    public static final float multiCargoDeshipotecar = 1.1f;
    
    public static final float multiIncrementoSolar = 1.05f;
    
    public static final String cNegro="\033[30m";
    public static final String cVermello="\033[31m";
    public static final String cVerde="\033[32m";
    public static final String cAmarelo="\033[33m";
    public static final String cAzul="\033[34m";
    public static final String cRosa ="\033[35m";
    public static final String cCian="\033[36m";
    //public static final String Marron="\033[37m";
    //public static final String Laranxa="\033["
    //public static final String Marron="\033[37m";
    //public static final String Laranxa="\033["
    
    //Alquileres
    
    public static final int numeroCartasSorte = 14;
    public static final int numeroCartasComunidade = 10;
}
