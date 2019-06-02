package com.izliang.provider.controller;


import com.alibaba.fastjson.JSONObject;

import com.izliang.provider.model.Developer;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.model.Settings;
import com.izliang.provider.model.UploadInfo;
import com.izliang.provider.operation.hw.UtilsForHwFile;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.repo.OBSInfoRepository;
import com.izliang.provider.repo.SettingsRepository;
import com.izliang.provider.repo.UploadInfoRepository;
import com.izliang.provider.utils.AES256;
import com.izliang.provider.utils.AesEncryptUtil;
import com.izliang.provider.utils.Result;
import com.izliang.provider.utils.Utils;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PartEtag;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.izliang.provider.operation.hw.UtilsForHwFile.completeMultipartUpload;


@RestController
@RequestMapping(value = "/upload")
@Api(value = "文件上传接口",tags = "upload",description = "文件上传接口，包含：单文件上传、多文件上传、公有云上传、私有云上传")
public class UploadController {

    @Autowired
    private UploadInfoRepository uploadInfoRepository;

    @Autowired
    private DeveloperRepository developerRepository;


    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @RequestMapping(value = "/common/{code}",method = RequestMethod.POST)
    @ApiOperation(
            value = "获取开发者上传链接",notes = "获取开发者上传链接",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个开发者的上传链接"),
            @ApiResponse(code = 404,message = "没有用户数据"),
            @ApiResponse(code = 405,message = "没有生成的上传链接"),
            @ApiResponse(code = 409,message = "保存出错")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "code",value = "上传密钥",required = true),
    })
    public JSONObject getUploadLink(
            @ApiParam("code")@PathVariable("code") String code,
            @RequestPart("file") MultipartFile file,
            @RequestParam("ip") String ip
    ) {

        //用户通用上传
        //code为通用用户识别码
        /*
        * 检测用户是否存在，检测文件大小
        * **/

        System.out.println("code = [" + code + "], file = [" + file + "], ip = [" + ip + "]");

        if (!file.isEmpty()) {
            //根据code换取数据
            Developer developer = developerRepository.findDeveloperByPublicUploadLink(code);

            if (developer != null) {

                List<ObsInfo> obsInfos = obsInfoRepository.findObsInfosByUserIdAndAvailableIsTrue(developer.getId());

                if (obsInfos.size() > 0) {

                    //随机命中obs服务器，进行上传
                    int random = (int) Math.floor(Math.random() * obsInfos.size());

                    //现在挑选出了可用的obs服务器，上传本服务器
                    String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/upload/" + code;
                    System.out.println(path);

                    File temp = new File(path);

                    long d = new Date().getTime();

                    if (!temp.exists()) temp.mkdirs();

                    try {
                        String pathName = path+d+"";
                        String type = Utils.ext(file.getOriginalFilename()) != null ?"."+Utils.ext(file.getOriginalFilename()) : "";
                        String newFileName = d+type;
                        String pStr = pathName+type;
                        BufferedOutputStream out = new BufferedOutputStream(
                                new FileOutputStream(new File(pStr)));
                        out.write(file.getBytes());
                        out.flush();
                        out.close();
                        ObsInfo currentObs = obsInfos.get(random);

                        String sk = currentObs.getSecretAccessKey();
                        String ak = currentObs.getAccessKeyId();
                        String endPoint = currentObs.getEndpoint();
                        String bucketName = currentObs.getBucketName();

                        System.out.println(currentObs.toString());
                        //现在获取文件md5值，获取后进行判断，如果存在该md5的文件，则直接存储响应的
                        String md5 = Utils.getMD5(new File(pStr));
                        if(md5 != null){
                            System.out.println(md5);

                            List<UploadInfo> uploadInfos = uploadInfoRepository.findUploadInfoByMd5(md5);

                            if(uploadInfos.size()>0){
                                UploadInfo uploadInfox = uploadInfos.get(0);
                                //重复文件
                                String content = d+""+file.getSize();
                                String password = code;
                                System.out.println("明文：" + content);
                                System.out.println("key：" + password);

                                byte[] encryptResult = AES256.encrypt(content, password);
                                //现在保存数据
                                String secret = AES256.parseByte2HexStr(encryptResult);
                                //上传完毕
                                UploadInfo uploadInfo = new UploadInfo();
                                uploadInfo.setObsInfoId(currentObs.getId());
                                uploadInfo.setOriginalName(file.getOriginalFilename());
                                uploadInfo.setReal_name(newFileName);
                                uploadInfo.setRealUrl(uploadInfox.getRealUrl());
                                uploadInfo.setUploadIp(ip);
                                uploadInfo.setSize(file.getSize());
                                uploadInfo.setDev_id(developer.getId());
                                uploadInfo.setUploadTime(Utils.getNowDate());
                                //真实的url+code+文件大小进行加密，生成访问密钥
                                uploadInfo.setUploadSecret(secret);
                                uploadInfo.setEnable(true);
                                Settings settings = settingsRepository.getOne(1);
                                uploadInfo.setDownloadUrl(settings.getDownLoadUrl()+"/"+secret);
                                uploadInfo.setAk(ak);
                                uploadInfo.setBucketName(bucketName);
                                uploadInfo.setEndpoint(endPoint);
                                uploadInfo.setSk(sk);
                                uploadInfo.setMd5(md5);

                                UploadInfo uploadInfo1 = uploadInfoRepository.save(uploadInfo);
                                System.out.println(uploadInfo.toString());

                                if(uploadInfo1 != null) {

                                    //重复上传不需要记录上传的流量
                                    //现在更新obs的信息
                                    developer.setUseSize(developer.getUseSize()+file.getSize());
                                    developer.setTodaySize(developer.getTodaySize()+file.getSize());
                                    developerRepository.save(developer);

//                                    currentObs.setPutNum(currentObs.getPutNum() + 1);
//                                    currentObs.setUseSize(currentObs.getUseSize() + file.getSize());
//                                    currentObs.setRemainderSize(currentObs.getSharpingSize() - currentObs.getUseSize());
//
//                                    ObsInfo obsInfo = obsInfoRepository.save(currentObs);
//                                    if (obsInfo != null) {
                                        return Result.Result(200, "", settings.getDownLoadUrl() + "/" + secret);
//                                    } else {
//                                        return Result.Result(409, "保存出错", "");
//                                    }
                                }else{
                                    return Result.Result(403, "保存出错", "");
                                }


                            }else {

                                //上传完毕
                                ObsConfiguration config = new ObsConfiguration();
                                config.setSocketTimeout(30000);
                                config.setConnectionTimeout(10000);
                                config.setEndPoint(endPoint);

                                String url = "";

                                //非重复文件
                                ObsClient obsClient = null;

                                try
                                {
                                    /*
                                     * Constructs a obs client instance with your account for accessing OBS
                                     */
                                    obsClient = new ObsClient(ak, sk, config);

                                    ExecutorService executorService = Executors.newFixedThreadPool(5);

                                    List<PartEtag> partETags = Collections.synchronizedList(new ArrayList<PartEtag>());

                                    /*
                                     * Create bucket
                                     */
                                    //System.out.println("Create a new bucket for demo\n");

                                    //path+d+file.getOriginalFilename().split(".")[1])
                                    UtilsForHwFile hw = new UtilsForHwFile(obsClient,bucketName,partETags,newFileName);

                                    //obsClient.createBucket(bucketName);

                                    /*
                                     * Claim a upload id firstly
                                     */
                                    String uploadId = hw.claimUploadId();
                                    System.out.println("Claiming a new upload id " + uploadId + "\n");

                                    long partSize = 5 * 1024 * 1024l;// 5MB
                                    //创建随机的示例文件，然后上传

                                    File sampleFile;

                                    try{
                                        sampleFile =  new File(pStr);
                                        long fileLength = sampleFile.length();

                                        long partCount = fileLength % partSize == 0 ? fileLength / partSize : fileLength / partSize + 1;

                                        if (partCount > 10000)
                                        {
                                            throw new RuntimeException("Total parts count should not exceed 10000");
                                        }
                                        else
                                        {
                                            System.out.println("Total parts count " + partCount + "\n");
                                        }

                                        /*
                                         * Upload multiparts to your bucket
                                         */
                                        System.out.println("Begin to upload multiparts to OBS from a file\n");
                                        for (int i = 0; i < partCount; i++)
                                        {
                                            long offset = i * partSize;
                                            long currPartSize = (i + 1 == partCount) ? fileLength - offset : partSize;
                                            executorService.execute(new UtilsForHwFile.PartUploader(sampleFile, offset, currPartSize, i + 1, uploadId));
                                        }

                                        /*
                                         * Waiting for all parts finished
                                         */
                                        executorService.shutdown();
                                        while (!executorService.isTerminated())
                                        {
                                            try
                                            {
                                                executorService.awaitTermination(5, TimeUnit.SECONDS);
                                            }
                                            catch (InterruptedException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }

                                        /*
                                         * Verify whether all parts are finished
                                         */
                                        if (partETags.size() != partCount)
                                        {
                                            throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
                                        }
                                        else
                                        {
                                            System.out.println("Succeed to complete multiparts into an object named " + hw.objectKey + "\n");
                                            System.out.println("upload URL : "+endPoint+"/tes/"+hw.objectKey);
                                            url = "https://"+endPoint+"/"+bucketName+"/"+hw.objectKey;
                                        }

                                        /*
                                         * View all parts uploaded recently
                                         */
                                        hw.listAllParts(uploadId);

                                        /*
                                         * Complete to upload multiparts
                                         */
                                        completeMultipartUpload(uploadId);

                                        //密钥组成规则
                                /*
                                上传日期+文件大小作为明文
                                 */
                                        String content = d+""+file.getSize();
                                        String password = code;
                                        System.out.println("明文：" + content);
                                        System.out.println("key：" + password);

                                        byte[] encryptResult = AES256.encrypt(content, password);
                                        //现在保存数据
                                        String secret = AES256.parseByte2HexStr(encryptResult);
                                        //上传完毕
                                        UploadInfo uploadInfo = new UploadInfo();
                                        uploadInfo.setObsInfoId(currentObs.getId());
                                        uploadInfo.setOriginalName(file.getOriginalFilename());
                                        uploadInfo.setReal_name(newFileName);
                                        uploadInfo.setRealUrl(url);
                                        uploadInfo.setUploadIp(ip);
                                        uploadInfo.setSize(file.getSize());
                                        uploadInfo.setDev_id(developer.getId());
                                        uploadInfo.setUploadTime(Utils.getNowDate());
                                        //真实的url+code+文件大小进行加密，生成访问密钥
                                        uploadInfo.setUploadSecret(secret);
                                        uploadInfo.setEnable(true);
                                        Settings settings = settingsRepository.getOne(1);
                                        uploadInfo.setDownloadUrl(settings.getDownLoadUrl()+"/"+secret);
                                        uploadInfo.setAk(ak);
                                        uploadInfo.setBucketName(bucketName);
                                        uploadInfo.setEndpoint(endPoint);
                                        uploadInfo.setSk(sk);
                                        uploadInfo.setMd5(md5);

                                        UploadInfo uploadInfo1 = uploadInfoRepository.save(uploadInfo);
                                        System.out.println(uploadInfo.toString());
                                        if(uploadInfo1 != null){

                                            //现在更新obs的信息
                                            currentObs.setPutNum(currentObs.getPutNum()+1);
                                            currentObs.setUseSize(currentObs.getUseSize()+file.getSize());
                                            currentObs.setRemainderSize(currentObs.getSharpingSize()-currentObs.getUseSize());

                                            ObsInfo obsInfo = obsInfoRepository.save(currentObs);
                                            if(obsInfo != null){

                                                developer.setUseSize(developer.getUseSize()+file.getSize());
                                                developer.setTodaySize(developer.getTodaySize()+file.getSize());
                                                developerRepository.save(developer);

                                                return Result.Result(200, "", settings.getDownLoadUrl()+"/"+secret);
                                            }else{
                                                return Result.Result(409, "保存出错", "");
                                            }
                                        }else{
                                            return Result.Result(403, "保存出错", "");
                                        }
                                    }catch (Exception e){
                                        return Result.Result(406, "分段上传出错", "");
                                    }
                                }
                                catch (ObsException e)
                                {
                                    System.out.println("Response Code: " + e.getResponseCode());
                                    System.out.println("Error Message: " + e.getErrorMessage());
                                    System.out.println("Error Code:       " + e.getErrorCode());
                                    System.out.println("Request ID:      " + e.getErrorRequestId());
                                    System.out.println("Host ID:           " + e.getErrorHostId());
                                    return Result.Result(200, "", "");
                                }
                            }

                        }else{
                            return Result.Result(408,"获取文件特征值错误","");
                        }
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return Result.Result(402,"上传失败"+e.getMessage(),"");
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Result.Result(401,"上传失败"+e.getMessage(),"");
                    }
                } else {
                    return Result.Result(405, "该用户没有可用的obs", "");
                }

            } else {
                return Result.Result(404, "没有该用户", "");
            }
        }else {
            return Result.Result(400,"上传失败,因为文件是空的","");
        }

    }

    @RequestMapping(value = "/private/{code}",method = RequestMethod.POST)
    @ApiOperation(
            value = "获取开发者上传链接",notes = "获取开发者上传链接",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个开发者的上传链接"),
            @ApiResponse(code = 404,message = "没有用户数据"),
            @ApiResponse(code = 405,message = "没有生成的上传链接"),
            @ApiResponse(code = 409,message = "保存出错")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "code",value = "上传链接",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "secret",value = "上传密钥",required = true),
    })
    public JSONObject privateUpload(
            @ApiParam("secret")@RequestParam("secret") String secret,
            @ApiParam("code")@PathVariable("code") String code,
            @RequestPart("file") MultipartFile file,
            @RequestParam("ip") String ip
    )throws Exception {

        System.out.println("secret = [" + secret + "], code = [" + code + "], file = [" + file + "], ip = [" + ip + "]");

        //用户通用上传
        //code为通用用户识别码
        /*
         * 检测用户是否存在，检测文件大小
         * **/

        secret = secret.replace("%2b","+");
//        secret = secret.replace("/","%3b");
//        secret = secret.replace("&","%4b");

        System.out.println("替换后"+secret);
        if (!file.isEmpty()) {
            //根据code换取数据
            //Developer developer = developerRepository.findDeveloperByPublicUploadLink(code);

            //重新进行逻辑

            //code为用户唯一的私有上传密钥

            Developer developer = developerRepository.findFirstByPrivateUpladLink(code);

            if(developer == null){
                return Result.Result(405,"您没有权限","");
        }

            String developerSecret = developer.getPrivateSecret();

            String jiemi = AesEncryptUtil.desEncrypt(secret,developerSecret.substring(0,16), AesEncryptUtil.IV).trim();

            //判断jiemie时间是否和当前时间有冲突

            long now =new Date().getTime();
            long secretTime = 0;
            System.out.println("解密后的密钥"+jiemi);
            try {
                secretTime = Long.parseLong(jiemi);
            }catch (Exception e){
                return Result.Result(405,"请检查您的密钥格式","");
            }
            System.out.println("解密后的时间戳"+secretTime);
            //时间差值小于10秒
            if(Math.abs(now - secretTime)>1000*10000){
                return Result.Result(405,"请检查您的密钥","");
            }

            if (true) {

                List<ObsInfo> obsInfos = obsInfoRepository.findObsInfosByUserIdAndAvailableIsTrue(developer.getId());

                if (obsInfos.size() > 0) {

                    //随机命中obs服务器，进行上传
                    int random = (int) Math.floor(Math.random() * obsInfos.size());

                    //现在挑选出了可用的obs服务器，上传本服务器
                    String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/upload/" + code;
                    System.out.println(path);

                    File temp = new File(path);

                    long d = new Date().getTime();

                    if (!temp.exists()) temp.mkdirs();

                    try {
                        String pathName = path+d+"";
                        String type = Utils.ext(file.getOriginalFilename()) != null ?"."+Utils.ext(file.getOriginalFilename()) : "";
                        String newFileName = d+type;
                        String pStr = pathName+type;
                        BufferedOutputStream out = new BufferedOutputStream(
                                new FileOutputStream(new File(pStr)));
                        out.write(file.getBytes());
                        out.flush();
                        out.close();
                        ObsInfo currentObs = obsInfos.get(random);

                        String sk = currentObs.getSecretAccessKey();
                        String ak = currentObs.getAccessKeyId();
                        String endPoint = currentObs.getEndpoint();
                        String bucketName = currentObs.getBucketName();

                        System.out.println(currentObs.toString());
                        //现在获取文件md5值，获取后进行判断，如果存在该md5的文件，则直接存储响应的
                        String md5 = Utils.getMD5(new File(pStr));
                        if(md5 != null){
                            System.out.println(md5);

                            List<UploadInfo> uploadInfos = uploadInfoRepository.findUploadInfoByMd5(md5);

                            if(uploadInfos.size()>0){

                                developer.setUseSize(developer.getUseSize()+file.getSize());
                                developer.setTodaySize(developer.getTodaySize()+file.getSize());
                                developerRepository.save(developer);

                                UploadInfo uploadInfox = uploadInfos.get(0);
                                //重复文件
                                String content = d+""+file.getSize();
                                String password = code;
                                System.out.println("明文：" + content);
                                System.out.println("key：" + password);

                                byte[] encryptResult = AES256.encrypt(content, password);
                                //现在保存数据
                                secret = AES256.parseByte2HexStr(encryptResult);
                                //上传完毕
                                UploadInfo uploadInfo = new UploadInfo();
                                uploadInfo.setObsInfoId(currentObs.getId());
                                uploadInfo.setOriginalName(file.getOriginalFilename());
                                uploadInfo.setReal_name(newFileName);
                                uploadInfo.setRealUrl(uploadInfox.getRealUrl());
                                uploadInfo.setUploadIp(ip);
                                uploadInfo.setSize(file.getSize());
                                uploadInfo.setUploadTime(Utils.getNowDate());
                                //真实的url+code+文件大小进行加密，生成访问密钥
                                uploadInfo.setUploadSecret(secret);
                                uploadInfo.setDev_id(developer.getId());
                                uploadInfo.setEnable(true);
                                uploadInfo.setType(true);
                                Settings settings = settingsRepository.getOne(1);
                                uploadInfo.setDownloadUrl(settings.getPrivateDownLoadUrl()+"/"+secret);
                                uploadInfo.setAk(ak);
                                uploadInfo.setBucketName(bucketName);
                                uploadInfo.setEndpoint(endPoint);
                                uploadInfo.setSk(sk);
                                uploadInfo.setMd5(md5);

                                UploadInfo uploadInfo1 = uploadInfoRepository.save(uploadInfo);
                                System.out.println(uploadInfo.toString());

                                if(uploadInfo1 != null) {

                                    //重复上传不需要记录上传的流量
                                    //现在更新obs的信息
//                                    currentObs.setPutNum(currentObs.getPutNum() + 1);
//                                    currentObs.setUseSize(currentObs.getUseSize() + file.getSize());
//                                    currentObs.setRemainderSize(currentObs.getSharpingSize() - currentObs.getUseSize());
//
//                                    ObsInfo obsInfo = obsInfoRepository.save(currentObs);
//                                    if (obsInfo != null) {
                                    return Result.Result(200, "", settings.getPrivateDownLoadUrl() + "/" + secret);
//                                    } else {
//                                        return Result.Result(409, "保存出错", "");
//                                    }
                                }else{
                                    return Result.Result(403, "保存出错", "");
                                }


                            }else {

                                //上传完毕
                                ObsConfiguration config = new ObsConfiguration();
                                config.setSocketTimeout(30000);
                                config.setConnectionTimeout(10000);
                                config.setEndPoint(endPoint);

                                String url = "";

                                //非重复文件
                                ObsClient obsClient = null;

                                try
                                {
                                    /*
                                     * Constructs a obs client instance with your account for accessing OBS
                                     */
                                    obsClient = new ObsClient(ak, sk, config);

                                    ExecutorService executorService = Executors.newFixedThreadPool(5);

                                    List<PartEtag> partETags = Collections.synchronizedList(new ArrayList<PartEtag>());

                                    /*
                                     * Create bucket
                                     */
                                    //System.out.println("Create a new bucket for demo\n");

                                    //path+d+file.getOriginalFilename().split(".")[1])
                                    UtilsForHwFile hw = new UtilsForHwFile(obsClient,bucketName,partETags,newFileName);

                                    //obsClient.createBucket(bucketName);

                                    /*
                                     * Claim a upload id firstly
                                     */
                                    String uploadId = hw.claimUploadId();
                                    System.out.println("Claiming a new upload id " + uploadId + "\n");

                                    long partSize = 5 * 1024 * 1024l;// 5MB
                                    //创建随机的示例文件，然后上传

                                    File sampleFile;

                                    try{
                                        sampleFile =  new File(pStr);
                                        long fileLength = sampleFile.length();

                                        long partCount = fileLength % partSize == 0 ? fileLength / partSize : fileLength / partSize + 1;

                                        if (partCount > 10000)
                                        {
                                            throw new RuntimeException("Total parts count should not exceed 10000");
                                        }
                                        else
                                        {
                                            System.out.println("Total parts count " + partCount + "\n");
                                        }

                                        /*
                                         * Upload multiparts to your bucket
                                         */
                                        System.out.println("Begin to upload multiparts to OBS from a file\n");
                                        for (int i = 0; i < partCount; i++)
                                        {
                                            long offset = i * partSize;
                                            long currPartSize = (i + 1 == partCount) ? fileLength - offset : partSize;
                                            executorService.execute(new UtilsForHwFile.PartUploader(sampleFile, offset, currPartSize, i + 1, uploadId));
                                        }

                                        /*
                                         * Waiting for all parts finished
                                         */
                                        executorService.shutdown();
                                        while (!executorService.isTerminated())
                                        {
                                            try
                                            {
                                                executorService.awaitTermination(5, TimeUnit.SECONDS);
                                            }
                                            catch (InterruptedException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }

                                        /*
                                         * Verify whether all parts are finished
                                         */
                                        if (partETags.size() != partCount)
                                        {
                                            throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
                                        }
                                        else
                                        {
                                            System.out.println("Succeed to complete multiparts into an object named " + hw.objectKey + "\n");
                                            System.out.println("upload URL : "+endPoint+"/tes/"+hw.objectKey);
                                            url = "https://"+endPoint+"/"+bucketName+"/"+hw.objectKey;
                                        }

                                        /*
                                         * View all parts uploaded recently
                                         */
                                        hw.listAllParts(uploadId);

                                        /*
                                         * Complete to upload multiparts
                                         */
                                        completeMultipartUpload(uploadId);

                                        //密钥组成规则
                                /*
                                上传日期+文件大小作为明文
                                 */
                                        String content = d+""+file.getSize();
                                        String password = code;
                                        System.out.println("明文：" + content);
                                        System.out.println("key：" + password);

                                        byte[] encryptResult = AES256.encrypt(content, password);
                                        //现在保存数据
                                        secret = AES256.parseByte2HexStr(encryptResult);
                                        //上传完毕
                                        UploadInfo uploadInfo = new UploadInfo();
                                        uploadInfo.setObsInfoId(currentObs.getId());
                                        uploadInfo.setOriginalName(file.getOriginalFilename());
                                        uploadInfo.setReal_name(newFileName);
                                        uploadInfo.setRealUrl(url);
                                        uploadInfo.setUploadIp(ip);
                                        uploadInfo.setSize(file.getSize());
                                        uploadInfo.setUploadTime(Utils.getNowDate());
                                        //真实的url+code+文件大小进行加密，生成访问密钥
                                        uploadInfo.setUploadSecret(secret);
                                        uploadInfo.setEnable(true);
                                        Settings settings = settingsRepository.getOne(1);
                                        uploadInfo.setDownloadUrl(settings.getPrivateDownLoadUrl()+"/"+secret);
                                        uploadInfo.setAk(ak);
                                        uploadInfo.setDev_id(developer.getId());
                                        uploadInfo.setBucketName(bucketName);
                                        uploadInfo.setEndpoint(endPoint);
                                        uploadInfo.setSk(sk);
                                        uploadInfo.setMd5(md5);
                                        uploadInfo.setType(true);
                                        UploadInfo uploadInfo1 = uploadInfoRepository.save(uploadInfo);
                                        System.out.println(uploadInfo.toString());
                                        if(uploadInfo1 != null){

                                            //现在更新obs的信息
                                            currentObs.setPutNum(currentObs.getPutNum()+1);
                                            currentObs.setUseSize(currentObs.getUseSize()+file.getSize());
                                            currentObs.setRemainderSize(currentObs.getSharpingSize()-currentObs.getUseSize());

                                            developer.setUseSize(developer.getUseSize()+file.getSize());
                                            developer.setTodaySize(developer.getTodaySize()+file.getSize());
                                            developerRepository.save(developer);

                                            ObsInfo obsInfo = obsInfoRepository.save(currentObs);
                                            if(obsInfo != null){
                                                return Result.Result(200, "", settings.getPrivateDownLoadUrl()+"/"+secret);
                                            }else{
                                                return Result.Result(409, "保存出错", "");
                                            }
                                        }else{
                                            return Result.Result(403, "保存出错", "");
                                        }
                                    }catch (Exception e){
                                        return Result.Result(406, "分段上传出错", "");
                                    }
                                }
                                catch (ObsException e)
                                {
                                    System.out.println("Response Code: " + e.getResponseCode());
                                    System.out.println("Error Message: " + e.getErrorMessage());
                                    System.out.println("Error Code:       " + e.getErrorCode());
                                    System.out.println("Request ID:      " + e.getErrorRequestId());
                                    System.out.println("Host ID:           " + e.getErrorHostId());
                                    return Result.Result(200, "", "");
                                }
                            }

                        }else{
                            return Result.Result(408,"获取文件特征值错误","");
                        }
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return Result.Result(402,"上传失败"+e.getMessage(),"");
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Result.Result(401,"上传失败"+e.getMessage(),"");
                    }
                } else {
                    return Result.Result(405, "该用户没有可用的obs", "");
                }

            } else {
                return Result.Result(404, "没有该用户", "");
            }
        }else {
            return Result.Result(400,"上传失败,因为文件是空的","");
        }

    }


}
