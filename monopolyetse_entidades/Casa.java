package monopolyetse_entidades;

/*Os tipos de edificios que se empregaran están ben definidos,
e non presentan características particulares que xustifiquen
a división dos mesmos en diferentes subtipos. Así, de querer
introducir un novo edificio, probablemente se faría isto como unha 
subclase diferenciada de Edificio
*/
public final class Casa extends Edificio {

    public Casa(Casilla casilla) {
        super(casilla);
    }

    @Override
    public float getPrezo() {
        return this.getCasilla().getGrupo().getPrezoCasa();
    }
}
