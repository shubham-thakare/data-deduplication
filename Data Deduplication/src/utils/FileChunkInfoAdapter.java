package utils;

public class FileChunkInfoAdapter {
    String checkSum;
    String filePath;
    int fileName;
    String fileOriginalName;
    String fileExtension;
    long chunkSize;
    public FileChunkInfoAdapter(String checkSum, String filePath, int fileName, String fileOriginalName, String fileExtension, long chunkSize){
        this.checkSum = checkSum;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileOriginalName = fileOriginalName;
        this.fileExtension = fileExtension;
        this.chunkSize = chunkSize;
    }
}
