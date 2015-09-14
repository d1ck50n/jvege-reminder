package com.jvege.util;

import com.jvege.model.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

/**
 *
 * @author Dickson
 */
public class DatabaseUtil {

    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());

    private static boolean checkDBFileExist() {
        File dbFile = new File(Constant.DB_FILE_TO);
        if (dbFile.exists()) {
            return true;
        }
        return false;
    }

    public static void initDatabase() {
        if (!checkDBFileExist()) {
            copyFileByClassLoaderPath(Constant.DB_FILE_SOURCE, Constant.DB_FILE_TO);
        }
    }

    public void initDatabaseWithScript() {
        File scriptFile = new File(this.getClass().getResource(Constant.SCRIPT_FILE_URL).getFile());
        if (scriptFile.exists()) {
            try {
                Connection conn = DriverManager.getConnection(Constant.JDBC_URL);
                Class.forName("org.h2.Driver");
                RunScript.execute(conn, new FileReader(new File(this.getClass().getResource(Constant.SCRIPT_FILE_URL).getFile())));
                conn.close();
                scriptFile.deleteOnExit();

            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Fail to initialize database, system exiting now.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void copyFileByClassLoaderPath(String fromFile, String dtFile) {

        try {
            File destFile = new File(dtFile);
            InputStream in = DatabaseUtil.class.getResourceAsStream(fromFile);
            OutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        } catch (Exception ex) {
            logger.debug("Failed to copy database file: " + ex.getMessage());
            System.exit(0);
        }
    }
}
