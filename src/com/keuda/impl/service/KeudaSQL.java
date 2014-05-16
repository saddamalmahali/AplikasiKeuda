/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.impl.service;

import com.keuda.model.*;
import com.keuda.services.IDBCConstant;
import com.keuda.services.IKeudaSQL;
import com.keuda.view.AkunStructureForTree;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author user
 */
public class KeudaSQL implements IKeudaSQL {

    private int index;

    @Override
    public long getMaxIndex(String tableName, Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT MAX(" + IDBCConstant.ATT_AKUN_INDEX + ") as maxindex from " + tableName);
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

            long index = getMaxIndex(IDBCConstant.TABLE_AKUN, conn);
            akun.setAkunIndex(index);

            if (akun.getParent() != null) {
                stm = conn.createStatement();
                stm.executeUpdate("INSERT INTO " + IDBCConstant.TABLE_AKUN_STRUKTUR + " values("
                        + akun.getParent().getAkunIndex() + "," + index + ")");
            }

            return akun;
        } catch (Exception e) {
            System.out.println(akun.getKodeAkun());
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
                vResult.addElement(new ProgramDipa(
                        rs.getLong(IDBCConstant.ATT_PROGRAMDIPA_DIPA_INDEX),
                        rs.getInt(IDBCConstant.ATT_PROGRAMDIPA_TAHUN_ANGGARAN),
                        prg.getProgramIndex(), prg));
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
                    + " where " + IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX+"="+kegiatanDipaIndex);

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
                    + " (" + IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA+ ", "
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
    public void updateIndikatorKegiatan(IndikatorKegiatan inke,  Connection conn) throws SQLException {
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
                    +IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA+"=? and "
            +IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT+"=?");
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
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_INDIKATOR_KEGIATAN
                                +" where "+IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA+"="+kegiatanDipa+" and "
            +IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT+"="+nrindikator);
            

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA),conn);
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
                    +" order by "+IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA+", "+IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT);

            while (rs.next()) {
                
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA),conn);
                
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
                    + " set " +
                    IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT + "=? where "
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
            rs = s.executeQuery("select * from " + IDBCConstant.TABLE_OUTPUT_KEGIATAN+ " where "
                    + IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA + "=" + kegiatanDipa
                    + " and " +IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT + "=" + nroutput);

            while (rs.next()) {
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA),conn);
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
            +" order by "+IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA+", "+IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT);

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
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_KEGIATAN_DIPA
            +" set "+IDBCConstant.ATT_KEGIATANDIPA_DIPAINDEX+"=?, "
                    +IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN+"=? where "
            +IDBCConstant.ATT_KEGIATANDIPA_KEGIATAN_DIPA_INDEX+"=?");
            pstat.setLong(1, kedip.getDipaindex());
            pstat.setLong(2, kedip.getKegiatan());
            pstat.setLong(3, kedip.getKegiatandipaindex());
            pstat.execute();
            
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memutakhirkan Kegiatan Dipa. \n"+e.toString());
        }finally{
            if(pstat != null){
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
                            +IDBCConstant.TABLE_OUTPUT_KEGIATAN+" where "
                            +IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA+" = "+kegiatanDipa
                            +" order by "+IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT);
            while(rs.next()){
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_OUTPUTKEGIATAN_KEGIATANDIPA), conn);
                vResult.addElement(new OutputKegiatan(kedip,
                        rs.getInt(IDBCConstant.ATT_OUTPUTKEIGATAN_NOMOR_URUT),
                        rs.getString(IDBCConstant.ATT_OUTPUTKEGIATAN_OUTPUT)));
            }
            
            OutputKegiatan[] result = new OutputKegiatan[vResult.size()];
            vResult.copyInto(result);
            return result;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Output Kegiatan. \n"+e.toString());
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
    public IndikatorKegiatan[] getAllIndikatorKegiatanByKegiatan(long kegiatanDipa, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<IndikatorKegiatan> vResult = new Vector<>();
        
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_INDIKATOR_KEGIATAN
            +" where "+IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA+" = "
            +kegiatanDipa+" order by "+IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT);
            
            while(rs.next()){
                KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_INDIKATORKEGIATAN_KEGIATANDIPA), conn);
                vResult.addElement(new IndikatorKegiatan(kedip, 
                        rs.getInt(IDBCConstant.ATT_INDIKATORKEGIATAN_NOMOR_URUT), 
                        rs.getString(IDBCConstant.ATT_INDIKATORKEGIATAN_INDIKATOR)));
            }
            
            IndikatorKegiatan[] results = new IndikatorKegiatan[vResult.size()];
            vResult.copyInto(results);
            return results;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Indikator Kegiatan. \n"+e.toString());
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
    public Dipa createDipa(Dipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "
                                +IDBCConstant.TABLE_DIPA+" ("
                                +IDBCConstant.ATT_DIPA_DIPAINDEX+", "
                                +IDBCConstant.ATT_DIPA_THNANGGARAN+", "
                                +IDBCConstant.ATT_DIPA_NOMORDIPA+") values (?,?,?)");
            pstat.setLong(1, dipa.getDipaindex());
            pstat.setInt(2, dipa.getThnanggaran());
            pstat.setString(3, dipa.getNomordipa());
            
            pstat.execute();
            
            return dipa;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Dipa.\n "+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void updateDipa(long oldDipaIndex, Dipa dipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "
                        +IDBCConstant.TABLE_DIPA+" set "
                        +IDBCConstant.ATT_DIPA_THNANGGARAN+" = ?,"
                        +IDBCConstant.ATT_DIPA_NOMORDIPA+"=? where "
                        +IDBCConstant.ATT_DIPA_DIPAINDEX+"= ?");
            pstat.setInt(1, dipa.getThnanggaran());
            pstat.setString(2, dipa.getNomordipa());
            pstat.setLong(3, oldDipaIndex);
            
            pstat.execute();
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal memutakhirkan DIPA. \n"+e.toString());
        }finally{
            if(pstat != null)
                pstat.close();
        }
    }

    @Override
    public void deleteDipa(long dipaIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "
                            +IDBCConstant.TABLE_DIPA+" where "
                            +IDBCConstant.ATT_DIPA_DIPAINDEX+"=?");
            pstat.setLong(1, dipaIndex);
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus DIPA. \n"+e.toString());
        }finally{
            if(pstat!= null){
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
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DIPA
                            +" where "+IDBCConstant.ATT_DIPA_DIPAINDEX+"="+dipaindex);
            
            
            while(rs.next()){
                dipa = new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX), 
                        rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN), 
                        rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA));
                
            }
            
            return dipa;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil Data Dipa. \n"+e.toString());
        }finally{
            if(rs!= null){
                rs.close();
            }
            
            if(s!= null){
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
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DIPA
                                +" order by "+IDBCConstant.ATT_DIPA_THNANGGARAN);
            if(rs!=null){
                while (rs.next()){
                    vResult.addElement(new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX), 
                        rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN), 
                        rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA)));
                }
                
                Dipa[] results = new Dipa[vResult.size()];
                vResult.copyInto(results);
                return results;
            }else
                return null;
            
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Dipa. \n"+e.toString());
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
    public Dipa[] getAllDipaByTahunAnggaran(int tahunAnggaran, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Dipa> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_DIPA+" where "
                                +IDBCConstant.ATT_DIPA_THNANGGARAN+"="+tahunAnggaran+" order by"
                                +IDBCConstant.ATT_DIPA_DIPAINDEX);
            
            if(rs !=null){
                while (rs.next()){
                    vResults.addElement(new Dipa(rs.getLong(IDBCConstant.ATT_DIPA_DIPAINDEX), 
                        rs.getInt(IDBCConstant.ATT_DIPA_THNANGGARAN), 
                        rs.getString(IDBCConstant.ATT_DIPA_NOMORDIPA)));
                }
                Dipa[] results = new Dipa[vResults.size()];
                vResults.copyInto(results);
                return results;
            }else
                return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List DIPA BY Tahun Anggaran. \n"
                                    +e.toString());
        }
    }

    @Override
    public Rincian createRincian(Rincian rincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_RINCIAN+" ("
                                        +IDBCConstant.ATT_RINCIAN_RNCIANINDEX+", "
                                        +IDBCConstant.ATT_RINCIAN_DIPAINDEX+", "
                                        +IDBCConstant.ATT_RINCIAN_KEGIATAN+") values (?,?,?)");
            pstat.setLong(1, rincian.getRincianindex());
            pstat.setLong(2, rincian.getDipaindex());
            pstat.setLong(3, rincian.getKegiatan());
            
            pstat.execute();
            
            return rincian;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menambahkan Rincian.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
                        
            }
        }
    }

    @Override
    public void updateRincian(long oldrincianindex, Rincian rincian, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_RINCIAN
                                        +" set "+IDBCConstant.ATT_RINCIAN_DIPAINDEX+"=?, "
                                        +IDBCConstant.ATT_RINCIAN_KEGIATAN+"=? where "
                                        +IDBCConstant.ATT_RINCIAN_RNCIANINDEX+"=?");
            pstat.setLong(1, rincian.getDipaindex());
            pstat.setLong(2, rincian.getKegiatan());
            pstat.setLong(3, oldrincianindex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Memutakhirkan Rincian.\n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void deleteRincian(long rincianIndex, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_RINCIAN
                                        +" where "+IDBCConstant.ATT_RINCIAN_RNCIANINDEX+"=?");
            pstat.setLong(1, rincianIndex);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Menghapus Rincian.\n"+e.toString());
        }
    }

    @Override
    public Rincian getRincian(long rincianIndex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIAN
                                +" where "+IDBCConstant.ATT_RINCIAN_RNCIANINDEX+"="+rincianIndex);
            
            if(rs != null){
                Dipa dipa = null;
                KegiatanDipa kedip = null;
                Rincian rincian = null;
                while(rs.next()){
                    dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                    kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_KEGIATAN), conn);
                    rincian = new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), kedip, dipa);
                }
                
                return rincian;
            }else
                return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil Data Rincian.\n"+e.toString());
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
    public Rincian[] getAllRincian(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIAN
                                +" order by "+IDBCConstant.ATT_RINCIAN_DIPAINDEX);
            if(rs != null){
                
                while(rs.next()){
                    Dipa dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                    KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_KEGIATAN), conn);
                    vResults.addElement(new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), kedip, dipa));
                    
                }
                Rincian[] results = new Rincian[vResults.size()];
                vResults.copyInto(results);
                return results;
            }else
                return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Mengambil List Rincian. \n"+e.toString());
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
    public Rincian[] getAllRincianByDipa(long dipaindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<Rincian> vResults = new Vector<>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIAN
                                +" while "+IDBCConstant.ATT_RINCIAN_DIPAINDEX+"="+dipaindex);
            if(rs !=null){
                while(rs.next()){
                    Dipa dipa = getDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_DIPAINDEX), conn);
                    KegiatanDipa kedip = getKegiatanDipa(rs.getLong(IDBCConstant.ATT_RINCIAN_KEGIATAN), conn);
                    vResults.addElement(new Rincian(rs.getLong(IDBCConstant.ATT_RINCIAN_RNCIANINDEX), kedip, dipa));
                }
                
                Rincian[] results = new Rincian[vResults.size()];
                vResults.copyInto(results);
                return results;
            }else
                return null;
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian. \n"+e.toString());
        }
    }

    @Override
    public RincianDipa createRincianDipa(RincianDipa rdipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("insert into "+IDBCConstant.TABLE_RINCIANDIPA
                                        +" ("+IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_KODEREKENING+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_LOKASI+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_KPPN+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN+", "
                                        +IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA+") values(?,?,?,?,?,?,?)");
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
            throw new SQLException("SQLSAP : Gagal Dalam Menambahkan Rincian DIPA. \n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void updateRincianDipa(long oldkodeRekening, RincianDipa rDipa, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("update "+IDBCConstant.TABLE_RINCIANDIPA
                                    +" set "+IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING+"=?, "
                                    +IDBCConstant.ATT_RINCIANDIPA_LOKASI+"=?, "
                                    +IDBCConstant.ATT_RINCIANDIPA_KPPN+"=?, "
                                    +IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN+"=?, "
                                    +IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA+"=? where "
                                    +IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX+"=? and "
                                    +IDBCConstant.ATT_RINCIANDIPA_KODEREKENING+"=?");
            
            pstat.setString(1, rDipa.getNamarekening());
            pstat.setString(2, rDipa.getLokasi());
            pstat.setString(3, rDipa.getKppn());
            pstat.setString(4, rDipa.getCarapenarikan());
            pstat.setDouble(5, rDipa.getJumlahdana());
            pstat.setLong(6, oldkodeRekening);
            pstat.setString(7, rDipa.getKoderekening());
            
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Memuktahirkan Rincin DIPA. \n"+e.toString());
        }finally{
            if(pstat != null){
                pstat.close();
            }
        }
    }

    @Override
    public void deleteRincianDipa(long rincianIndex, String kodeRekening, Connection conn) throws SQLException {
        PreparedStatement pstat = null;
        try {
            pstat = conn.prepareStatement("delete from "+IDBCConstant.TABLE_RINCIANDIPA
                                    +" where "+IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX+"=? and "
                                    +IDBCConstant.ATT_RINCIANDIPA_KODEREKENING+"=?");
            pstat.setLong(1, rincianIndex);
            pstat.setString(2, kodeRekening);
            pstat.execute();
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Menghapus Rincian Dipa. \n"+e.toString());
        }finally{
            if(pstat != null){
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
            if(rs != null){
                RincianDipa rdipa = null;
                while(rs.next()){
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
                    rdipa = new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN), 
                    rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA));
                }
                
                return rdipa;
            }else
                return null;
            
        } catch (Exception e) {
            
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil Data Rincian DIPA. \n"+e.toString());
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
    public RincianDipa[] getAllRincianDipa(Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipa> vResult = new Vector<>();
                
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIANDIPA+" order by "+IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN);
            
            if(rs != null){
                while(rs.next()){
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
                    vResult.addElement(new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN), 
                    rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA)));
                }
                
                RincianDipa[] result = new RincianDipa[vResult.size()];
                vResult.copyInto(result);
                return result;
            }else
                return null;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian DIPA.\n"+e.toString());
        }
    }

    @Override
    public RincianDipa[] getAllRincianDipaByRincian(long rincianindex, Connection conn) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        Vector<RincianDipa> vResult = new Vector<>();
                
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from "+IDBCConstant.TABLE_RINCIANDIPA+" where "+IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX+"="+rincianindex+" order by "+IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN);
            
            if(rs != null){
                while(rs.next()){
                    Rincian rincian = getRincian(rs.getLong(IDBCConstant.ATT_RINCIANDIPA_RINCIANINDEX), conn);
                    vResult.addElement(new RincianDipa(rincian, rs.getString(IDBCConstant.ATT_RINCIANDIPA_KODEREKENING),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_NAMAREKENING), rs.getString(IDBCConstant.ATT_RINCIANDIPA_LOKASI),
                    rs.getString(IDBCConstant.ATT_RINCIANDIPA_KPPN), rs.getString(IDBCConstant.ATT_RINCIANDIPA_CARAPENARIKAN), 
                    rs.getDouble(IDBCConstant.ATT_RINCIANDIPA_JUMLAHDANA)));
                }
                
                RincianDipa[] result = new RincianDipa[vResult.size()];
                vResult.copyInto(result);
                return result;
            }else
                return null;
            
        } catch (Exception e) {
            throw new SQLException("SQLSAP : Gagal Dalam Mengambil List Rincian DIPA.\n"+e.toString());
        }
    }    
}
