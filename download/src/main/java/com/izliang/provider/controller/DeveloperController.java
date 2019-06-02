package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Developer;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.model.Settings;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.repo.SettingsRepository;
import com.izliang.provider.utils.MD5Utils;
import com.izliang.provider.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/developer")
@Api(value = "开发者信息管理",tags = "developer",description = "对开发者的账号信息进行管理")
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    //开发者修改自己的信息

    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者信息列表",notes = "获取开发者信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回开发者信息的列表")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
    })
    public JSONObject open(
            @ApiParam("page")@RequestParam("page")int page
    ) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Page<Developer> datas = developerRepository.findAll(pageable);

        return Result.ResultPage(200,"",datas.getContent(),page,datas.getTotalPages(),(int) datas.getTotalElements());
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者信息数据",notes = "获取单个开发者信息数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个开发者信息数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject get(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Developer developer = developerRepository.getOne(id);

        return Result.Result(200,"",developer);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(
            value = "更新开发者信息数据",notes = "更新开发者信息数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个obs数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject update(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Developer developer = developerRepository.getOne(id);

        return Result.Result(200,"",developer);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(
            value = "添加开发者信息数据",notes = "添加开发者信息数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个obs数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject add(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Developer developer = developerRepository.getOne(id);

        return Result.Result(200,"",developer);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ApiOperation(
            value = "删除单个开发者信息数据",notes = "删除单个开发者信息数据",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "删除成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject del(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        developerRepository.delete(id);

        return Result.Result(200,"","");
    }

    /**
     * 获取开发者的资源上传链接
     *
     * **/
    @RequestMapping(value = "/getuploadlink",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者上传链接",notes = "获取开发者上传链接",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个开发者的上传链接"),
            @ApiResponse(code = 404,message = "没有用户数据"),
            @ApiResponse(code = 405,message = "没有生成的上传链接")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "用户id",required = true),
    })
    public JSONObject getUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Developer developer = developerRepository.getOne(id);

        if(developer != null){

            if(developer.getPublicUploadLink() == null || developer.getPublicUploadLink().equals("")){
                //没有生成对应的上传链接
                return Result.Result(405,"没有生成的上传链接","");
            }else{
                //有上传链接
                Settings settings = settingsRepository.getOne(1);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("publicUploadLink",settings.getUploadUrl()+"/"+developer.getPublicUploadLink());
                jsonObject.put("privateUploadLink",settings.getPrivateUpload()+"/"+developer.getPrivateUpladLink());
                jsonObject.put("privateSecret",developer.getPrivateSecret());
                return Result.Result(200,"",jsonObject);
            }
        }else{
            return Result.Result(404,"没有用户数据","");
        }
    }

    /**
     * 开发者生成对应的上传链接
     * */
    @RequestMapping(value = "/generateuploadlink",method = RequestMethod.GET)
    @ApiOperation(
            value = "开发者生成上传链接",notes = "开发者生成上传链接",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "生成成功，返回生成的链接"),
            @ApiResponse(code = 404,message = "没有用户数据"),
            @ApiResponse(code = 405,message = "生成失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "用户id",required = true),
    })
    public JSONObject generateUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        //生成用户上传链接
        Settings settings = settingsRepository.getOne(1);

        Developer developer = developerRepository.getOne(id);

        if(developer != null){

            //encode2hex
            /**
             * 公共上传链接,文件可以公共访问
             * 生成规则：
             * MD5:用户id+用户用户名+用户邮箱+
             *  生成用户公共密钥
             * 私有上传密钥
             * AES256:生成规则：明文为=>用户邮箱
             *  key为=>用户密钥
             *  私有上传链接：文件只能私有访问
             *  私有访问
             *  生成规则
            **/
            String uploadLink = MD5Utils.encode2hex(developer.getId()+developer.getUsername()+developer.getEamil());
            String privateUploadLink = MD5Utils.encode2hex(developer.getId()+developer.getUsername()+developer.getEamil()+uploadLink);
            String privateSecret = MD5Utils.encode2hex(developer.getId()+developer.getUsername()+developer.getEamil()+uploadLink+privateUploadLink);

            developer.setPublicUploadLink(uploadLink);
            developer.setPrivateSecret(privateSecret);
            developer.setPrivateUpladLink(privateUploadLink);

            Developer developer1 = developerRepository.save(developer);
            if(developer1 != null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("publicUploadLink",settings.getUploadUrl()+"/"+developer.getPublicUploadLink());
                jsonObject.put("privateUploadLink",settings.getPrivateUpload()+"/"+developer.getPrivateUpladLink());
                jsonObject.put("privateSecret",developer.getPrivateSecret());
                return Result.Result(200,"",jsonObject);
            }else{
                return Result.Result(405,"生成失败","");
            }
        }else{
            return Result.Result(404,"没有用户数据","");
        }
    }


}
