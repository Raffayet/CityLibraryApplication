package view.modeliTabela;

import model.Knjiga;
import repozitorijum.KnjigeRepo;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class KnjigeCitanostModel extends AbstractTableModel {

    private Map<Integer, Integer> data;
    private KnjigeRepo knjigeRepo;
    private String[] columnNames = { "Id", "ISBN", "Naslov","Autor","Kategorija", "Izdavac", "Mesto", "Godina", "Br.Str.", "Br.Citanja"};

    public KnjigeCitanostModel(Map<Integer, Integer> data, KnjigeRepo knjigeRepo) {
        this.data = data;
        this.knjigeRepo = knjigeRepo;
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

    private int getNthElementIzMape(int row) {
        int br = 0;
        for (Map.Entry<Integer, Integer> elem : data.entrySet()) {
            if (row == br) {
                return elem.getKey();
            }
            ++br;
        }
        return 0;
    }


    @Override
    public Object getValueAt(int row, int col) {
        Knjiga p = knjigeRepo.getById(getNthElementIzMape(row));
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getIsbn();
            case 2:
                return p.getNaslov();
            case 3:
                return p.getImeGlavnogAutora() + " " + p.getPrezimeGlavnogAutora();
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
                return data.get(p.getId());
            default:
                return null;
        }
    }
}
