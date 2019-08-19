package monopolyetse_funcionalidades;

import monopolyetse_excepcions.NonPosue;
import monopolyetse_excepcions.ExcCompra;
import monopolyetse_excepcions.AccionNonPermitida;
import monopolyetse_excepcions.INumeroNegativo;
import monopolyetse_excepcions.NonHaiTratos;
import monopolyetse_excepcions.ExcHipoteca;
import monopolyetse_excepcions.FNumeroNegativo;
import monopolyetse_excepcions.ExcTrato;
import monopolyetse_excepcions.ExcXogador;
import monopolyetse_excepcions.XaHipotecada;
import monopolyetse_excepcions.RestriccionAcciones;
import monopolyetse_excepcions.NonTenTrato;
import monopolyetse_excepcions.ExcAritmetica;
import monopolyetse_excepcions.ExcDeshipotecar;
import monopolyetse_excepcions.TipoInadecuado;
import monopolyetse_excepcions.HipotecadaGrupo;
import monopolyetse_excepcions.NonExisteXogador;
import monopolyetse_excepcions.TratoUnMesmo;
import monopolyetse_excepcions.ExcEdificar;
import monopolyetse_excepcions.MonopolyExcepcion;
import monopolyetse_entidades.Avatar;
import monopolyetse_entidades.Xogador;
import monopolyetse_entidades.Casilla;
import monopolyetse_entidades.Imposto;
import monopolyetse_entidades.Tableiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import monopolyetse_entidades.AccionC;
import monopolyetse_entidades.Accions;
import monopolyetse_entidades.Carcel;
import monopolyetse_entidades.Coche;
import monopolyetse_entidades.Concepto;
import monopolyetse_entidades.Esfinge;
import monopolyetse_entidades.Propiedade;
import monopolyetse_entidades.Grupo;
import monopolyetse_entidades.Parking;
import monopolyetse_entidades.Pelota;
import monopolyetse_entidades.Solar;
import monopolyetse_entidades.Sombrero;
import monopolyetse_excepcions.EdificiosInsuficientes;
import monopolyetse_excepcions.ExcVender;
import monopolyetse_excepcions.NonExisteCasilla;
import static monopolyetse_funcionalidades.Juego.consola;

public class Accion {
    //Non precisamos getters nin setters para os atributos da clase Accion
    //xa que esta é a clase que se encargará de centralizar todas as accións
    //do xogo, polo que ningunha clase precisa acceder a ningún dos seus
    //atributos xa que todas as operacións sobre os mesmos se xestionan de
    //maneira interna e con total opacidade

    private final Dado dado;
    private final Turno turno;
    private final Tableiro tableiro;

    private boolean actualizacionSolares;

    private boolean cambioRealizado;
    private int doblesSeguidos;

    public Accion(Tableiro tableiro) {
        this.tableiro = tableiro;
        this.dado = tableiro.getDado();
        this.turno = new Turno();

        this.actualizacionSolares = true;

        this.doblesSeguidos = 0;

        this.cambioRealizado = false;
    }

    public Tableiro getTableiro() {
        return this.tableiro;
    }

    public int[] movementoNormal(Avatar avatarXogador, int[] pos, int posicions) {
        int[] novaPos = new int[2];
        int aux;
        int sign = 0;

        novaPos[0] = (pos[0] + posicions) % this.tableiro.getNumCasillas();

        //Java non sabe facer módulos
        if (novaPos[0] < 0) {
            novaPos[0] += this.tableiro.getNumCasillas();
            sign = -1;
        }

        aux = (pos[1] + ((pos[0] + posicions) / this.tableiro.getNumCasillas()));

        if (sign == -1) {
            aux -= 1;
        }

        //Teremos que borralo posteriormente
        novaPos[1] = aux % this.tableiro.getNumLados();

        aux = aux / this.tableiro.getNumLados();

        if (aux > 0) {
            avatarXogador.completarVolta();
        }

        if (novaPos[1] < 0) {
            novaPos[1] += this.tableiro.getNumLados();
        }

        return novaPos;
    }

    public void cobrarImposto(Imposto imposto) {
        this.cobrarCasilla(imposto, this.tableiro.getBanca(), imposto.getValorImposto(), 3);
    }

    public void cobrarEnVenta(Propiedade enventa) {
        this.cobrarCasilla(enventa, enventa.getPropietario(), enventa.alquiler(), 1);
    }

    public void cobrarMulta(Carcel carcel) {
        this.cobrarCasilla(carcel, this.tableiro.getBanca(), carcel.getMultaCarcel(), 3);
    }

    public void transferirBote(Xogador xogador) {
        Parking parking = this.tableiro.getBanca().getParking();
        //Deberiamos simplificar este tipo
        float cantidade = parking.getBote();
        xogador.sumarCantidade(this.tableiro.getBanca(), cantidade, Concepto.cobroPagoPremioImposto);

        parking.anularBote();

        consola.imprimir("Cobras " + cantidade + "$ do bote!");
    }

    public void enviarCarcel() {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        //Actualizacións Turno
        avatarXogador.setTurnosDisponibles(0);
        avatarXogador.setEstarCarcel(true);

        avatarXogador.getXogador().setAccionsPermitidas(Accions.NINGUNA);

        //Actualizacións movemento
        if (avatarXogador instanceof Pelota) {
            Pelota avatar = (Pelota) avatarXogador;
            avatar.setMovementoFinalizado(true);
            avatar.setPosicionsRestantes(0);
        }

        Casilla carcel = this.tableiro.getCasilla("Carcel");
        this.tableiro.situarAvatarCasilla(avatarXogador, carcel);

        consola.imprimir("Vas a cárcel, acabouse o teu turno.");

        this.acabarTurno();
    }

    public boolean pagarXogador(Xogador xogador, float cantidade, int concepto) {
        boolean pagoRealizado = true;

        Xogador xogadorTurno = this.turno.getXogadorTurno();

        float debeda = xogadorTurno.restarCantidade(xogador, cantidade, concepto);

        if (debeda > 0) {
            xogador.sumarCantidade(xogadorTurno, cantidade - debeda, concepto);
            pagoRealizado = !this.declararBancarrota(xogador, debeda, concepto);
        } else {
            xogador.sumarCantidade(xogadorTurno, cantidade, concepto);
        }

        return pagoRealizado;
    }

    public void cobrarCasilla(Casilla casilla, Xogador propietario, float cantidade, int concepto) {
        if (pagarXogador(propietario, cantidade, concepto)) {
            casilla.cobroRealizado(cantidade);
        }
        else
        {
            this.acabarTurno();
        }
    }

    public void lanzarDadoCarcel(boolean dobles) {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        if (dobles) {
            consola.imprimir("Sacaches dobles, polo que saes da carcel!\n");
            avatarXogador.setTurnosDisponibles(0); //non se poden volver a lanzar se che saen dobles para salir da carcel
            avatarXogador.setEstarCarcel(false);
        } else {
            consola.imprimir("Non sacaches dobles, non saes da cárcel!\n");
            avatarXogador.sumarTurnosCarcel();

            avatarXogador.setTurnosDisponibles(0);
        }

        if (avatarXogador.getTurnosCarcel() == 3) {
            consola.imprimir("Leva tres turnos na carcel sen sacar dobles. Debe pagará pagar a multa para salir da cárcel!");
            this.cobrarMulta((Carcel) avatarXogador.getCasilla());
        }
    }

    public void lanzarDadoTresDobles() {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        consola.imprimir("O xogador " + this.turno.getXogadorTurno().getNomeXogador() + " obtivo 3 dobles seguidos! Vai directo a cárcel!");

        this.enviarCarcel();
    }

    public void lanzarDadoEspecial(int resultado, boolean dobles) {
        this.moverAvatar(resultado, dobles);
    }

    /*
				//Modificacións para adaptar movemento especial
				public void lanzarDadoEspecial(int resultado, boolean dobles)
				{
				Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();
				//Unha vez lanzamos os dados debemos poder realizar accións
				
				if(avatarXogador.getModoMovemento())
				{
				if(avatarXogador instanceof Coche)
				{
				Coche avatar = (Coche)avatarXogador;
				if(resultado > 4)
				{
				this.moverAvatar(resultado);
				avatar.setTurnosSeguidos(avatar.getTurnosSeguidos() + 1);
				if(avatar.getTurnosSeguidos() > 4)
				{
				consola.imprimir("Levas catro turnos seguidos!");
				
				avatar.setTurnosSeguidos(0);
				avatar.setTurnosDisponibles(0);
				}
				else if(avatar.getTurnosSeguidos() == 1)
				{
				//No primeiro turno permitímoslle realizar calquer acción
				consola.imprimir("Obtiveches máis dun 4! Podes volver a lanzar");
				avatar.getXogador().setAccionsPermitidas(0);
				}
				else
				{
				consola.imprimir("Obtiveches máis dun 4! Podes volver a lanzar");
				avatar.setTurnosDisponibles(1);
				}
				}
				else
				{
				this.moverAvatar(-resultado);
				
				avatar.setTurnosSeguidos(0);
				avatar.setTurnosDisponibles(-3); //nos siguientes dous turnos non pdoerá lanzar
				}
				}
				else if(avatarXogador instanceof Pelota)
				{
				Pelota avatar = (Pelota)avatarXogador;
				//O mellor esta condicion podería ir nun bucle posterior
				int posicionsRestantes;
				
				if(resultado > 4)
				{
				this.moverAvatar(5);
				
				posicionsRestantes = resultado - 5;
				}
				else
				{
				this.moverAvatar(-1);
				
				posicionsRestantes = -resultado + 1;
				}
				
				if(posicionsRestantes == 0)
				{
				avatar.setMovementoFinalizado(true);
				
				//Se acabou o movemento pode comprar e construir
				avatar.getXogador().setAccionsPermitidas(0);
				}
				else
				{
				avatar.setMovementoFinalizado(false);
				//Se non o acabou debe proseguilo para poder construir e edificar
				avatar.getXogador().setAccionsPermitidas(3);
				}
				
				avatar.setPosicionsRestantes(posicionsRestantes);
				avatar.setTurnosDisponibles(0);
				}
				}
				else
				{
				//Permitimos todas as accións
				avatarXogador.getXogador().setAccionsPermitidas(0);
				
				this.moverAvatar(resultado);
				
				if(dobles)
				{
				consola.imprimir("Obtiveches dobles! Podes lanzar outra vez.");
				avatarXogador.setTurnosDisponibles(1);
				}
				else
				{
				avatarXogador.setTurnosDisponibles(0);
				}
				}
				}*/
    public void proseguirMovemento() {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        if (avatarXogador instanceof Pelota) {
            Pelota pelota = (Pelota) avatarXogador;
            if (pelota.getMovementoFinalizado()) {
                consola.imprimir("Xa completou o movemento por este turno!");
            } else {
                this.moverAvatar();
            }
        } else {
            consola.imprimir("Este avatar non precisa completar movementos!");
        }
    }

    /*
				public void proseguirMovemento()
				{
				Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();
				
				int posicionsRestantes;
				int signo;
				
				if(avatarXogador instanceof Pelota)
				{
				Pelota avatar = (Pelota)avatarXogador;
				if(avatar.getTurnosDisponibles() == 0 && avatar.getModoMovemento() && !avatar.getMovementoFinalizado())
				{
				posicionsRestantes = avatar.getPosicionsRestantes();
				signo = Integer.signum(posicionsRestantes);
				
				if(Math.abs(posicionsRestantes) > 1)
				{
				posicionsRestantes -= 2*signo;
				this.moverAvatar(2*signo);
				
				if(posicionsRestantes != 0)
				{
				avatar.setPosicionsRestantes(posicionsRestantes);
				avatar.setMovementoFinalizado(false);
				}
				else
				{
				avatar.setPosicionsRestantes(0);
				avatar.setMovementoFinalizado(true);
				
				//Unha vez acabamos o movemento da pelota poderemos comprar
				avatar.getXogador().setAccionsPermitidas(0);
				}
				}
				else
				{
				this.moverAvatar(signo);
				
				avatar.setPosicionsRestantes(0);
				avatar.setMovementoFinalizado(true);
				}
				}
				else
				{
				consola.imprimir("Non hai ningún movemento que completar!");
				}
				}
				else
				{
				consola.imprimir("Este avatar nunca precisa completar movementos!");
				}
				}*/
    public void moverAvatar(int posicions, boolean dobles) {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        Casilla inicio = avatarXogador.getCasilla();
        this.moverAvatarXogador(posicions, dobles);
        Casilla fin = avatarXogador.getCasilla();

        //Aquí habería que facer cambios para adaptar os movementos especiales
        consola.imprimir("O avatar " + avatarXogador.getCodigo() + " avanza " + posicions
                + " posicions, dende " + inicio.getNome() + " ata " + fin.getNome());

        this.realizarAccion(fin);
    }

    public void moverAvatar() //Completar movemento pelota
    {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        Casilla inicio = avatarXogador.getCasilla();
        this.moverAvatarXogador();
        Casilla fin = avatarXogador.getCasilla();

        //Aquí habería que facer cambios para adaptar os movementos especiales
        consola.imprimir("O avatar " + avatarXogador.getCodigo()
                + " avanza, completando o seu movemento pendente, dende "
                + inicio.getNome() + " ata " + fin.getNome());

        this.realizarAccion(fin);
    }

    public void moverAvatarXogador() {
        Pelota avatarXogador = (Pelota) this.turno.getXogadorTurno().getAvatar();

        Casilla inicio = avatarXogador.getCasilla();

        int[] novaPos = avatarXogador.moverEnAvanzado(this.tableiro.getNumCasillas(), this.tableiro.getNumLados());

        avatarXogador.getXogador().setAccionsPermitidas(Accions.TODAS);

        Casilla fin = this.tableiro.getCasilla(novaPos);

        this.tableiro.situarAvatarCasilla(avatarXogador, fin);
    }

    public void moverAvatarXogador(int posicions, boolean dobles) {
        Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

        Casilla inicio = avatarXogador.getCasilla();

        int[] novaPos;

        if (avatarXogador.getModoMovemento()) {
            novaPos = avatarXogador.moverEnAvanzado(posicions, this.tableiro.getNumCasillas(), this.tableiro.getNumLados());

            //Comprobación necesaria para o caso do coche
            if (avatarXogador.getXogador().getAccionsPermitidas() == Accions.NINGUNA) {
                if (avatarXogador instanceof Coche && avatarXogador.getModoMovemento() && ((Coche) avatarXogador).getTurnosSeguidos() >= 2) {
                    avatarXogador.getXogador().setAccionsPermitidas(Accions.SO_EDF);
                } else {
                    avatarXogador.getXogador().setAccionsPermitidas(Accions.TODAS);
                }
            }
        } else {
            novaPos = avatarXogador.moverEnBasico(posicions, dobles, this.tableiro.getNumCasillas(), this.tableiro.getNumLados());

            avatarXogador.getXogador().setAccionsPermitidas(Accions.TODAS);
        }

        Casilla fin = this.tableiro.getCasilla(novaPos);

        this.tableiro.situarAvatarCasilla(avatarXogador, fin);
    }

    public void lanzarDado() {
        if (this.turno.haiXogadores()) {
            Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

            if (avatarXogador.getTurnosDisponibles() > 0) {
                this.dado.lanzarDados(this.turno.getXogadorTurno());

                if (avatarXogador.getEstarCarcel() && avatarXogador.getTurnosCarcel() < 3) {
                    this.lanzarDadoCarcel(this.dado.getDobles());
                } else {
                    if (this.dado.getDobles()) {
                        this.doblesSeguidos++;

                        if (doblesSeguidos != 3) {
                            this.lanzarDadoEspecial(this.dado.getResultado(), this.dado.getDobles());
                        } else {
                            this.lanzarDadoTresDobles();
                        }
                    } else {
                        this.lanzarDadoEspecial(this.dado.getResultado(), this.dado.getDobles());
                    }
                }
            } else {
                consola.imprimir("O xogador xa lanzou os dados!");
            }
        } else {
            consola.imprimir("Non hai ningún xogador creado!");
        }
    }

    public void lanzarDadoHackeado(int posicions, boolean dobles) {
        if (this.turno.haiXogadores()) {
            Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

            if (avatarXogador.getTurnosDisponibles() > 0) {
                this.dado.lanzarDados(this.turno.getXogadorTurno());

                if (avatarXogador.getEstarCarcel() && avatarXogador.getTurnosCarcel() < 3) {
                    this.lanzarDadoCarcel(dobles);
                } else {
                    if (dobles) {
                        this.doblesSeguidos++;

                        if (doblesSeguidos != 3) {
                            this.lanzarDadoEspecial(posicions, dobles);
                        } else {
                            this.lanzarDadoTresDobles();
                        }
                    } else {
                        this.lanzarDadoEspecial(posicions, dobles);
                    }
                }
            } else {
                consola.imprimir("O xogador xa lanzou os dados!");
            }
        } else {
            consola.imprimir("Non hai ningún xogador creado!");
        }
    }

    public void realizarAccion(Casilla casilla) {
        Xogador xogadorTurno = this.turno.getXogadorTurno();

        String accion = casilla.accionCasilla(xogadorTurno);

        switch (accion) {
            case "Imposto":
                this.cobrarImposto((Imposto) casilla);
                break;

            case "IrCarcel":
                //Deberiamos bloquear o turno
                this.enviarCarcel();
                break;

            case "Parking":
                this.transferirBote(xogadorTurno);
                break;

            case "Cobrar":
                this.cobrarEnVenta((Propiedade) casilla);
                break;

            case "Comprar":
                consola.imprimir("Esta casilla pódese comprar!");
                break;
            case "Sorte":
                this.accionCarta();
                break;
            case "Comunidade":
                this.accionCarta();
                break;
            case "Edificar":
                consola.imprimir("Vostede pode edificar aqui");
                break;
        }
    }

    public void accionCarta() {
        AccionC casilla = (AccionC) this.turno.getXogadorTurno().getAvatar().getCasilla();
        casilla.barallar();
        int carta = this.elixirCarta();
        casilla.accion(carta);
    }

    public void crearXogador(String nomeXogador, String tipoAvatar) {
        while (this.tableiro.existeXogador(nomeXogador)) {
            consola.imprimir("O xogador " + nomeXogador + " xa está rexistrado!");
            String novoNome = consola.leer("Introduce outro nome: ");
            String[] partes = novoNome.split(" ");
            if (partes.length != 1) {
                consola.imprimir("Non se introduciu un nome válido!");
            } else {
                nomeXogador = partes[0];
            }
        }

        Xogador xogador = new Xogador(this.tableiro.getBanca(), nomeXogador);
        Casilla salida = this.tableiro.getCasilla("Salida");

        switch (tipoAvatar.toLowerCase()) {
            case "pelota":
                xogador.setAvatar(new Pelota(xogador, salida, this.tableiro.getAvatares()));
                break;
            case "coche":
                xogador.setAvatar(new Coche(xogador, salida, this.tableiro.getAvatares()));
                break;
            case "esfinge":
                xogador.setAvatar(new Esfinge(xogador, salida, this.tableiro.getAvatares()));
                break;
            case "sombrero":
                xogador.setAvatar(new Sombrero(xogador, salida, this.tableiro.getAvatares()));
                break;
            default:
                consola.imprimir("Entrada incorrecta! Por defecto asignaselle o avatar Pelota.");
                xogador.setAvatar(new Pelota(xogador, salida, this.tableiro.getAvatares()));
                break;
        }

        this.tableiro.engadirXogador(xogador);
        this.turno.engadirXogador(xogador);

        this.tableiro.situarAvatarCasilla(xogador.getAvatar(), salida);

        consola.imprimir(xogador.toString());

    }

    public void mostrarXogadorTurno() {
        if (this.turno.haiXogadores()) {
            Xogador xogadorTurno = this.turno.getXogadorTurno();

            consola.imprimir(xogadorTurno.toString());
        } else {
            consola.imprimir("Non hai xogadores!");
        }
    }

    public Turno getTurno() {
        return this.turno;
    }

    public void listarXogadores() {
        if (this.turno.haiXogadores()) {
            ArrayList<Xogador> listaXogadores = this.turno.getXogadores();

            for (Xogador xogador : listaXogadores) {
                consola.imprimir(xogador.mostrarDatos());
            }
        } else {
            consola.imprimir("Non hai xogadores!");
        }

    }

    public void listarAvatares() {
        if (this.turno.haiXogadores()) {
            ArrayList<Xogador> listaXogadores = this.turno.getXogadores();

            for (Xogador xogador : listaXogadores) {
                consola.imprimir(xogador.getAvatar().toString());
            }
        } else {
            consola.imprimir("Non hai xogadores!");
        }
    }

    public void actualizarPrezosSolares() {
        HashMap<String, Avatar> avatares = this.tableiro.getAvatares();
        Set<String> clavesAvatares = avatares.keySet();

        int numeroAvatares = clavesAvatares.size();
        int numeroAvataresVolta = 0;

        boolean edificiosActualizados = false;

        for (String clave : clavesAvatares) {
            if (avatares.get(clave).getVoltasParciais() >= 4) {
                numeroAvataresVolta++;
            }
        }

        if (numeroAvatares == numeroAvataresVolta) {
            for (String clave : clavesAvatares) {
                avatares.get(clave).anularVoltas();
            }

            ArrayList<ArrayList<Casilla>> casillas = this.tableiro.getCasillas();
            {
                for (ArrayList<Casilla> lado : casillas) {
                    for (Casilla casilla : lado) {
                        if (casilla instanceof Solar) {
                            Solar solar = (Solar) casilla;
                            if (!solar.getSerComprada()) {
                                edificiosActualizados = true;

                                float prezo = solar.valor() * Valor.multiIncrementoSolar;
                                solar.setPrezo(prezo);
                            }
                        }
                    }

                }
            }

            if (!edificiosActualizados) {
                this.actualizacionSolares = false;
            }

        }
    }

    public void acabarTurno() {
        if (this.turno.haiXogadores()) {
            Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

            if (avatarXogador.getTurnosDisponibles() > 0) {
                consola.imprimir("O xogador debe lanzar os dados aínda!");
            } else {
                //Operacións para o caso da pelota

                avatarXogador.setTurnosDisponibles(avatarXogador.getTurnosDisponibles() + 1);
                avatarXogador.getXogador().getEstatistica().calcularValorAcumulado();

                //Actualizamos os turnos nos que non pagamos
                ArrayList<NoAlquiler> aux = new ArrayList<>();
                for (NoAlquiler noalquiler : avatarXogador.getXogador().getNoAlquileres()) {
                    aux.add(noalquiler);
                }
                for (NoAlquiler noalquiler : aux) {
                    noalquiler.restarTurno();
                }

                this.turno.pasarTurno();
                
                avatarXogador = this.turno.getXogadorTurno().getAvatar();

                if (this.actualizacionSolares) {
                    this.actualizarPrezosSolares();
                }

                if (!this.turno.getXogadorTurno().getNoAlquileres().isEmpty()) {
                    consola.imprimir("Nas seguintes casillas non pagará alquiler: ");
                    consola.imprimir(this.turno.getXogadorTurno().getNoAlquileres().toString());
                }

                this.doblesSeguidos = 0;
                this.cambioRealizado = false; //cambio de modo movemento

                //Non pode realizar accións ata lanzar os dados
                avatarXogador.getXogador().setAccionsPermitidas(Accions.NINGUNA);

                //Revisar caso sacar dobles e ir carcel (non se mostra o mensaxe)
                //Revisar tamén booleans edificios
                if (!this.turno.getXogadorTurno().getTratosRecibidos().isEmpty()) {
                    consola.imprimir(this.turno.getXogadorTurno().getTratosRecibidos().toString());
                }

                consola.imprimir("O xogador actual é " + this.turno.getXogadorTurno().getNomeXogador());
            }
        } else {
            consola.imprimir("Non hai ningún xogador rexistrado!");
        }

    }
    
    public void acabarTurnoBancarrota() {
        if (this.turno.haiXogadores()) {
            
            this.turno.pasarTurno();

            Avatar avatarXogador = this.turno.getXogadorTurno().getAvatar();

            if (this.actualizacionSolares) {
                this.actualizarPrezosSolares();
            }

            if (!this.turno.getXogadorTurno().getNoAlquileres().isEmpty()) {
                consola.imprimir("Nas seguintes casillas non pagará alquiler: ");
                consola.imprimir(this.turno.getXogadorTurno().getNoAlquileres().toString());
            }

            this.doblesSeguidos = 0;
            this.cambioRealizado = false; //cambio de modo movemento

            //Non pode realizar accións ata lanzar os dados
            avatarXogador.getXogador().setAccionsPermitidas(Accions.NINGUNA);

            //Revisar caso sacar dobles e ir carcel (non se mostra o mensaxe)
            //Revisar tamén booleans edificios
            if (!this.turno.getXogadorTurno().getTratosRecibidos().isEmpty()) {
                consola.imprimir(this.turno.getXogadorTurno().getTratosRecibidos().toString());
            }

            consola.imprimir("O xogador actual é " + this.turno.getXogadorTurno().getNomeXogador());
        }
        else 
        {
            consola.imprimir("Non hai ningún xogador rexistrado!");
        }

    }

    public void describirCasilla(String nomeCasilla) throws NonExisteCasilla {
        Casilla casilla = this.tableiro.getCasilla(nomeCasilla);
        if (casilla != null) {
            consola.imprimir(casilla.toString());
        } else {
            throw new NonExisteCasilla(nomeCasilla);
        }
    }

    public void describirXogador(String nomeXogador) {
        Xogador xogador = this.tableiro.getXogador(nomeXogador);

        if (xogador != null) {
            consola.imprimir(xogador.describirXogador());
        } else {
            consola.imprimir("O xogador " + nomeXogador + " non está rexistrado!");
        }
    }

    public void describirAvatar(String nomeAvatar) {
        Avatar avatar = this.tableiro.getAvatar(nomeAvatar);

        if (avatar != null) {
            consola.imprimir(avatar.toString());
        } else {
            consola.imprimir("O avatar " + nomeAvatar + " non está rexistrado!");
        }
    }

    public boolean declararBancarrota(Xogador xogador, float debeda, int concepto) {
        boolean xogadorEliminado = false;
        Xogador xogadorTurno = this.turno.getXogadorTurno();

        if (xogadorTurno.tenPropiedades() && (xogadorTurno.acumuladoHipotecas() + xogadorTurno.acumuladoEdificios()) >= debeda) {
            consola.imprimir("Debes hipotecar as túas propiedades para evitar a bancarrota!");
            float cantidade = 0.0f;
            do {
                cantidade += this.solicitarVentas(xogadorTurno);
            } while (cantidade < debeda);

            xogadorTurno.restarCantidade(xogador, debeda, concepto);
            xogador.sumarCantidade(xogador, debeda, concepto);
        } else {
            consola.imprimir("O xogador " + xogadorTurno.getNomeXogador() + " está en bancarrota! Elimínase do xogo.");
            xogador.recibirPropiedades(xogadorTurno);
            this.eliminarXogador(xogadorTurno);

            xogadorEliminado = true;
        }

        return xogadorEliminado;
    }

    public float solicitarVentas(Xogador xogador) {
        this.describirXogador(xogador.getNomeXogador());
        consola.imprimir("Sintaxe: ");
        consola.imprimir("vender <tipo> <solar> <num> ");
        consola.imprimir("hipotecar <tipo> <solar> <num> ");

        String orden = consola.leer("Que desexas facer? ");
        String[] partes = orden.split(" ");
        String comando = partes[0];

        float cantidade = 0.0f;

        String nome = "";

        switch (comando) {
            case "vender":
                if (partes.length > 3) {
                    int i;
                    for (i = 2; i < partes.length - 1; i++) {
                        if (i < partes.length - 2) {
                            nome += partes[i] + " ";
                        } else {
                            nome += partes[i];
                        }
                    }
                    boolean error;
                    try {
                        Integer.parseInt(partes[i]);
                        error = false;
                    } catch (NumberFormatException excepcion) {
                        error = true;
                    }
                    if (error == true) {
                        consola.imprimir("Sintaxe: vender <tipo> <solar> <num>");
                        break;
                    }

                    try
                    {
                        cantidade = this.venderEdificio(partes[1], Integer.parseInt(partes[i]), nome);
                    }
                    catch(ExcXogador | ExcVender | NonExisteCasilla excepcion)
                    {
                        consola.imprimir(excepcion.getMessage());
                    }
                    catch(TipoInadecuado excepcion)
                    {
                        consola.imprimir(excepcion.getMessage());
                        consola.imprimir("\tRequerido: " + excepcion.getTipoPrecisado()
                            + ", Tipo Casilla: " + excepcion.getTipoCasilla());
                    }
                    catch(ExcAritmetica excepcion)
                    {
                        consola.imprimir(excepcion.getMessage());
                        if (excepcion instanceof INumeroNegativo) {
                            INumeroNegativo exc = (INumeroNegativo) excepcion;
                            consola.imprimir("\t\t" + exc.getNumeroIncorrecto());
                        }
                    }
                }
                break;
            case "hipotecar":
                for (int i = 1; i < partes.length; i++) {
                    if (i < partes.length - 1) {
                        nome += partes[i] + " ";
                    } else {
                        nome += partes[i];
                    }
                }
                try {
                    cantidade = xogador.hipotecarPropiedade(nome);
                } catch (MonopolyExcepcion excepcion) {
                    consola.imprimir(excepcion.getMessage());
                    cantidade = 0.0f;
                }
                break;
            default:
                cantidade = 0.0f;
                break;
        }

        return 0.0f;
    }

    public void eliminarXogador(Xogador xogador) {
        this.tableiro.eliminarXogador(xogador);
        this.turno.eliminarXogador(xogador);
    }

    public void mostrarTableiro() {
        consola.imprimir(this.tableiro.toString());
    }

    public void listarEnVenta() {
        this.tableiro.listarEnVenta();
    }

    public void salirCarcel() {
        Xogador xogadorTurno = this.turno.getXogadorTurno();
        Avatar avatarXogador = xogadorTurno.getAvatar();

        if (avatarXogador.getEstarCarcel()) {
            if (xogadorTurno.getFortuna() >= Valor.prezoSalirCarcel) {
                xogadorTurno.restarCantidade(this.tableiro.getBanca(), Valor.prezoSalirCarcel, Concepto.pagoInversion);

                this.doblesSeguidos = 0;
                avatarXogador.setTurnosDisponibles(1);
                avatarXogador.setEstarCarcel(false);

                consola.imprimir(xogadorTurno.getNomeXogador() + " paga " + Valor.prezoSalirCarcel
                        + "$ e sale da cárcel. Pode lanzar os dados");

            } else {
                consola.imprimir(xogadorTurno.getNomeXogador() + " non ten suficiente fortuna para salir da carcel");
            }
        } else {
            consola.imprimir(xogadorTurno.getNomeXogador() + " non está na cárcel!");
        }
    }

    public void comprarCasilla(String nomeCasilla) throws TipoInadecuado, ExcXogador, RestriccionAcciones, ExcCompra, NonExisteCasilla {
        Xogador xogador = this.turno.getXogadorTurno();
        if (xogador.getAccionsPermitidas() == Accions.TODAS || xogador.getAccionsPermitidas() == Accions.SO_EDF) {
            Casilla casilla = this.tableiro.getCasilla(nomeCasilla);
            if (casilla != null) {
                if (casilla instanceof Propiedade) {
                    Propiedade propiedade = (Propiedade) casilla;

                    propiedade.comprar(xogador);
                } else {
                    throw new TipoInadecuado("Propiedade", casilla);
                }
            } else {
                throw new NonExisteCasilla(nomeCasilla);
            }
        } else {
            throw new AccionNonPermitida("Comprar", xogador.getAccionsPermitidas());
        }
    }

    public int numeroXogadores() {
        return this.turno.numeroXogadores();
    }
    
    //Funcións entrega 2
    public void cambiarModoMovemento() {
        if (this.turno.haiXogadores()) {
            Xogador xogadorTurno = this.turno.getXogadorTurno();

            if (this.cambioRealizado) {
                consola.imprimir("Xa cambiaches de modo neste turno!");
            } else {
                xogadorTurno.getAvatar().cambiarModoMovemento();

                consola.imprimir("O xogador cambia o seu avatar "
                        + xogadorTurno.getAvatar().getTipo() +" a modo " 
                        + (xogadorTurno.getAvatar().getModoMovemento() ? "Especial" : "Normal"));

                this.cambioRealizado = true;
            }
        } else {
            consola.imprimir("Non hai xogadores!");
        }
    }

    public int elixirCarta() {
        int numCarta = 0;

        do {
            String orden = consola.leer(this.turno.getXogadorTurno().getNomeXogador() + ", elixe unha carta: ");
            String[] partes = orden.split(" ");
            if (partes.length == 1) {
                try {
                    numCarta = Integer.parseInt(partes[0]);
                } catch (NumberFormatException excepcion) {
                    consola.imprimir("Non se introduciu un numero!");
                    //Forzamos outra iteracion do bucle
                    numCarta = 0;
                }
            } else {
                consola.imprimir("Debese escoller unha carta válida");
            }
        } while (numCarta < 0 || numCarta > 9);

        return numCarta;
    }

    public void edificar(String tipoEdificio) throws TipoInadecuado, ExcHipoteca, RestriccionAcciones, ExcXogador, ExcEdificar {
        Casilla casilla = this.turno.getXogadorTurno().getAvatar().getCasilla();
        if (casilla instanceof Solar) {
            Solar solar = (Solar) casilla;
            if (this.getTurno().getXogadorTurno().getPropiedades().containsKey(solar.getNome()) == false) { 
                if (this.getTurno().getXogadorTurno().getHipotecas().containsKey(solar.getNome())) {
                    throw new XaHipotecada(solar);
                } else {
                    throw new NonPosue(this.turno.getXogadorTurno(), solar.getNome());
                }
            } else {
                ArrayList<Propiedade> casillasHipotecadas = new ArrayList<>();
                Grupo grupoSolar = solar.getGrupo();
                //Revisar
                for (Propiedade sol : grupoSolar.getPropiedadesGrupo()) {
                    if (this.getTurno().getXogadorTurno().getHipotecas().containsKey(sol.getNome())) {
                        casillasHipotecadas.add(sol);
                        //consola.imprimir("Non podes edificar no grupo " + sol.getGrupo().getSubtipo()																						return;*/
                    }
                }

                if (!casillasHipotecadas.isEmpty()) {
                    throw new HipotecadaGrupo(casillasHipotecadas, solar.getGrupo().getSubtipo());
                }

                if (this.turno.getXogadorTurno().getAccionsPermitidas() == Accions.TODAS
                        || this.turno.getXogadorTurno().getAccionsPermitidas() == Accions.SO_EDF) {
                    solar.edificar(tipoEdificio);
                } else {
                    throw new AccionNonPermitida("Edificar", this.turno.getXogadorTurno().getAccionsPermitidas());
                }
            }
        } else {
            throw new TipoInadecuado("Solar", casilla);
        }
    }

    public void eliminarEdificio(String tipo, int num, Solar solar) {
        this.turno.getXogadorTurno().eliminarEdificio(tipo, num, solar);
        solar.eliminarEdificio(tipo, num);
    }

    public float venderEdificio(String tipo, int num, String nome) throws ExcXogador, ExcVender, ExcAritmetica, TipoInadecuado, NonExisteCasilla{

        float venta = 0.0f;
        Casilla solar = this.tableiro.getCasilla(nome);
        if(num <= 0)
        {
            throw new INumeroNegativo(num, false);
        }
        if (solar != null) {
            if (solar instanceof Solar) {
                if (((Solar) solar).getPropietario().getNomeXogador().equalsIgnoreCase(this.turno.getXogadorTurno().getNomeXogador())) {
                    switch (tipo) {
                        case "casa":
                            if (num > ((Solar) solar).numCasas()) {
                                throw new EdificiosInsuficientes(this.turno.getXogadorTurno(), tipo, nome, num, ((Solar) solar).numCasas());
                            }
                            venta = ((Solar) solar).getGrupo().getPrezoCasa() * num / 2;
                            break;
                        case "hotel":
                            if (num > ((Solar) solar).numHoteles()) {
                                throw new EdificiosInsuficientes(this.turno.getXogadorTurno(), tipo, nome, num, ((Solar) solar).numHoteles());
                            }
                            venta = ((Solar) solar).getGrupo().getPrezoHotel() * num / 2;
                            break;
                        case "piscina":
                            if (num > ((Solar) solar).numPiscinas()) {
                                throw new EdificiosInsuficientes(this.turno.getXogadorTurno(), tipo, nome, num, ((Solar) solar).numPiscinas());
                            }
                            venta = ((Solar) solar).getGrupo().getPrezoPiscina() * num / 2;
                            break;
                        case "pista":
                            if (num > ((Solar) solar).numPistas()) {
                                throw new EdificiosInsuficientes(this.turno.getXogadorTurno(), tipo, nome, num, ((Solar) solar).numPistas());
                            }
                            venta = ((Solar) solar).getGrupo().getPrezoPista() * num / 2;
                            break;
                    }
                    this.eliminarEdificio(tipo, num, (Solar) solar);
                    this.turno.getXogadorTurno().sumarCantidade(this.tableiro.getBanca(), venta, 0);
                    consola.imprimir(this.turno.getXogadorTurno().getNomeXogador() + " vendeu " + num + " " + tipo + "s en " + nome + ", recibindo " + venta + "$.");
                } else {
                    throw new NonPosue(this.turno.getXogadorTurno(), solar.getNome());
                }
            } else {
                throw new TipoInadecuado("Solar", solar);
            }
        } else {
            throw new NonExisteCasilla(nome);
        }
        return venta;
    }

    public void listarEdificios() {
        boolean flag = true;
        for (ArrayList<Casilla> lado : this.tableiro.getCasillas()) {
            for (Casilla casilla : lado) {
                if (casilla instanceof Solar) {
                    Solar solar = (Solar) casilla;
                    if (!solar.getEdificios().isEmpty()) {
                        flag = false;
                        solar.listarEdificios();
                    }
                }
            }
        }
        if (flag) {
            consola.imprimir("Non se construiron edificios ainda!");
        }
    }

    public void listarEdificiosGrupo(String color) {
        for (ArrayList<Casilla> lado : this.tableiro.getCasillas()) {
            for (Casilla casilla : lado) {
                if (casilla instanceof Solar && ((Solar) casilla).getGrupo().getSubtipo().equalsIgnoreCase(color)) {
                    ((Solar) casilla).listarEdificiosGrupo();
                }
            }
        }
    }

    //Funcións hipotecar
    public void hipotecarPropiedade(String nomeCasilla) throws ExcHipoteca, ExcXogador {
        Xogador xogadorTurno = this.turno.getXogadorTurno();
        xogadorTurno.hipotecarPropiedade(nomeCasilla);
    }

    public void deshipotecarPropiedade(String nomeCasilla) throws ExcDeshipotecar, ExcXogador {
        if (this.turno.haiXogadores()) {
            Xogador xogadorTurno = this.turno.getXogadorTurno();
            xogadorTurno.deshipotecarPropiedade(nomeCasilla);
        } else {
            consola.imprimir("Non hai xogadores!");
        }
    }

    public void mostrarEstatisticasXogador(String nomeXogador) {
        if (this.turno.haiXogadores()) {
            Xogador xogador = this.tableiro.getXogador(nomeXogador);
            if (xogador != null) {
                consola.imprimir(xogador.getEstatistica().toString());
            } else {
                consola.imprimir("Non existe o xogador " + nomeXogador);
            }
        } else {
            consola.imprimir("Non hai xogadores!");
        }
    }

    public void mostrarEstatisticasGlobais() {
        ArrayList<String> casillasMaisRentables = new ArrayList<>();
        ArrayList<String> gruposMaisRentables = new ArrayList<>();
        ArrayList<String> casillasMaisFrecuentadas = new ArrayList<>();
        ArrayList<String> xogadoresMaisVoltas = new ArrayList<>();
        ArrayList<String> xogadoresMaisLanzamentos = new ArrayList<>();
        ArrayList<String> xogadoresEnCabeza = new ArrayList<>();

        float maxCasillaMaisRentable = 0.0f;
        float maxGrupoMaisRentable = 0.0f;
        int maxCasillasMaisFrecuentadas = 0;

        int maxVoltas = 0;
        int maxLanzamentos = 0;
        float maxValorAcumulado = 0.0f;

        float tempF;
        int tempI;

        ArrayList<ArrayList<Casilla>> casillas = this.tableiro.getCasillas();

        for (ArrayList<Casilla> lado : casillas) {

            for (Casilla casilla : lado) {
                if (casilla instanceof Propiedade) {
                    Propiedade enventa = (Propiedade) casilla;
                    tempF = enventa.getAlquilerAcumulado();
                    //CASILLAS MAIS RENTABLES
                    if (tempF > maxCasillaMaisRentable) {
                        maxCasillaMaisRentable = tempF;

                        casillasMaisRentables.clear();
                        casillasMaisRentables.add(casilla.getNome());
                    } else if (tempF == maxCasillaMaisRentable) {
                        casillasMaisRentables.add(casilla.getNome());
                    }
                    //GRUPOS MAIS RENTABLES
                    tempF = enventa.getGrupo().getAlquilerAcumulado();
                    if (tempF > maxGrupoMaisRentable) {
                        maxGrupoMaisRentable = tempF;

                        gruposMaisRentables.clear();
                        gruposMaisRentables.add(enventa.getGrupo().getSubtipo());
                    } else if (tempF == maxGrupoMaisRentable) {
                        if (!gruposMaisRentables.contains(enventa.getGrupo().getSubtipo())) {
                            gruposMaisRentables.add(enventa.getGrupo().getSubtipo());
                        }
                    }
                }

                //CASILLAS MAIS FRECUENTADAS
                tempI = casilla.frecuenciaVisita();

                if (tempI > maxCasillasMaisFrecuentadas) {
                    maxCasillasMaisFrecuentadas = tempI;

                    casillasMaisFrecuentadas.clear();
                    casillasMaisFrecuentadas.add(casilla.getNome());
                } else if (tempI == maxCasillasMaisFrecuentadas) {
                    casillasMaisFrecuentadas.add(casilla.getNome());
                }
            }

        }

        ArrayList<Xogador> xogadores = this.turno.getXogadores();

        for (Xogador xogador : xogadores) {
            tempI = xogador.getAvatar().getVoltasTotais();

            if (tempI > maxVoltas) {
                maxVoltas = tempI;

                xogadoresMaisVoltas.clear();
                xogadoresMaisVoltas.add(xogador.getNomeXogador());
            } else if (tempI == maxVoltas) {
                xogadoresMaisVoltas.add(xogador.getNomeXogador());
            }

            tempI = xogador.getEstatistica().getLanzamentosDados();

            if (tempI > maxLanzamentos) {
                maxLanzamentos = tempI;

                xogadoresMaisLanzamentos.clear();
                xogadoresMaisLanzamentos.add(xogador.getNomeXogador());
            } else if (tempI == maxLanzamentos) {
                xogadoresMaisLanzamentos.add(xogador.getNomeXogador());
            }

            tempF = xogador.getEstatistica().getValorAcumulado();

            if (tempF > maxValorAcumulado) {
                maxValorAcumulado = tempF;

                xogadoresEnCabeza.clear();
                xogadoresEnCabeza.add(xogador.getNomeXogador());
            } else if (tempF == maxValorAcumulado) {
                xogadoresEnCabeza.add(xogador.getNomeXogador());
            }
        }

        String s = "{"
                + "\n\tcasillaMasRentable:" + casillasMaisRentables
                + "\n\tgrupoMasRentable:" + gruposMaisRentables
                + ",\n\tcasillaMasFrecuentada:" + casillasMaisFrecuentadas
                + ",\n\tjugadorMasVueltas:" + xogadoresMaisVoltas
                + ",\n\tjugadorMasVecesDados:" + xogadoresMaisLanzamentos
                + ",\n\tjugadorEnCabeza:" + xogadoresEnCabeza
                + "\n}";

        consola.imprimir(s);
    }

    private boolean esFloat(String cadena) {
        boolean esFloat;
        try {
            Float.parseFloat(cadena);
            esFloat = true;
        } catch (NumberFormatException excepcion) {
            esFloat = false;
        }
        return esFloat;
    }

    /*
    public int contarTratos(){ //unha alternativa sería gardar nos xogadores tanto os datos que emiten como os que reciben
    int n=0;
    for (Xogador xogador: this.turno.getXogadores()) {
    n+=xogador.getTratosRecibidos().size();
    }
    return n;
    }*/
    public float procesarParteTrato(String[] frase, ArrayList<Propiedade> propiedades) throws NonExisteCasilla, TipoInadecuado{
        String nomeCasilla = "";
        float cartos = 0; //toda a funcion
        boolean lendoCasilla = false;
        for (int i = 0; i < frase.length; i++) {
            if (esFloat(frase[i])) {
                if (lendoCasilla == true) {
                    nomeCasilla = nomeCasilla.substring(0, nomeCasilla.length() - 1); //quitase o ultimo espazo
                    if (this.tableiro.getCasilla(nomeCasilla) == null) { //modularizar
                        throw new NonExisteCasilla(nomeCasilla);
                    } else if (!(this.tableiro.getCasilla(nomeCasilla) instanceof Propiedade)) {
                        throw new TipoInadecuado("Propiedade", this.tableiro.getCasilla(nomeCasilla));
                    }
                    //comprobar que a casilla existe e é de tipo propiedade
                    propiedades.add((Propiedade) this.tableiro.getCasilla(nomeCasilla));
                }
                cartos += Float.valueOf(frase[i]);
                lendoCasilla = false;
            } else if (frase[i].equalsIgnoreCase("y")) {
                if (lendoCasilla == true) {
                    nomeCasilla = nomeCasilla.substring(0, nomeCasilla.length() - 1); //quitase o ultimo espazo
                    if (this.tableiro.getCasilla(nomeCasilla) == null) { //modularizar
                        throw new NonExisteCasilla(nomeCasilla);
                    } else if (!(this.tableiro.getCasilla(nomeCasilla) instanceof Propiedade)) {
                        throw new TipoInadecuado("Propiedade", this.tableiro.getCasilla(nomeCasilla));
                    }
                    //comprobar que a casilla existe e é de tipo propiedade
                    propiedades.add((Propiedade) this.tableiro.getCasilla(nomeCasilla));
                    lendoCasilla = false;
                    nomeCasilla = "";
                }
            } else {
                lendoCasilla = true;
                nomeCasilla += frase[i] + " ";
            }
        }
        if (lendoCasilla == true) {
            nomeCasilla = nomeCasilla.substring(0, nomeCasilla.length() - 1); //quitase o ultimo espazo
            if (this.tableiro.getCasilla(nomeCasilla) == null) { //modularizar
                throw new NonExisteCasilla(nomeCasilla);
            } else if (!(this.tableiro.getCasilla(nomeCasilla) instanceof Propiedade)) {
                throw new TipoInadecuado("Propiedade", this.tableiro.getCasilla(nomeCasilla));
            }
            //comprobar que a casilla e de tipo propiedade
            propiedades.add((Propiedade) this.tableiro.getCasilla(nomeCasilla));
            lendoCasilla = false;
            nomeCasilla = "";
        }
        return cartos;
    }

    public void procesarTrato(String sentence) throws ExcXogador, ExcAritmetica, ExcTrato, NonExisteCasilla, TipoInadecuado {
        String receptor = ""; //quitei nomeCasilla
        ArrayList<Propiedade> propiedadesPropostas = new ArrayList<>();
        float cartosPropostos = 0;
        ArrayList<Propiedade> propiedadesRecibidas = new ArrayList<>();
        float cartosRecibidos = 0;
        ArrayList<Propiedade> propiedadesNoAlquiler = new ArrayList<>();
        int turnosNoAlquiler = 0;
        float turnos = 0;

        String primeiraParte = sentence.substring(0, sentence.indexOf("("));
        String proposta = sentence.substring(sentence.indexOf("(") + 1, sentence.indexOf((",")));
        String recibo = sentence.substring(sentence.indexOf(",") + 1, sentence.indexOf(")"));

        primeiraParte = primeiraParte.replaceAll("[^a-zA-Z0-9 ]", ""); //quitamos todo menos caracteres, numeros e espazos
        proposta = proposta.replaceAll("[^a-zA-Z0-9 ] ", "");
        recibo = recibo.replaceAll("[^a-zA-Z0-9 ]", "");
        if (primeiraParte.startsWith(" ")) {
            primeiraParte = primeiraParte.substring(1); //en caso de introducirse un espazo despois dos parénteses, comas, etc., quitamolo
        }
        if (proposta.startsWith(" ")) {
            proposta = proposta.substring(1);
        }
        if (recibo.startsWith(" ")) {
            recibo = recibo.substring(1);
        }

        String[] palabrasPrimeira = primeiraParte.split(" ");
        String[] palabrasProposta = proposta.split(" ");
        String[] palabrasRecibo = recibo.split(" ");

        for (int i = 0; i < palabrasPrimeira.length; i++) { //detectamos o receptor
            if (this.tableiro.existeXogador(palabrasPrimeira[i])) {
                receptor = palabrasPrimeira[i];
                break;
            }
        }
        if (receptor.equalsIgnoreCase("")) {
            throw new NonExisteXogador();
        }
        if (receptor.equalsIgnoreCase(this.turno.getXogadorTurno().getNomeXogador())) {
            throw new TratoUnMesmo(this.turno.getXogadorTurno());
        }
        cartosPropostos = procesarParteTrato(palabrasProposta, propiedadesPropostas);
        if (cartosPropostos < 0) {
            throw new FNumeroNegativo(cartosRecibidos, true);
        }
        for (Casilla casilla : propiedadesPropostas) { //modularizar
            if (!((Propiedade) casilla).pertenceAXogador(this.turno.getXogadorTurno())) {
                throw new NonPosue(this.turno.getXogadorTurno(), casilla.getNome());
            }
        }
        cartosRecibidos = procesarParteTrato(palabrasRecibo, propiedadesRecibidas);
        if (cartosRecibidos < 0) {
            throw new FNumeroNegativo(cartosRecibidos, true);
        }
        for (Casilla casilla : propiedadesRecibidas) { //modularizar
            if (!((Propiedade) casilla).pertenceAXogador(this.tableiro.getXogador(receptor))) {
                throw new NonPosue(this.tableiro.getXogador(receptor), casilla.getNome());
            }
        }
        if (sentence.indexOf("alq") >= 0) { //no caso de que a entrada teña a palabra alquiler
            String alquiler = sentence.substring(sentence.indexOf("(", sentence.indexOf("alquiler")), sentence.indexOf(")", sentence.indexOf("alquiler")));
            alquiler = alquiler.replaceAll("[^a-zA-Z0-9 ]", "");
            String[] palabrasAlquiler = alquiler.split(" ");
            turnos = (procesarParteTrato(palabrasAlquiler, propiedadesNoAlquiler));
            turnosNoAlquiler = (int) turnos;
            if (turnosNoAlquiler <= 0) {
                throw new INumeroNegativo(turnosNoAlquiler, false);
            }
            for (Propiedade propiedade : propiedadesNoAlquiler) { //modularizar
                if (!propiedade.pertenceAXogador(this.tableiro.getXogador(receptor))) {
                    throw new NonPosue(this.tableiro.getXogador(receptor), propiedade.getNome());
                }
            }
        }

        Xogador xogador = tableiro.getXogador(receptor);

        /*if (turnosNoAlquiler > 0) {
            for (Propiedade propiedade : propiedadesNoAlquiler) {
                xogador.getNoAlquileres().add(new NoAlquiler(xogador, propiedade, turnosNoAlquiler));
            }
        }*/

        Trato trato = new Trato(this.turno.getXogadorTurno(), this.tableiro.getXogador(receptor), propiedadesPropostas, cartosPropostos, propiedadesRecibidas, cartosRecibidos, propiedadesNoAlquiler, turnosNoAlquiler);

        xogador.recibirTrato(trato);
        this.turno.getXogadorTurno().emitirTrato(trato);
        trato.enunciarTrato();

    }

    public void eliminarTrato(String tratoid) throws ExcTrato {

        String id = tratoid.replaceAll("[^0-9]", ""); //quitamos todo o que non sexan dixitos do 1-9

        for (Trato trato : this.turno.getXogadorTurno().getTratosEmitidos()) {
            if (Integer.valueOf(id) == trato.Identificador()) {
                trato.eliminarTrato();
                consola.imprimir("Trato eliminado\n");
                return;
            }
        }

        throw new NonTenTrato(this.turno.getXogadorTurno(), Integer.valueOf(id));
    }

    public void listarTratosEmitidos() {  //esta función non é un requisito do proxecto
        for (Trato trato : this.turno.getXogadorTurno().getTratosEmitidos()) {
            consola.imprimir("Trato-" + trato.Identificador());
        }
    }

    public void listarTratosRecibidos() throws ExcTrato {
        boolean hai = false;
        for (Trato trato : this.turno.getXogadorTurno().getTratosRecibidos()) {
            consola.imprimir(trato.toString());
            hai = true;
        }
        if (!hai) {
            throw new NonHaiTratos(this.turno.getXogadorTurno());
        }
    }

    public void listarTratos() { //esta funcion non e un requisito do proxecto
        boolean hai = false;
        for (Xogador xogador : this.turno.getXogadores()) {
            for (Trato trato : xogador.getTratosRecibidos()) {
                consola.imprimir(trato.toString());
                hai = true;
            }
        }
        if (!hai) {
            consola.imprimir("Non ten tratos propostos actualmente\n");
        }
    }

    public void aceptarTrato(String tratoid) throws ExcXogador, ExcTrato {
        String id = tratoid.replaceAll("[^0-9]", ""); //quitamos todo o que non sexan dixitos do 1-9
        for (Trato trato : this.turno.getXogadorTurno().getTratosRecibidos()) {
            if (Integer.valueOf(id) == trato.Identificador()) { //cambiei todos os .getIdentificador por Identnficador()		
                if (trato.posible()) {
                    trato.facerIntercambios();
                    consola.imprimir("Intercambios realizados");
                    if (trato.getTurnosNoAlquiler() > 0) {
                        for (Propiedade propiedade : trato.getPropiedadesNoAlquiler()) {
                            trato.getEmisor().getNoAlquileres().add(new NoAlquiler(trato.getEmisor(), propiedade, trato.getTurnosNoAlquiler()));
                        }
                    }
                    trato.eliminarTrato();
                    return;
                }
            }
        }
        throw new NonTenTrato(this.turno.getXogadorTurno(), Integer.valueOf(id));
    }
    
    public void mostrarGanador()
    {
        consola.imprimir(this.turno.getXogadorTurno().getNomeXogador() + " e  o novo rei do Monopoly");
    }
}
