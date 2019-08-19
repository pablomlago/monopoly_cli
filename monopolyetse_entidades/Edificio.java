package monopolyetse_entidades;

import static monopolyetse_funcionalidades.Juego.consola;

/*Esta clase nunca se instanciará, xa que nunca construiremos
un edificio xenértico, senón tipos concretos de edificios
polo que o modificador apropiada é abstract.
*/
public abstract class Edificio {

    private String identificador;
    private Solar casilla;

    public Edificio(Casilla casilla) {
        if (casilla instanceof Solar) {
            this.casilla = (Solar) casilla;
            if (this instanceof Casa) {
                this.identificador = "casa-" + (this.casilla.numCasas() + 1);
            } else if (this instanceof Hotel) {
                this.identificador = "hotel-" + (this.casilla.numHoteles() + 1);
            } else if (this instanceof Piscina) {
                this.identificador = "piscina-" + (this.casilla.numPiscinas() + 1);
            } else {
                this.identificador = "pista-" + (this.casilla.numPistas() + 1);
            }
        } else {
            consola.imprimir("A casilla non e un solar, non se pode edificar");
        }
    }

    public Solar getCasilla() {
        return this.casilla;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String id) {
        this.identificador = id;
    }

    public Xogador getPropietario() {
        return this.casilla.getPropietario();
    }

    @Override
    public String toString() {
        return "{\n"
                + "\n\tid:" + identificador
                + "\n\tpropietario: " + this.casilla.getPropietario().getNomeXogador()
                + "\n\tcasilla: " + this.casilla.getNome()
                + "\n\tgrupo: " + this.casilla.getGrupo().getSubtipo()
                + "\n\tcoste: " + this.getPrezo();
    }

    public abstract float getPrezo();

}
