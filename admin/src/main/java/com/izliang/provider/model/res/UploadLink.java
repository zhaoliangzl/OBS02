package com.izliang.provider.model.res;

public class UploadLink {

    //公有上传链接
    private String publicUploadLink;
    //私有上传链接
    private String privateUploadlink;
    //私有上传密钥
    private String privateSecret;

    public String getPublicUploadLink() {
        return publicUploadLink;
    }

    public void setPublicUploadLink(String publicUploadLink) {
        this.publicUploadLink = publicUploadLink;
    }

    public String getPrivateUploadlink() {
        return privateUploadlink;
    }

    public void setPrivateUploadlink(String privateUploadlink) {
        this.privateUploadlink = privateUploadlink;
    }

    public String getPrivateSecret() {
        return privateSecret;
    }

    public void setPrivateSecret(String privateSecret) {
        this.privateSecret = privateSecret;
    }
}
