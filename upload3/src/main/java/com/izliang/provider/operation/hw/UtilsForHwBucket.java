package com.izliang.provider.operation.hw;
/*
 *  @package com.zliang.demo.utils
 *  @author zliang
 *  @create 2018/10/6 23:30
 *  @description：
 *
 */


import com.alibaba.fastjson.JSONObject;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;

import java.util.Date;


public class UtilsForHwBucket {


    public static ObsClient obsClient;

    public static String bucketName = "";

    public UtilsForHwBucket(ObsClient obsClient, String bucketName) {

        UtilsForHwBucket.obsClient  = obsClient;
        UtilsForHwBucket.bucketName = bucketName;

    }

    public static void optionsBucket() throws ObsException
    {
        System.out.println("Options bucket\n");
        OptionsInfoRequest optionInfo = new OptionsInfoRequest();
        optionInfo.setOrigin("http://www.a.com");
        optionInfo.getRequestHeaders().add("Authorization");
        optionInfo.getRequestMethod().add("PUT");
        System.out.println(obsClient.optionsBucket(bucketName, optionInfo));
    }

    public static void getBucketMetadata()
            throws ObsException
    {
        System.out.println("Getting bucket metadata\n");
        BucketMetadataInfoRequest request = new BucketMetadataInfoRequest(bucketName);
        request.setOrigin("http://www.a.com");
        request.getRequestHeaders().add("Authorization");
        BucketMetadataInfoResult result = obsClient.getBucketMetadata(request);
        System.out.println("StorageClass:" + result.getBucketStorageClass());
        System.out.println("\tAllowedOrigins " + result.getAllowOrigin());
        System.out.println("\tAllowedMethods " + result.getAllowMethods());
        System.out.println("\tAllowedHeaders " + result.getAllowHeaders());
        System.out.println("\tExposeHeaders " + result.getExposeHeaders());
        System.out.println("\tMaxAgeSeconds " + result.getMaxAge() + "\n");

        System.out.println("Deleting bucket CORS\n");
        obsClient.deleteBucketCors(bucketName);
    }

    public static void doBucketTaggingOperation()
            throws ObsException
    {
        System.out.println("Setting bucket tagging\n");

        BucketTagInfo tagInfo = new BucketTagInfo();
        BucketTagInfo.TagSet tagSet = new BucketTagInfo.TagSet();
        tagSet.addTag("key1", "value1");
        tagSet.addTag("关键字", "测试值");
        tagInfo.setTagSet(tagSet);

        obsClient.setBucketTagging(bucketName, tagInfo);

        System.out.println("Getting bucket tagging\n");

        System.out.println(obsClient.getBucketTagging(bucketName));

        System.out.println("Deleting bucket tagging\n");
        obsClient.deleteBucketTagging(bucketName);
    }

    public static void doBucketVersioningOperation()
            throws ObsException
    {
        System.out.println("Getting bucket versioning config " + obsClient.getBucketVersioning(bucketName) + "\n");
        //Enable bucket versioning
        obsClient.setBucketVersioning(bucketName, new BucketVersioningConfiguration(VersioningStatusEnum.ENABLED));
        System.out.println("Current bucket versioning config " + obsClient.getBucketVersioning(bucketName) + "\n");
        //Suspend bucket versioning
        BucketVersioningConfiguration suspended = new BucketVersioningConfiguration(VersioningStatusEnum.SUSPENDED);
        obsClient.setBucketVersioning(bucketName, suspended);
        System.out.println("Current bucket versioning config " + obsClient.getBucketVersioning(bucketName) + "\n");
    }

    public static void doBucketQuotaOperation()
            throws ObsException
    {
        BucketQuota quota = new BucketQuota();
        //Set bucket quota to 1GB
        quota.setBucketQuota(1024 * 1024 * 1024l);
        obsClient.setBucketQuota(bucketName, quota);
        System.out.println("Getting bucket quota " + obsClient.getBucketQuota(bucketName) + "\n");
    }

    public static BucketStorageInfo getBucketStorageInfo()
            throws ObsException
    {
        BucketStorageInfo storageInfo = obsClient.getBucketStorageInfo(bucketName);
        System.out.println("Getting bucket storageInfo " + storageInfo + "\n");
        return storageInfo;
    }

    public static void doBucketAclOperation()
            throws ObsException
    {
        System.out.println("Setting bucket ACL to public-read \n");

        obsClient.setBucketAcl(bucketName, AccessControlList.REST_CANNED_PUBLIC_READ);

        System.out.println("Getting bucket ACL " + obsClient.getBucketAcl(bucketName) + "\n");

        System.out.println("Setting bucket ACL to public \n");

        //obsClient.setBucketAcl(bucketName, AccessControlList.REST_CANNED_public);

        System.out.println("Getting bucket ACL " + obsClient.getBucketAcl(bucketName) + "\n");
    }

    public static void doBucketCorsOperation()
            throws ObsException
    {
        BucketCors bucketCors = new BucketCors();
        BucketCorsRule rule = new BucketCorsRule();
        rule.getAllowedHeader().add("Authorization");
        rule.getAllowedOrigin().add("http://www.a.com");
        rule.getAllowedOrigin().add("http://www.b.com");
        rule.getExposeHeader().add("x-obs-test1");
        rule.getExposeHeader().add("x-obs-test2");
        rule.setMaxAgeSecond(100);
        rule.getAllowedMethod().add("HEAD");
        rule.getAllowedMethod().add("GET");
        rule.getAllowedMethod().add("PUT");
        bucketCors.getRules().add(rule);

        System.out.println("Setting bucket CORS\n");
        obsClient.setBucketCors(bucketName, bucketCors);

        System.out.println("Getting bucket CORS:" + obsClient.getBucketCors(bucketName) + "\n");

    }

    public static void doBucketLifecycleOperation()
            throws ObsException
    {
        final String ruleId0 = "delete obsoleted files";
        final String matchPrefix0 = "obsoleted/";
        final String ruleId1 = "delete temporary files";
        final String matchPrefix1 = "temporary/";
        final String ruleId2 = "delete temp files";
        final String matchPrefix2 = "temp/";

        LifecycleConfiguration lifecycleConfig = new LifecycleConfiguration();
        LifecycleConfiguration.Rule rule0 = lifecycleConfig.new Rule();
        rule0.setEnabled(true);
        rule0.setId(ruleId0);
        rule0.setPrefix(matchPrefix0);
        LifecycleConfiguration.Expiration expiration0 = lifecycleConfig.new Expiration();
        expiration0.setDays(10);

        rule0.setExpiration(expiration0);
        lifecycleConfig.addRule(rule0);

        LifecycleConfiguration.Rule rule1 = lifecycleConfig.new Rule();
        rule1.setEnabled(true);
        rule1.setId(ruleId1);
        rule1.setPrefix(matchPrefix1);
        LifecycleConfiguration.Expiration expiration1 = lifecycleConfig.new Expiration();
        try
        {
            expiration1.setDate(new Date());
        }
        catch (ObsException e)
        {
            e.printStackTrace();
        }
        rule1.setExpiration(expiration1);
        lifecycleConfig.addRule(rule1);

        LifecycleConfiguration.Rule rule2 = lifecycleConfig.new Rule();
        rule2.setEnabled(true);
        rule2.setId(ruleId2);
        rule2.setPrefix(matchPrefix2);
        LifecycleConfiguration.NoncurrentVersionExpiration noncurrentVersionExpiration = lifecycleConfig.new NoncurrentVersionExpiration();
        noncurrentVersionExpiration.setDays(10);
        rule2.setNoncurrentVersionExpiration(noncurrentVersionExpiration);
        lifecycleConfig.addRule(rule2);

        System.out.println("Setting bucket lifecycle\n");
        obsClient.setBucketLifecycleConfiguration(bucketName, lifecycleConfig);

        System.out.println("Getting bucket lifecycle:");
        LifecycleConfiguration result = obsClient.getBucketLifecycleConfiguration(bucketName);
        LifecycleConfiguration.Rule r0 = result.getRules().get(0);
        LifecycleConfiguration.Rule r1 = result.getRules().get(1);
        LifecycleConfiguration.Rule r2 = result.getRules().get(2);
        System.out.println("\tRule0: Id=" + r0.getId() + ", Prefix=" + r0.getPrefix() + ", Status=" + r0.getEnabled() + ", ExpirationDays="
                + r0.getExpiration().getDays());
        System.out.println("\tRule1: Id=" + r1.getId() + ", Prefix=" + r1.getPrefix() + ", Status=" + r1.getEnabled() + ", ExpirationTime="
                + r1.getExpiration().getDate());

        System.out.println("\tRule1: Id=" + r2.getId() + ", Prefix=" + r2.getPrefix() + ", Status=" + r2.getEnabled()
                + ", NocurrentExpirationDays=" + r2.getNoncurrentVersionExpiration().getDays());
        System.out.println();

        System.out.println("Deleting bucket lifecycle\n");
        obsClient.deleteBucketLifecycleConfiguration(bucketName);
    }

    public static void doBucketLoggingOperation()
            throws ObsException
    {
        final String targetBucket = bucketName;
        final String targetPrefix = "log-";
        BucketLoggingConfiguration configuration = new BucketLoggingConfiguration();
        configuration.setTargetBucketName(targetBucket);
        configuration.setLogfilePrefix(targetPrefix);
        configuration.setAgency("test");

        System.out.println("Setting bucket logging\n");
        obsClient.setBucketLoggingConfiguration(bucketName, configuration, true);

        System.out.println("Getting bucket logging:");
        BucketLoggingConfiguration result = obsClient.getBucketLoggingConfiguration(bucketName);
        System.out.println("\tTarget bucket=" + result.getTargetBucketName() + ", target prefix=" + result.getLogfilePrefix() + "\n");
        System.out.println();

        System.out.println("Deleting bucket logging\n");
        obsClient.setBucketLoggingConfiguration(targetBucket, new BucketLoggingConfiguration());
    }

    public static void doBucketWebsiteOperation()
            throws ObsException
    {
        WebsiteConfiguration websiteConfig = new WebsiteConfiguration();
        websiteConfig.setSuffix("index.html");
        websiteConfig.setKey("error.html");

        System.out.println("Setting bucket website\n");
        obsClient.setBucketWebsiteConfiguration(bucketName, websiteConfig);

        System.out.println("Getting bucket website:");
        WebsiteConfiguration result = obsClient.getBucketWebsiteConfiguration(bucketName);
        System.out.println("\tIndex document=" + result.getKey() + ", error document=" + result.getSuffix() + "\n");

        System.out.println("Deleting bucket website\n");
        obsClient.deleteBucketWebsiteConfiguration(bucketName);
    }

    public static void deleteBucket(String bucketName)
            throws ObsException
    {
        System.out.println("Deleting bucket " + bucketName + "\n");
        obsClient.deleteBucket(bucketName);
    }

    public static void getBucketLocation()
            throws ObsException
    {
        String location = obsClient.getBucketLocation(bucketName);
        System.out.println("Getting bucket location " + location + "\n");
    }

    public static void createBucket(String bucketName)
            throws ObsException
    {
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        obsClient.createBucket(obsBucket);
        System.out.println("Create bucket:" + bucketName + " successfully!\n");
    }

}
