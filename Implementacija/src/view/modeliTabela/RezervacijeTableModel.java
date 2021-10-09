package view.modeliTabela;

import model.Clan;
import model.IzdataClanarina;
import model.Knjiga;
import model.Rezervacija;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RezervacijeTableModel extends AbstractTableModel {

    private List<Rezervacija> data;
    private String[] columnNames = { "Id", "Dat.Rezervacije", "Clan","JMBG", "Naslov", "Autor", "StatusPrimerka", "PrimerakVracen", "OcekivanDat.Vracanja", "Dat.Vracanja", "ClanKodKojegJe(Bio)Primerak"};

    public RezervacijeTableModel(List<Rezervacija> data) {
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
        Rezervacija p = data.get(row);
        switch (col) {
            case 0:
                return p.getId();
            case 1:
                return p.getDatumRezervacije();
            case 2:
                return p.getClan().getIme() + " " + p.getClan().getPrezime();
            case 3:
                return p.getClan().getJmbg();
            case 4:
                return p.getIzdatPrimerak().getPrimerakKnjige().getKnjiga().getNaslov();
            case 5:
            {
                Knjiga knjiga = p.getIzdatPrimerak().getPrimerakKnjige().getKnjiga();
                return knjiga.getImeGlavnogAutora() + " " + knjiga.getPrezimeGlavnogAutora();
            }
            case 6:
                return p.getIzdatPrimerak().getPrimerakKnjige().getRaspolozivost();
            case 7:
                return p.getIzdatPrimerak().vracen();
            case 8:
                return p.getIzdatPrimerak().getOcekivaniDatumVracanja().toString();
            case 9:
                if (p.getIzdatPrimerak().getDatumVracanja() == null) {
                    return "nije vracen";
                }
                return p.getIzdatPrimerak().getDatumVracanja().toString();
            case 10:
            {
                Clan clan = p.getIzdatPrimerak().getClan();
                return clan.getIme() + " " + clan.getPrezime();
            }
            default:
                return null;
        }
    }
}
