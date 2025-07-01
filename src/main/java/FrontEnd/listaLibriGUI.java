package FrontEnd;


import Template.Libro;
import Observer.InterfacciaObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class listaLibriGUI extends AbstractTableModel implements InterfacciaObserver {
    private ArrayList<Libro> libri;
    private final String[] columns = {"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"};
    public listaLibriGUI(ArrayList<Libro> libri) {
        this.libri = new ArrayList<>(libri);
    }
    @Override
    public void update(ArrayList<Libro> libri) {
        this.libri = libri;
        fireTableDataChanged();
    }
    public void setLibri(ArrayList<Libro> libri) {
        this.libri = libri;
    }
    @Override
    public int getRowCount() {
        return libri.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro libro = libri.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> libro.getTitolo();
            case 1 -> libro.getAutore();
            case 2 -> libro.getIsbn();
            case 3 -> libro.getGenere().toString();
            case 4 -> libro.getValutazione();
            case 5 -> libro.getStato().toString();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column) {
        if (column >= 0 && column < columns.length) {
            return columns[column];
        }
        return super.getColumnName(column);
    }
    public ArrayList<Libro> getLibri() {
        return libri;
    }
}
