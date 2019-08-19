/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_entidades;

import java.util.HashMap;

public abstract class Casilla implements ICasilla{
    
    private final String nome; //Casilla
    //protected final Grupo grupo; //Casilla
    private final HashMap<String, Avatar> avatares; //Casilla
    
    private int vecesFrecuentada;
    
    private final int[] posCasilla;
    
    /*public Casilla (Grupo grupo, String nome, int[] posCasilla){
        //Deberia ser o único no constructor
        this.nome=nome;
        //this.grupo=grupo;
        avatares=new HashMap<>();
        
        this.vecesFrecuentada = 0;
        
        this.posCasilla = new int[2];
        this.posCasilla[0] = posCasilla[0];
        this.posCasilla[1] = posCasilla[1];
    }
    
    public Casilla (String subtipo, String nome, int[] posCasilla)
    {
        this.nome=nome;
        //this.grupo= new Grupo(subtipo);
        avatares=new HashMap<>();
        
        this.vecesFrecuentada = 0;
        
        this.posCasilla = new int[2];
        this.posCasilla[0] = posCasilla[0];
        this.posCasilla[1] = posCasilla[1];
    }*/
    
    public Casilla (String nome, int[] posCasilla)
    {
        this.nome=nome;
        avatares=new HashMap<>();
        
        this.vecesFrecuentada = 0;
        
        this.posCasilla = new int[2];
        this.posCasilla[0] = posCasilla[0];
        this.posCasilla[1] = posCasilla[1];
    }
    
    //Poderiamolo ter que pasar a abstract
    public abstract void cobroRealizado(float cantidade); //Revisar as súas aparicions
    public abstract String getCodigoColor();
    
    
    public String getNome()
    {
        return this.nome;
    }
    
    /*public Grupo getGrupo()
    {
        return this.grupo;
    }*/
    
    public HashMap<String, Avatar> getAvatares()
    {
        return this.avatares;
    }
    
    @Override
    public int frecuenciaVisita()
    {
        return this.vecesFrecuentada;
    }
    
    @Override
    public boolean estaAvatar(String codigo)
    {
        return this.avatares.containsKey(codigo);
    }
    
    public int[] getPosCasilla()
    {
        return posCasilla;
    }
    
    public void introducirAvatar(Avatar avatar)
    {
        avatares.put(avatar.getCodigo(), avatar);
    }
    
    public void introducirAvatar(Xogador xogador)
    {
        avatares.put(xogador.getAvatar().getCodigo(), xogador.getAvatar());
    }
    
    public void eliminarAvatar(Avatar avatar)
    {
        this.avatares.remove(avatar.getCodigo());
    }
    
    public String imprimirAvatares()
    {
        if(!this.avatares.isEmpty()){
               String s=" &"+this.avatares.keySet();
               return s;
            }  
        return "";
    }
    
    public String accionCasilla(Xogador xogador)
    {
        this.vecesFrecuentada++;
                
        return "";
    }
   
    public String xogadoresEnCasilla()
    {
        String s = "";
        if(!this.avatares.isEmpty())
        {
           
            for(String clave : this.avatares.keySet())
            {
                s += " " + this.avatares.get(clave).getXogador().getNomeXogador() + " ";
            }
           
        }
        
        return s;
    }
        
    @Override
    public String toString()
    {
        String  s = "{\n\t"
                            + "jugadores: ["
                            + this.xogadoresEnCasilla()
                            + "]"
                    +"\n}";
        
        return s;
    }
}
