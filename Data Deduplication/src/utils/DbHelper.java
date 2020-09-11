package utils;

import static data.deduplication.AdminHome.UploadFileStatus;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHelper {
    private String[] chunkPaths;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement s=null;
    public long insertChunksRecordInDB(String checkSum, String filePath, String fileName, String fileOriginalName, String fileExtension, long chunkSize)
    {
        try
        {
            con=DBConnector.getConnection();
            String ifAvailable="select chunk_path from chunks_information where chunk_hash='"+ checkSum +"'";
            String sql="insert into chunks_information values (?,?,?,?,?,?)";

            s=con.prepareStatement(ifAvailable);
            rs=s.executeQuery();
            
            long sizeOccupiedOnStorage = 0;
            
            if(rs.next())
            {
                s=con.prepareStatement(sql);

                s.setInt(1, 0);
                s.setString(2, checkSum);
                s.setString(3, rs.getString(1));
                s.setString(4, fileName);
                s.setString(5, fileOriginalName);
                s.setString(6, fileExtension);
                s.executeUpdate();
                s.close();
                
                File presentChunk = new File(filePath);
                presentChunk.delete();
                
                UploadFileStatus.append("Duplicate chunk - " + checkSum + "\n");
                UploadFileStatus.update(UploadFileStatus.getGraphics());
            }
            else
            {
                s=con.prepareStatement(sql);

                s.setInt(1, 0);
                s.setString(2, checkSum);
                s.setString(3, filePath);
                s.setString(4, fileName);
                s.setString(5, fileOriginalName);
                s.setString(6, fileExtension);
                s.executeUpdate();
                s.close();
                
                sizeOccupiedOnStorage = chunkSize;
            }
            return sizeOccupiedOnStorage;
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
    
    public String[] selectChunksPath(String directoryName)
    {
        try
        {
            con=DBConnector.getConnection();
            String ifAvailable="select chunk_path from chunks_information where file_directory='"+ directoryName +"'";

            s=con.prepareStatement(ifAvailable);
            rs=s.executeQuery();
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            
            int count = 0;
            chunkPaths = new String[rows];
            
            while(rs.next())
            {
                chunkPaths[count] = rs.getString(1);
                count++;
            }
            
            return chunkPaths;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    
    public String getFileExtension(String directoryName)
    {
        try
        {
            con=DBConnector.getConnection();
            String ifAvailable="select file_extension from chunks_information where file_directory='"+ directoryName +"'";

            s=con.prepareStatement(ifAvailable);
            rs=s.executeQuery();
            String fileExt = null;
            
            if(rs.next())
            {
                fileExt = rs.getString(1);
            }
            
            return fileExt;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    
    public int getRecordCount(ResultSet resultSet){
        int size = 0;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }
        return size;
    }
    
    public Object[][] getAllFilesTableData(){
        Object[][] tableData = null;
        try
        {
            con=DBConnector.getConnection();            
            String sql="select distinct file_original_name, file_directory, file_extension from chunks_information";
            s=con.prepareStatement(sql);
            rs=s.executeQuery();
            
            tableData = new Object[getRecordCount(rs)][3];
            int i = 0;
            while(rs.next())
            {
                tableData[i][0] = i+1;
                tableData[i][1] = rs.getString("file_original_name") + "." + rs.getString("file_extension");
                tableData[i][2] = rs.getString("file_directory");   
                i++;
            }
        }catch(Exception ex){return null;}
        return tableData;
    }
    
    public Object[][] getSelectedFileTableData(String fileDirectory){
        Object[][] tableData = null;
        try
        {
            con=DBConnector.getConnection();            
            String sql="select * from chunks_information where file_directory = '" + fileDirectory + "'";
            s=con.prepareStatement(sql);
            rs=s.executeQuery();
            
            tableData = new Object[getRecordCount(rs)][6];
            int i = 0;
            while(rs.next())
            {
                tableData[i][0] = i+1;
                tableData[i][1] = rs.getString("chunk_hash");
                tableData[i][2] = rs.getString("chunk_path");   
                tableData[i][3] = rs.getString("file_directory");   
                tableData[i][4] = rs.getString("file_original_name");   
                tableData[i][5] = rs.getString("file_extension");   
                i++;
            }
        }catch(Exception ex){return null;}
        return tableData;
    }
}
