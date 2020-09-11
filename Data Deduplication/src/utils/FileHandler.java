package utils;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class FileHandler {
    private FileCheckSumMD5 fileCheckSumMD5 = new FileCheckSumMD5();
    private static List<FileChunkInfoAdapter> chunkInfo;
    public void join(String outputPath, String outputFileName, String fileExtension, String[] pathArray) {
        long leninfile = 0, leng = 0;
        int count = 1, data = 0, chunkCount = 0;
        try {
            File filename = new File(CommonProperties.outputFilePath + outputFileName + "." + fileExtension);

            try (OutputStream outfile = new BufferedOutputStream(new FileOutputStream(filename))) {
                while (chunkCount < pathArray.length) {
                    filename = new File(pathArray[chunkCount]);
                    
                    if (filename.exists()) {
                        try (InputStream infile = new BufferedInputStream(new FileInputStream(filename))) {
                            data = infile.read();
                            while (data != -1) {
                                outfile.write(data);
                                data = infile.read();
                            }
                            leng++;
                        }
                        count++;
                        chunkCount++;
                    } else {
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "File Downloaded: " + CommonProperties.outputFilePath + outputFileName + "." + fileExtension);
            }
        } catch (IOException | HeadlessException e) {}
    }

    public List<FileChunkInfoAdapter> split(String FilePath, long splitlen) {
        chunkInfo = new ArrayList<>();
        long leninfile = 0, leng = 0;
        int count = 1, data;
        try {
            int dateHashCode = new Date().hashCode();
            new File(CommonProperties.tempFilePath + dateHashCode).mkdirs();
            File filename = new File(FilePath);
            String fileNameWithoutExtension = getFileNameWithoutExtension(filename);
            String fileExtension = getFileExtension(filename);
            InputStream infile = new BufferedInputStream(new FileInputStream(filename));
            data = infile.read();
            while (data != -1) {
                String tempFilePath = CommonProperties.tempFilePath + dateHashCode + "\\" + dateHashCode + count + "." + fileExtension;
                filename = new File(tempFilePath);
                try (OutputStream outfile = new BufferedOutputStream(new FileOutputStream(filename))) {
                    while (data != -1 && leng < splitlen) {
                        outfile.write(data);
                        leng++;
                        data = infile.read();
                    }
                    leninfile += leng;
                    leng = 0;
                }
                count++;
                
                String tempFileChecksum = fileCheckSumMD5.checksum(tempFilePath);
                chunkInfo.add(new FileChunkInfoAdapter(tempFileChecksum, tempFilePath, dateHashCode, fileNameWithoutExtension, fileExtension, filename.length()));   
            }
            return chunkInfo;
        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    private String getFileNameWithoutExtension(File file) {
        String fileName = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            fileName = "";
        }
 
        return fileName;
    }
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
