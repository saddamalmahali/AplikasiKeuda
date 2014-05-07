/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasikeuda;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.impl.service.KeudaSQL;
import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.Program;
import com.keuda.services.IDBCConstant;
import com.keuda.services.IKeudaSQL;
import com.keuda.util.KoneksiAplikasi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 * 
 */

public class TesMetode {
    public static void main(String[]args) throws IOException, SQLException, ClassNotFoundException{
        IKeudaSQL sql = new KeudaSQL();
        KoneksiAplikasi koneksi = new KoneksiAplikasi();
        Connection conn = koneksi.getConnection();
        BusinessLogic logic = new BusinessLogic(conn);
        Program p = new Program("124", "Keuda");
        IndikatorKegiatan ike = new IndikatorKegiatan(5, 3, "bafbgsjdrg");
        try {
            sql.createIndikatorKegiatan(ike, conn);
        } catch (SQLException ex) {
            Logger.getLogger(TesMetode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println();
    }
}
