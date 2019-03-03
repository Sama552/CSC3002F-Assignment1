package Compress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//TODO: verify locations of file and filenames and how to handle the compression  and decompression.

public class CompressionUtils
{
    public static void main(String[] args)
    {
        File newFile = new File(new File("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.txt"), "11mb.txt");
        System.out.println(newFile.getAbsolutePath());

//        System.out.println(
//                "Decompressed: " + CompressionUtils.decompress(
//                        "C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.zip",
//                        "C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\toBeCreated\\"));

        System.out.println(
                "Decompressed: " + CompressionUtils.decompress(
                        "C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.zip"));

//        CompressionUtils.compressFile(
//                "C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.txt",
//                "C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.zip");
//
//        System.out.println(
//                CompressionUtils.getFileSize("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.txt") / 1024);
//        System.out.println(
//                CompressionUtils.getFileSize("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.zip") / 1024);
    }

    // TODO: ADD THE SINGLE ARGUMENT VERSION OF THE BELOW METHOD.
    public static boolean compressFile(String fileName)
    {
        int pathEndIndex = fileName.lastIndexOf("/");
        if(pathEndIndex == -1)
        {
            pathEndIndex = fileName.lastIndexOf("\\");
        }

        return compressFile(fileName, fileName.substring(0, pathEndIndex));
    }

    /**
     * Compresses the given file to the specified zip file.
     *
     * @param fileName the absolute path of the file we want to compress.
     * @param zipToFile the absolute path of the destination zip file.
     */
    public static boolean compressFile(String fileName, String zipToFile){

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;

        try {
            fos = new FileOutputStream(zipToFile);
            zipOut = new ZipOutputStream(fos);

            File fileToZip = new File(fileName);
            fis = new FileInputStream(fileToZip);

            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;

            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            return true;
        }
        catch(Throwable t)
        {
            /* Package the source of error, add a custom message and display the original StackTrace.
             * Catches exceptions in reading the file to be compressed, reading/writing to the destination file and
             * adding entries to the zip archive.
             */
            new Exception(
                    "Unable to compress the file: " + fileName + "; Source of error attached in StackTrace.",
                    t).printStackTrace();

            return false;
        }
        finally
        {
            // house-keeping.
            try {
                if (zipOut != null) {
                    zipOut.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch(IOException e)
            {
                // Do nothing.
            }
        }
    }

    // TODO: ADD THE SINGLE ARGUMENT VERSION OF THE BELOW METHOD.
    public static boolean decompress(String fileName)
    {
        int pathEndIndex = fileName.lastIndexOf("/");
        if(pathEndIndex == -1)
        {
            pathEndIndex = fileName.lastIndexOf("\\");
        }

        return decompress(fileName, fileName.substring(0, pathEndIndex));
    }

    public static boolean decompress(File file, String destination)
    {
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try{
            // Get the destination directory, and create it if it doesn't exist.
            File destDir = new File(destination);
            if(!destDir.exists())
            {
                destDir.mkdir();
            }

            byte[] buffer = new byte[1024];
            zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                fos = new FileOutputStream(newFile);
                int len;

                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                zipEntry = zis.getNextEntry();
            }

            return true;
        }
        catch(Throwable t)
        {
            /* Package the source of error, add a custom message and display the original StackTrace.
             * Catches exceptions in reading the file to be compressed, reading/writing to the destination file and
             * adding entries to the zip archive.
             */
            new Exception(
                    "Unable to decompress the file: " + file.getName() + "; Source of error attached in StackTrace.",
                    t).printStackTrace();

            return false;
        }
        finally
        {
            // house-keeping.
            try {
                if (zipOut != null) {
                    zipOut.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (zis != null) {
                    zis.closeEntry();
                    zis.close();
                }
            }
            catch(IOException e)
            {
                // Do nothing.
            }
        }
    }

    public static boolean decompress(String fileName, String destination){

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try{
            // Get the destination directory, and create it if it doesn't exist.
            File destDir = new File(destination);
            if(!destDir.exists())
            {
                destDir.mkdir();
            }

            byte[] buffer = new byte[1024];
            zis = new ZipInputStream(new FileInputStream(fileName));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                fos = new FileOutputStream(newFile);
                int len;

                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                zipEntry = zis.getNextEntry();
            }

            return true;
        }
        catch(Throwable t)
        {
            /* Package the source of error, add a custom message and display the original StackTrace.
             * Catches exceptions in reading the file to be compressed, reading/writing to the destination file and
             * adding entries to the zip archive.
             */
            new Exception(
                    "Unable to decompress the file: " + fileName + "; Source of error attached in StackTrace.",
                    t).printStackTrace();

            return false;
        }
        finally
        {
            // house-keeping.
            try {
                if (zipOut != null) {
                    zipOut.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (zis != null) {
                    zis.closeEntry();
                    zis.close();
                }
            }
            catch(IOException e)
            {
                // Do nothing.
            }
        }
    }



    /**
     *
     * @param fileName the absolute path of the file to evaluate.
     * @return the fileSize in bytes if the file exists, else -1.
     */
    public static long getFileSize(String fileName){
        File myFile = new File(fileName);
        return myFile.exists() ? myFile.length() : -1;
    }
}
