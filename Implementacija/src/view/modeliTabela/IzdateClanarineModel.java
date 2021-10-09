package view.modeliTabela;

import model.IzdataClanarina;
import model.Knjiga;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class IzdateClanarineModel extends AbstractTableModel {
    private List<IzdataClanarina> data;
    private String[] columnNames = { "Id", "Dat.Izdavanja", "Clan","TipClanarine","Cena"};

    public IzdateClanarineModel(List<IzdataClanarina> data) {
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
        IzdataClanarina p = data.get(row);
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getDatumIzdavanja().toString();
            case 2:
                return p.getClan().getIme() + " " + p.getClan().getPrezime();
            case 3:
                return p.getClanarina().getTipClanarine();
            case 4:
                return p.getClanarina().getCena();
            default:
                return null;
        }
    }
}
