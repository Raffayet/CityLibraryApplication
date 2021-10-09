package view.modeliTabela;

import kontroler.RezervisanPrimerakController;
import model.Clan;
import model.IzdatPrimerak;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class IzdatPrimerakModel extends AbstractTableModel {
    private List<IzdatPrimerak> data;
    private RezervisanPrimerakController rezervisanPrimerakController;
    private String[] columnNames = { "Id", "IdPrimerka", "StatusPrimerka", "Naslov","Clan","JMBG", "Dat.Izdavanja", "RokVracanja",
            "Dat.Vracanja", "ProduzenoCitanje", "Vracen", "Ocena", "Rezervisan", "ClanRezervacije" };

    public IzdatPrimerakModel(List<IzdatPrimerak> data, RezervisanPrimerakController rezervisanPrimerakController) {
        this.data = data;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
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
        IzdatPrimerak p = data.get(row);
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getPrimerakKnjige().getId();
            case 2:
                return p.getPrimerakKnjige().getRaspolozivost();
            case 3:
                return p.getPrimerakKnjige().getKnjiga().getNaslov();
            case 4:
                return p.getClan().getIme() + " " + p.getClan().getPrezime();
            case 5:
                return p.getClan().getJmbg();
            case 6:
                return p.getDatumIzdavanja().toString();
            case 7:
                return p.getOcekivaniDatumVracanja().toString();
            case 8:
            {
                if (p.getDatumVracanja() == null) {
                    return "nije vracena";
                }
                return p.getDatumVracanja().toString();
            }
            case 9:
                return p.produzenoCitanje();
            case 10:
                return p.vracen();
            case 11:
                return p.getOcena().toString();
            case 12:
                return rezervisanPrimerakController.izdatPrimerakJeRezervisan(p.getId());
            case 13:
            {
                Clan clan = rezervisanPrimerakController.getClanKojiJeRezevisaoIzdatPrimerak(p.getId());
                if (clan == null) {
                    return "nije rezervisan";
                }
                return clan.getIme() + " " + clan.getPrezime();
            }
            default:
                return null;
        }
    }
}
