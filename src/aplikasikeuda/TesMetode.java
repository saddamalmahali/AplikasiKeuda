/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasikeuda;

import com.keuda.impl.service.KeudaSQL;
import com.keuda.services.IKeudaSQL;
import com.keuda.util.KoneksiAplikasi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author user
 * 
 */

public class TesMetode {
    public static void main(String[]args) throws IOException, SQLException, ClassNotFoundException{
        IKeudaSQL sql = new KeudaSQL();
        
//        SubKomponen subkom = new SubKomponen(1, "011", "Evaluasi Raperda tentang Perubahan APBD Provinsi TA 2014 dan "
//                + "Raperkada tentang Penjabaran Perubahan APBD TA 2014 serta Raperda tentang APBD Provinsi TA 2015 dan "
//                + "Raperkada tentang Penjabaran APBD TA 20");
        KoneksiAplikasi koneksi = new KoneksiAplikasi();
//        String url = "jdbc:postgresql://localhost:5432/migrasi";
        Connection conn = koneksi.getConnection();
        Connection conn2 = koneksi.getConnection2();
//        sql.migrasidatarkakl(conn2, conn);
        for(int i=2938; i<5679; i++){
            sql.deleteDetailRincian(i, conn);
        }
//        System.out.println(list);
    }
}
