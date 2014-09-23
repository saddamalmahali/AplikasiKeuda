package com.keuda.services;

/**
 *
 * @author user
 */
public interface IDBCConstant {

    public static final String TABLE_AKUN = "akun";
    public static final String TABLE_AKUN_STRUKTUR = "akunstruktur";
    public static final String ATT_AKUN_INDEX = "akunindex";
    public static final String ATT_KODE_AKUN = "kodeakun";
    public static final String ATT_NAMA_AKUN = "namaakun";
    public static final String ATT_TIPE_AKUN = "tipeakun";
    public static final String ATT_LEVEL_AKUN = "levelakun";
    public static final String ATT_AKUN_GRUP = "akungrup";
    public static final String ATT_PARENT_AKUN = "parentakun";
    public static final String ATT_SUB_AKUN = "subakun";
    public static final String ATT_AUTO_INDEX = "autoindex";
    public static final String TABLE_ITEM_MASTER = "itemmaster";
    public static final String MODUL_USERGROUP = "Grup Pengguna";
    public static final String MODUL_MODUL = "modul";
    public static final String MODUL_APPLICATION = "aplikasi";
    public static final String MODUL_USER = "pengguna";
    public static final String MODUL_ORGANISASI = "Organisasi";
    public static final String MODUL_CONFIGURATION = "Konfigurasi";
    public static final String MODUL_PERENCANAAN = "Perencanaan";
    public static final String MODUL_PENGADAAN = "Pengadaan";
    public static final String MODUL_PEMELIHARAAN = "Pemeliharaan";
    public static final String MODUL_PENERIMAAN = "Penerimaan";
    public static final String MODUL_PENYALURAN = "Penyaluran";
    public static final String MODUL_PENGHAPUSAN = "Penghapusan";
    public static final String MODUL_PEMINDAHTANGANAN = "Pemindahtanganan";
    public static final String MODUL_PENATAUSAHAAN = "Penatauasahaan";
    public static final String MODUL_LAPORAN = "Laporan";
    public static final String MODUL_BOM = "BOM";
    public static final String ATTR_SUB_INDEX = "subakun";
    public static final String ATTR_SUPER_INDEX = "parentakun";
    //Program & Kegiatan 
    public static final String TABLE_KEGIATAN = "kegiatan";
    public static final String TABLE_PROGRAM = "program";
    public static final String ATT_PROGRAM_INDEX = "programindex";
    public static final String ATT_PROGRAM_CODE = "programcode";
    public static final String ATT_PROGRAM_NAME = "programname";
    public static final String ATT_KEGIATAN_INDEX = "kegiatanindex";
    public static final String ATT_KEGIATAN_PROGRAM_INDEX = "programindex";
    public static final String ATT_KEGIATAN_CODE = "kegiatancode";
    public static final String ATT_KEGIATAN_NAME = "kegiatanname";
    //Program DIPA, Outcome Program & Iku Program
    public static final String TABLE_PROGRAM_DIPA = "programdipa";
    public static final String TABLE_OUTCOME_PROGRAM = "outcomeprogram";
    public static final String TABLE_IKU_PROGRAM = "ikuprogram";
    public static final String ATT_PROGRAMDIPA_DIPA_INDEX = "dipaindex";
    public static final String ATT_PROGRAMDIPA_TAHUN_ANGGARAN = "thnanggaran";
    public static final String ATT_PROGRAMDIPA_PROGRAM_DIPA = "program";
    public static final String ATT_OUTCOME_PROGRAM_DIPA = "programdipa";
    public static final String ATT_OUTCOME_NOMOR_URUT = "nroutcome";
    public static final String ATT_OUTCOME_NAME = "outcome";
    public static final String ATT_IKUPROGRAM_PROGRAM_DIPA = "programdipa";
    public static final String ATT_IKUPROGRAM_NOMOR_URUT = "nriku";
    public static final String ATT_IKUPROGRAM_NAME = "iku";

    //Kegiatan Dipa, Indikator Kegiatan & Output Kegiatan
    public static final String TABLE_KEGIATAN_DIPA = "kegiatandipa";
    public static final String TABLE_INDIKATOR_KEGIATAN = "indikatorkegiatan";
    public static final String TABLE_OUTPUT_KEGIATAN = "outputkegiatan";

    public static final String ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX = "kegiatandipaindex";
    public static final String ATT_KEGIATANDIPA_DIPAINDEX = "dipaindex";
    public static final String ATT_KEGIATANDIPA_KEGIATAN = "kegiatan";
    public static final String ATT_INDIKATORKEGIATAN_KEGIATANDIPA = "kegiatandipa";
    public static final String ATT_INDIKATORKEGIATAN_NOMOR_URUT = "nrindikator";
    public static final String ATT_INDIKATORKEGIATAN_INDIKATOR = "indikator";
    public static final String ATT_OUTPUTKEGIATAN_KEGIATANDIPA = "kegiatandipa";
    public static final String ATT_OUTPUTKEIGATAN_NOMOR_URUT = "nroutput";
    public static final String ATT_OUTPUTKEGIATAN_OUTPUT = "output";
    public static final String ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX = "outputkegiatanindex";

    public static final String TABLE_USERACCOUNT = "uaccount";
    public static final String TABLE_USERGROUP = "usrgroup";
    public static final String TABLE_USERTYPE = "usertype";
    public static final String TABLE_MODUL = "modul";
    public static final String TABLE_MODUL_RIGHT = "modulright";
    public static final String TABLE_APPLICATION = "application";
    public static final String TABLE_APPLICATION_RIGHT = "appright";
    public static final String ATT_CREATE = "create";
    public static final String ATT_UPDATE = "update";
    public static final String TABLE_USERID = "userid";

    public static final String ATT_USERNAME = "username";
    public static final String ATT_PASSWD = "passwd";
    public static final String ATT_SESSIONID = "sessionid";
    public static final String ATT_UGROUP = "ugroup";
    public static final String ATT_MODUL = "modul";
    public static final String ATT_WRITE = "writes";
    public static final String ATT_READ = "reads";
    public static final String ATT_APP = "app";
    public static final String ATT_NOTE = "note";
    public static final String ATT_NAME = "name";
    public static final String UGROUP_ADMINISTRATORS = "administrator";

    /**
     * DIPA, RINCIAN & RINCIANDIPA
     */
    public static final String TABLE_DIPA = "dipa";
    public static final String ATT_DIPA_DIPAINDEX = "dipaindex";
    public static final String ATT_DIPA_THNANGGARAN = "thnanggaran";
    public static final String ATT_DIPA_NOMORDIPA = "nomordipa";

    public static final String TABLE_RINCIAN = "rincian";
    public static final String ATT_RINCIAN_RNCIANINDEX = "rincianindex";
    public static final String ATT_RINCIAN_DIPAINDEX = "dipaindex";
    public static final String ATT_RINCIAN_KEGIATAN = "kegiatan";
    public static final String ATT_RINCIAN_SUBKOMPONENINDEX = "subkomponen";
    public static final String ATT_RINCIAN_KODEREKENING = "koderekening";
    public static final String ATT_RINCIAN_NAMAREKENING = "namarekening";

    public static final String TABLE_DETAILRINCIAN = "detailrincian";
    public static final String ATT_DETAILRINCIAN_DETAILRINCIANINDEX = "detailrincianindex";
    public static final String ATT_DETAILRINCIAN_RINCIAN = "rincianindex";
    public static final String ATT_DETAILRINCIAN_DETAILRINCIAN = "detailrincian";
    public static final String ATT_DETAILRINCIAN_LOKASI = "lokasi";
    public static final String ATT_DETAILRINCIAN_KPPN = "kppn";
    public static final String ATT_DETAILRINCIAN_CARAPENARIKAN = "carapenarikan";
    public static final String ATT_DETAILRINCIAN_JUMLAHDANA = "jumlahdana";
    public static final String ATT_DETAILRINCIAN_VOLUME = "volume";
    public static final String ATT_DETAILRINCIAN_JENISVOLUME = "jenisvolume";

    public static final String TABLE_DETAILRINCIANSTRUKTUR = "detailrincianstruktur";
    public static final String ATT_DETAILRINCIANSTRUKTUR_PARENTRINCIAN = "parentrincian";
    public static final String ATT_DETAILRINCIANSTRUKTUR_SUBRINCIAN = "subrincian";

    public static final String TABLE_SUBDETAILRINCIAN = "subdetailrincian";
    public static final String ATT_SUBDETAILRINCIAN_SUBDETAILRINCIANINDEX = "";
    public static final String ATT_SUBDETAILRINCIAN_SUBDETAILRINCIAN = "";
    public static final String ATT_SUBDETAILRINCIAN_LOKASI = "";
    public static final String ATT_SUBDETAILRINCIAN_KPPN = "";
    public static final String ATT_SUBDETAILRINCIAN_CARAPENARIKAN = "";
    public static final String ATT_SUBDETAILRINCIAN_JUMLAHDANA = "jumlahdana";

    public static final String TABLE_RINCIANDIPA = "rinciandipa";
    public static final String ATT_RINCIANDIPA_RINCIANINDEX = "rincianindex";
    public static final String ATT_RINCIANDIPA_KODEREKENING = "koderekening";
    public static final String ATT_RINCIANDIPA_NAMAREKENING = "namarekening";
    public static final String ATT_RINCIANDIPA_LOKASI = "lokasi";
    public static final String ATT_RINCIANDIPA_KPPN = "kppn";
    public static final String ATT_RINCIANDIPA_CARAPENARIKAN = "carapenarikan";
    public static final String ATT_RINCIANDIPA_JUMLAHDANA = "jumlahdana";

    /**
     * Komponen, sub komponen
     */
    public static final String TABLE_KOMPONEN = "komponen";
    public static final String TABLE_SUBKOMPONEN = "subkomponen";
    public static final String ATT_KOMPONEN_KOMPONENINDEX = "komponenindex";
    public static final String ATT_KOMPONEN_OUTPUTKEGIATANINDEX = "outputkegiatanindex";
    public static final String ATT_KOMPONEN_KOMPONENKODE = "komponenkode";
    public static final String ATT_KOMPONEN_KOMPONENNAME = "komponenname";
    public static final String ATT_SUBKOMPONEN_SUBKOMPONENINDEX = "subkomponenindex";
    public static final String ATT_SUBKOMPONEN_KOMPONEN = "komponenindex";
    public static final String ATT_SUBKOMPONEN_SUBKOMPONENKODE = "subkomponenkode";
    public static final String ATT_SUBKOMPONEN_SUBKOMPONENNAME = "subkomponenname";

    public static final String TABLE_RINCIAN_KODREK = "rinciankodrek";
    public static final String ATT_RINCIANKODREK_RINCIANKODREKINDEX = "rinciankodrekindex";
    public static final String ATT_RINCIANKODREK_RINCIANINDEX = "rincianindex";
    public static final String ATT_RINCIANKODREK_KODEREKENING = "koderekening";
    public static final String ATT_RINCIANKODREK_NAMAREKENING = "namarekening";
    public static final String ATT_RINCIANKODREK_LOKASI = "lokasi";
    public static final String ATT_RINCIANKODREK_KPPN = "kppn";
    public static final String ATT_RINCIANKODREK_CARAPENARIKAN = "carapenarikan";
    public static final String ATT_RINCIANKODREK_JUMLAHDANA = "jumlahdana";

    /**
     * GrupItem, grupitem, kodrekrincian, grupkodrekrincian
     */
    public static final String TABLE_GRUPITEM = "grupitem";
    public static final String ATT_GRUPITEM_GRUPINDEX = "grupindex";
    public static final String ATT_GRUPITEM_GRUPNAME = "grupname";
    public static final String TABLE_KODREKRINCIAN = "kodrekrincian";
    public static final String ATT_KODREKRINCIAN_RINCIANINDEX = "rincianindex";
    public static final String ATT_KODREKRINCIAN_KODREK = "kodrek";
    public static final String ATT_KODREKRINCIAN_RINCIANITEM = "rincianitem";
    public static final String ATT_KODREKRINCIAN_NILAIITEM = "nilaiitem";
    public static final String TABLE_GRUPKODREKRINCIAN = "grupkodrekrincian";
    public static final String ATT_GRUPKODREKRINCIAN_GRUPINDEX = "grupindex";
    public static final String ATT_GRUPKODREKRINCIAN_RINCIANINDEX = "rincianindex";
    public static final String TABLE_KODREKOUTPUT = "kodrekoutput";
    public static final String ATT_KODREKOUTPUT_RINCIANKODREK = "rinciankodrek";
    public static final String ATT_KODREKOUTPUT_OUTPUT = "output";
    public static final String TABLE_KODREKKOMPONEN = "kodrekkomponen";
    public static final String ATT_KODREKKOMPONEN_RINCIANKODREK = "rinciandipakodrek";
    public static final String ATT_KODREKKOMPONEN_KOMPONEN = "komponen";
    public static final String TABLE_KODREKSUBKOMPONEN = "kodreksubkomponen";
    public static final String ATT_KODREKSUBKOMPONEN_RINCIANKODREK = "rinciankodrek";
    public static final String ATT_KODREKSUBKOMPONEN_SUBKOMPONEN = "subkomponen";

    public static final String TABLE_MASTERVOLUME = "mastervolume";
    public static final String ATT_MASTERVOLUME_MASTERVOLUMEID = "mastervolumeid";
    public static final String ATT_MASTERVOLUME_KODEVOLUME = "kodevolume";
    public static final String ATT_MASTERVOLUME_NAMAJENIS = "namajenis";
    public static final String ATT_MASTERVOLUME_DESCRIPTION = "description";

    public static final String TABLE_MARKMAP = "markmap";
    public static final String ATT_MARKMAP_NO = "no";
    public static final String ATT_MARKMAP_KDREK = "kdperk6";
    public static final String ATT_MARKMAP_NMREK = "nmperk6";

    /**
     * Departemen, Unitdepartemen & Satker
     */
    
    public static final String TABLE_SATKER = "satker";
    
    public static final String ATT_SATKER_SATKERID = "satkerid";
    public static final String ATT_SATKER_KODEDEPARTEMEN = "kodedepartemen";
    public static final String ATT_SATKER_NAMADEPARTEMEN = "namadepartemen";
    public static final String ATT_SATKER_KODEUNIT = "kodeunit";
    public static final String ATT_SATKER_NAMAUNIT = "namaunit";
    public static final String ATT_SATKER_KODESATKER = "kodesatker";
    public static final String ATT_SATKER_NAMASATKER = "namasatker";
    
    /**
     * Bendahara, SPP
     */
    
    public static final String TABLE_BENDAHARA = "bendaharapengeluaran";
    public static final String ATT_BENDAHARA_BENDAHARAINDEX = "bendaharaindex";
    public static final String ATT_BENDAHARA_SATKER = "satker";
    public static final String ATT_BENDAHARA_JENISBENDAHARA = "jenisbendahara";
    public static final String ATT_BENDAHARA_NIP = "nip";
    public static final String ATT_BENDAHARA_ALAMAT = "alamat";
    public static final String ATT_BENDAHARA_EMAIL = "email";
    public static final String ATT_BENDAHARA_KODEBANK ="kodebank";
    public static final String ATT_BENDAHARA_REKENING = "rekening";
    public static final String ATT_BENDAHARA_SALDO = "saldo";
    public static final String ATT_BENDAHARA_NPWP = "npwp";
    public static final String ATT_BENDAHARA_TANGGAL = "tanggalstop";
    
    public static final String TABLE_SPP = "spp";
    public static final String ATT_SPP_SPPINDEX = "sppindex";
    public static final String ATT_SPP_OUTPUTKEGIATAN = "outputkegiatan";
    public static final String ATT_SPP_SATKERID = "satkerid";
    public static final String ATT_SPP_BENDAHARAID = "bendaharaid";
    public static final String ATT_SPP_LOKASI = "lokasi";
    public static final String ATT_SPP_KEWENANGAN = "kewenangan";
    public static final String ATT_SPP_JENISBELANJA = "jenisbelanja";
    public static final String ATT_SPP_ATASNAMA = "atasnama";
    public static final String ATT_SPP_ALAMAT = "alamat";
    public static final String ATT_SPP_KODEREKENING = "koderekening";
    public static final String ATT_SPP_NOSPK = "nospk";
    public static final String ATT_SPP_JUMLAHSPK = "jumlahspk";
    public static final String ATT_SPP_KETERANGAN = "keterangan";
    public static final String ATT_SPP_JUMLAH = "jumlah";

}
