package com.keuda.services;

import com.keuda.model.Akun;
import com.keuda.model.BankRef;
import com.keuda.model.Bendahara;
import com.keuda.model.DetailRincian;
import com.keuda.model.DetailRincianStruktur;
import com.keuda.model.Dipa;
import com.keuda.model.GrupItem;
import com.keuda.model.GrupKodrekRincian;
import com.keuda.model.IkuProgram;
import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.Kegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.KodrekRincian;
import com.keuda.model.Komponen;
import com.keuda.model.Markmap;
import com.keuda.model.MasterVolume;
import com.keuda.model.OutcomeProgram;
import com.keuda.model.OutputKegiatan;
import com.keuda.model.Program;
import com.keuda.model.ProgramDipa;
import com.keuda.model.Rincian;
import com.keuda.model.RincianDipa;
import com.keuda.model.RincianDipaKodrek;
import com.keuda.model.SPP;
import com.keuda.model.Satker;
import com.keuda.model.SubKomponen;
import com.keuda.view.AkunStructureForTree;
import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public interface IKeudaSQL {

    long getMaxIndex(String field, String tableName, Connection conn) throws SQLException;

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
    
    ProgramDipa[] getAllProgramDipa2(Connection conn) throws SQLException;    

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
    
    KegiatanDipa createKegiatanDipa(KegiatanDipa kedip, Connection conn) throws SQLException;

    void updateKegiatanDipa(long oldKegiatanDipaIndex, KegiatanDipa kedip, Connection conn) throws SQLException;

    void deleteKegiatanDipa(long kegiatandipaindex, Connection conn) throws SQLException;

    KegiatanDipa getKegiatanDipa(long kegiatanDipaIndex, Connection conn) throws SQLException;
    
    KegiatanDipa getKegiatanDipaByKegiatanAndTahun(Kegiatan kegiatan, short tahun, Connection conn)throws SQLException;

    KegiatanDipa[] getAllKegiatanDipa(Connection conn) throws SQLException;
    
    KegiatanDipa[] getAllKegiatanDipaByTahun(short tahun, Connection conn)throws SQLException;
    
    KegiatanDipa[] getAllKegiatanDipaByProdi(ProgramDipa programdipa, Connection conn)throws SQLException;
    
    KegiatanDipa[] getAllKegiatanDipaByProdi2(ProgramDipa programdipa, Connection conn)throws SQLException;

    //Indikator Kegiatan
    IndikatorKegiatan createIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException;

    void updateIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException;

    void deleteIndikatorKegiatan(long kegiatanDipa, int nrIndikator, Connection conn) throws SQLException;

    IndikatorKegiatan getIndikatorKegiatan(long kegiatanDipa, int nroutput, Connection conn) throws SQLException;

    IndikatorKegiatan[] getAllIndikatorKegiatan(Connection conn) throws SQLException;

    IndikatorKegiatan[] getAllIndikatorKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException;

    //Output Kegiatan
    OutputKegiatan createOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException;

    void updateOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException;

    void deleteOutputKegiatan(long oldKegiatanDipa, int nrOutputKegiatanDipa, Connection conn) throws SQLException;

    OutputKegiatan getOutputKegiatan(long kegiatanDipa, int nroutput, Connection conn) throws SQLException;

    OutputKegiatan getOutputKegiatan(long outputKegiatanDipa, Connection conn) throws SQLException;
    
    OutputKegiatan getOutputKegiatanByKegiatandipaAndKodeOutput(long kegiatandipa, String nroutput, Connection conn) throws SQLException;

    OutputKegiatan[] getAllOutputKegiatan(Connection conn) throws SQLException;

    OutputKegiatan[] getAllOutputKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException;
    
    OutputKegiatan[] getAllOutputKegiatanByKegiatan2(long kegiatanDipa, Connection conn) throws SQLException;
    
    OutputKegiatan[] getAllOutputKegiatanByTahun(short tahun, Connection conn) throws SQLException;

    /**
     * DIPA, RINCIAN & RINCIAN DIPA
     */
    Dipa createDipa(Dipa dipa, Connection conn) throws SQLException;

    void updateDipa(long oldDipaIndex, Dipa dipa, Connection conn) throws SQLException;

    void deleteDipa(long dipaIndex, Connection conn) throws SQLException;

    Dipa getDipa(long dipaindex, Connection conn) throws SQLException;

    Dipa[] getAllDipa(Connection conn) throws SQLException;

    Dipa getAllDipaByTahunAnggaran(int tahunAnggaran, Connection conn) throws SQLException;

    Rincian createRincian(Rincian rincian, Connection conn) throws SQLException;

    void updateRincian(long oldrincianindex, Rincian rincian, Connection conn) throws SQLException;

    void deleteRincian(long rincianIndex, Connection conn) throws SQLException;

    Rincian getRincian(long rincianIndex, Connection conn) throws SQLException;
    
    Rincian getRincianBySubKompAndKode(long subkompid, String koderekening,  Connection conn) throws SQLException;
    
    Rincian[] getRincianBySubkomp(long subkompid, Connection conn)throws SQLException;
    
    Rincian[] getRincianBySubkomp2(long subkompid, Connection conn)throws SQLException;

    Rincian[] getAllRincian(Connection conn) throws SQLException;

    Rincian[] getAllRincianByDipa(long dipaindex, Connection conn) throws SQLException;
    

    RincianDipa createRincianDipa(RincianDipa rdipa, Connection conn) throws SQLException;

    void updateRincianDipa(long oldkodeRekening, RincianDipa rDipa, Connection conn) throws SQLException;

    void deleteRincianDipa(long rincianIndex, String kodeRekening, Connection conn) throws SQLException;

    RincianDipa getRincianDipa(long rincianindex, String koderekening, Connection conn) throws SQLException;

    RincianDipa[] getAllRincianDipa(Connection conn) throws SQLException;

    RincianDipa[] getAllRincianDipaByRincian(long rincianindex, Connection conn) throws SQLException;

    Komponen createKomponen(Komponen komponen, Connection conn) throws SQLException;

    void updateKomponen(long oldKomponenIndex, Komponen komponen, Connection conn) throws SQLException;

    void deleteKomponen(long komponenindex, Connection conn) throws SQLException;

    Komponen getKomponen(long komponenindex, Connection conn) throws SQLException;
    
    Komponen getKomponenByOutput(long outputKegiatan, String komponencode, Connection conn)throws SQLException;

    Komponen[] getAllKomponen(Connection conn) throws SQLException;

    Komponen[] getAllKomponenByOutputKegiatan(long outputKegiatanIndex, Connection conn) throws SQLException;
    
    Komponen[] getAllKomponenByOutputKegiatan2(long outputKegiatanIndex, Connection conn) throws SQLException;

    /**
     *
     * 
     *
     * @param Subkom
     * @param conn
     * @return 
     * @throws java.sql.SQLException 
     * 
     */
    SubKomponen createSubKomponen(SubKomponen Subkom, Connection conn) throws SQLException;

    void updateSubKomponen(long oldsubkomponen, SubKomponen subkomponen, Connection conn) throws SQLException;

    void deleteSubKomponen(long subkomponenindex, Connection conn) throws SQLException;

    SubKomponen getSubKomponen(long subkomponenindex, Connection conn) throws SQLException;
    
    SubKomponen getSubKomponenByKomponen(long komponenid, String subkomponenkode, Connection conn) throws SQLException;
   
    
    SubKomponen[] getAllSubKomponen(Connection conn) throws SQLException;

    SubKomponen[] getAllSubKomponenByKomponen(long komponenindex, Connection conn) throws SQLException;
    
    SubKomponen[] getAllSubKomponenByKomponen2(long komponenindex, Connection conn) throws SQLException;

    /**
     * GrupItem, KodrekRincian, GrupKodrekRincian, KodrekOutput, KodrekKomponen
     * & KodrekSubkomponen
     */
    
    RincianDipaKodrek createRincianDipaKodrek(RincianDipaKodrek rincianDipaKodrek, Connection conn) throws SQLException;

    void updateRincianDipaKodrek(long oldRincianDipaKodrekIndex, RincianDipaKodrek rincianDipaKodrek, Connection conn) throws SQLException;

    void deleteRincianDipaKodrek(long rincianDipaKodrekIndex, Connection conn) throws SQLException;

    RincianDipaKodrek getRincianDipaKodrek(long rincianDipaKodrekIndex, Connection conn) throws SQLException;

    RincianDipaKodrek[] getAllRincianDipaKodrek(Connection conn) throws SQLException;
    
    RincianDipaKodrek[] getAllRincianDipaKodrekByKomponen(long komponenindex, Connection conn)throws SQLException;

    GrupItem createGrupItem(GrupItem grupItem, Connection conn) throws SQLException;

    void updateGrupItem(long oldIndexGrup, GrupItem grupItem, Connection conn) throws SQLException;

    void deleteGrupItem(long oldIndexGrup, Connection conn) throws SQLException;

    GrupItem getGrupItem(long indexGrup, Connection conn) throws SQLException;

    GrupItem[] getAllGrupItem(Connection conn) throws SQLException;
    

    KodrekRincian createKodrekRincian(KodrekRincian kodrekRincian, Connection conn) throws SQLException;

    void updateKodrekRincian(long oldKodrekRincianIndex, KodrekRincian kodrekRincian, Connection conn) throws SQLException;

    void deleteKodrekRincian(long kodrekRincianIndex, Connection conn) throws SQLException;

    KodrekRincian getKodrekRincian(long kodrekRincianIndex, Connection conn) throws SQLException;

    KodrekRincian[] getAllKodrekRincian(Connection conn) throws SQLException;
    

    GrupKodrekRincian createGrupKodrekRincian(GrupKodrekRincian kodrekrincian, Connection conn) throws SQLException;

    void deleteGrupKodrekRincian(GrupKodrekRincian kodrekRincian, Connection conn) throws SQLException;

    GrupKodrekRincian[] getKodrekRincianByGrupItem(GrupItem grupItem, Connection conn) throws SQLException;

    GrupKodrekRincian getKodrekRincianByKodrekRincian(KodrekRincian kodrekRincian, Connection conn) throws SQLException;
    
    
    MasterVolume createMasterVolume(MasterVolume mv, Connection conn)throws SQLException;
    
    void updateMasterVolume(long oldMasterVolumeId, MasterVolume mv, Connection conn)throws SQLException;
    
    void deleteMasterVolume(long masterVolumeId, Connection conn)throws SQLException;
    
    MasterVolume getMasterVolume(long masterVolumeId, Connection conn)throws SQLException;
    
    MasterVolume[] getAllMasterVolume(Connection conn)throws SQLException;
    
    MasterVolume getMasterVolumeByName(String masterVolumeName, Connection conn)throws SQLException;
    
    void createRincianAkun(Dipa dipa, Rincian rincian, RincianDipa rincianDipa, Connection conn)throws SQLException;
    
    
    public DetailRincian createDetailRincian(DetailRincian detail, Connection conn)throws SQLException;
    
    public void updateDetailRincian(long oldDetailRincianId, DetailRincian detail, Connection conn)throws SQLException;
    
    public void deleteDetailRincian(long detailRincianId, Connection conn)throws SQLException;
    
    public DetailRincian getDetailRincian(long detailRincianId, Connection conn)throws SQLException;
    
    public DetailRincian getDetailRincianByRincianAndDetail(Rincian rincian, String detailRincian, Connection conn)throws SQLException;
    
    public DetailRincian[] getAllDetailRincian(Connection conn)throws SQLException;
    
    public DetailRincian[] getAllDetaiRincianByRincian(Rincian rincian, Connection conn)throws SQLException;
        
    public DetailRincianStruktur[] getDetailRincianStruktur(long rincianId, Connection conn)throws SQLException;
    
    public Markmap[] getAllMarkmap(Connection conn)throws SQLException;
    
    public Markmap getMarkmapByCode(String kode, Connection conn)throws SQLException;
    
    public void createDetailRincianStruktur(long parent, long sub, Connection conn) throws SQLException;
    
    public void migrasidatarkakl(Connection conn, Connection conn2)throws SQLException;
    
    public boolean cekrincian(SubKomponen subKomponen, String koderekening, Connection conn) throws SQLException;
    
    public boolean cekDetail(Rincian rincian, String detail, Connection conn)throws SQLException;
    
    /**
     * 
     * Departemen, Unit & Satker
     * 
     */   
    
    public Satker createSatker(Satker satker, Connection conn)throws SQLException;
    
    public void updateSatker(long oldSatkerId, Satker satker, Connection conn)throws SQLException;
    
    public void deleteSatker(long satkerId, Connection conn)throws SQLException;
    
    public Satker getSatker(long satkerId, Connection conn)throws SQLException;
    
    public Satker[] getAllSatker(Connection conn)throws SQLException;
    
    /**
     * 
     * BENDAHARA, SPP
     * @param spp
     * @param conn
     * @return
     * @throws SQLException 
     * 
     */
    
    public Bendahara createBendahara(Bendahara bendahara, Connection conn)throws SQLException;
    
    public void updateBendahara(long oldbendaharaid, Bendahara bendahara, Connection conn)throws SQLException;
    
    public void deleteBendahara(long bendaharaid, Connection conn)throws SQLException;
    
    public Bendahara getBendahara(long bendaharaId, Connection conn)throws SQLException;
    
    public Bendahara[] getAllBendahara(Connection conn)throws SQLException;
    
    public Bendahara[] getAllBendaharaBySatker(long satkerid, Connection conn)throws SQLException;
    
    public SPP createSPP(SPP spp, Connection conn)throws SQLException;
    
    public void updateSPP(long oldSPPId, SPP spp, Connection conn)throws SQLException;
    
    public void deleteSPP(long sppid, Connection conn)throws SQLException;
    
    public SPP getSPP(long sppid, Connection conn)throws SQLException;
    
    public SPP[] getAllSPP(Connection conn)throws SQLException;
    
    public SPP[] getAllSPPByUnitOrganisasi(long unitorganisasiid, Connection conn)throws SQLException;
    
    /**
     * 
     *  BankRef
     * 
     */
    
    public BankRef createBankRef(BankRef bank, Connection conn)throws SQLException;
    
    public void updateBankRef(long oldbankrefid, BankRef bank, Connection conn)throws SQLException;
    
    public void deleteBankRef(long bankrefid, Connection conn)throws SQLException;
    
    public BankRef getBankRef(long bankrefid, Connection conn)throws SQLException;
    
    public BankRef[] getAllBankRefByWilayah(String wilayah, Connection conn)throws SQLException;
    
    public BankRef[] getAllBankRef(Connection conn)throws SQLException;
    
    public BankRef[] getAllBankRefSortByWilayah(Connection conn)throws SQLException;
    
}
