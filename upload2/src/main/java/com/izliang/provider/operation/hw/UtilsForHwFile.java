package com.izliang.provider.operation.hw;
/*
 *  @package com.zliang.demo.utils
 *  @author zliang
 *  @create 2018/10/7 0:28
 *  @description：
 *
 */

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


public class UtilsForHwFile {


    public static ObsClient obsClient;

    public static String bucketName = "";

    public static String objectKey = "";

    //public static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static List<PartEtag> partETags;

    public UtilsForHwFile(ObsClient obsClient, String bucketName) {

        UtilsForHwFile.obsClient  = obsClient;
        UtilsForHwFile.bucketName = bucketName;

    }

    public UtilsForHwFile(ObsClient obsClient, String bucketName, List<PartEtag> partETags, String objectKey){

        UtilsForHwFile.obsClient  = obsClient;
        UtilsForHwFile.bucketName = bucketName;
        UtilsForHwFile.partETags  = partETags;
        UtilsForHwFile.objectKey  = objectKey;

    }

    
    public static class PartUploader implements Runnable
    {

        public File sampleFile;

        public long offset;

        public long partSize;

        public int partNumber;

        public String uploadId;

        public PartUploader(File sampleFile, long offset, long partSize, int partNumber, String uploadId)
        {
            this.sampleFile = sampleFile;
            this.offset = offset;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
        }

        @Override
        public void run()
        {
            try
            {
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setObjectKey(objectKey);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setFile(this.sampleFile);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setOffset(this.offset);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = obsClient.uploadPart(uploadPartRequest);
                System.out.println("Part#" + this.partNumber + " done\n");
                partETags.add(new PartEtag(uploadPartResult.getEtag(), uploadPartResult.getPartNumber()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String claimUploadId()
            throws ObsException
    {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResult result = obsClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    public static File createSampleFile()
            throws IOException
    {
        File file = File.createTempFile("obs-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < 10000; i++)
        {
            writer.write(UUID.randomUUID() + "\n");
            writer.write(UUID.randomUUID() + "\n");
        }
        writer.flush();
        writer.close();

        return file;
    }

    public static void completeMultipartUpload(String uploadId)
            throws ObsException
    {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartEtag>()
        {

            @Override
            public int compare(PartEtag o1, PartEtag o2)
            {
                return o1.getPartNumber() - o2.getPartNumber();
            }
        });

        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partETags);
        obsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    public static void listAllParts(String uploadId)
            throws ObsException
    {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);
        ListPartsResult partListing = obsClient.listParts(listPartsRequest);

        for (Multipart part : partListing.getMultipartList())
        {

            System.out.println("\tPart#" + part.getPartNumber() + ", ETag=" + part.getEtag());
        }
        System.out.println();
    }


}
