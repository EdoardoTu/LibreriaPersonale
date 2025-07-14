package Command;

import BackEnd.Libro;
import BackEnd.Template;

import java.util.ArrayList;

public class aggiungiCommand extends undoComune {


    private ArrayList<Libro> aggiunti;

    public aggiungiCommand(Template r, ArrayList<Libro> ag) {
        super(r);
        this.aggiunti = ag;
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.save(repo.getFilepath(),this.aggiunti);
    }
}
