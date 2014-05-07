/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.services;

import com.keuda.model.Akun;
import com.keuda.model.IkuProgram;
import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.Kegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.OutcomeProgram;
import com.keuda.model.OutputKegiatan;
import com.keuda.model.Program;
import com.keuda.model.ProgramDipa;
import com.keuda.view.AkunStructureForTree;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public interface IKeudaSQL {

    long getMaxIndex(String tableName, Connection conn) throws SQLException;

    Akun createAkun(Akun akun, Connection conn) throws SQLException;

    void updateAkun(long oldAkunIndex, Akun akun, Connection conn) throws SQLException;

    void deleteAkun(long akunIndex, Connection conn) throws SQLException;

    void deleteAkunStrukturBySubAkun(long subAkun, Connection conn) throws SQLException;

    Akun getAkun(long akunIndex, Connection conn) throws SQLException;

    Akun getAkunSimple(long akunIndex, Connection conn) throws SQLException;

    Akun[] getAllAkun(Connection conn) throws SQLException;

    Akun[] getSuperAkun(Connection conn) throws SQLException;

    Akun[] getSubAkun(long superIndex, Connection conn) throws SQLException;

    void createAkunStruktur(long superAkun, long subAkun, Connection conn) throws SQLException;
    ////

    Akun[] getAkunRoot(Connection conn) throws SQLException;

    Akun[] getAkunByParent(Akun parent, Connection conn) throws SQLException;

    Akun[] getAkunPrinting(Connection conn) throws SQLException;

    Akun[] getAkunPrintingByParent(Akun parent, Connection conn) throws SQLException;

    AkunStructureForTree getAkunStruktures(Connection conn) throws SQLException;

    //    ItemMasterStructureForTree getItemMasterAndStructure(Connection conn) throws SQLException;
    ///PROGRAM
    Program createProgram(Program program, Connection conn) throws SQLException;

    void updateProgram(long oldProgramIndex, Program program, Connection conn) throws SQLException;

    void deleteProgram(long programIndex, Connection conn) throws SQLException;

    Program getProgram(long programIndex, Connection conn) throws SQLException;

    Program[] getAllProgram(Connection conn) throws SQLException;

    Program getProgramByCode(String programCode, Connection conn) throws SQLException;

    //KEGIATAN
    Kegiatan createKegiatan(Kegiatan kegiatan, Connection conn) throws SQLException;

    void updateKegiatan(long oldKegiatanIndek, Kegiatan kegiatan, Connection conn) throws SQLException;

    void deleteKegiatan(long kegiatanIndex, Connection conn) throws SQLException;

    Kegiatan getKegiatan(long kegiatanIndex, Connection conn) throws SQLException;

    Kegiatan[] getAllKegiatan(Connection conn) throws SQLException;

    Kegiatan[] getKegiatanSimple(Connection conn) throws SQLException;

    Kegiatan[] getKegiatanByProgram(long programIndex, Connection conn) throws SQLException;

    Kegiatan getKegiatanByCode(String kegiatanCode, Connection conn) throws SQLException;

    //PROGRAM DIPA
    ProgramDipa createProgramDipa(ProgramDipa dipa, Connection conn) throws SQLException;

    void updateProgramDipa(long oldDipaIndex, ProgramDipa dipa, Connection conn) throws SQLException;

    void deleteProgramDipa(long dipaIndex, Connection conn) throws SQLException;

    ProgramDipa getProgramDipa(long dipaIndex, Connection conn) throws SQLException;

    ProgramDipa[] getAllProgramDipa(Connection conn) throws SQLException;

    ProgramDipa[] getProgramDipaSimple(Connection conn) throws SQLException;

    ProgramDipa[] getProgramDipaByProgram(long programIndex, Connection conn) throws SQLException;

    //OUTCOME PROGRAM DIPA
    OutcomeProgram createOutcomeProdi(OutcomeProgram op, Connection conn) throws SQLException;

    void updateOutcomeProdi(OutcomeProgram outCome, Connection conn) throws SQLException;

    void deleteOutcomeProdi(OutcomeProgram prg, Connection conn) throws SQLException;

    OutcomeProgram getOutProdi(long programDipaId, int nomorUrutOutcome, Connection conn) throws SQLException;

    OutcomeProgram[] getAllOutcomeProdi(Connection conn) throws SQLException;

    //IKU PROGRAM
    IkuProgram createIkuProgram(IkuProgram iku, Connection conn) throws SQLException;

    void updateIku(IkuProgram iku, Connection conn) throws SQLException;

    void deleteIku(long programDipa, int nrIku, Connection conn) throws SQLException;

    IkuProgram getIkuProgram(long programDipa, int nrIku, Connection conn) throws SQLException;

    IkuProgram[] getAllIkuProgram(Connection conn) throws SQLException;

    /**
     * KEGIATAN DIPA, INDIKATORKEGIATAN & OUTPUTKEGIATAN
     *
     * @param kedip
     * @param conn
     * @return
     * @throws SQLException
     */
    //Kegiatan DIPA
    KegiatanDipa createKegiatanDipa(KegiatanDipa kedip, Connection conn) throws SQLException;

    void updateKegiatanDipa(long oldKegiatanDipaIndex, KegiatanDipa kedip, Connection conn) throws SQLException;

    void deleteKegiatanDipa(long kegiatandipaindex, Connection conn) throws SQLException;

    KegiatanDipa getKegiatanDipa(long kegiatanDipaIndex, Connection conn) throws SQLException;

    KegiatanDipa[] getAllKegiatanDipa(Connection conn) throws SQLException;

    //Indikator Kegiatan
    IndikatorKegiatan createIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException;

    void updateIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException;

    void deleteIndikatorKegiatan(long kegiatanDipa, int nrIndikator, Connection conn) throws SQLException;

    IndikatorKegiatan getIndikatorKegiatan(long kegiatanDipa, int nroutput, Connection conn) throws SQLException;

    IndikatorKegiatan[] getAllIndikatorKegiatan(Connection conn) throws SQLException;
    
    IndikatorKegiatan[] getAllIndikatorKegiatanByKegiatan(long kegiatanDipa, Connection conn)throws SQLException;
    

    //Output Kegiatan
    OutputKegiatan createOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException;

    void updateOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException;

    void deleteOutputKegiatan(long oldKegiatanDipa, int nrOutputKegiatanDipa, Connection conn) throws SQLException;

    OutputKegiatan getOutputKegiatan(long kegiatanDipa, int nroutput, Connection conn) throws SQLException;

    OutputKegiatan[] getAllOutputKegiatan(Connection conn) throws SQLException;

    OutputKegiatan[] getAllOutputKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException;
}