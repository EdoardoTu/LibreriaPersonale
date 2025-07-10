package Command;

import Template.Repository2;
import Template.Template;

public class aggiungiCommand extends undoComune {


    public aggiungiCommand(Template r) {
        super(r);
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.save(repo.getFilepath());
    }
}
