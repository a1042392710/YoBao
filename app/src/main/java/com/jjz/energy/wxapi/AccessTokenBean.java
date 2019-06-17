package com.jjz.energy.wxapi;

/**
 * @ author FX
 * @ date  2019/1/3  17:11
 * @ fuction
 */
public class AccessTokenBean {
    /**
     * access_token : YXYY1phy453rnuNb6eUGM208lOT7SW_BBTCJ06sllwKS3x8zWL6aphh9FkVx_ZajcIOeG_Y3qQ3MVRZOjjJqcwdCnF0ugA-J8pIgoYYfGeA
     * expires_in : 7200
     * refresh_token : tTHgUMA5zgeh7V8DmMYe7CmsKn1hUgz9jkmtAFWuthszKCpREsBBVzRjWYd5IAueXMlUWcn6psUhWdlP9pnfn80cy4e2idQxWgDSHwSxe8A
     * openid : okVKhwHPx6upTkGq99-1SpvxY8UA
     * scope : snsapi_userinfo
     * unionid : oiazt0Ul9_XpH3ETZh3smc8fWIkY
     */
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
