package view.modeliTabela;

import model.Clan;
import model.Knjiga;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClanoviSaClanarinamaTableModel extends AbstractTableModel {

    private List<Clan> data;
    private String[] columnNames = { "Id", "Clan", "JMBG","VrstaClana", "TipTrenutneClanarine", "DatumIzdavanja", "Cena"};

    public ClanoviSaClanarinamaTableModel(List<Clan> data) {
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
        Clan p = data.get(row);
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getIme() + " " + p.getPrezime();
            case 2:
                return p.getJmbg();
            case 3:
                return p.getVrstaClana().toString();
            case 4:
                return p.getTrenutnaClanarina().getClanarina().getTipClanarine().toString();
            case 5:
                return p.getTrenutnaClanarina().getDatumIzdavanja();
            case 6:
                return p.getTrenutnaClanarina().getClanarina().getCena();
            default:
                return null;
        }
    }
}
