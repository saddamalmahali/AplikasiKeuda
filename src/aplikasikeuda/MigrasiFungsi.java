/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasikeuda;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.Fungsi;
import com.keuda.model.FungsiSPM;
import com.keuda.model.SubFungsi;
import com.keuda.model.SubFungsiSPM;
import com.keuda.services.IDBCConstant;
import com.keuda.util.KoneksiAplikasi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BENDAHARA
 */
public class MigrasiFungsi {
    static Fungsi[] listfungsi;
    static SubFungsi[] listsubfungsi;
    static FungsiSPM[] listfungsispm;
    static SubFungsiSPM[] listsubfungsispm;
    long sessionid = -1;

    public MigrasiFungsi() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, KeudaException {
        KoneksiAplikasi koneksi = null;
        
        koneksi = new KoneksiAplikasi();
        
        
        Connection conn1 = null;
        Connection conn2 =null;
        
        
        conn1 = koneksi.getConnection();
        conn2 = koneksi.getConnection2();
        
        listfungsispm = getFungsi(conn2);
        listsubfungsispm = getSubFungsiSPM(conn2);
        
        
        
        insertintodatabase(conn1);
    }
    
    
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, FileNotFoundException, KeudaException{
        new MigrasiFungsi();
        
    }
    
    public FungsiSPM[] getFungsi(Connection conn) throws SQLException{
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from t_fungsi");
        Vector<FungsiSPM> vResult = new Vector<>();
        while(rs.next()){
            vResult.addElement(new FungsiSPM(rs.getString("kdfungsi"), rs.getString("nmfungsi")));
            
        }
        FungsiSPM[] results = new FungsiSPM[vResult.size()];
        vResult.copyInto(results);
        return results;
    }
    
    public SubFungsiSPM[] getSubFungsiSPM(Connection conn) throws SQLException{
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from t_sfung");
        Vector<SubFungsiSPM> vResult = new Vector<>();
        while(rs.next()){
            vResult.addElement(new SubFungsiSPM(rs.getString("kdfungsi"), rs.getString("kdsfung"), rs.getString("nmsfung")));
            
        }
        SubFungsiSPM[] results = new SubFungsiSPM[vResult.size()];
        vResult.copyInto(results);
        
        return results;
    }
    
    public void insertintodatabase(Connection conn) throws SQLException, KeudaException{
        for(int i =0; i<listfungsispm.length; i++){
            BusinessLogic logic = new BusinessLogic(conn);
            Fungsi fungsi = new Fungsi(listfungsispm[i].getKdfungsi(), listfungsispm[i].getNmfungsi());
            logic.createFungsi(fungsi, sessionid, IDBCConstant.MODUL_CONFIGURATION);
        }
        
        for(int i =0; i<listsubfungsispm.length; i++){
            BusinessLogic logic = new BusinessLogic(conn);
            Fungsi fungsi = logic.getFungsiByKode(listsubfungsispm[i].getKdfungsi(), sessionid, IDBCConstant.MODUL_CONFIGURATION);
            SubFungsi subfungsi = new SubFungsi(fungsi, listsubfungsispm[i].getKdsfung(), listsubfungsispm[i].getNmsfung());
            
            logic.createSubFungsi(subfungsi, sessionid, IDBCConstant.MODUL_CONFIGURATION);
            
        }
    }
}
