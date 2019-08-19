package monopolyetse_entidades;

import java.util.ArrayList;
import monopolyetse_funcionalidades.Valor;

import java.util.HashMap;
import java.util.Set;
import monopolyetse_funcionalidades.Cambios;
import monopolyetse_excepcions.DineiroInsuficienteDeshipotecar;
import monopolyetse_excepcions.ExcDeshipotecar;
import monopolyetse_excepcions.ExcHipoteca;
import monopolyetse_excepcions.ExcXogador;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.NoAlquiler;
import monopolyetse_excepcions.NonPosue;
import monopolyetse_excepcions.TenEdificios;
import monopolyetse_funcionalidades.Trato;
import monopolyetse_excepcions.XaDeshipotecada;
import monopolyetse_excepcions.XaHipotecada;

public class Xogador {

    private final String nome;
    private Avatar avatar;

    private final Banca banca;

    private final Cambios cambios;

    private final HashMap<String, Propiedade> propiedades;
    private final HashMap<String, Propiedade> hipotecas;
    private final ArrayList<Edificio> edificios;
    private final ArrayList<Trato> tratosRecibidos;
    private final ArrayList<Trato> tratosEmitidos;
    private final ArrayList<NoAlquiler> noAlquileres;
    //Non empregaremos un setter para fortuna xa que as operacións aplicables
    //sobre este atributo son moi restrinxidas e, empregando funcións
    //particulares, logramos unha maior opacidade e garantizamos
    //a coherencia deste atributo
    private float fortuna;

    //O mellor é necesario eliminar despois este constructor
    //xa que estará implementando para a subclase banca
    private int accionsPermitidas;
    //0: Todas
    //1: Só edificar
    //2: Só comprar
    //3: Nin edificar nin comprar

    //ESTADISTICAS
    private final Estatistica estatistica;

    public Xogador() {
        this.nome = "Banca";
        this.fortuna = Float.POSITIVE_INFINITY;
        this.propiedades = new HashMap<>();
        this.hipotecas = new HashMap<>();
        this.edificios = new ArrayList<>();
        this.avatar = null;
        
        this.tratosEmitidos = null;
        this.tratosRecibidos = null;
        this.noAlquileres = null;
        
        this.estatistica = new Estatistica(this);
        this.accionsPermitidas = 0;
        this.cambios = new Cambios();
        this.banca = null;
    }

    public Xogador(Banca banca, String nome) {
        this.nome = nome;
        this.fortuna = Valor.dineiroInicio;
        this.propiedades = new HashMap<>();
        this.hipotecas = new HashMap<>();
        this.edificios = new ArrayList<>();
        this.avatar = null;
        
        this.tratosEmitidos = new ArrayList<>();
        this.tratosRecibidos = new ArrayList<>();
        this.noAlquileres = new ArrayList<>();
        
        this.accionsPermitidas = 0;
        this.estatistica = new Estatistica(this);
        this.cambios = new Cambios();
        this.banca = banca;
    }

    public String getNomeXogador() {
        return this.nome;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public float getFortuna() {
        return this.fortuna;
    }

    public HashMap<String, Propiedade> getPropiedades() {
        return this.propiedades;
    }

    public HashMap<String, Propiedade> getHipotecas() {
        return this.hipotecas;
    }

    public ArrayList<Edificio> getEdificios() {
        return this.edificios;
    }

    public int getAccionsPermitidas() {
        return this.accionsPermitidas;
    }

    public Estatistica getEstatistica() {
        return this.estatistica;
    }

    public Cambios getCambios() {
        return this.cambios;
    }

    public Banca getBanca() {
        return this.banca;
    }

    public ArrayList<Trato> getTratosRecibidos() {
        return this.tratosRecibidos;
    }

    public ArrayList<Trato> getTratosEmitidos() {
        return this.tratosEmitidos;
    }
    
    public ArrayList<NoAlquiler> getNoAlquileres()
    {
        return this.noAlquileres;
    }
    
    public void setAvatar(Avatar avatar) {
        if (avatar != null) {
            this.avatar = avatar;
        } else {
            //Non podemos asignar un novo avatar ao xogador
            consola.imprimir("Este xogador xa ten avatar");
        }
    }

    public void setAccionsPermitidas(int accionsPermitidas) {
        this.accionsPermitidas = accionsPermitidas;
    }

    public void sumarCantidade(Xogador emisor, float cantidade, int concepto) {
        if (emisor != null) {
            this.cambios.engadirPago(emisor, cantidade, concepto);
        }

        this.fortuna += cantidade;

        switch (concepto) {
            case Concepto.cobroPagoAlquiler: //1
                this.estatistica.sumarCobroDeAlquileres(cantidade);
                break;
            case Concepto.cobroPagoPremioImposto: //3
                this.estatistica.sumarPremiosInversiones(cantidade);
                break;
            case Concepto.cobroSaida: //4
                this.estatistica.sumarPasoCasillaSaida(cantidade);
                break;
        }
    }

    //No caso de quedar sen diñeiro o xogador será
    //necesario devolver a canto ascende a súa débeda
    public float restarCantidade(Xogador receptor, float cantidade, int concepto) {
        if (receptor != null) {
            this.cambios.engadirPago(receptor, -cantidade, concepto);
        }

        float debeda = 0.0f;

        this.fortuna -= cantidade;

        if (this.fortuna < 0) {
            debeda = Math.abs(this.fortuna);
            this.fortuna = 0.0f;
        }

        switch (concepto) {
            case Concepto.cobroPagoAlquiler: //1
                this.estatistica.sumarPagoDeAlquileres(cantidade);
                break;
            case Concepto.pagoInversion: //2
                this.estatistica.sumarInvertido(cantidade);
                break;
            case Concepto.cobroPagoPremioImposto: //3
                this.estatistica.sumarPagoTasasImpuestos(cantidade);
                break;
        }

        return debeda;
    }

    public boolean tenPropiedades() {
        boolean terPropiedades = true;

        if (this.propiedades.isEmpty()) {
            terPropiedades = false;
        }

        return terPropiedades;
    }

    //Permite a transferencia total de propiedades e hipotecas
    //entre dous xogadores
    public void recibirPropiedades(Xogador xogador) {
        xogador.getPropiedades().putAll(this.propiedades);
        xogador.getHipotecas().putAll(this.hipotecas);

        Set<String> claves = xogador.getPropiedades().keySet();

        for (String clave : claves) {
            xogador.getPropiedades().get(clave).setPropietario(xogador);
        }

        claves = xogador.getHipotecas().keySet();

        for (String clave : claves) {
            xogador.getHipotecas().get(clave).setPropietario(xogador);
        }

        this.propiedades.clear();
        this.hipotecas.clear();
    }

    public void transferirPropiedade(Xogador xogador, Propiedade propiedade) {
        xogador.getPropiedades().put(propiedade.getNome(), propiedade);

        propiedade.setPropietario(xogador);

        this.propiedades.remove(propiedade.getNome());
    }

    //Podemos obter a cantidade total de diñeiro que podería chegar a obter
    //o xogador de hipotecar todas as súas propiedades
    public float acumuladoHipotecas() {
        float acumulado = 0.0f;

        for (String clave : this.propiedades.keySet()) {
            acumulado += this.propiedades.get(clave).getGrupo().getPrezoHipotecaCasilla();
        }

        return acumulado;
    }

    public float acumuladoEdificios() {
        float acumulado = 0.0f;

        for (Edificio edificio : this.edificios) {
            acumulado += edificio.getPrezo();
        }

        return acumulado;
    }

    public float hipotecarPropiedade(String nomePropiedade) throws ExcHipoteca, ExcXogador {
        float prezoHipoteca = 0.0f;

        if (this.propiedades.containsKey(nomePropiedade)) {
            Propiedade propiedade = this.propiedades.get(nomePropiedade);

            if (propiedade instanceof Solar && !((Solar) propiedade).getEdificios().isEmpty()) {
                throw new TenEdificios(propiedade);
            } else {
                prezoHipoteca = propiedade.getGrupo().getPrezoHipotecaCasilla();
                this.sumarCantidade(this.banca, prezoHipoteca, Concepto.pagoInversion);

                this.hipotecas.put(propiedade.getNome(), propiedade);
                this.propiedades.remove(nomePropiedade);

                if (propiedade instanceof Solar) {
                    consola.imprimir(this.nome + " recibe " + prezoHipoteca + " pola hipoteca de " + nomePropiedade
                            + ". Non pode recibir alquileres nin edificar no grupo " + propiedade.getGrupo().getSubtipo());
                } else {
                    consola.imprimir(this.nome + " recibe " + prezoHipoteca + " pola hipoteca de " + nomePropiedade);
                }
            }
        } else if (this.hipotecas.containsKey(nomePropiedade)) {
            throw new XaHipotecada(this.hipotecas.get(nomePropiedade));
        } else {
            throw new NonPosue(this, nomePropiedade);
        }

        return prezoHipoteca;
    }

    public void deshipotecarPropiedade(String nomeHipoteca) throws ExcDeshipotecar, ExcXogador {
        float prezoHipoteca = 0.0f;

        if (this.hipotecas.containsKey(nomeHipoteca)) {
            Propiedade propiedade = this.hipotecas.get(nomeHipoteca);
            prezoHipoteca = propiedade.getGrupo().getPrezoHipotecaCasilla();
            if (this.fortuna >= prezoHipoteca) {
                this.restarCantidade(this.banca, prezoHipoteca, Concepto.pagoInversion);

                this.propiedades.put(nomeHipoteca, propiedade);
                this.hipotecas.remove(nomeHipoteca);

                if (propiedade instanceof Solar) {
                    consola.imprimir(this.nome + " paga " + prezoHipoteca + "por deshipotecar de " + nomeHipoteca
                            + ". Non pode recibir alquileres nin edificar no grupo " + propiedade.getGrupo().getSubtipo());
                } else {
                    consola.imprimir(this.nome + " paga " + prezoHipoteca + " por deshipotecar de " + nomeHipoteca);
                }
            } else {
                throw new DineiroInsuficienteDeshipotecar(this, propiedade, prezoHipoteca - fortuna);
            }
        } else if (this.propiedades.containsKey(nomeHipoteca)) {
            throw new XaDeshipotecada(this.propiedades.get(nomeHipoteca));
        } else {
            throw new NonPosue(this, nomeHipoteca);
        }
    }

    public void engadirPropiedade(Propiedade casilla) {
        this.propiedades.put(casilla.getNome(), casilla);
        casilla.setPropietario(this);
    }

    public boolean comprobarFortuna(float apagar) {
        if (this.fortuna - apagar < 0) {
            consola.imprimir("Non ten suficientes cartos\n");
            return false;
        }
        return true;
    }

    public void engadirEdificio(Edificio edificio) {
        this.edificios.add(edificio);
    }

    public void eliminarEdificio(String tipo, int num, Solar solar) {
        switch (tipo) {
            case "casa":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Casa && solar.getEdificios().contains(this.edificios.get(i))) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "hotel":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Hotel && solar.getEdificios().contains(this.edificios.get(i))) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "piscina":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Piscina && solar.getEdificios().contains(this.edificios.get(i))) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
            case "pista":
                for (int i = this.edificios.size() - 1; i >= 0; i--) {
                    if (num > 0) {
                        if (this.edificios.get(i) instanceof Pista && solar.getEdificios().contains(this.edificios.get(i))) {
                            this.edificios.remove(i);
                            num--;
                        }
                    }
                }
                break;
        }
    }

    public void recibirTrato(Trato trato) {
        this.tratosRecibidos.add(trato);
    }

    public void emitirTrato(Trato trato) {
        this.tratosEmitidos.add(trato);
    }
    
    public void eliminarNoAlquiler(NoAlquiler noalq) {
        this.noAlquileres.remove(noalq);
    }

    public String mostrarDatos() {
        String s = "{\n\t"
                + "nombre:" + this.nome
                + "\n\tavatar:" + this.avatar.getCodigo()
                + "\n\tfortuna:" + this.fortuna
                + "\n\tpropiedades:" + this.propiedades.keySet()
                + "\n\thipotecas:" + this.hipotecas.keySet()
                + "\n\tedificios: ";
        for (Edificio ed : edificios) {
            s += ed.getIdentificador() + " (" + ed.getCasilla().getNome() + "), ";
        }
        s += "\n}";
        return s;
    }

    public String describirXogador() {
        String s = "{\n\tnombre:" + this.getNomeXogador()
                + ",\n\tavatar:" + this.getAvatar().getCodigo()
                + ",\n\tfortuna:" + this.fortuna
                + ",\n\tpropiedades:" + this.propiedades.keySet()
                + ",\n\thipotecas:" + this.hipotecas.keySet()
                + ",\n\tedificios: ";
        for (Edificio ed : edificios) {
            s += ed.getIdentificador() + " (" + ed.getCasilla().getNome() + "), ";
        }
        s += "\n}";
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        s += "{\n"
                + "\tnombre:" + this.nome
                + "\n\tavatar:" + this.getAvatar().getCodigo() + "\n}";
        return s;
    }
}
