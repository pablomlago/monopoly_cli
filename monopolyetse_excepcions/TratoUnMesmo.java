package monopolyetse_excepcions;

import monopolyetse_entidades.Xogador;

public final class TratoUnMesmo extends ExcTrato
{
    public TratoUnMesmo(Xogador xogador)
    {
        super("Non podes facer tratos con ti mesmo!"
            + "\n\t\t" + xogador.getNomeXogador() + " intentou facer un trato consigo mesmo");
    }
}
