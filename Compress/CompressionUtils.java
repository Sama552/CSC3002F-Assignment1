package Compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CompressionUtils
{
    public void compress(String fileName)
    {
    }

    public void decompress(String fileName)
    {
    }

    /**
     *
     * @param fileName the absolute path of the file to evaluate.
     * @return the fileSize in bytes if the file exists, else -1.
     */
    public long getFileSize(String fileName)
    {
        File myFile = new File(fileName);
        return myFile.exists() ? myFile.length() : -1;
    }
}
