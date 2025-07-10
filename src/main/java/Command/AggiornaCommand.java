package Command;

import Template.Repository2;
import Template.Template;

public class AggiornaCommand extends undoComune {


    public AggiornaCommand(Template r) {
        super(r);
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.update(repo.getFilepath());
    }
}
