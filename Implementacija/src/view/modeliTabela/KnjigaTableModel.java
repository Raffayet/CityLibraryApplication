package view.modeliTabela;

import model.Knjiga;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnjigaTableModel extends AbstractTableModel {

    private List<Knjiga>data;
    private String[] columnNames = { "Id", "ISBN", "Naslov","Autor","Kategorija", "Izdavac", "Mesto", "Godina", "Br.Str.", "Visina", "UkupanBr.", "Br.Slobodnih", "Br.Rezer.", "Iznosiva"};

    public KnjigaTableModel(List<Knjiga> data) {
        this.data = data;
    }


    @Override
    public Class<?> getColumnClass(int column) {
        return this.getValueAt(0, column).getClass();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Knjiga p = data.get(row);
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getIsbn();
            case 2:
                return p.getNaslov();
            case 3: {
                if (p.getAutori().size()!=0)
                    return p.getImeGlavnogAutora() + " " + p.getPrezimeGlavnogAutora();
                else return "";
            }
            case 4:
                return p.getKateogrije().get(0).getNaziv();
            case 5:
                return p.getNazivIzdavaca();
            case 6:
                return p.getMestoIzdavaca();
            case 7:
                return p.getGodinaIzdanja();
            case 8:
                return p.getBrojStranica();
            case 9:
                return p.getVisina();
            case 10:
                return p.getUkupanBroj();
            case 11:
                return p.getBrojSlobodnih();
            case 12:
                return p.getBrojRezervisanih();
            case 13:
                return p.getIznosiva();
            default:
                return null;
        }
    }
}
