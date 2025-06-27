package Command;

import Template.Repository2;

public class aggiungiCommand implements Command {

    private Repository2 repo;
    public aggiungiCommand(Repository2 r) {
        this.repo = r;

    }
    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
