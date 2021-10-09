package view.pozajmljivac;

import izuzeci.*;
import kontroler.ClanController;
import kontroler.IzdatPrimerakController;
import kontroler.KategorijaKnjigeController;
import kontroler.KnjigaController;
import model.Clan;
import model.PrimerakKnjige;
import net.miginfocom.swing.MigLayout;
import repozitorijum.KategorijeKnjigaRepo;
import repozitorijum.KnjigeRepo;
import view.modeliTabela.KnjigaTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IzdavanjaKnjigaDialog extends JDialog {

    private KnjigeRepo knjigeRepo;
    private KategorijaKnjigeController kategorijaKnjigeController;
    private ClanController clanController;
    private Clan clan;
    private KnjigaController knjigaController;
    private IzdatPrimerakController izdatPrimerakController;

    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);
    protected JList listGrupe;
    protected JButton btnIzdajKnjigu = new JButton("IZDAJ KNJIGU");
    protected JButton btnPretraziClana = new JButton("Pretrazi clana");

    protected JLabel lblJmbg = new JLabel("Unesite jmbg clana:");
    protected JTextField tfJmbg = new JTextField(50);
    protected JLabel lblIme = new JLabel("Ime:");
    protected JTextField tfIme = new JTextField(50);
    protected JLabel lblPrezime = new JLabel("Prezime:");
    protected JTextField tfPrezime = new JTextField(50);
    protected JLabel lblSelektujteStavkuIzTbl = new JLabel("SELEKTUJTE KNJIGU IZ TABELE");
    protected JLabel lblPretraga = new JLabel("Pretraga:");
    protected JLabel lblKategorijeKnjiga = new JLabel("Kategorije knjiga:");
    protected JScrollPane src;


    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public IzdavanjaKnjigaDialog(JFrame roditelj, boolean modal, KnjigeRepo knjigeRepo,
                                 KategorijeKnjigaRepo kategorijeKnjigaRepo,
                                 ClanController clanController,
                                 KnjigaController knjigaController,
                                 IzdatPrimerakController izdatPrimerakController) {
        super(roditelj, modal);
        this.knjigeRepo = knjigeRepo;
        this.clanController = clanController;
        this.knjigaController = knjigaController;
        this.izdatPrimerakController = izdatPrimerakController;
        this.kategorijaKnjigeController = new KategorijaKnjigeController(kategorijeKnjigaRepo);

        this.listGrupe = new JList(kategorijaKnjigeController.getNaziviKategorija().toArray());
        this.src = new JScrollPane(listGrupe);
        setTitle("Izdavanje knjiga");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();

    }

    private void initGUI() {
        podesiSever();
        podesiCentar();
        podesiJug();
        sakri();
    }

    private void sakri() {
        lblIme.setVisible(false);
        tfIme.setVisible(false);
        lblPrezime.setVisible(false);
        tfPrezime.setVisible(false);
        lblSelektujteStavkuIzTbl.setVisible(false);
        tabelaStavke.setVisible(false);
        lblPretraga.setVisible(false);
        tfSearch.setVisible(false);
        lblKategorijeKnjiga.setVisible(false);
        src.setVisible(false);
        listGrupe.setVisible(false);
        btnIzdajKnjigu.setVisible(false);
    }

    private void otkritCuTiTajnuMaluKaoLajnu() {
        lblIme.setVisible(true);
        tfIme.setVisible(true);
        lblPrezime.setVisible(true);
        tfPrezime.setVisible(true);
        lblSelektujteStavkuIzTbl.setVisible(true);
        tabelaStavke.setVisible(true);
        lblPretraga.setVisible(true);
        tfSearch.setVisible(true);
        lblKategorijeKnjiga.setVisible(true);
        src.setVisible(true);
        listGrupe.setVisible(true);
        btnIzdajKnjigu.setVisible(true);
        tfJmbg.setEnabled(false);
        btnPretraziClana.setEnabled(false);
        tabelaStavke.setVisible(true);
        tfIme.setText(this.clan.getIme());
        tfPrezime.setText(this.clan.getPrezime());
        btnIzdajKnjigu.setVisible(true);
    }

    private void podesiSever() {
        JPanel pnl = new JPanel(new MigLayout());
        pnl.add(lblJmbg, "split 3, sg a");
        pnl.add(tfJmbg);
        pnl.add(btnPretraziClana, "pushx, growx, wrap");
        pnl.add(lblIme, "split 2, sg a");
        pnl.add(tfIme, "pushx, growx, wrap");
        tfIme.setEnabled(false);
        pnl.add(lblPrezime, "split 2, sg a");
        pnl.add(tfPrezime, "pushx, growx, wrap");
        tfPrezime.setEnabled(false);
        lblSelektujteStavkuIzTbl.setForeground(Color.BLUE);
        pnl.add(lblSelektujteStavkuIzTbl);

        this.getContentPane().add(pnl, BorderLayout.NORTH);

    }

    private void podesiCentar() {
        setTableData();
        JPanel panel = new JPanel(new GridLayout(1, 1));
        tabelaStavke.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaStavke.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaStavke.getTableHeader().setReorderingAllowed(false);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
        tabelaStavke.setRowSorter(tableSorter);
        JScrollPane srcPan = new JScrollPane(tabelaStavke);
        panel.add(srcPan);
        this.getContentPane().add(panel, BorderLayout.CENTER);

    }

    private void setTableData() {
        tabelaStavke.setModel(new KnjigaTableModel(this.knjigeRepo.getListaEntiteta()));
    }


    private void podesiJug() {
        btnIzdajKnjigu.setBackground(Color.BLUE);
        btnIzdajKnjigu.setForeground(Color.WHITE);
        src.setPreferredSize(new Dimension(200, 100));
        JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelJug.add(lblPretraga);
        panelJug.add(tfSearch);
        panelJug.add(lblKategorijeKnjiga);
        panelJug.add(src);
        panelJug.add(btnIzdajKnjigu);


        this.getContentPane().add(panelJug, BorderLayout.SOUTH);
    }

    private void initAkcije() {
        this.tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (tfSearch.getText().trim().length() == 0) {
                    tableSorter.setRowFilter(null);
                } else {
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
                }
            }
        });

        this.listGrupe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tfSearch.setText(listGrupe.getSelectedValue().toString());
            }
        });

        btnPretraziClana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    IzdavanjaKnjigaDialog.this.clan = clanController.getClanSaZadatimJMBGbrojem(tfJmbg.getText());
                    otkritCuTiTajnuMaluKaoLajnu();
                }
                catch (NedostajucaVrednost ex) {
                    JOptionPane.showMessageDialog(IzdavanjaKnjigaDialog.this, "Niste uneli JMBG", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (PogresanFormat ex) {
                    JOptionPane.showMessageDialog(IzdavanjaKnjigaDialog.this, "Neispravan format. JMBG mora imati 13 cifara", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (KorisnikNijeNadjen ex) {
                    JOptionPane.showMessageDialog(IzdavanjaKnjigaDialog.this, "Nije moguce pronaci korisnika sa unetim JMBG brojem", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIzdajKnjigu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da izdate jedan od primeraka ove knjige", "Pitanje",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        int idKnjige = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());

                        try{
                            PrimerakKnjige primerakKnjige = knjigaController.getSlobodanPrimerakKnjige(idKnjige);
                            izdatPrimerakController.kreirajNoviIzdatiPrimerak(primerakKnjige, IzdavanjaKnjigaDialog.this.clan);
                            JOptionPane.showMessageDialog(null, "Primerak ove knjige je uspesno izdat", "Informacija", JOptionPane.INFORMATION_MESSAGE);
                            IzdavanjaKnjigaDialog.this.dispose();
                        }
                        catch (SviPrimerciSuZauzeti ex) {
                            JOptionPane.showMessageDialog(null, "Svi primerci ove knjige su zauzeti", "Greška", JOptionPane.WARNING_MESSAGE);
                        }
                        catch (PredjenLimitZaMaksBrojTrenutnoIzdatihPrimerakaClanu ex) {
                            JOptionPane.showMessageDialog(null, "Predjen maksimalan limit trenutno izdatih knjiga za citanje za ovog clana", "Greška", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
