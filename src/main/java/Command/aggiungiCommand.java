package Command;

import Template.Repository2;

public class aggiungiCommand extends undoComune {


    public aggiungiCommand(Repository2 r) {
        super(r);
    }
    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        System.out.println("aggiungiCommand");
        System.out.println(repo.getFilepath());
        repo.save(repo.getFilepath());
    }
}
