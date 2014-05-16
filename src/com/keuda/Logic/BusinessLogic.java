/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.Logic;

import com.keuda.exception.AuthorizationException;
import com.keuda.exception.KeudaException;
import com.keuda.impl.service.KeudaSQL;
import com.keuda.model.*;
import com.keuda.services.IKeudaSQL;
import com.keuda.view.AkunStructureForTree;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class BusinessLogic {

    private Connection k_conn;

    public BusinessLogic(Connection conn) {
        this.k_conn = conn;
    }

    public Akun createAkun(Akun akun1, long sessionId, String modul) {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();

            k_conn.setAutoCommit(false);

            Akun akun2 = sql.createAkun(akun1, k_conn);

            k_conn.commit();
            System.out.println("SQLSAP: Object " + akun2.getNamaAkun() + " berhasil ditambahkan ke database");
            k_conn.setAutoCommit(true);

            return akun2;
        } catch (Exception e) {
            if (k_conn != null) {
                try {
                    k_conn.rollback();
                    k_conn.setAutoCommit(true);
                } catch (Exception ex) {
                }
            }

        }
        return null;
    }

    public Akun getAkun(long akunIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkun(akunIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void createAkunStruktur(long parentAkun, long subAkun, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.createAkunStruktur(parentAkun, subAkun, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun updateAkun(long oldIndex, Akun acc, long sessionId, String modul) throws AuthorizationException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();

            sql.updateAkun(oldIndex, acc, k_conn);

            return new Akun(oldIndex, acc);
        } catch (Exception e) {
            throw new AuthorizationException(e.getMessage());
        }
    }

//    public ItemMasterStructureForTree getItemMasterAndStructure(long sessionId, String modul) throws SQLException{
//        //Initialized Authorization Business Logic
//        
//        //end of initialized Authorization Business Logic
//        try {
//            //authorized            
//            
//            //end of authorized
//            IKeudaSQL sql = new KeudaSQL();
//            return sql.getItemMasterAndStructure(k_conn);
//        } catch (Exception e) {
//            throw new SQLException(e.toString());
//        }
//    }
    public AkunStructureForTree getAccountStruktures(long sessionId, String modul) throws SQLException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunStruktures(k_conn);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteAccount(long akunIndex, long sessionId, String modul) throws SQLException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteAkun(akunIndex, k_conn);
        } catch (Exception e) {
            throw new SQLException("Gagal Menghapus Akun dengan pesan :" + e.getMessage());
        }
    }

    public Akun[] getAllAkun(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllAkun(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] gettAccountRoot(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized            
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunRoot(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] getAkunByParent(Akun parent, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunByParent(parent, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] getSuperAkun(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunRoot(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] getSubAkun(long superindex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getSubAkun(superindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] getAkunPrinting(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunPrinting(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Akun[] getAkunPrintingByParent(Akun parent, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAkunPrintingByParent(parent, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Program createProgram(Program program, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createProgram(program, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Program updateProgram(long oldProgramIndex, Program program, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateProgram(oldProgramIndex, program, k_conn);
            return new Program(program.getProgramIndex(), program.getProgramCode(), program.getProgramName());
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteProgram(long programIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic        
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteProgram(programIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Program getProgram(long programIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getProgram(programIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Program[] getAllProgram(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllProgram(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Program getProgramByCode(String programCode, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getProgramByCode(programCode, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan createKegiatan(Kegiatan kegiatan, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createKegiatan(kegiatan, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan updateKegiatan(long oldKegiatanIndex, Kegiatan kegiatan, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateKegiatan(oldKegiatanIndex, kegiatan, k_conn);
            kegiatan.setKegiatanIndex(oldKegiatanIndex);
            return kegiatan;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteKegiatan(long kegiatanIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteKegiatan(kegiatanIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan getKegiatan(long kegiatanIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getKegiatan(kegiatanIndex, k_conn);

        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan[] getAllKegiatan(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllKegiatan(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan[] getKegiatanSimple(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getKegiatanSimple(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Kegiatan[] getKegiatanByProgram(long programCode, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getKegiatanByProgram(programCode, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    /**
     *
     * @param kegiatanCode
     * @param sessionId
     * @param modul
     * @return
     * @throws KeudaException
     */
    public Kegiatan getKegiatanByCode(String kegiatanCode, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getKegiatanByCode(kegiatanCode, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa createProgramDipa(ProgramDipa prodi, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createProgramDipa(prodi, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa updateProgramDipa(long oldDipaIndex, ProgramDipa dipa, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateProgramDipa(oldDipaIndex, dipa, k_conn);
            return dipa;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteProgramDipa(long dipaIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteProgramDipa(dipaIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa getProgramDipa(long dipaIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getProgramDipa(dipaIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa[] getAllProgramDipa(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllProgramDipa(k_conn);

        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa[] getProgramDipaSimple(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getProgramDipaSimple(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public ProgramDipa[] getProgramDipaByProgram(long programIndex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getProgramDipaByProgram(programIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutcomeProgram createOutComeProdi(OutcomeProgram op, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createOutcomeProdi(op, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutcomeProgram updateOutcomeProdi(OutcomeProgram outcome, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateOutcomeProdi(outcome, k_conn);
            return outcome;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteOutcomeProdi(OutcomeProgram outcome, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteOutcomeProdi(outcome, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutcomeProgram getOutProdi(long programDipa, int nomorUrut, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getOutProdi(programDipa, nomorUrut, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutcomeProgram[] getAllOutcomeProdi(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllOutcomeProdi(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }

    }

    public IkuProgram createIkuProgram(IkuProgram iku, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createIkuProgram(iku, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IkuProgram updateIku(IkuProgram iku, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateIku(iku, k_conn);
            return iku;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteIkuProgram(long programDipa, int noUrutIku, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteIku(programDipa, noUrutIku, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IkuProgram getIkuProgram(long programDipa, int noUrutIku, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getIkuProgram(programDipa, noUrutIku, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IkuProgram[] getAllIkuProgram(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllIkuProgram(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public KegiatanDipa createKegiatanDipa(KegiatanDipa kedip, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createKegiatanDipa(kedip, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public KegiatanDipa updateKegiatanDipa(long oldKedip, KegiatanDipa kedip, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.updateKegiatanDipa(oldKedip, kedip, k_conn);
            return kedip;
        } catch (SQLException ex) {
            throw new KeudaException(ex.getMessage());
        }

    }

    public void deleteKegiatanDipa(long kegiatandipaindex, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteKegiatanDipa(kegiatandipaindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public KegiatanDipa getKegiatanDipa(long kegiatandipaindex, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getKegiatanDipa(kegiatandipaindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public KegiatanDipa[] getAllKegiatanDipa(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllKegiatanDipa(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IndikatorKegiatan createIndikatorKegiatan(IndikatorKegiatan inke, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createIndikatorKegiatan(inke, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IndikatorKegiatan updateIndikatorKegiatan(IndikatorKegiatan inke, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateIndikatorKegiatan(inke, k_conn);
            return inke;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteIndikatorKegiatan(long kegiatanDipa, int nrIndikator, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteIndikatorKegiatan(kegiatanDipa, nrIndikator, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IndikatorKegiatan getIndikatorKegiatan(long kegiatanDipa, int nrKegiatan, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getIndikatorKegiatan(kegiatanDipa, nrKegiatan, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IndikatorKegiatan[] getAllIndikatorKegiatan(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllIndikatorKegiatan(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutputKegiatan createOutputKegiatan(OutputKegiatan ouke, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.createOutputKegiatan(ouke, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutputKegiatan updateOutputKegiatan(OutputKegiatan ouke, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.updateOutputKegiatan(ouke, k_conn);
            return ouke;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public void deleteOutputKegiatan(long kegiatanDipa, int nrOutput, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteOutputKegiatan(kegiatanDipa, nrOutput, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutputKegiatan getOutputKegiatan(long kegiatanDipa, int nrOutput, long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getOutputKegiatan(kegiatanDipa, nrOutput, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutputKegiatan[] getAllOutputKegiatan(long sessionId, String modul) throws KeudaException {
        //Initialized Authorization Business Logic

        //end of initialized Authorization Business Logic
        try {
            //authorized            

            //end of authorized
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllOutputKegiatan(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public OutputKegiatan[] getAllOutputKegiatanByKegiatan(long kegiatanDipa, long sessionId, String modul) throws KeudaException {

        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllOutputKegiatanByKegiatan(kegiatanDipa, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public IndikatorKegiatan[] getAllIndikatorKegiatanByKegiatan(long kegiatanDipa, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllIndikatorKegiatanByKegiatan(kegiatanDipa, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    Dipa createDipa(Dipa dipa, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.createDipa(dipa, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Dipa updateDipa(long oldDipaIndex, Dipa dipa, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.updateDipa(oldDipaIndex, dipa, k_conn);
            return dipa;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    void deleteDipa(long dipaIndex, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteDipa(dipaIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Dipa getDipa(long dipaIndex, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getDipa(dipaIndex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Dipa[] getAllDipa(long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllDipa(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }

    public Dipa[] getAllDipaByTahunAnggaran(int tahunAnggaran, long sessionId, String modul) throws KeudaException {
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllDipaByTahunAnggaran(tahunAnggaran, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    Rincian createRincian(Rincian rincian, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.createRincian(rincian, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public Rincian updateRincian(long oldrincianindex, Rincian rincian, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.updateRincian(oldrincianindex, rincian, k_conn);
            return rincian;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public void deleteRincian(long rincianindex, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteRincian(rincianindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public Rincian getRincian(long rincianindex, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getRincian(rincianindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public Rincian[] getAllRincian(long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllRincian(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public Rincian[] getAllRincianByDipa(long dipaindex, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllRincianByDipa(dipaindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    RincianDipa createRincianDipa(RincianDipa rdipa, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.createRincianDipa(rdipa, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public RincianDipa updateRincianDipa(long oldKodeRekening, RincianDipa rdipa, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.updateRincianDipa(oldKodeRekening, rdipa, k_conn);
            return rdipa;
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public void deleteRincianDipa(long rincianindex, String koderekening, Connection conn )throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            sql.deleteRincianDipa(rincianindex, koderekening, conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public RincianDipa getRincianDipa(long rincianindex, String koderekening, long sessionid, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getRincianDipa(rincianindex, koderekening, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public RincianDipa[] getAllRincianDipa(long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllRincianDipa(k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
    
    public RincianDipa[] getRincianDipaByRincian(long rincianindex, long sessionId, String modul)throws KeudaException{
        try {
            IKeudaSQL sql = new KeudaSQL();
            return sql.getAllRincianDipaByRincian(rincianindex, k_conn);
        } catch (Exception e) {
            throw new KeudaException(e.getMessage());
        }
    }
}
