
package com.keuda.impl.service;

import com.keuda.model.*;
import com.keuda.services.IDBCConstant;
import com.keuda.services.IKeudaSQL;
import com.keuda.view.AkunStructureForTree;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author user
 */
public class KeudaSQL implements IKeudaSQL {

    private int index;

    @Override
    public long getMaxIndex(String fieldname, String tableName, Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT MAX(" + fieldname + ") as maxindex from " + tableName);
            rs.next();
            return rs.getLong("maxindex");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public Akun createAkun(Akun akun, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        Statement stm = null;

        try {
            pstat = conn.prepareStatement("INSERT INTO "
                    + IDBCConstant.TABLE_AKUN + " ("
                    + IDBCConstant.ATT_KODE_AKUN + ","
                    + IDBCConstant.ATT_NAMA_AKUN + ", "
                    + IDBCConstant.ATT_TIPE_AKUN + ", "
                    + IDBCConstant.ATT_LEVEL_AKUN + ", "
                    + IDBCConstant.ATT_AKUN_GRUP + ")"
                    + " values (?,?,?,?,?)");

            pstat.setString(1, akun.getKodeAkun());
            pstat.setString(2, akun.getNamaAkun());
            pstat.setShort(3, akun.getTipeakun());
            pstat.setShort(4, akun.getLevelakun());
            pstat.setBoolean(5, akun.isAkungrup());

            pstat.execute();

            long index = getMaxIndex(IDBCConstant.ATT_AKUN_INDEX ,IDBCConstant.TABLE_AKUN, conn);
            akun.setAkunIndex(index);

            if (akun.getParent() != null) {
                stm = conn.createStatement();
                stm.executeUpdate("INSERT INTO " + IDBCConstant.TABLE_AKUN_STRUKTUR + " values("
                        + akun.getParent().getAkunIndex() + "," + index + ")");
            }

            return akun;
        } catch (Exception e) {
            conn.rollback();
            throw new SQLException("SQLSAP : Gagal Menyimpan Akun \n" + e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateAkun(long oldAkunIndex, Akun akun, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        Statement stmt = null;

        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_AKUN + " set "
                    + IDBCConstant.ATT_KODE_AKUN + "=?,"
                    + IDBCConstant.ATT_NAMA_AKUN + "=?,"
                    + IDBCConstant.ATT_TIPE_AKUN + "=?,"
                    + IDBCConstant.ATT_LEVEL_AKUN + "=?,"
                    + IDBCConstant.ATT_AKUN_GRUP + "=?"
                    + "where "
                    + IDBCConstant.ATT_AKUN_INDEX + "=" + oldAkunIndex);
            pstat.setString(1, akun.getKodeAkun());
            pstat.setString(2, akun.getNamaAkun());
            pstat.setShort(3, akun.getTipeakun());
            pstat.setShort(4, akun.getLevelakun());
            pstat.setBoolean(5, akun.isAkungrup());

            pstat.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Data Account\n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }

    }

    @Override
    public void deleteAkun(long akunIndex, Connection conn) throws SQLException {
        Statement stm = null;

        try {
            deleteAkunStrukturBySubAkun(akunIndex, conn);
            stm = conn.createStatement();
            stm.executeUpdate("DELETE from " + IDBCConstant.TABLE_AKUN
                    + " WHERE " + IDBCConstant.ATT_AKUN_INDEX + "=" + akunIndex);
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Account \n"
                    + e.getMessage());
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Akun getAkun(long akunIndex, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Akun akun = null;

        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM " + IDBCConstant.TABLE_AKUN + " WHERE " + IDBCConstant.ATT_AKUN_INDEX + "=" + akunIndex);
            while (rs.next()) {
                akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));
                return akun;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Account \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

    }

//    @Override
//    public ItemMasterStructureForTree getItemMasterAndStructure(Connection conn) throws SQLException {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    
    @Override
    public Akun getAkunSimple(long akunIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Akun akun = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM " + IDBCConstant.TABLE_AKUN + " WHERE "
                    + IDBCConstant.ATT_AKUN_INDEX + "=" + akunIndex);
            while (rs.next()) {
                akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN));
                return akun;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Akun \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }

    }

    @Override
    public Akun[] getAllAkun(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Akun akun = null;

        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM " + IDBCConstant.TABLE_AKUN
                    + " ORDER BY " + IDBCConstant.ATT_KODE_AKUN);
            Vector<Akun> vector = new Vector<>();

            while (rs.next()) {
                akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));
                vector.add(akun);
            }

            Akun[] result = new Akun[vector.size()];

            vector.copyInto(result);

            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Semua Akun \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public Akun[] getSuperAkun(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;

        Vector<Akun> vResult = new Vector<>();

        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM " + IDBCConstant.TABLE_AKUN
                    + " WHERE " + IDBCConstant.ATT_AKUN_INDEX
                    + " NOT IN (SELECT " + IDBCConstant.ATTR_SUB_INDEX
                    + " FROM " + IDBCConstant.TABLE_AKUN_STRUKTUR
                    + ") ORDER BY " + IDBCConstant.ATT_KODE_AKUN);

            while (rs.next()) {
                vResult.addElement(new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP)));
            }

            Akun[] result = new Akun[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Super Akun \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public Akun[] getSubAkun(long superIndex, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Akun> vresult = new Vector<Akun>();

        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT " + IDBCConstant.TABLE_AKUN
                    + ".* FROM " + IDBCConstant.TABLE_AKUN + ", " + IDBCConstant.TABLE_AKUN_STRUKTUR + " where "
                    + IDBCConstant.ATTR_SUPER_INDEX + " = " + superIndex
                    + " AND " + IDBCConstant.ATTR_SUB_INDEX + "="
                    + IDBCConstant.ATT_AKUN_INDEX + " order by "
                    + IDBCConstant.TABLE_AKUN + "." + IDBCConstant.ATT_KODE_AKUN);

            while (rs.next()) {
                vresult.addElement(new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX), rs.getString(IDBCConstant.ATT_KODE_AKUN), rs.getString(IDBCConstant.ATT_NAMA_AKUN), rs.getShort(IDBCConstant.ATT_TIPE_AKUN), rs.getShort(IDBCConstant.ATT_LEVEL_AKUN), rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP)));
            }
            Akun[] result = new Akun[vresult.size()];
            vresult.copyInto(result);

            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Sub Account \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public void createAkunStruktur(long superAkun, long subAkun, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("INSERT INTO "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + " values (?,?)");

            int col = 1;
            pstat.setLong(col++, superAkun);
            pstat.setLong(col++, subAkun);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP: Gagal Dalam Menyimpan Struktur Akun \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public Akun[] getAkunRoot(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = conn.createStatement();
            String sql = "select * from akun where tipeakun=1";

            rs = stm.executeQuery(sql);
            Vector<Akun> vresult = new Vector<Akun>();
            while (rs.next()) {
                vresult.addElement(new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX), rs.getString(IDBCConstant.ATT_KODE_AKUN), rs.getString(IDBCConstant.ATT_NAMA_AKUN), rs.getShort(IDBCConstant.ATT_TIPE_AKUN), rs.getShort(IDBCConstant.ATT_LEVEL_AKUN), rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP)));
            }

            Akun[] result = new Akun[vresult.size()];
            vresult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Root Akun \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Akun[] getAkunByParent(Akun parent, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Akun> vresult = new Vector<Akun>();
        try {
            stm = conn.createStatement();
            String sql = "select acc.* from " + IDBCConstant.TABLE_AKUN + " acc inner join "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + " acs on acc." + IDBCConstant.ATT_AKUN_INDEX
                    + "=acs." + IDBCConstant.ATTR_SUB_INDEX + " and acs." + IDBCConstant.ATTR_SUPER_INDEX + "=" + parent.getAkunIndex();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                vresult.addElement(new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX), rs.getString(IDBCConstant.ATT_KODE_AKUN), rs.getString(IDBCConstant.ATT_NAMA_AKUN), rs.getShort(IDBCConstant.ATT_TIPE_AKUN), rs.getShort(IDBCConstant.ATT_LEVEL_AKUN), rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP)));

            }
            Akun[] result = new Akun[vresult.size()];
            vresult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Akun Berdasarkan Parent \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();

            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Akun[] getAkunPrinting(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = conn.createStatement();
            String sql = "select * from akun where tipeakun=1";
            rs = stm.executeQuery(sql);
            Vector<Akun> vresult = new Vector<>();
            while (rs.next()) {
                Akun akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));
                akun.setSub(getAkunPrintingByParent(akun, conn));
                vresult.addElement(akun);
            }

            Akun[] result = new Akun[vresult.size()];
            vresult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Root Akun \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Akun[] getAkunPrintingByParent(Akun parent, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Statement stmAkn = null;
        ResultSet rsAkn = null;

        try {
            stm = conn.createStatement();
            String sql = "select acc.* from " + IDBCConstant.TABLE_AKUN + " acc inner join "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + " acs on acc." + IDBCConstant.ATT_AKUN_INDEX
                    + "=acs." + IDBCConstant.ATTR_SUB_INDEX + " and acs." + IDBCConstant.ATTR_SUPER_INDEX + "=" + parent.getAkunIndex();
            rs = stm.executeQuery(sql);
            Vector<Akun> vresult = new Vector<>();
            while (rs.next()) {
                Akun akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));
                if (akun.isAkungrup()) {
                    akun.setSub(getAkunPrintingByParent(akun, conn));
                }
                vresult.addElement(akun);
            }
            Akun[] result = new Akun[vresult.size()];
            vresult.copyInto(result);
            parent.setSub(result);
            return result;
        } catch (SQLException e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Akun Berdasarkan Parent \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public AkunStructureForTree getAkunStruktures(Connection conn) throws SQLException {
        Statement stm = null;
        String acclegal = "";

        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from "
                    + IDBCConstant.TABLE_AKUN + " inner join "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + " ON("
                    + IDBCConstant.TABLE_AKUN + "."
                    + IDBCConstant.ATT_AKUN_INDEX + " = "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + "."
                    + IDBCConstant.ATTR_SUB_INDEX + ") ORDER BY "
                    + IDBCConstant.TABLE_AKUN + "."
                    + IDBCConstant.ATT_KODE_AKUN);

            AkunStruktur[] as;
            AkunStructureForTree result;

            Vector v = new Vector();
            while (rs.next()) {
                Akun akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));

                v.addElement(new AkunStruktur(akun, rs.getLong(IDBCConstant.ATTR_SUPER_INDEX),
                        rs.getLong(IDBCConstant.ATTR_SUB_INDEX)));
            }

            as = new AkunStruktur[v.size()];

            rs = stm.executeQuery("select * from " + IDBCConstant.TABLE_AKUN);
            Vector vv = new Vector();
            while (rs.next()) {
                vv.addElement(new Long(rs.getLong(IDBCConstant.ATT_AKUN_INDEX)));
            }

            v.copyInto(as);

            result = new AkunStructureForTree(as, vv);
            rs = stm.executeQuery("select * from "
                    + IDBCConstant.TABLE_AKUN + " where "
                    + IDBCConstant.ATT_AKUN_INDEX + " not in ( select "
                    + IDBCConstant.ATTR_SUB_INDEX + " from "
                    + IDBCConstant.TABLE_AKUN_STRUKTUR + ")");

            Vector vvv = new Vector();
            while (rs.next()) {
                Akun akun = new Akun(rs.getLong(IDBCConstant.ATT_AKUN_INDEX),
                        rs.getString(IDBCConstant.ATT_KODE_AKUN),
                        rs.getString(IDBCConstant.ATT_NAMA_AKUN),
                        rs.getShort(IDBCConstant.ATT_TIPE_AKUN),
                        rs.getShort(IDBCConstant.ATT_LEVEL_AKUN),
                        rs.getBoolean(IDBCConstant.ATT_AKUN_GRUP));

                vvv.addElement(akun);
            }

            Akun[] supers = new Akun[vvv.size()];
            vvv.copyInto(supers);
            result.setSuper(supers);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP: Gagal dalam mengambil account \n"
                    + e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public void deleteAkunStrukturBySubAkun(long subAkun, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_AKUN_STRUKTUR + " where subakun=" + subAkun);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public Program createProgram(Program program, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_PROGRAM + " ("
                    + IDBCConstant.ATT_PROGRAM_CODE + ", "
                    + IDBCConstant.ATT_PROGRAM_NAME + ") values(?,?)");

            pstat.setString(1, program.getProgramCode());
            pstat.setString(2, program.getProgramName());

            pstat.execute();

            return program;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan program \n"
                    + e.getMessage());
        } finally {
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (Exception e) {
                }
            }
        }

    }

    @Override
    public void updateProgram(long oldProgramIndex, Program program, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_PROGRAM
                    + " set " + IDBCConstant.ATT_PROGRAM_CODE + "=?, "
                    + IDBCConstant.ATT_PROGRAM_NAME + "=? where "
                    + IDBCConstant.ATT_PROGRAM_INDEX + "=" + oldProgramIndex);
            pstat.setString(1, program.getProgramCode());
            pstat.setString(2, program.getProgramName());

            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memuktahirkan Program\n"
                    + e.getMessage());
        } finally {
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (Exception e) {
                }
            }
        }

    }

    @Override
    public void deleteProgram(long programIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_PROGRAM + " where "
                    + IDBCConstant.ATT_PROGRAM_INDEX + " = ?");
            pstat.setLong(1, programIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Program \n"
                    + e.getMessage());
        } finally {
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public Program getProgram(long programIndex, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.createStatement();
            String sql = "select * from "
                    + IDBCConstant.TABLE_PROGRAM + " where "
                    + IDBCConstant.ATT_PROGRAM_INDEX + " = " + programIndex;
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Program program = new Program();
                program.setProgramIndex(rs.getLong(IDBCConstant.ATT_PROGRAM_INDEX));
                program.setProgramCode(rs.getString(IDBCConstant.ATT_PROGRAM_CODE));
                program.setProgramName(rs.getString(IDBCConstant.ATT_PROGRAM_NAME));

                return program;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("Gagal Mengambil Program dengan programindex = " + programIndex + " dari database \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Program[] getAllProgram(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Program> vresult = new Vector<>();
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("select * from " + IDBCConstant.TABLE_PROGRAM + " order by " + IDBCConstant.ATT_PROGRAM_CODE);
            while (rs.next()) {
                vresult.addElement(new Program(rs.getLong(IDBCConstant.ATT_PROGRAM_INDEX),
                        rs.getString(IDBCConstant.ATT_PROGRAM_CODE),
                        rs.getString(IDBCConstant.ATT_PROGRAM_NAME)));
            }
            Program[] result = new Program[vresult.size()];
            vresult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Program \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();

            }
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public Program getProgramByCode(String programCode, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = conn.createStatement();
            String query = "select * from "
                    + IDBCConstant.TABLE_PROGRAM + " where "
                    + IDBCConstant.ATT_PROGRAM_CODE + " = \'" + programCode + "\'";

            rs = stm.executeQuery(query);
            while (rs.next()) {
                Program program = new Program(rs.getLong(IDBCConstant.ATT_PROGRAM_INDEX),
                        rs.getString(IDBCConstant.ATT_PROGRAM_CODE),
                        rs.getString(IDBCConstant.ATT_PROGRAM_NAME));
                return program;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Program \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Kegiatan createKegiatan(Kegiatan kegiatan, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_KEGIATAN + " ("
                    + IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX + ", "
                    + IDBCConstant.ATT_KEGIATAN_CODE + ", "
                    + IDBCConstant.ATT_KEGIATAN_NAME + ") values(?,?,?)");
            pstat.setLong(1, kegiatan.getProgramIndex());
            pstat.setString(2, kegiatan.getKegiatanCode());
            pstat.setString(3, kegiatan.getKegiatanName());
            pstat.execute();

            return kegiatan;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void updateKegiatan(long oldKegiatanIndek, Kegiatan kegiatan, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_KEGIATAN + " set "
                    + IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX + "=?, "
                    + IDBCConstant.ATT_KEGIATAN_CODE + "=?, "
                    + IDBCConstant.ATT_KEGIATAN_NAME + "=? where "
                    + IDBCConstant.ATT_KEGIATAN_INDEX + "=" + oldKegiatanIndek);

            pstat.setLong(1, kegiatan.getProgramIndex());
            pstat.setString(2, kegiatan.getKegiatanCode());
            pstat.setString(3, kegiatan.getKegiatanName());
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memuktahirkan Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (pstat != null) {
                try {
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void deleteKegiatan(long kegiatanIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_KEGIATAN + " where "
                    + IDBCConstant.ATT_KEGIATAN_INDEX + " = ?");
            pstat.setLong(1, kegiatanIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Kegiatan \n"
                    + e.getMessage());
        }
    }

    @Override
    public Kegiatan getKegiatan(long kegiatanIndex, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.createStatement();
            String sql = "select * from "
                    + IDBCConstant.TABLE_KEGIATAN + " where "
                    + IDBCConstant.ATT_KEGIATAN_INDEX + " = " + kegiatanIndex;
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Program program = getProgram(rs.getLong(IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX), conn);
                Kegiatan kegiatan = new Kegiatan(rs.getLong(IDBCConstant.ATT_KEGIATAN_INDEX),
                        rs.getLong(IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_CODE),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_NAME));

                return kegiatan;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Kegiatan[] getAllKegiatan(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Kegiatan> vresult = new Vector<>();
        try {
            stm = conn.createStatement();
            String sql = "select * from "
                    + IDBCConstant.TABLE_KEGIATAN + " order by "
                    + IDBCConstant.ATT_KEGIATAN_CODE;
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Program prg = getProgram(rs.getLong(IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX), conn);
                Kegiatan kegiatan = new Kegiatan(rs.getLong(IDBCConstant.ATT_KEGIATAN_INDEX),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_CODE),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_NAME), prg);

                vresult.addElement(kegiatan);
            }
            Kegiatan[] kegiatan = new Kegiatan[vresult.size()];
            vresult.copyInto(kegiatan);
            return kegiatan;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Kegiatan[] getKegiatanSimple(Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Kegiatan> vresult = new Vector<>();
        try {
            stm = conn.createStatement();
            String sql = "select "
                    + IDBCConstant.ATT_KEGIATAN_INDEX + ", "
                    + IDBCConstant.ATT_KEGIATAN_CODE + ","
                    + IDBCConstant.ATT_KEGIATAN_NAME + " from "
                    + IDBCConstant.TABLE_KEGIATAN + " order by "
                    + IDBCConstant.ATT_KEGIATAN_CODE;
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Kegiatan kegiatan = new Kegiatan();
                kegiatan.setKegiatanIndex(rs.getLong(IDBCConstant.ATT_KEGIATAN_INDEX));
                kegiatan.setKegiatanCode(rs.getString(IDBCConstant.ATT_KEGIATAN_CODE));
                kegiatan.setKegiatanName(rs.getString(IDBCConstant.ATT_KEGIATAN_NAME));
                vresult.addElement(kegiatan);
            }
            Kegiatan[] kegiatan = new Kegiatan[vresult.size()];
            vresult.copyInto(kegiatan);
            return kegiatan;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Kegiatan[] getKegiatanByProgram(long programIndex, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Vector<Kegiatan> vresult = new Vector<>();
        try {
            stm = conn.createStatement();
            String sql = "select * from "
                    + IDBCConstant.TABLE_KEGIATAN + " where " + IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX + "="
                    + programIndex + " order by "
                    + IDBCConstant.ATT_KEGIATAN_CODE;
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Program p = getProgram(rs.getLong(IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX), conn);
                Kegiatan kegiatan = new Kegiatan(rs.getLong(IDBCConstant.ATT_KEGIATAN_INDEX),
                        p,
                        rs.getString(IDBCConstant.ATT_KEGIATAN_CODE),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_NAME));

                vresult.addElement(kegiatan);
            }
            Kegiatan[] kegiatan = new Kegiatan[vresult.size()];
            vresult.copyInto(kegiatan);
            return kegiatan;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Kegiatan getKegiatanByCode(String kegiatanCode, Connection conn) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.createStatement();
            String sql = "select * from "
                    + IDBCConstant.TABLE_KEGIATAN + " where "
                    + IDBCConstant.ATT_KEGIATAN_CODE + " = \'" + kegiatanCode + "\'";

            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Kegiatan kegiatan = new Kegiatan(rs.getLong(IDBCConstant.ATT_KEGIATAN_INDEX),
                        rs.getLong(IDBCConstant.ATT_KEGIATAN_PROGRAM_INDEX),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_CODE),
                        rs.getString(IDBCConstant.ATT_KEGIATAN_NAME));

                return kegiatan;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Kegiatan \n"
                    + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ProgramDipa createProgramDipa(ProgramDipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " ("
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN + ","
                    + IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA + ") values(?,?)");
            pstat.setInt(1, dipa.getTahunAnggaran());
            pstat.setLong(2, dipa.getProgramindex());
            pstat.execute();
            ProgramDipa pd = new ProgramDipa(dipa);

            return pd;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Program Dipa\n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateProgramDipa(long oldDipaIndex, ProgramDipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " set "
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN + "=?,"
                    + IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA + "=? where "
                    + IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX + "=" + oldDipaIndex);
            pstat.setInt(1, dipa.getTahunAnggaran());
            pstat.setLong(2, dipa.getProgramindex());
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Program Dipa\n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }

    }

    @Override
    public void deleteProgramDipa(long dipaIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " where "
                    + IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX + " = ?");
            pstat.setLong(1, dipaIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQPSAP : Gagal Menghapus Program Dipa \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }

    }

    @Override
    public ProgramDipa getProgramDipa(long dipaIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Program program = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " where "
                    + IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX + "=" + dipaIndex);

            while (rs.next()) {
                program = getProgram(rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA), conn);
                program.setView(Program.VIEW_NAME);
                ProgramDipa pd = new ProgramDipa(
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX),
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        program);

                return pd;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Program Dipa \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }

        }
    }

    @Override
    public ProgramDipa[] getAllProgramDipa(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<ProgramDipa> vResult = new Vector<ProgramDipa>();
        try {
            
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " order by "
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN);

            while (rs.next()) {
                Program prg = new Program(getProgram(rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA), conn));
                ProgramDipa prodi;
                prodi = new ProgramDipa(
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX),
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        prg.getProgramIndex(), prg);                
                vResult.addElement(prodi);
               
            }

            ProgramDipa[] result = new ProgramDipa[vResult.size()];
            vResult.copyInto(result);

            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Program Dipa \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public ProgramDipa[] getAllProgramDipa2(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<ProgramDipa> vResult = new Vector<ProgramDipa>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " order by "
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN);

            while (rs.next()) {
                Program prg = new Program(getProgram(rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA), conn));
                
                ProgramDipa prodi = new ProgramDipa(
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX),
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        prg.getProgramIndex(), prg);
                System.out.println(prodi.getProgramindex());
                KegiatanDipa[] kedip = getAllKegiatanDipaByProdi2(prodi, conn);
                
                prodi.setKegiatandipa(kedip);
                vResult.addElement(prodi);
               
            }

            ProgramDipa[] result = new ProgramDipa[vResult.size()];
            vResult.copyInto(result);
            
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Program Dipa. \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }
    
    
    @Override
    public ProgramDipa[] getProgramDipaSimple(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<ProgramDipa> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select "
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN + ", "
                    + IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA + " from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " order by "
                    + IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN);
            while (rs.next()) {
                vResult.addElement(new ProgramDipa(
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA)));
            }

            ProgramDipa[] result = new ProgramDipa[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Program Dipa \n"
                    + e.toString());

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public ProgramDipa[] getProgramDipaByProgram(long programIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<ProgramDipa> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_PROGRAM_DIPA + " where "
                    + IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA + "=" + programIndex);

            while (rs.next()) {
                vResult.addElement(new ProgramDipa(
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX),
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_PROGRAM_DIPA)));

            }

            ProgramDipa[] result = new ProgramDipa[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Program Dipa \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public OutcomeProgram createOutcomeProdi(OutcomeProgram op, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_OUTCOME_PROGRAM + " ("
                    + IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA + ","
                    + IDBCConstant.ATT_OUTCOME_NOMOR_URUT + ","
                    + IDBCConstant.ATT_OUTCOME_NAME + ") values (?,?,?)");
            pstat.setLong(1, op.getDipaIndex());;
            pstat.setInt(2, op.getNmrUrut());
            pstat.setString(3, op.getOutcome());
            pstat.execute();

            OutcomeProgram outcome = op;
            return outcome;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Outcome Program Dipa \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateOutcomeProdi(OutcomeProgram outCome, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_OUTCOME_PROGRAM + " set "
                    + IDBCConstant.ATT_OUTCOME_NAME + "=? where "
                    + IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA + "=? and "
                    + IDBCConstant.ATT_OUTCOME_NOMOR_URUT + "=?");
            pstat.setString(1, outCome.getOutcome());
            pstat.setLong(2, outCome.getDipaIndex());
            pstat.setInt(3, outCome.getNmrUrut());

            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Outcome Program Dipa \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteOutcomeProdi(OutcomeProgram prg, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareCall("delete from "
                    + IDBCConstant.TABLE_OUTCOME_PROGRAM + " where "
                    + IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA + "=? and "
                    + IDBCConstant.ATT_OUTCOME_NOMOR_URUT + "=?");
            pstat.setLong(1, prg.getDipaIndex());
            pstat.setInt(2, prg.getNmrUrut());
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("Gagal Menghapus Outcome Program Dipa \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public OutcomeProgram getOutProdi(long programDipa, int nomorUrutOutcome, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_OUTCOME_PROGRAM + " where "
                    + IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA + "=" + programDipa + " and "
                    + IDBCConstant.ATT_OUTCOME_NOMOR_URUT + "=" + nomorUrutOutcome);
            while (rs.next()) {
                OutcomeProgram op = new OutcomeProgram(
                        rs.getLong(IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA),
                        rs.getInt(IDBCConstant.ATT_OUTCOME_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTCOME_NAME));

                return op;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Objek dari Outcome Program \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public OutcomeProgram[] getAllOutcomeProdi(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<OutcomeProgram> vResult = new Vector<>();
        ProgramDipa programDipa = null;

        try {

            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_OUTCOME_PROGRAM + " order by "
                    + IDBCConstant.ATT_OUTCOME_NOMOR_URUT);
            while (rs.next()) {
                programDipa = getProgramDipa(rs.getLong(IDBCConstant.ATT_OUTCOME_PROGRAM_DIPA), conn);
                programDipa.setView(ProgramDipa.VIEW_PROGRAM);
                vResult.addElement(new OutcomeProgram(
                        rs.getInt(IDBCConstant.ATT_OUTCOME_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTCOME_NAME),
                        programDipa));
            }

            OutcomeProgram[] result = new OutcomeProgram[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Outcome Program \n"
                    + e.toString());
        }
    }

    @Override
    public IkuProgram createIkuProgram(IkuProgram iku, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_IKU_PROGRAM + " ("
                    + IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA + ", "
                    + IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT + ", "
                    + IDBCConstant.ATT_IKUPROGRAM_NAME + ") values(?,?,?)");
            pstat.setLong(1, iku.getProgramDipa());
            pstat.setInt(2, iku.getNrIku());
            pstat.setString(3, iku.getIku());
            pstat.execute();

            IkuProgram ip = iku;
            return ip;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan IKU Program \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateIku(IkuProgram iku, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_IKU_PROGRAM + " set "
                    + IDBCConstant.ATT_IKUPROGRAM_NAME + "=? where "
                    + IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA + "=? and "
                    + IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT + "=?");
            pstat.setString(1, iku.getIku());
            pstat.setLong(2, iku.getProgramDipa());
            pstat.setInt(3, iku.getNrIku());
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan IKU Program \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteIku(long programDipa, int nrIku, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_IKU_PROGRAM + " where "
                    + IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA + "=" + programDipa
                    + " and " + IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT + "=" + nrIku);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus IKU Program \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public IkuProgram getIkuProgram(long programDipa, int nrIku, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_IKU_PROGRAM + " while "
                    + IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA + "=" + programDipa
                    + " and " + IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT + "=" + nrIku);

            while (rs.next()) {
                IkuProgram iku = new IkuProgram(
                        rs.getLong(IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA),
                        rs.getInt(IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_IKUPROGRAM_NAME));

                return iku;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Objek dari Iku Program \n"
                    + e.toString());
        }

    }

    @Override
    public IkuProgram[] getAllIkuProgram(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<IkuProgram> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_IKU_PROGRAM + " order by "
                    + IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT);

            while (rs.next()) {
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_IKUPROGRAM_PROGRAM_DIPA), conn);
                vResult.addElement(new IkuProgram(
                        prodi,
                        rs.getInt(IDBCConstant.ATT_IKUPROGRAM_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_IKUPROGRAM_NAME)));
            }

            IkuProgram[] result = new IkuProgram[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Iku Program \n"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public KegiatanDipa createKegiatanDipa(KegiatanDipa kedip, Connection conn) throws SQLException {

        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_KEGIATAN_DIPA
                    + " (" + IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX + ", "
                    + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN + ") values (?,?)");
            pstat.setLong(1, kedip.getDipaindex());
            pstat.setLong(2, kedip.getKegiatan());

            pstat.execute();
            return kedip;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }

    }

    @Override
    public void deleteKegiatanDipa(long kegiatanDipaIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_KEGIATAN_DIPA
                    + " where "
                    + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX + "=?");
            pstat.setLong(1, kegiatanDipaIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Kegiatan DIPA. \n" + e.toString());

        }
    }

    @Override
    public KegiatanDipa getKegiatanDipa(long kegiatanDipaIndex, Connection conn) throws SQLException {

        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KEGIATAN_DIPA
                    + " where " + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX + "=" + kegiatanDipaIndex);

            while (rs.next()) {
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);
//                System.out.println(kgt.getKegiatanName());
                KegiatanDipa kedip = new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt);

                return kedip;
            }

            return null;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil Data Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }

    }
    @Override
    public KegiatanDipa getKegiatanDipaByKegiatanAndTahun(Kegiatan kegiatan, short tahun, Connection conn)throws SQLException{
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KEGIATAN_DIPA
                    + " where " + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN + "=" + kegiatan.getKegiatanIndex()+" and "+IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX+" = 15");

            while (rs.next()) {
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);
//                System.out.println(kgt.getKegiatanName());
                KegiatanDipa kedip = new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt);

                return kedip;
            }

            return null;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil Data Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public KegiatanDipa[] getAllKegiatanDipa(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<KegiatanDipa> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KEGIATAN_DIPA);

            while (rs.next()) {
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);

                vResult.addElement(new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt));

            }

            KegiatanDipa[] results = new KegiatanDipa[vResult.size()];

            vResult.copyInto(results);

            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public IndikatorKegiatan createIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                    + " (" + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + ", "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT + ", "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR + ") values (?,?,?)");
            pstat.setLong(1, inke.getK_kedip().getKegiatandipaindex());
            pstat.setInt(2, inke.getNrindikator());
            pstat.setString(3, inke.getIndikator());

            pstat.execute();
            return inke;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal menambahkan Indikator Kegiatan. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateIndikatorKegiatan(IndikatorKegiatan inke, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                    + " set " + IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR + " = ? where "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + "=? and "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT + "=?");
            pstat.setString(1, inke.getIndikator());
            pstat.setLong(2, inke.getK_kedip().getKegiatandipaindex());
            pstat.setInt(3, inke.getNrindikator());

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Indikator kegiatan DIPA. \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteIndikatorKegiatan(long kegiatanDipa, int nrIndikatorKegiatan, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_INDIKATOR_KEGIATAN + " where "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + "=? and "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT + "=?");
            pstat.setLong(1, kegiatanDipa);
            pstat.setInt(2, nrIndikatorKegiatan);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Indikator Kegiatan DIPA. \n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public IndikatorKegiatan getIndikatorKegiatan(long kegiatanDipa, int nrindikator, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                    + " where " + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + "=" + kegiatanDipa + " and "
                    + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT + "=" + nrindikator);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA), conn);
                IndikatorKegiatan ike = new IndikatorKegiatan(kedip, rs.getInt(IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT), rs.getString(IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR));
                return ike;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil data Indikator Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public IndikatorKegiatan[] getAllIndikatorKegiatan(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<IndikatorKegiatan> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                    + " order by " + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + ", " + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT);

            while (rs.next()) {

                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA), conn);

//                System.out.println(kedip.getK_kegiatan().getKegiatanCode());
                vResult.addElement(new IndikatorKegiatan(kedip,
                        rs.getInt(IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR)));
            }

            IndikatorKegiatan[] ike = new IndikatorKegiatan[vResult.size()];
            vResult.copyInto(ike);

            return ike;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Indikator Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }

    }

    @Override
    public OutputKegiatan createOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " ("
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + ", "
                    + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT + ", "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT + ") values(?,?,?)");
            pstat.setLong(1, ouke.getK_kedip().getKegiatandipaindex());
            pstat.setInt(2, ouke.getNroutput());
            pstat.setString(3, ouke.getOutput());

            pstat.execute();

            return ouke;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Output Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateOutputKegiatan(OutputKegiatan ouke, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_OUTPUT_KEGIATAN
                    + " set "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT + "=? where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + " =? and "
                    + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT + "=?");
            pstat.setString(1, ouke.getOutput());
            pstat.setLong(2, ouke.getK_kedip().getKegiatandipaindex());
            pstat.setInt(3, ouke.getNroutput());

            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Output Kegiatan DIPA. \n" + e.toString());
        }

    }

    @Override
    public void deleteOutputKegiatan(long kegiatanDipa, int nrKegiatan, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN
                    + " where " + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + "=? and "
                    + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT + "=?");
            pstat.setLong(1, kegiatanDipa);
            pstat.setInt(2, nrKegiatan);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Output Kegiatan DIPA. \n" + e.toString());
        }
    }

    @Override
    public OutputKegiatan getOutputKegiatan(long kegiatanDipa, int nroutput, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + "=" + kegiatanDipa
                    + " and " + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT + "=" + nroutput);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                OutputKegiatan ouke = new OutputKegiatan(kedip, rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT), rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT));
                return ouke;
            }
            return null;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data Output Kegiatan DIPA. \n" + e.toString());
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public OutputKegiatan[] getAllOutputKegiatan(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<OutputKegiatan> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN
                    + " order by " + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + ", " + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                vResult.addElement(new OutputKegiatan(kedip,
                        rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT)));
            }
            OutputKegiatan[] result = new OutputKegiatan[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Output Kegiatan DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public void updateKegiatanDipa(long oldKegiatanDipaIndex, KegiatanDipa kedip, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_KEGIATAN_DIPA
                    + " set " + IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX + "=?, "
                    + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN + "=? where "
                    + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX + "=?");
            pstat.setLong(1, kedip.getDipaindex());
            pstat.setLong(2, kedip.getKegiatan());
            pstat.setLong(3, kedip.getKegiatandipaindex());
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Kegiatan Dipa. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public OutputKegiatan[] getAllOutputKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<OutputKegiatan> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + " = " + kegiatanDipa
                    + " order by " + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT);
            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                OutputKegiatan outputkegiatan = new OutputKegiatan(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX), kedip,
                        rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT));
                
                vResult.addElement(outputkegiatan);
            }

            OutputKegiatan[] result = new OutputKegiatan[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Output Kegiatan. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public OutputKegiatan[] getAllOutputKegiatanByKegiatan2(long kegiatanDipa, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<OutputKegiatan> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "
                    + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + " = " + kegiatanDipa
                    + " order by " + IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT);
            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                OutputKegiatan outputkegiatan = new OutputKegiatan(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX), kedip,
                        rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT));
                Komponen[] komponen = getAllKomponenByOutputKegiatan2(outputkegiatan.getOutputkegiatanindex(), conn);
                outputkegiatan.setKomponen(komponen);
                vResult.addElement(outputkegiatan);
            }

            OutputKegiatan[] result = new OutputKegiatan[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Output Kegiatan. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public IndikatorKegiatan[] getAllIndikatorKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<IndikatorKegiatan> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                    + " where " + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA + " = "
                    + kegiatanDipa + " order by " + IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA), conn);
                vResult.addElement(new IndikatorKegiatan(kedip,
                        rs.getInt(IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR)));
            }

            IndikatorKegiatan[] results = new IndikatorKegiatan[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Indikator Kegiatan. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Dipa createDipa(Dipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                    + IDBCConstant.TABLE_DIPA + " ("
                    + IDBCConstant.ATT_DIPA_DIPAINDEX + ", "
                    + IDBCConstant.ATT_DIPA_THNANGGARAN + ", "
                    + IDBCConstant.ATT_DIPA_NOMORDIPA + ") values (?,?,?)");
            pstat.setLong(1, dipa.getDipaindex());
            pstat.setInt(2, dipa.getThnanggaran());
            pstat.setString(3, dipa.getNomordipa());

            pstat.execute();

            return dipa;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Dipa.\n " + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateDipa(long oldDipaIndex, Dipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "
                    + IDBCConstant.TABLE_DIPA + " set "
                    + IDBCConstant.ATT_DIPA_THNANGGARAN + " = ?,"
                    + IDBCConstant.ATT_DIPA_NOMORDIPA + "=? where "
                    + IDBCConstant.ATT_DIPA_DIPAINDEX + "= ?");
            pstat.setInt(1, dipa.getThnanggaran());
            pstat.setString(2, dipa.getNomordipa());
            pstat.setLong(3, oldDipaIndex);

            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal memutakhirkan DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteDipa(long dipaIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                    + IDBCConstant.TABLE_DIPA + " where "
                    + IDBCConstant.ATT_DIPA_DIPAINDEX + "=?");
            pstat.setLong(1, dipaIndex);

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public Dipa getDipa(long dipaindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Dipa dipa = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_DIPA
                    + " where " + IDBCConstant.ATT_DIPA_DIPAINDEX + "=" + dipaindex);

            while (rs.next()) {
                dipa = new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX),
                        rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN),
                        rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA));

            }

            return dipa;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data Dipa. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Dipa[] getAllDipa(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Dipa> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_DIPA
                    + " order by " + IDBCConstant.ATT_DIPA_THNANGGARAN);
            if (rs != null) {
                while (rs.next()) {
                    vResult.addElement(new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX),
                            rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN),
                            rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA)));
                }

                Dipa[] results = new Dipa[vResult.size()];
                vResult.copyInto(results);
                return results;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Dipa. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Dipa getAllDipaByTahunAnggaran(int tahunAnggaran, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Dipa> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_DIPA + " where "
                    + IDBCConstant.ATT_DIPA_THNANGGARAN + "=" + tahunAnggaran);

            if (rs != null) {
                while (rs.next()) {
                    return new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX),
                            rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN),
                            rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA));
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List DIPA BY Tahun Anggaran. \n"
                    + e.toString());
        }
    }

    @Override
    public Rincian createRincian(Rincian rincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_RINCIAN + " ("
                    + IDBCConstant.ATT_RINCIAN_DIPAINDEX + ", "
                    + IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX+"," 
                    + IDBCConstant.ATT_RINCIAN_KODEREKENING+","
                    + IDBCConstant.ATT_RINCIAN_NAMAREKENING
                    +") values (?,?, ?, ?)");
            
            pstat.setLong(1, rincian.getDipaindex());
            pstat.setLong(2, rincian.getSubkomponen());
            pstat.setString(3, rincian.getKoderekening());
            pstat.setString(4, rincian.getNamarekening());
            
            
            
            pstat.execute();
            
            long idrincian = getMaxIndex(IDBCConstant.ATT_RINCIAN_RNCIANINDEX, IDBCConstant.TABLE_RINCIAN, conn);
            rincian.setRincianindex(idrincian);

            return rincian;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();

            }
        }
    }

    @Override
    public void updateRincian(long oldrincianindex, Rincian rincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_RINCIAN
                    + " set " + IDBCConstant.ATT_RINCIAN_DIPAINDEX + "=?, "
                    + IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX + "=?,"
                    +IDBCConstant.ATT_RINCIAN_KODEREKENING+"=?, "
                    +IDBCConstant.ATT_RINCIAN_NAMAREKENING+"=? "
                    +" where "
                    + IDBCConstant.ATT_RINCIAN_RNCIANINDEX + "=?");
            pstat.setLong(1, rincian.getDipaindex());
            pstat.setLong(2, rincian.getSubkomponen());
            pstat.setString(3, rincian.getKoderekening());
            pstat.setString(4, rincian.getNamarekening());
            pstat.setLong(5, oldrincianindex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Memutakhirkan Rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteRincian(long rincianIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_RINCIAN
                    + " where " + IDBCConstant.ATT_RINCIAN_RNCIANINDEX + "=?");
            pstat.setLong(1, rincianIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Rincian.\n" + e.toString());
        }
    }

    @Override
    public Rincian getRincian(long rincianIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIAN
                    + " where " + IDBCConstant.ATT_RINCIAN_RNCIANINDEX + "=" + rincianIndex);

            if (rs != null) {
                Dipa dipa = null;
                KegiatanDipa kedip = null;
                Rincian rincian = null;
                while (rs.next()) {
                    dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                    SubKomponen subkomp = getSubKomponen(rs.getLong(IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX), conn);
                    rincian = new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa, rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING));
                }

                return rincian;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil Data Rincian.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }

        }
    }
    
    @Override
    public Rincian getRincianBySubKompAndKode(long subkompid, String koderekening,  Connection conn) throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIAN+" where "
                    +IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX+" = "+subkompid+" and "
                    +IDBCConstant.ATT_RINCIAN_KODEREKENING+" = \'"+koderekening+"\'");
            Rincian rincian = null;
            if(rs != null){
                Dipa dipa = null;
                KegiatanDipa kedip = null;
                
                while (rs.next()) {
                    dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                    SubKomponen subkomp = getSubKomponen(rs.getLong(IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX), conn);
                    rincian = new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa, rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING));
                }

                return rincian;
            }else{
                rincian = null;
                return rincian;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data rincian.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public Rincian[] getAllRincian(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIAN
                    + " order by " + IDBCConstant.ATT_RINCIAN_DIPAINDEX);
            if (rs != null) {

                while (rs.next()) {
                    Dipa dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
//                    KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_KEGIATAN), conn);
                    SubKomponen subkomp = getSubKomponen(rs.getLong(IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX), conn);
                    vResults.addElement(new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa, 
                            rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING)));

                }
                Rincian[] results = new Rincian[vResults.size()];
                vResults.copyInto(results);
                return results;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Rincian. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Rincian[] getAllRincianByDipa(long dipaindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIAN
                    + " where " + IDBCConstant.ATT_RINCIAN_DIPAINDEX + "=" + dipaindex);
            if (rs != null) {
                while (rs.next()) {
                    Dipa dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
//                    KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_KEGIATAN), conn);
                    SubKomponen subkomp = getSubKomponen(rs.getLong(IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX), conn);
                    vResults.addElement(new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa,
                    rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING)));
                }

                Rincian[] results = new Rincian[vResults.size()];
                vResults.copyInto(results);
                return results;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian. \n" + e.toString());
        }
    }
    
    

    @Override
    public RincianDipa createRincianDipa(RincianDipa rdipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_RINCIANDIPA
                    + " (" + IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_KODEREKENING + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_LOKASI + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_KPPN + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN + ", "
                    + IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA + ") values(?,?,?,?,?,?,?)");
            pstat.setLong(1, rdipa.getRincianindex());
            pstat.setString(2, rdipa.getKoderekening());
            pstat.setString(3, rdipa.getNamarekening());
            pstat.setString(4, rdipa.getLokasi());
            pstat.setString(5, rdipa.getKppn());
            pstat.setString(6, rdipa.getCarapenarikan());
            pstat.setDouble(7, rdipa.getJumlahdana());

            pstat.execute();

            return rdipa;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Menambahkan Rincian DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateRincianDipa(long oldkodeRekening, RincianDipa rDipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_RINCIANDIPA
                    + " set " + IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING + "=?, "
                    + IDBCConstant.ATT_RINCIANDIPA_LOKASI + "=?, "
                    + IDBCConstant.ATT_RINCIANDIPA_KPPN + "=?, "
                    + IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN + "=?, "
                    + IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA + "=? where "
                    + IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX + "=? and "
                    + IDBCConstant.ATT_RINCIANDIPA_KODEREKENING + "=?");

            pstat.setString(1, rDipa.getNamarekening());
            pstat.setString(2, rDipa.getLokasi());
            pstat.setString(3, rDipa.getKppn());
            pstat.setString(4, rDipa.getCarapenarikan());
            pstat.setDouble(5, rDipa.getJumlahdana());
            pstat.setLong(6, oldkodeRekening);
            pstat.setString(7, rDipa.getKoderekening());

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memuktahirkan Rincin DIPA. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteRincianDipa(long rincianIndex, String kodeRekening, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_RINCIANDIPA
                    + " where " + IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX + "=? and "
                    + IDBCConstant.ATT_RINCIANDIPA_KODEREKENING + "=?");
            pstat.setLong(1, rincianIndex);
            pstat.setString(2, kodeRekening);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Menghapus Rincian Dipa. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public RincianDipa getRincianDipa(long rincianindex, String koderekening, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery(koderekening);
            if (rs != null) {
                RincianDipa rdipa = null;
                while (rs.next()) {
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
//                    rdipa = new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN),
//                            rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA));
                }

                return rdipa;
            } else {
                return null;
            }

        } catch (Exception e) {

            throw new SQLException("SQLSAP : Gagal Dalam Mengambil Data Rincian DIPA. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();

            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public RincianDipa[] getAllRincianDipa(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipa> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIANDIPA + " order by " + IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN);

            if (rs != null) {
                while (rs.next()) {
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
//                    vResult.addElement(new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN),
//                            rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA)));
                }

                RincianDipa[] result = new RincianDipa[vResult.size()];
                vResult.copyInto(result);
                return result;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian DIPA.\n" + e.toString());
        }
    }

    @Override
    public RincianDipa[] getAllRincianDipaByRincian(long rincianindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipa> vResult = new Vector<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIANDIPA + " where " + IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX + "=" + rincianindex + " order by " + IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN);

            if (rs != null) {
                while (rs.next()) {
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
//                    vResult.addElement(new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
//                            rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN),
//                            rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA)));
                }

                RincianDipa[] result = new RincianDipa[vResult.size()];
                vResult.copyInto(result);
                return result;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian DIPA.\n" + e.toString());
        }
    }

    @Override
    public Komponen createKomponen(Komponen komponen, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_KOMPONEN + "("
                    + IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX + ", "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENKODE + ", "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENNAME + ") values(?,?,?)");
            
            pstat.setLong(1, komponen.getOutputkegiatanindex());
            pstat.setString(2, komponen.getKomponenkode());
            pstat.setString(3, komponen.getKomponenname());

            pstat.execute();

            return komponen;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan data ke Komponen.\n"
                    + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }

    }

    @Override
    public void updateKomponen(long oldKomponenIndex, Komponen komponen, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_KOMPONEN + " set "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENKODE + "=?, "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENNAME + "=? where "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX + "=?");

            pstat.setString(1, komponen.getKomponenkode());
            pstat.setString(2, komponen.getKomponenname());
            pstat.setLong(3, oldKomponenIndex);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memuktahirkan Komponen.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteKomponen(long komponenindex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_KOMPONEN
                    + " where " + IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX + "=?");
            pstat.setLong(1, komponenindex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus data komponen.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public Komponen getKomponen(long komponenindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Komponen komp = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KOMPONEN + " where "
                    + IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX + "=" + komponenindex);
            while (rs.next()) {
                OutputKegiatan outke = getOutputKegiatan(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX), conn);
                komp = new Komponen(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX),
                        outke, rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENNAME));

                return komp;

            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            
            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public Komponen getKomponenByOutput(long outputKegiatan,String komponenkode, Connection conn)throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        Komponen komp = null;
//        System.out.println("Kode Output Kegiatan : "+outputKegiatan);
//                System.out.println("Kode Komponen : "+komponenkode);
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KOMPONEN + " where "
                    + IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX + "=" +outputKegiatan +" and "+IDBCConstant.ATT_KOMPONEN_KOMPONENKODE+" = \'"+komponenkode+"\'");
            while (rs.next()) {
                OutputKegiatan outke = getOutputKegiatan(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX), conn);
                
                komp = new Komponen(rs.getLong(IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX),
                        outke, rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENNAME));

                return komp;

            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Komponen[] getAllKomponen(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Komponen> vResult = new Vector<>();
        Komponen[] result;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KOMPONEN + " order by " + IDBCConstant.ATT_KOMPONEN_KOMPONENKODE);
            while (rs.next()) {
                OutputKegiatan outke = getOutputKegiatan(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX), conn);
                vResult.addElement(new Komponen(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX),
                        outke, rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENNAME)));
            }

            result = new Komponen[vResult.size()];
            vResult.copyInto(result);

            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public Komponen[] getAllKomponenByOutputKegiatan(long outputKegiatanIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Komponen> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KOMPONEN + " where "
                    + IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX + "=" + outputKegiatanIndex);

            while (rs.next()) {
                OutputKegiatan outke = getOutputKegiatan(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX), conn);
                Komponen komponen = new Komponen(rs.getLong(IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX), outke,
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENNAME));
                
                vResult.addElement(komponen);
            }
            
            Komponen[] result = new Komponen[vResult.size()];
            vResult.copyInto(result);

            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Komponen. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public Komponen[] getAllKomponenByOutputKegiatan2(long outputKegiatanIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Komponen> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KOMPONEN + " where "
                    + IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX + "=" + outputKegiatanIndex);

            while (rs.next()) {
                OutputKegiatan outke = getOutputKegiatan(rs.getLong(IDBCConstant.ATT_KOMPONEN_OUTPUTKEGIATANINDEX), conn);
                Komponen komponen = new Komponen(rs.getLong(IDBCConstant.ATT_KOMPONEN_KOMPONENINDEX), outke,
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_KOMPONEN_KOMPONENNAME));
                SubKomponen[] subkomponen = getAllSubKomponenByKomponen2(komponen.getKomponenindex(), conn);
                komponen.setSubkomponen(subkomponen);
                vResult.addElement(komponen);
            }
            
            Komponen[] result = new Komponen[vResult.size()];
            vResult.copyInto(result);

            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Komponen. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public OutputKegiatan getOutputKegiatan(long outputKegiatanDipa, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX + "=" + outputKegiatanDipa);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                OutputKegiatan ouke = new OutputKegiatan(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX), kedip, rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT), rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT));
                return ouke;
            }
            return null;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data Output Kegiatan DIPA. \n" + e.toString());
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public OutputKegiatan getOutputKegiatanByKegiatandipaAndKodeOutput(long kegiatandipa, String nroutput, Connection conn) throws SQLException{
        Statement s = null;
        ResultSet rs = null;

        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN + " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA+ "=" + kegiatandipa + " and "+IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT+" = \'"+nroutput+"\'");

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                OutputKegiatan ouke = new OutputKegiatan(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUTKEGIATANINDEX),kedip, rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT), rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT));
                return ouke;
            }
            return null;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data Output Kegiatan DIPA. \n" + e.toString());
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public SubKomponen createSubKomponen(SubKomponen Subkom, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_SUBKOMPONEN + " ("
                    + IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN + ", "
                    + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE + ", "
                    + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME + ") values (?,?,?)");
                    
            pstat.setLong(1, Subkom.getKomponenindex());
            pstat.setString(2, Subkom.getSubkomponenkode());
            pstat.setString(3, Subkom.getSubkomponenname());

            pstat.execute();
            
            return Subkom;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Sub Komponen. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateSubKomponen(long oldsubkomponen, SubKomponen subkomponen, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_SUBKOMPONEN + " set " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE + "=?,"
                    + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME + "=? where " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX + "=?");
            pstat.setString(1, subkomponen.getSubkomponenkode());
            pstat.setString(2, subkomponen.getSubkomponenname());
            pstat.setLong(3, subkomponen.getSubkomponenindex());
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan sub komponen. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteSubKomponen(long subkomponenindex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_SUBKOMPONEN + " where " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX + "=?");

            pstat.setLong(1, subkomponenindex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus subkomponen.\n" + e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public SubKomponen getSubKomponen(long subkomponenindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_SUBKOMPONEN + " where " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX + "=" + subkomponenindex);
            while (rs.next()) {
                Komponen komp = getKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN), conn);
                SubKomponen subkomp = new SubKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX), komp,
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME));

                return subkomp;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Subkomponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public SubKomponen getSubKomponenByKomponen(long komponenid, String subkomponenkode, Connection conn) throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_SUBKOMPONEN 
                    + " where " + IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN + "=" 
                    + komponenid+" and "
                    +IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE+"= \'"+subkomponenkode+"\'");
            while (rs.next()) {
                Komponen komp = getKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN), conn);
                SubKomponen subkomp = new SubKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX), komp,
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME));

                return subkomp;
            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Subkomponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public SubKomponen[] getAllSubKomponen(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<SubKomponen> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_SUBKOMPONEN + " order by " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE);
            while (rs.next()) {
                Komponen komp = getKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN), conn);
                vResult.addElement(new SubKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX), komp,
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME)));
            }

            SubKomponen[] result = new SubKomponen[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list Sub Komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public SubKomponen[] getAllSubKomponenByKomponen(long komponenindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<SubKomponen> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_SUBKOMPONEN
                    + " where " + IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN + "=" + komponenindex
                    + " order by " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE);
            while (rs.next()) {
                Komponen komp = getKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN), conn);
                
                SubKomponen subkomponen = new SubKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX), komp,
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME));
                
                
                vResult.addElement(subkomponen);
            }

            SubKomponen[] result = new SubKomponen[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list Sub Komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }
    
    @Override
    public SubKomponen[] getAllSubKomponenByKomponen2(long komponenindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<SubKomponen> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_SUBKOMPONEN
                    + " where " + IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN + "=" + komponenindex
                    + " order by " + IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE);
            while (rs.next()) {
                Komponen komp = getKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_KOMPONEN), conn);
                
                SubKomponen subkomponen = new SubKomponen(rs.getLong(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENINDEX), komp,
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENKODE),
                        rs.getString(IDBCConstant.ATT_SUBKOMPONEN_SUBKOMPONENNAME));
                Rincian[] rincian = getRincianBySubkomp2(subkomponen.getSubkomponenindex(), conn);
                subkomponen.setRincian(rincian);
                vResult.addElement(subkomponen);
            }

            SubKomponen[] result = new SubKomponen[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list Sub Komponen.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public GrupItem createGrupItem(GrupItem grupItem, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_GRUPITEM + " ("
                    + IDBCConstant.ATT_GRUPITEM_GRUPNAME + ") values (?)");
            pstat.setString(1, grupItem.getGrupname());
            pstat.execute();
            return grupItem;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan Grup Item. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateGrupItem(long oldIndexGrup, GrupItem grupItem, Connection conn) throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_GRUPITEM + " set "
                    + IDBCConstant.ATT_GRUPITEM_GRUPNAME + "=? where "
                    + IDBCConstant.ATT_GRUPITEM_GRUPINDEX + "=?");
            pstat.setString(1, grupItem.getGrupname());
            pstat.setLong(2, oldIndexGrup);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan Grup Item. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteGrupItem(long oldIndexGrup, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_GRUPITEM + " where "
                    + IDBCConstant.ATT_GRUPITEM_GRUPINDEX + "=?");
            pstat.setLong(1, oldIndexGrup);
            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus Grup Item. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public GrupItem getGrupItem(long indexGrup, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        GrupItem result = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_GRUPITEM
                    + " where " + IDBCConstant.ATT_GRUPITEM_GRUPINDEX + "=" + indexGrup);
            while (rs.next()) {
                return new GrupItem(rs.getLong(IDBCConstant.ATT_GRUPITEM_GRUPINDEX),
                        rs.getString(IDBCConstant.ATT_GRUPITEM_GRUPNAME));
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Grup Item.\n");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }

        }
    }

    @Override
    public GrupItem[] getAllGrupItem(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<GrupItem> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_GRUPITEM);
            while (rs.next()) {
                vResult.addElement(new GrupItem(rs.getLong(IDBCConstant.ATT_GRUPITEM_GRUPINDEX),
                        rs.getString(IDBCConstant.ATT_GRUPITEM_GRUPNAME)));

            }
            GrupItem[] result = new GrupItem[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil List Grup Item. \n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }

    }

    @Override
    public KodrekRincian createKodrekRincian(KodrekRincian kodrekRincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_KODREKRINCIAN
                    + " (" + IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX + ", "
                    + IDBCConstant.ATT_KODREKRINCIAN_KODREK + ", "
                    + IDBCConstant.ATT_KODREKRINCIAN_RINCIANITEM + ", "
                    + IDBCConstant.ATT_KODREKRINCIAN_NILAIITEM + ") values(?,?,?,?)");
            pstat.setLong(1, kodrekRincian.getKodrekrincianindex());
            pstat.setLong(2, kodrekRincian.getKodrek());
            pstat.setString(3, kodrekRincian.getRincianitem());
            pstat.setDouble(4, kodrekRincian.getNilaiitem());
            pstat.execute();

            return kodrekRincian;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan kodrek rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateKodrekRincian(long oldKodrekRincianIndex, KodrekRincian kodrekRincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_KODREKRINCIAN + " set "
                    + IDBCConstant.ATT_KODREKRINCIAN_KODREK + "=?, "
                    + IDBCConstant.ATT_KODREKRINCIAN_RINCIANITEM + "=?, "
                    + IDBCConstant.ATT_KODREKRINCIAN_NILAIITEM + "=? where "
                    + IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX + "=?");

            pstat.setLong(1, kodrekRincian.getKodrek());
            pstat.setString(2, kodrekRincian.getRincianitem());
            pstat.setDouble(3, kodrekRincian.getNilaiitem());
            pstat.setLong(4, oldKodrekRincianIndex);

            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan kodrek rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteKodrekRincian(long kodrekRincianIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_KODREKRINCIAN
                    + " where " + IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX + "=?");
            pstat.setLong(1, kodrekRincianIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal menghapus kodrek rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public KodrekRincian getKodrekRincian(long kodrekRincianIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        KodrekRincian result = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KODREKRINCIAN + " where "
                    + IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX + "=" + kodrekRincianIndex);
            while (rs.next()) {
                RincianDipaKodrek rincianDipaKodrek = getRincianDipaKodrek(rs.getLong(IDBCConstant.ATT_KODREKRINCIAN_KODREK), conn);
                return new KodrekRincian(rs.getLong(IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX),
                        rincianDipaKodrek,
                        rs.getString(IDBCConstant.ATT_KODREKRINCIAN_RINCIANITEM), rs.getDouble(IDBCConstant.ATT_KODREKRINCIAN_NILAIITEM));

            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Kodrek Rincian.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public KodrekRincian[] getAllKodrekRincian(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<KodrekRincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_KODREKRINCIAN);
            while (rs.next()) {
                RincianDipaKodrek rincianDipaKodrek = getRincianDipaKodrek(rs.getLong(IDBCConstant.ATT_KODREKRINCIAN_KODREK), conn);
                vResult.addElement(new KodrekRincian(rs.getLong(IDBCConstant.ATT_KODREKRINCIAN_RINCIANINDEX),
                        rincianDipaKodrek,
                        rs.getString(IDBCConstant.ATT_KODREKRINCIAN_RINCIANITEM),
                        rs.getDouble(IDBCConstant.ATT_KODREKRINCIAN_NILAIITEM)));
            }

            KodrekRincian[] results = new KodrekRincian[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list Kodrek Rincian.\n" + e.toString());
        } finally {

            if (rs != null) {
                rs.close();
            }

            if (s != null) {
                s.close();
            }

        }
    }

    @Override
    public GrupKodrekRincian createGrupKodrekRincian(GrupKodrekRincian kodrekrincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_GRUPKODREKRINCIAN + " ("
                    + IDBCConstant.ATT_GRUPKODREKRINCIAN_GRUPINDEX + ", "
                    + IDBCConstant.ATT_GRUPKODREKRINCIAN_RINCIANINDEX + ") values(?,?)");
            pstat.setLong(1, kodrekrincian.getIndexgrup());
            pstat.setLong(2, kodrekrincian.getRincianindex());
            pstat.execute();
            return kodrekrincian;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan Grup Kodrek Rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteGrupKodrekRincian(GrupKodrekRincian kodrekRincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_GRUPKODREKRINCIAN
                    + " where " + IDBCConstant.ATT_GRUPKODREKRINCIAN_GRUPINDEX + "=? and "
                    + IDBCConstant.ATT_GRUPKODREKRINCIAN_RINCIANINDEX + "=?");
            pstat.setLong(1, kodrekRincian.getIndexgrup());
            pstat.setLong(2, kodrekRincian.getRincianindex());
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus Grup Kodrek Rincian.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public GrupKodrekRincian[] getKodrekRincianByGrupItem(GrupItem grupItem, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<GrupKodrekRincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_GRUPKODREKRINCIAN+" where "
                                +IDBCConstant.ATT_GRUPKODREKRINCIAN_GRUPINDEX+"="+grupItem);
            while(rs.next()){
                vResult.addElement(new GrupKodrekRincian(rs.getLong(IDBCConstant.ATT_GRUPKODREKRINCIAN_GRUPINDEX), 
                        rs.getLong(IDBCConstant.ATT_GRUPKODREKRINCIAN_RINCIANINDEX)));
            }
            GrupKodrekRincian[] results = new GrupKodrekRincian[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Grup Kodrek Rincian.\n");
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public GrupKodrekRincian getKodrekRincianByKodrekRincian(KodrekRincian kodrekRincian, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_GRUPKODREKRINCIAN+" where "
                                +IDBCConstant.ATT_GRUPKODREKRINCIAN_RINCIANINDEX+"="+kodrekRincian);
            while(rs.next()){
                return new GrupKodrekRincian(rs.getLong(IDBCConstant.ATT_GRUPKODREKRINCIAN_GRUPINDEX), 
                        rs.getLong(IDBCConstant.ATT_GRUPKODREKRINCIAN_RINCIANINDEX));
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Grup Kodrek Rincian.\n"+e.toString());
        }
    }

    @Override
    public RincianDipaKodrek createRincianDipaKodrek(RincianDipaKodrek rincianDipaKodrek, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into " + IDBCConstant.TABLE_RINCIAN_KODREK + " ("
                    + IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_KODEREKENING + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_NAMAREKENING + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_LOKASI + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_KPPN + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_CARAPENARIKAN + ", "
                    + IDBCConstant.ATT_RINCIANKODREK_JUMLAHDANA + ") values(?,?,?,?,?,?,?,?)");
            pstat.setLong(1, rincianDipaKodrek.getRinciapdipakodrekindex());
            pstat.setLong(2, rincianDipaKodrek.getRincianindex());
            pstat.setString(3, rincianDipaKodrek.getKoderekening());
            pstat.setString(4, rincianDipaKodrek.getNamarekening());
            pstat.setString(5, rincianDipaKodrek.getLokasi());
            pstat.setString(6, rincianDipaKodrek.getKppn());
            pstat.setString(7, rincianDipaKodrek.getCarapenarikan());
            pstat.setDouble(8, rincianDipaKodrek.getJumlahdana());

            pstat.execute();

            return rincianDipaKodrek;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan Rincian Dipa Kodrek.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void updateRincianDipaKodrek(long oldRincianDipaKodrekIndex, RincianDipaKodrek rincianDipaKodrek, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update " + IDBCConstant.TABLE_RINCIAN_KODREK + " set "
                    + IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_KODEREKENING + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_NAMAREKENING + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_LOKASI + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_KPPN + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_CARAPENARIKAN + "=?, "
                    + IDBCConstant.ATT_RINCIANKODREK_JUMLAHDANA + "=? where "
                    + IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX + "=?");
            pstat.setLong(1, rincianDipaKodrek.getRincianindex());
            pstat.setString(2, rincianDipaKodrek.getKoderekening());
            pstat.setString(3, rincianDipaKodrek.getNamarekening());
            pstat.setString(4, rincianDipaKodrek.getLokasi());
            pstat.setString(5, rincianDipaKodrek.getKppn());
            pstat.setString(6, rincianDipaKodrek.getCarapenarikan());
            pstat.setDouble(7, rincianDipaKodrek.getJumlahdana());
            pstat.setLong(8, oldRincianDipaKodrekIndex);

            pstat.execute();

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan Rincian Dipa Kodrek. \n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public void deleteRincianDipaKodrek(long rincianDipaKodrekIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from " + IDBCConstant.TABLE_RINCIAN_KODREK
                    + " where " + IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX + "=?");
            pstat.setLong(1, rincianDipaKodrekIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus Rincian Dipa Kodrek.\n" + e.toString());
        } finally {
            if (pstat != null) {
                pstat.close();
            }
        }
    }

    @Override
    public RincianDipaKodrek getRincianDipaKodrek(long rincianDipaKodrekIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        RincianDipaKodrek result = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIAN_KODREK + " where "
                    + IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX + "=" + rincianDipaKodrekIndex);
            while (rs.next()) {
                Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX), conn);
                return new RincianDipaKodrek(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX),
                        rincian, rs.getString(IDBCConstant.ATT_RINCIANKODREK_KODEREKENING),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_NAMAREKENING),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_LOKASI),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_KPPN),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_CARAPENARIKAN),
                        rs.getDouble(IDBCConstant.ATT_RINCIANKODREK_JUMLAHDANA));

            }

            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Rincian DIPA Kodrek.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public RincianDipaKodrek[] getAllRincianDipaKodrek(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipaKodrek> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_RINCIAN_KODREK + " order by "
                    + IDBCConstant.ATT_RINCIANKODREK_KODEREKENING);
            while (rs.next()) {
                Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX), conn);
                vResult.addElement(new RincianDipaKodrek(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX),
                        rincian, rs.getString(IDBCConstant.ATT_RINCIANKODREK_KODEREKENING),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_NAMAREKENING),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_LOKASI),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_KPPN),
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_CARAPENARIKAN),
                        rs.getDouble(IDBCConstant.ATT_RINCIANKODREK_JUMLAHDANA)));
            }

            RincianDipaKodrek[] result = new RincianDipaKodrek[vResult.size()];
            vResult.copyInto(result);
            return result;

        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list Rincian Dipa Kodrek.\n" + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }

    }

    @Override
    public MasterVolume createMasterVolume(MasterVolume mv, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_MASTERVOLUME+" ( "
                                            +IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME+", "
                                            +IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS+", "
                                            +IDBCConstant.ATT_MASTERVOLUME_DESCRIPTION+ ") values(?,?,?)");
            
            pstat.setShort(1, mv.getKodevolume());
            pstat.setString(2, mv.getNamajenis());
            pstat.setString(3, mv.getDescription());
            pstat.execute();
            
            return mv;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan Master Volume.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void updateMasterVolume(long oldMasterVolumeId, MasterVolume mv, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_MASTERVOLUME
                                            +" set "+IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME+"=?, "
                                            +IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS+"=?, "
                                            +IDBCConstant.ATT_MASTERVOLUME_DESCRIPTION+"=? where "
                                            +IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID+"=?");
            pstat.setShort(1, mv.getKodevolume());
            pstat.setString(2, mv.getNamajenis());
            pstat.setString(3, mv.getDescription());
            pstat.setLong(4, oldMasterVolumeId);
                    
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan Master Volume.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void deleteMasterVolume(long masterVolumeId, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_MASTERVOLUME+" where "+IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID+"=?");
            pstat.setLong(1, masterVolumeId);
            
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus Master Volume.\n"+e.toString());
        }finally{
            if(pstat!= null)
                pstat.close();
        }
    }

    @Override
    public MasterVolume getMasterVolume(long masterVolumeId, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_MASTERVOLUME+" where "+IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID+"=?");
            
            while(rs.next()){
                MasterVolume mv = new MasterVolume(rs.getLong(IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID), 
                                        rs.getShort(IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_DESCRIPTION));
                return mv;
            }
            
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil data untuk Master Volume.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
        
    }

    @Override
    public MasterVolume[] getAllMasterVolume(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<MasterVolume> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_MASTERVOLUME+" order by "
                                +IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME);
            
            while(rs.next()){
                
                vResult.addElement(new MasterVolume(rs.getLong(IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID), 
                                        rs.getShort(IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_DESCRIPTION)));
                
            }
            
            MasterVolume[] results = new MasterVolume[vResult.size()];
            vResult.copyInto(results);
            
            return results;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Master Volume.\n"+e.toString());
        }finally{
            
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
            
        }
    }

    @Override
    public MasterVolume getMasterVolumeByName(String masterVolumeName, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_MASTERVOLUME+
                                " where "+IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS+"="+masterVolumeName);
            
            while(rs.next()){
                return new MasterVolume(rs.getLong(IDBCConstant.ATT_MASTERVOLUME_MASTERVOLUMEID), 
                                        rs.getShort(IDBCConstant.ATT_MASTERVOLUME_KODEVOLUME), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_NAMAJENIS), 
                                        rs.getString(IDBCConstant.ATT_MASTERVOLUME_DESCRIPTION));
            }
            
            return  null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data Master Volume.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public OutputKegiatan[] getAllOutputKegiatanByTahun(short tahun, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<OutputKegiatan> vResult = new Vector<>();
        
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_PROGRAM_DIPA+", "
                                +IDBCConstant.TABLE_KEGIATAN_DIPA+", "
                                +IDBCConstant.TABLE_OUTPUT_KEGIATAN+" where "+IDBCConstant.TABLE_PROGRAM_DIPA+"."
                                +IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN+"="+tahun);
            
            while(rs.next()){
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                vResult.addElement(new OutputKegiatan(kedip,
                        rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT)));
            }
            
            OutputKegiatan[] results = new OutputKegiatan[vResult.size()];
            vResult.copyInto(results);
            
            return results;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List OutputKegiatan by Tahun.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public KegiatanDipa[] getAllKegiatanDipaByTahun(short tahun, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<KegiatanDipa> vResult = new Vector<>();
        
        try {
            System.out.println(tahun);
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_PROGRAM_DIPA+
                            " inner join "+IDBCConstant.TABLE_KEGIATAN_DIPA+" ON "+
                            IDBCConstant.TABLE_PROGRAM_DIPA+"."+IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX+"="
                            +IDBCConstant.TABLE_KEGIATAN_DIPA+"."+IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX+" and "
                            +IDBCConstant.TABLE_PROGRAM_DIPA+"."+IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN+"="+tahun);
            
            while(rs.next()){
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);

                vResult.addElement(new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt));
            }
            
            KegiatanDipa[] results = new KegiatanDipa[vResult.size()];
            vResult.copyInto(results);
            
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil list KegiatanDipa by Tahun.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
        
    }
    
    @Override
    public KegiatanDipa[] getAllKegiatanDipaByProdi(ProgramDipa programdipa, Connection conn)throws SQLException{
         Statement s = null;
        ResultSet rs = null;
        Vector<KegiatanDipa> vResult = new Vector<>();
        
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_PROGRAM_DIPA+
                            " inner join "+IDBCConstant.TABLE_KEGIATAN_DIPA+" ON "+
                            IDBCConstant.TABLE_PROGRAM_DIPA+"."+IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX+"="
                            +IDBCConstant.TABLE_KEGIATAN_DIPA+"."+IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX+" and "
                            +IDBCConstant.TABLE_PROGRAM_DIPA+"."+IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN+"="+programdipa.getDipaIndex());
            
            while(rs.next()){
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                
                
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);
                
                KegiatanDipa kedip = new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt);
                
                vResult.addElement(kedip);
            }
            
            KegiatanDipa[] results = new KegiatanDipa[vResult.size()];
            vResult.copyInto(results);
            
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil list KegiatanDipa by Tahun.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }
    
    @Override
    public KegiatanDipa[] getAllKegiatanDipaByProdi2(ProgramDipa programdipa, Connection conn)throws SQLException{
         Statement s = null;
        ResultSet rs = null;
        Vector<KegiatanDipa> vResult = new Vector<>();
        
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_KEGIATAN_DIPA+" where "
                    +IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX+" = "+programdipa.getDipaIndex());
            
            while(rs.next()){
                ProgramDipa prodi = getProgramDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX), conn);
                
                
                Kegiatan kgt = getKegiatan(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN), conn);
                
                KegiatanDipa kedip = new KegiatanDipa(rs.getLong(IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX), prodi, kgt);
                OutputKegiatan[] outputkegiatan = getAllOutputKegiatanByKegiatan2(kedip.getKegiatandipaindex(), conn);
                
                kedip.setOutputkegiatan(outputkegiatan);
                vResult.addElement(kedip);
            }
            
            KegiatanDipa[] results = new KegiatanDipa[vResult.size()];
            vResult.copyInto(results);
            
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil list KegiatanDipa by Tahun.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public RincianDipaKodrek[] getAllRincianDipaKodrekByKomponen(long komponenindex, Connection conn) throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipaKodrek> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIAN_KODREK+" where "+IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX+"="+komponenindex);
            
            while(rs.next()){
                Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANINDEX), conn);
                vResult.addElement(new RincianDipaKodrek(rs.getLong(IDBCConstant.ATT_RINCIANKODREK_RINCIANKODREKINDEX), rincian, 
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_KODEREKENING), 
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_NAMAREKENING), 
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_LOKASI), 
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_KPPN), 
                        rs.getString(IDBCConstant.ATT_RINCIANKODREK_CARAPENARIKAN), rs.getDouble(IDBCConstant.ATT_RINCIANKODREK_JUMLAHDANA)));
            }
            
            RincianDipaKodrek[] results = new RincianDipaKodrek[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Rincian DIPA.\n"+e.toString());            
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public void createRincianAkun(Dipa dipa, Rincian rincian, RincianDipa rincianDipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            
        } catch (Exception e) {
        }
    }
    
    @Override
    public DetailRincian createDetailRincian(DetailRincian detail, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            if(detail.getParent() != null){
                pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_DETAILRINCIAN
                                            +"("+IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_LOKASI+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_KPPN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_VOLUME+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA+") values (?,?,?,?,?,?,?,?)");

                pstat.setLong(1, detail.getRincianindex());
                pstat.setString(2, detail.getDetailrincian());
                pstat.setString(3, detail.getLokasi());
                pstat.setString(4, detail.getKppn());
                pstat.setString(5, detail.getCarapenarikan());
                pstat.setInt(6, detail.getVolume());
                pstat.setString(7, detail.getJenisvolume());
                pstat.setDouble(8, detail.getJumlahdana());

                pstat.execute();               

                long index = getMaxIndex(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX, IDBCConstant.TABLE_DETAILRINCIAN, conn);
                
                detail.setDetailrincianindex(index);
                createDetailRincianStruktur(detail.getParent().getDetailrincianindex(), index, conn);
                

                return detail;
            }else{
                pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_DETAILRINCIAN
                                            +"("+IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_LOKASI+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_KPPN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_VOLUME+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME+","
                                            +IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA+") values (?,?,?,?,?,?,?,?)");

                pstat.setLong(1, detail.getRincianindex());
                pstat.setString(2, detail.getDetailrincian());
                pstat.setString(3, detail.getLokasi());
                pstat.setString(4, detail.getKppn());
                pstat.setString(5, detail.getCarapenarikan());
                pstat.setInt(6, detail.getVolume());
                pstat.setString(7, detail.getJenisvolume());
                pstat.setDouble(8, detail.getJumlahdana());

                pstat.execute();

                long index = getMaxIndex(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX, IDBCConstant.TABLE_DETAILRINCIAN, conn);

                detail.setDetailrincianindex(index);

                return detail;
            }
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal menambahkan detailrincian.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }
    
    public void createDetailRincianStruktur(long parent, long sub, Connection conn) throws SQLException{
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+
                    IDBCConstant.TABLE_DETAILRINCIANSTRUKTUR+" ("
                    +IDBCConstant.ATT_DETAILRINCIANSTRUKTUR_PARENTRINCIAN+", "
                    +IDBCConstant.ATT_DETAILRINCIANSTRUKTUR_SUBRINCIAN+") values(?,?)");
            pstat.setLong(1, parent);
            pstat.setLong(2, sub);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan Akun Struktur.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void updateDetailRincian(long oldDetailRincianId, DetailRincian detail, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_DETAILRINCIAN+" set "
                                        +IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+"=?,"
                                        +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN+"=?, "
                                        +IDBCConstant.ATT_DETAILRINCIAN_LOKASI+"=?, "
                                        +IDBCConstant.ATT_DETAILRINCIAN_KPPN+"=?, "
                                        +IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN+"=?, "
                                        +IDBCConstant.ATT_DETAILRINCIAN_VOLUME+"=?,"
                                        +IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME+"=?, "
                                        +IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA+"=? where "
                                        +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX+"=?");
            
            
            pstat.setLong(1, detail.getRincian().getRincianindex());
            pstat.setString(2, detail.getDetailrincian());
            pstat.setString(3, detail.getLokasi());
            pstat.setString(4, detail.getKppn());
            pstat.setString(5, detail.getCarapenarikan());
            pstat.setInt(6, detail.getVolume());
            pstat.setString(7, detail.getJenisvolume());
            pstat.setDouble(8, detail.getJumlahdana());
            pstat.setLong(9, oldDetailRincianId);
            
            pstat.executeUpdate();
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan Detail Rincian.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void deleteDetailRincian(long detailRincianId, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        
        try {
            
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "
                                    +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX+" =?");
            pstat.setLong(1, detailRincianId);
            
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal menghapus detailrincian.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public DetailRincian getDetailRincian(long detailRincianId, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "
                            +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX+"="+detailRincianId);
            
            while(rs.next()){
                DetailRincian detail = new DetailRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA));
                
                return detail;
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data detailrincian.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }
    
    public DetailRincian getDetailRincianByRincianAndDetail(Rincian rincian, String detailRincian, Connection conn)throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        DetailRincian detail = null;
        
        try {
//            System.out.println(rincian.getRincianindex());
//            System.out.println(detailRincian);
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "
                            +IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+"="
                    +rincian.getRincianindex()+" and "
                    +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN+"=\'"+detailRincian+"\'");
            
            if(rs != null){
                while(rs.next()){
                    detail = new DetailRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA));
                
                
                }
                return detail;
            }else{
                detail = null;
                return detail;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data detailrincian.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }

    @Override
    public DetailRincian[] getAllDetailRincian(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<DetailRincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" order by "+IDBCConstant.ATT_DETAILRINCIAN_RINCIAN);
            while(rs.next()){
                vResult.addElement(new DetailRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA)));
            }
            
            DetailRincian[] result = new DetailRincian[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Detail Rincian. \n"+e.toString());
        }finally{
            
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }
    
    @Override
    public DetailRincian[] getAllDetaiRincianByRincian(Rincian rincian, Connection conn)throws SQLException{
        Statement s = null;
        ResultSet rs = null;
        Vector<DetailRincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "+IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+"="+rincian.getRincianindex()+" order by "+IDBCConstant.ATT_DETAILRINCIAN_RINCIAN);
            while(rs.next()){
                vResult.addElement(new DetailRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA)));
            }
            
            DetailRincian[] result = new DetailRincian[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil List Detail Rincian. \n"+e.toString());
        }finally{
            
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }
    
    @Override
    public Rincian[] getRincianBySubkomp(long subkompid, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            
            
            rs = s.executeQuery("select * from "
                    +IDBCConstant.TABLE_RINCIAN+" where "
                    +IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX+"="+subkompid);
            
            while(rs.next()){
                SubKomponen subkomp = getSubKomponen(rs.getLong(IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX), conn);
                Dipa dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                Rincian rincian = new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa, 
                        rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING));
                
                vResult.addElement(rincian);
            }
            
            Rincian[] result = new Rincian[vResult.size()];
            vResult.copyInto(result);
            
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil data rincian.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }
    
    @Override
    public Rincian[] getRincianBySubkomp2(long subkompid, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            SubKomponen subkomp = getSubKomponen(subkompid, conn);
            
            Komponen komponen = getKomponen(subkomp.getKomponenindex(), conn);
            OutputKegiatan outke = getOutputKegiatan(komponen.getOutputkegiatanindex(), conn);
            KegiatanDipa kedip = getKegiatanDipa(outke.getOutputkegiatanindex(), conn);
            ProgramDipa prodi = getProgramDipa(kedip.getDipaindex(), conn);
            Dipa dipa = getAllDipaByTahunAnggaran(prodi.getTahunAnggaran(), conn);
            
            rs = s.executeQuery("select * from "
                    +IDBCConstant.TABLE_RINCIAN+" where "
                    +IDBCConstant.ATT_RINCIAN_SUBKOMPONENINDEX+"="+subkompid+" and "+IDBCConstant.ATT_RINCIAN_DIPAINDEX+" = "+dipa.getDipaindex());
            
            while(rs.next()){
                Dipa dipa2 = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                Rincian rincian = new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), subkomp, dipa, 
                        rs.getString(IDBCConstant.ATT_RINCIAN_KODEREKENING), rs.getString(IDBCConstant.ATT_RINCIAN_NAMAREKENING));
                
                DetailRincian[] detailrincian = getAllDetaiRincianByRincian(rincian, conn);
                rincian.setDetailrincian(detailrincian);
                vResult.addElement(rincian);
            }
            
            Rincian[] result = new Rincian[vResult.size()];
            vResult.copyInto(result);
            
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil data rincian.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }

    @Override
    public DetailRincianStruktur[] getDetailRincianStruktur(long rincianId, Connection conn) throws SQLException {
        Statement s = null;
        Statement s2 = null;
        ResultSet rs= null;
        ResultSet rs2 = null;
        
        Vector<DetailRincianStruktur> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            s2 = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "
                    +IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+" = "+rincianId+ " and "
                    +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX+" not in ( select "
                    +IDBCConstant.ATT_DETAILRINCIANSTRUKTUR_SUBRINCIAN+" from "
                    +IDBCConstant.TABLE_DETAILRINCIANSTRUKTUR+")");
            DetailRincian parent = null;
            
            while(rs.next()){
                Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), conn);
                parent = new DetailRincian(rs.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rincian, rs.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA));
                
                rs2 = s2.executeQuery("select * from "+IDBCConstant.TABLE_DETAILRINCIAN+" where "
                        +IDBCConstant.ATT_DETAILRINCIAN_RINCIAN+" = "+rincianId+" and "
                        +IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX+" in ( select "
                        +IDBCConstant.ATT_DETAILRINCIANSTRUKTUR_SUBRINCIAN+" from "
                        +IDBCConstant.TABLE_DETAILRINCIANSTRUKTUR+" where "
                        +IDBCConstant.ATT_DETAILRINCIANSTRUKTUR_PARENTRINCIAN+"="
                        +parent.getDetailrincianindex()+") order by "+IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX);
                Vector<DetailRincian> vv = new Vector<>();
                while(rs2.next()){
                    Rincian rincian2 = getRincian(rs2.getLong(IDBCConstant.ATT_DETAILRINCIAN_RINCIAN), conn);
                    vv.addElement(new DetailRincian(rs2.getLong(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIANINDEX), 
                        rincian2, rs2.getString(IDBCConstant.ATT_DETAILRINCIAN_DETAILRINCIAN), 
                        rs2.getString(IDBCConstant.ATT_DETAILRINCIAN_LOKASI), rs2.getString(IDBCConstant.ATT_DETAILRINCIAN_KPPN), 
                        rs2.getString(IDBCConstant.ATT_DETAILRINCIAN_CARAPENARIKAN), rs2.getInt(IDBCConstant.ATT_DETAILRINCIAN_VOLUME), 
                        rs2.getString(IDBCConstant.ATT_DETAILRINCIAN_JENISVOLUME), rs2.getDouble(IDBCConstant.ATT_DETAILRINCIAN_JUMLAHDANA)));
                }
                DetailRincian[] subs = new DetailRincian[vv.size()];
                vv.copyInto(subs);
                
                vResult.addElement(new DetailRincianStruktur(parent, subs));
            }
            
            DetailRincianStruktur[] results = new DetailRincianStruktur[vResult.size()];
            
            vResult.copyInto(results);
            return results;
            
            
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data detailrincianstruktur.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(rs2 != null){
                rs2.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }

    @Override
    public Markmap[] getAllMarkmap(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Markmap> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_MARKMAP+" order by "+IDBCConstant.ATT_MARKMAP_KDREK);
            while(rs.next()){
                vResult.addElement(new Markmap(rs.getLong(IDBCConstant.ATT_MARKMAP_NO), 
                        rs.getString(IDBCConstant.ATT_MARKMAP_KDREK), 
                        rs.getString(IDBCConstant.ATT_MARKMAP_NMREK)));
            }
            
            Markmap[] result = new Markmap[vResult.size()];
            vResult.copyInto(result);
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Markmap.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public void migrasidatarkakl(Connection conn, Connection conn2) throws SQLException {
        Statement s_mig = null;
        ResultSet rs_mig = null;
        Statement s = null;
        ResultSet rs = null;
        
        try {
            ItemMigrasi item = null;
            long jumlah = 1;
            s_mig = conn.createStatement();
            rs_mig = s_mig.executeQuery("select * from d_item");
            while(rs_mig.next()){
                String thn = rs_mig.getString("thang");
                thn = StringUtils.remove(thn, "'");
                short tahun = NumberUtils.toShort(thn);
                
                String kdgiat = rs_mig.getString("kdgiat");
                kdgiat = StringUtils.remove(kdgiat, "'");
                kdgiat = kdgiat.trim();
                
                String kdoutput = rs_mig.getString("kdoutput");
                kdoutput = StringUtils.remove(kdoutput, "'");
                kdoutput = kdoutput.trim();
                
                String kdsoutput = rs_mig.getString("kdsoutput");
                kdsoutput = StringUtils.remove(kdsoutput, "'");
                kdsoutput = kdsoutput.trim();
                
                String kdkmpnen = rs_mig.getString("kdkmpnen");
                kdkmpnen = StringUtils.remove(kdkmpnen, "'");
                kdkmpnen = kdkmpnen.trim();
                
                String kdakun = rs_mig.getString("kdakun");
                kdakun = StringUtils.remove(kdakun, "'");
                kdakun = kdakun.trim();
                
                String kdlokasi = rs_mig.getString("kdlokasi");
                kdlokasi = StringUtils.remove(kdlokasi, "'");
                kdlokasi = kdlokasi.trim();
                
                String kdkppn = rs_mig.getString("kdkppn");
                kdkppn = StringUtils.remove(kdkppn, "'");
                kdkppn = kdkppn.trim();
                
                String kdctarik = rs_mig.getString("kdctarik");
                kdctarik = StringUtils.remove(kdctarik, "'");
                kdctarik = kdctarik.trim();
                
                String nmitem = rs_mig.getString("nmitem");
                nmitem = nmitem.trim();
                
                double hargasat = rs_mig.getDouble("hargasat");
                int volkeg = rs_mig.getInt("volkeg");
                String satkeg = rs_mig.getString("satkeg");
                
                /**
                 * Parsing data ke objek migrasidata
                 **/
                
                Kegiatan kegiatan = getKegiatanByCode(kdgiat, conn2);
                
                KegiatanDipa kedip = getKegiatanDipaByKegiatanAndTahun(kegiatan, tahun, conn2);
                
                OutputKegiatan outke = getOutputKegiatanByKegiatandipaAndKodeOutput(kedip.getKegiatandipaindex(), kdoutput, conn2);
                
                Komponen komponen = getKomponenByOutput(outke.getOutputkegiatanindex(), kdsoutput, conn2);
//              
                
                if(komponen != null){
                    SubKomponen subkomp = getSubKomponenByKomponen(komponen.getKomponenindex(), kdkmpnen, conn2);
                    
                    if(subkomp != null){
                        Dipa dipa = new Dipa(1, 2014, "DIPA-010.09.1.662766/2014");
                        Markmap mark = getMarkmapByCode(kdakun, conn2);
                        Rincian r = new Rincian(subkomp, dipa, mark.getKdrekening(), mark.getNmrekening());
                        
                        boolean cek = cekrincian(subkomp, kdakun, conn2);
                        Rincian rincian = null;
                        
                        if(cek){
                            rincian = getRincianBySubKompAndKode(subkomp.getSubkomponenindex(), kdakun, conn2);
                        }else{
                            rincian = createRincian(r, conn2);
                            
                            
                            System.out.println(rincian.getNamarekening());
                        }
                        DetailRincian drincian = new DetailRincian(rincian, nmitem, kdlokasi, kdkppn, kdctarik, volkeg, satkeg, hargasat);
                        
                        createDetailRincian(drincian, conn2);
                        
                        jumlah++;
                    }
                    
                }
                
            }
            
            
            
            System.out.println(jumlah);
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam migrasi data.\n"+e.toString());
        }
    }

    @Override
    public Markmap getMarkmapByCode(String kode, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Markmap markmap = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_MARKMAP+ " where "+IDBCConstant.ATT_MARKMAP_KDREK+" = \'"+kode+"\'");
            while(rs.next()){
                markmap = new Markmap(rs.getLong(IDBCConstant.ATT_MARKMAP_NO), 
                        rs.getString(IDBCConstant.ATT_MARKMAP_KDREK), 
                        rs.getString(IDBCConstant.ATT_MARKMAP_NMREK));
                
                return markmap;
            }
            
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data markmap.\n"+e.getMessage());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public boolean cekrincian(SubKomponen subKomponen, String koderekening, Connection conn) throws SQLException {
        try {
            Rincian rincian = getRincianBySubKompAndKode(subKomponen.getSubkomponenindex(), koderekening, conn);
            if(rincian != null)
                return true;
            else
                return false;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengecek data rincian.\n"+e.toString());
        }
    }

    @Override
    public boolean cekDetail(Rincian rincian, String detailrincian, Connection conn) throws SQLException {
        try {
            DetailRincian detail = getDetailRincianByRincianAndDetail(rincian, detailrincian, conn);
            if(rincian != null){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengecek data detail rincian"+e.toString());
        }
        
        
    }

    

    @Override
    public Satker createSatker(Satker satker, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_SATKER+" ("
                                        +IDBCConstant.ATT_SATKER_KODEDEPARTEMEN+", "
                                        +IDBCConstant.ATT_SATKER_NAMADEPARTEMEN+", "
                                        +IDBCConstant.ATT_SATKER_KODEUNIT+", "
                                        +IDBCConstant.ATT_SATKER_NAMAUNIT+", "
                                        +IDBCConstant.ATT_SATKER_KODESATKER+", "    
                                        +IDBCConstant.ATT_SATKER_NAMASATKER+") values (?, ?, ?, ?, ?, ?)");
            pstat.setString(1, satker.getKodedepartemen());
            pstat.setString(2, satker.getNamadepartemen());
            pstat.setString(3, satker.getKodeunitdepartemen());
            pstat.setString(4, satker.getUnitdepartemen());            
            pstat.setString(5, satker.getKodesatker());
            pstat.setString(6, satker.getNamasatker());
            pstat.execute();
            
            long id = getMaxIndex(IDBCConstant.ATT_SATKER_SATKERID, IDBCConstant.TABLE_SATKER, conn);
            satker.setSatkerid(id);
            return satker;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan satker.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void updateSatker(long oldSatkerId, Satker satker, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_SATKER+" set "
                                        +IDBCConstant.ATT_SATKER_KODEDEPARTEMEN+" = ?, "
                                        +IDBCConstant.ATT_SATKER_NAMADEPARTEMEN+"=?,"
                                        +IDBCConstant.ATT_SATKER_KODEUNIT+"=?,"
                                        +IDBCConstant.ATT_SATKER_NAMAUNIT+"=?," 
                                        +IDBCConstant.ATT_SATKER_KODESATKER+"=?, "
                                        +IDBCConstant.ATT_SATKER_NAMASATKER+"=? where "
                                        +IDBCConstant.ATT_SATKER_SATKERID+"=?");
            pstat.setString(1, satker.getKodedepartemen());
            pstat.setString(2, satker.getNamadepartemen());
            pstat.setString(3, satker.getKodeunitdepartemen());
            pstat.setString(4, satker.getUnitdepartemen());
            pstat.setString(5, satker.getKodesatker());
            pstat.setString(6, satker.getNamasatker());
            pstat.setLong(4, oldSatkerId);
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan satker.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void deleteSatker(long satkerId, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_SATKER+" where "+IDBCConstant.ATT_SATKER_SATKERID+"=?");
            pstat.setLong(1, satkerId);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus satker.\n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public Satker getSatker(long satkerId, Connection conn) throws SQLException {
       Statement  s = null;
       ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_SATKER+" where "+IDBCConstant.ATT_SATKER_SATKERID+"="+satkerId);
            
            while(rs.next()){
                
                return new Satker(rs.getLong(IDBCConstant.ATT_SATKER_SATKERID), 
                        rs.getString(IDBCConstant.ATT_SATKER_KODEDEPARTEMEN), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMADEPARTEMEN), 
                        rs.getString(IDBCConstant.ATT_SATKER_KODEUNIT), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMAUNIT),
                        rs.getString(IDBCConstant.ATT_SATKER_KODESATKER), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMASATKER));
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil data satker.\n"+e.toString());
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
    }

    @Override
    public Satker[] getAllSatker(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs=null;
        Vector<Satker> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_SATKER);
            while(rs.next()){
                
                vResult.addElement(new Satker(rs.getLong(IDBCConstant.ATT_SATKER_SATKERID), 
                        rs.getString(IDBCConstant.ATT_SATKER_KODEDEPARTEMEN), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMADEPARTEMEN), 
                        rs.getString(IDBCConstant.ATT_SATKER_KODEUNIT), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMAUNIT),
                        rs.getString(IDBCConstant.ATT_SATKER_KODESATKER), 
                        rs.getString(IDBCConstant.ATT_SATKER_NAMASATKER)));
            }
            Satker[] result = new Satker[vResult.size()];
            vResult.copyInto(result);
            
            return result;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list satker.\n"+e.toString());
            
        }finally{
            if(rs != null)
                rs.close();
            
            if(s != null)
                s.close();
        }
        
    }

    @Override
    public SPP createSPP(SPP spp, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_SPP+" ("
                                    +IDBCConstant.ATT_SPP_OUTPUTKEGIATAN+", "
                                    +IDBCConstant.ATT_SPP_SATKERID+", "
                                    +IDBCConstant.ATT_SPP_BENDAHARAID+", "
                                    +IDBCConstant.ATT_SPP_LOKASI+", "
                                    +IDBCConstant.ATT_SPP_KEWENANGAN+", "
                                    +IDBCConstant.ATT_SPP_JENISBELANJA+", "
                                    +IDBCConstant.ATT_SPP_ATASNAMA+", "
                                    +IDBCConstant.ATT_SPP_ALAMAT+", "
                                    +IDBCConstant.ATT_SPP_KODEREKENING+", "
                                    +IDBCConstant.ATT_SPP_NOSPK+", "
                                    +IDBCConstant.ATT_SPP_JUMLAHSPK+", "
                                    +IDBCConstant.ATT_SPP_KETERANGAN+", "
                                    +IDBCConstant.ATT_SPP_JUMLAH+") values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pstat.setLong(1, spp.getOutputkegiatanId());
            pstat.setLong(2, spp.getSatkerId());
            pstat.setLong(3, spp.getBendaharaid());
            pstat.setString(4, spp.getLokasi());
            pstat.setString(5, spp.getKewenangan());
            pstat.setString(6, spp.getJenisbelanja());
            pstat.setString(7, spp.getAtasnama());
            pstat.setString(8, spp.getAlamat());
            pstat.setString(9, spp.getKoderekening());
            pstat.setString(10, spp.getNospk());
            pstat.setDouble(11, spp.getJumlahspk());
            pstat.setString(12, spp.getKeterangan());
            pstat.setDouble(13, spp.getJumlah());
            pstat.execute();
            
            long sppid = getMaxIndex(IDBCConstant.ATT_SPP_SPPINDEX, IDBCConstant.TABLE_SPP, conn);
            
            spp.setSppid(sppid);
            return spp;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan SPP. \n"+e.toString());
        }finally{
            if(pstat!= null){
                pstat.close();
            }
        }
    }

    @Override
    public void updateSPP(long oldSPPId, SPP spp, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_SPP+" set "
                                    +IDBCConstant.ATT_SPP_OUTPUTKEGIATAN+"=?, "
                                    +IDBCConstant.ATT_SPP_SATKERID+"=?, "
                                    +IDBCConstant.ATT_SPP_BENDAHARAID+"=?, "
                                    +IDBCConstant.ATT_SPP_LOKASI+"=?, "
                                    +IDBCConstant.ATT_SPP_KEWENANGAN+"=?, "
                                    +IDBCConstant.ATT_SPP_JENISBELANJA+"=?, "
                                    +IDBCConstant.ATT_SPP_ATASNAMA+"=?, "
                                    +IDBCConstant.ATT_SPP_ALAMAT+"=?, "
                                    +IDBCConstant.ATT_SPP_KODEREKENING+"=?, "
                                    +IDBCConstant.ATT_SPP_NOSPK+"=?, "
                                    +IDBCConstant.ATT_SPP_JUMLAHSPK+"=?, "
                                    +IDBCConstant.ATT_SPP_KETERANGAN+"=?, "
                                    +IDBCConstant.ATT_SPP_JUMLAH+"=? where "+IDBCConstant.ATT_SPP_SPPINDEX+"=?");
            
            pstat.setLong(1, spp.getOutputkegiatanId());
            pstat.setLong(2, spp.getSatkerId());
            pstat.setLong(3, spp.getBendaharaid());
            pstat.setString(4, spp.getLokasi());
            pstat.setString(5, spp.getKewenangan());
            pstat.setString(6, spp.getJenisbelanja());
            pstat.setString(7, spp.getAtasnama());
            pstat.setString(8, spp.getAlamat());
            pstat.setString(9, spp.getKoderekening());
            pstat.setString(10, spp.getNospk());
            pstat.setDouble(11, spp.getJumlahspk());
            pstat.setString(12, spp.getKeterangan());
            pstat.setDouble(13, spp.getJumlah());
            pstat.setLong(14, oldSPPId);
            pstat.execute();
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan spp.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void deleteSPP(long sppid, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_SPP+" where "+IDBCConstant.ATT_SPP_SPPINDEX+"=?");
            pstat.setLong(1, sppid);
            pstat.execute();
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus spp.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public SPP getSPP(long sppid, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public SPP[] getAllSPP(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public SPP[] getAllSPPByUnitOrganisasi(long unitorganisasiid, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Bendahara createBendahara(Bendahara bendahara, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_BENDAHARA+" ("
                                        +IDBCConstant.ATT_BENDAHARA_SATKER+", "
                                        +IDBCConstant.ATT_BENDAHARA_JENISBENDAHARA+", "
                                        +IDBCConstant.ATT_BENDAHARA_NIP+", "
                                        +IDBCConstant.ATT_BENDAHARA_ALAMAT+", "
                                        +IDBCConstant.ATT_BENDAHARA_EMAIL+", "
                                        +IDBCConstant.ATT_BENDAHARA_KODEBANK+", "
                                        +IDBCConstant.ATT_BENDAHARA_REKENING+", "
                                        +IDBCConstant.ATT_BENDAHARA_SALDO+", "
                                        +IDBCConstant.ATT_BENDAHARA_NPWP+", "
                                        +IDBCConstant.ATT_BENDAHARA_TANGGAL+") values(?,?,?,?,?,?,?,?,?,?)");
            
            pstat.setLong(1, bendahara.getSatkerid());
            pstat.setString(2, bendahara.getJenisbendahara());
            pstat.setString(3, bendahara.getNip());
            pstat.setString(4, bendahara.getAlamat());
            pstat.setString(5, bendahara.getEmail());
            pstat.setString(6, bendahara.getKodebank());
            pstat.setString(7, bendahara.getRekening());
            pstat.setDouble(8, bendahara.getSaldo());
            pstat.setString(9, bendahara.getNpwp());
            pstat.setDate(10, new Date(bendahara.getTanggal().getDate()));
            
            pstat.execute();
            
            long bendaharaid = getMaxIndex(IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX, IDBCConstant.TABLE_BENDAHARA, conn);
            bendahara.setBendaharaindex(bendaharaid);
            
            return bendahara;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menambahkan bendahara. \n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void updateBendahara(long oldbendaharaid, Bendahara bendahara, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_BENDAHARA+" set "
                                        +IDBCConstant.ATT_BENDAHARA_SATKER+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_JENISBENDAHARA+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_NIP+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_ALAMAT+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_EMAIL+" = ?, "
                                        +IDBCConstant.ATT_BENDAHARA_KODEBANK+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_REKENING+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_SALDO+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_NPWP+"=?, "
                                        +IDBCConstant.ATT_BENDAHARA_TANGGAL+"=? where "
                                        +IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX+"=?");
            
            pstat.setLong(1, bendahara.getSatkerid());
            pstat.setString(2, bendahara.getJenisbendahara());
            pstat.setString(3, bendahara.getNip());
            pstat.setString(4, bendahara.getAlamat());
            pstat.setString(5, bendahara.getEmail());
            pstat.setString(6, bendahara.getKodebank());
            pstat.setString(7, bendahara.getRekening());
            pstat.setDouble(8, bendahara.getSaldo());
            pstat.setString(9, bendahara.getNpwp());
            pstat.setDate(10, new Date(bendahara.getTanggal().getDate()));
            pstat.setLong(11, oldbendaharaid);
            pstat.execute();
            
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam memutakhirkan bendahara. \n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void deleteBendahara(long bendaharaid, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_BENDAHARA+" where "+IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX+" =?");
            pstat.setLong(1, bendaharaid);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam menghapus bendahara.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public Bendahara getBendahara(long bendaharaId, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_BENDAHARA+" where "+IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX+"=?");
            while(rs.next()){
                Satker satker = getSatker(rs.getLong(IDBCConstant.ATT_BENDAHARA_SATKER), conn);
                return new Bendahara(rs.getLong(IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX), 
                        satker, rs.getString(IDBCConstant.ATT_BENDAHARA_JENISBENDAHARA), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NIP), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_ALAMAT), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_EMAIL), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_KODEBANK), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_REKENING), 
                                rs.getDouble(IDBCConstant.ATT_BENDAHARA_SALDO), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NPWP), new java.util.Date(rs.getDate(IDBCConstant.ATT_BENDAHARA_TANGGAL).getDate()) );
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal mengambil bendahara. \n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }

    @Override
    public Bendahara[] getAllBendahara(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Bendahara> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_BENDAHARA);
            while(rs.next()){
                Satker satker = getSatker(rs.getLong(IDBCConstant.ATT_BENDAHARA_SATKER), conn);
                vResult.addElement(new Bendahara(rs.getLong(IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX), 
                        satker, rs.getString(IDBCConstant.ATT_BENDAHARA_JENISBENDAHARA), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NIP), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_ALAMAT), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_EMAIL), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_KODEBANK), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_REKENING), 
                                rs.getDouble(IDBCConstant.ATT_BENDAHARA_SALDO), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NPWP), new java.util.Date(rs.getDate(IDBCConstant.ATT_BENDAHARA_TANGGAL).getDate())));
            }
            
            Bendahara[] results = new Bendahara[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list bendahara.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }

    @Override
    public Bendahara[] getAllBendaharaBySatker(long satkerid, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Bendahara> vResult = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_BENDAHARA+ "where "+IDBCConstant.ATT_BENDAHARA_SATKER+" = "+satkerid);
            while(rs.next()){
                Satker satker = getSatker(rs.getLong(IDBCConstant.ATT_BENDAHARA_SATKER), conn);
                vResult.addElement(new Bendahara(rs.getLong(IDBCConstant.ATT_BENDAHARA_BENDAHARAINDEX), 
                        satker, rs.getString(IDBCConstant.ATT_BENDAHARA_JENISBENDAHARA), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NIP), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_ALAMAT), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_EMAIL), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_KODEBANK), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_REKENING), 
                                rs.getDouble(IDBCConstant.ATT_BENDAHARA_SALDO), 
                                rs.getString(IDBCConstant.ATT_BENDAHARA_NPWP), new java.util.Date(rs.getDate(IDBCConstant.ATT_BENDAHARA_TANGGAL).getDate())));
            }
            
            Bendahara[] results = new Bendahara[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal dalam mengambil list bendahara.\n"+e.toString());
        }finally{
            if(rs != null){
                rs.close();
            }
            
            if(s != null){
                s.close();
            }
        }
    }
       
}
