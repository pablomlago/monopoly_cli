package monopolyetse_entidades;

import java.util.ArrayList;
import monopolyetse_excepcions.DineiroInsuficienteEdificar;
import monopolyetse_excepcions.ExcEdificar;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_excepcions.RequisitosEdificios;

/*De novo, ainda que existan diferentes tipos de solares 
en funcion dos seus colores, cada un deles non teñen unhas
caracteristicas diferenciadoras que xustifiquen a súa 
division en subtipos, tal como esta planteado o xogo
*/
public final class Solar extends Propiedade
{
    private final ArrayList<Edificio> edificios;
    private final GrupoSolar grupo;
    private boolean serComprada;
    
    public Solar(GrupoSolar grupo, String nome, int[] posCasilla)
    {
        super(grupo, nome, posCasilla);
        
        this.grupo = grupo;
        edificios = new ArrayList<>();
        this.serComprada = false;
    }
    
    public void foiComprada()
    {
        this.serComprada = true;
    }
    
    public boolean getSerComprada()
    {
        return this.serComprada;
    }
    
    @Override
    public GrupoSolar getGrupo()
    {
        return this.grupo;
    }
    
    @Override
    public float alquiler()
    {
        float alquilerAcumulado = super.alquiler();
        
        ArrayList<Propiedade>  solaresGrupo = this.getGrupo().getPropiedadesGrupo();
        
        int solaresPropietario = 0;
        for(Propiedade solar : solaresGrupo)
        {
            if(solar.getPropietario().equals(this.getPropietario()))
            {
                solaresPropietario++;
            }
        }
        
        if(solaresPropietario == solaresGrupo.size())
        {
            alquilerAcumulado *= 2;
        }
        
        switch (this.numCasas()) {
            case 1:
                alquilerAcumulado += this.getPrezoAlquiler() * 5;
                break;
            case 2:
                alquilerAcumulado += this.getPrezoAlquiler() * 15;
                break;
            case 3:
                alquilerAcumulado += this.getPrezoAlquiler() * 35;
                break;
            case 4:
                alquilerAcumulado += this.getPrezoAlquiler() * 50;
                break;
        }

        alquilerAcumulado += this.numHoteles() * 70 * this.getPrezoAlquiler();
        alquilerAcumulado += (this.numPistas() + this.numPiscinas()) * 25 * this.getGrupo().getPrezoAlquiler();
        
        return alquilerAcumulado;
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        String s;
        if(xogador.equals(this.getPropietario()))
        {
            s = "Edificar";
        }
        else
        {
            Grupo grupoSolar = this.getGrupo();
            boolean haiHipotecadas = false;
            for(Propiedade sol : grupoSolar.getPropiedadesGrupo())
            {
                if(this.getPropietario().getHipotecas().containsKey(sol.getNome()))
                {
                    haiHipotecadas = true;
                }
            }
            if(haiHipotecadas)
            {
                s = "";
            }
            else
            {
                s = super.accionCasilla(xogador);
            }
            
        }
        return s;
    }
    
    //Edificios
    public ArrayList getEdificios(){
            return this.edificios;
    }		


    public void engadirEdificio(Edificio edificio){
        this.edificios.add(edificio);
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        super.cobroRealizado(cantidade);
        consola.imprimir("Pagaronse "+ cantidade+ "$ de alquiler a " + this.getPropietario().getNomeXogador() + "!");
    }
    
    public void edificar(String tipoEdificio) throws ExcEdificar
    {
        switch (tipoEdificio) {
            case "casa":
                if (this.getPropietario().comprobarFortuna(this.getGrupo().getPrezoCasa()) == false) {
                    throw new DineiroInsuficienteEdificar(this.getPropietario(), "Casa", this, 
                            this.getGrupo().getPrezoCasa() - this.getPropietario().getFortuna());
                }

                this.edificarCasa();
                break;
            case "hotel":
                if (this.getPropietario().comprobarFortuna(this.getGrupo().getPrezoHotel()) == false) {
                    throw new DineiroInsuficienteEdificar(this.getPropietario(), "Hotel", this, 
                            this.getGrupo().getPrezoHotel() - this.getPropietario().getFortuna());
                }
                if (this.numCasas() < 4) {
                    throw new RequisitosEdificios(this, "hotel", this.numCasas(), this.numHoteles());
                }
                this.edificarHotel();
                break;
            case "piscina":
                if (this.getPropietario().comprobarFortuna(this.getGrupo().getPrezoPiscina()) == false) {
                    throw new DineiroInsuficienteEdificar(this.getPropietario(), "Piscina", this, 
                            this.getGrupo().getPrezoPiscina() - this.getPropietario().getFortuna());
                }
                if (this.numCasas() < 2 || this.numHoteles() < 1) {
                    throw new RequisitosEdificios(this, "piscina", this.numCasas(), this.numHoteles());
                }
                this.edificarPiscina();
                break;
            case "pista":
                if (this.getPropietario().comprobarFortuna(this.getGrupo().getPrezoPista()) == false) {
                    throw new DineiroInsuficienteEdificar(this.getPropietario(), "Pista", this, 
                            this.getGrupo().getPrezoPista() - this.getPropietario().getFortuna());
                }
                if (this.numHoteles() < 2) {
                    throw new RequisitosEdificios(this, "pista", this.numCasas(), this.numHoteles());
                }
                this.edificarPista();
                break;
            default:
                consola.imprimir("Comando incorrecto\nSintaxe: edificar <casa | hotel | piscina | pista>");
        }
    }
    
    public void edificarCasa() {
        Casa casa = new Casa(this);
        this.getEdificios().add(casa);
        this.getPropietario().engadirEdificio(casa);
        
        this.getPropietario().getCambios().engadirEdificio(casa);
        
        this.getPropietario().restarCantidade(this.getPropietario().getBanca(), casa.getPrezo(), Concepto.pagoInversion);
        consola.imprimir("Casa edificada\n"
																+ "Gastou: " + casa.getPrezo() + " $, quedanlle: " + this.getPropietario().getFortuna());
           
    }

    public void edificarHotel() {
    Hotel hotel = new Hotel(this);
        this.getEdificios().add(hotel);
        this.getPropietario().engadirEdificio(hotel);
        
        this.getPropietario().getCambios().engadirEdificio(hotel);
        
        this.getPropietario().eliminarEdificio("casa", 4, this);
        this.getPropietario().restarCantidade(this.getPropietario().getBanca(), hotel.getPrezo(), Concepto.pagoInversion);
        this.eliminarEdificio("casa", 4);

        consola.imprimir("Hotel edificado\n"
                + "Gastou: " + hotel.getPrezo() + " $, quedanlle: " + this.getPropietario().getFortuna());
    }

    public void edificarPiscina() {
        Piscina piscina = new Piscina(this);
        this.getEdificios().add(piscina);
        this.getPropietario().engadirEdificio(piscina);
        
        this.getPropietario().getCambios().engadirEdificio(piscina);
        
        this.getPropietario().restarCantidade(this.getPropietario().getBanca(), piscina.getPrezo(), Concepto.pagoInversion);
        consola.imprimir("Piscina edificada\n"
                + "Gastou: " + piscina.getPrezo() + " $, quedanlle: " + this.getPropietario().getFortuna());
    }

    public void edificarPista() {
        Pista pista = new Pista(this);
        this.getEdificios().add(pista);
        this.getPropietario().engadirEdificio(pista);
        
        this.getPropietario().getCambios().engadirEdificio(pista);
        
        this.getPropietario().restarCantidade(this.getPropietario().getBanca(), pista.getPrezo(), Concepto.pagoInversion);
        consola.imprimir("Piscina edificada\n"
                + "Gastou: " + pista.getPrezo() + " $, quedanlle: " + this.getPropietario().getFortuna());
    }
    
    public void eliminarEdificio(String tipo, int num1) {
        int num = num1;
        switch (tipo) {
            case "casa":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Casa) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "hotel":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Hotel) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "piscina":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Piscina) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "pista":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Pista) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
        }
        if (num > 0) {
            consola.imprimir("Non habia tantos edificios deste tipo, polo que se eliminaron " + (num1 - num) + " " + tipo + "s");
        }
    }

    public int numCasas() {
        int num = 0;
        for (Edificio ed : edificios) {
            if (ed instanceof Casa) {
                num++;
            }
        }
        return num;
    }

    public int numHoteles() {
        int num = 0;
        for (Edificio ed : edificios) {
            if (ed instanceof Hotel) {
                num++;
            }
        }
        return num;
    }

    public int numPiscinas() {
        int num = 0;
        for (Edificio ed : edificios) {
            if (ed instanceof Piscina) {
                num++;
            }
        }
        return num;
    }

    public int numPistas() {
        int num = 0;
        for (Edificio ed : edificios) {
            if (ed instanceof Pista) {
                num++;
            }
        }
        return num;
    }

    public void listarEdificios() {

        for (Edificio ed : this.edificios) {
            consola.imprimir(ed.toString());
        }
    }

    public void listarEdificiosGrupo() {
        String hoteles = "", casas = "", piscinas = "", pistas = "";
        for (Edificio ed : this.edificios) {
            if (ed instanceof Casa) {
                casas += ed.getIdentificador() + ", ";
            } else if (ed instanceof Hotel) {
                hoteles += ed.getIdentificador() + ", ";
            } else if (ed instanceof Piscina) {
                piscinas += ed.getIdentificador() + ", ";
            } else {
                pistas += ed.getIdentificador() + ", ";
            }
        }
        consola.imprimir("\n}" + "{"
                + "\n\tpropiedad: " + this.getNome()
                + "\n\thoteles: " + hoteles
                + "\n\tcasas: " + casas
                + "\n\tpiscinas: " + piscinas
                + "\n\tpistas: " + pistas
                + "\n\talquiler: " + this.alquiler());

    }

    @Override
    public String toString() {
        String s = "{\n\t"
                + "tipo:" + grupo.getTipo()
                + "\n\tgrupo:" + grupo.getSubtipo()
                + "\n\tpropietario:" + this.getPropietario().getNomeXogador()
                + "\n\tvalor:" + this.valor()
                + "\n\talquiler:" + this.alquiler()
                + "\n\tvalor hotel:" + grupo.getPrezoHotel()
                + "\n\tvalor casa:" + grupo.getPrezoCasa()
                + "\n\tvalor piscina:" + grupo.getPrezoPiscina()
                + "\n\tvalor pista de deporte:" + grupo.getPrezoPista()
                + "\n\talquiler de una casa:" + grupo.getPrezoAlquiler() * 5
                + "\n\talquiler de duas casas:" + grupo.getPrezoAlquiler() * 15
                + "\n\talquiler de tres casas:" + grupo.getPrezoAlquiler() * 35
                + "\n\talquiler de catro casas:" + grupo.getPrezoAlquiler() * 50
                + "\n\talquiler de hotel:" + grupo.getPrezoAlquiler() * 70
                + "\n\talquiler de piscina:" + grupo.getPrezoAlquiler() * 25
                + "\n\talquiler de piscina de deporte:" + grupo.getPrezoAlquiler() * 25
                + "\n\tedificios: ";
        for (Edificio ed : edificios) {
            s += ed.getIdentificador() + ", ";
        }
        s += "\n}";
        return s;
    }

    @Override
    public String resumenPropiedade() {
        String s = "{\n\ttipo:" + this.getGrupo().getTipo()
                + ",\n\tgrupo:" + this.getGrupo().getSubtipo()
                + ",\n\tvalor:" + this.valor()+ "\n}";

        return s;
    }
    //Funcions edificios
}
