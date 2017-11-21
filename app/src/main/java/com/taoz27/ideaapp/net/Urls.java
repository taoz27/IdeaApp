package com.taoz27.ideaapp.net;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class Urls {
    /**服务器地址*/
    public static final String Server="http://120.78.189.95/";
//    public static final String Server="http://113.54.213.253:8080/";

    /**用户接口模块*/
    public static final String User=Server+"user/";
    /**用户登录*/
    public static final String UserLogin=User+"login.do";
    /**用户注册*/
    public static final String UserRegister=User+"register.do";
    /**检查邮箱合法性*/
    public static final String UserCheckValid=User+"check_valid.do";
    /**用户认证*/
    public static final String UserVilidate=User+"vilidate.do";
    /**获取用户是否登陆、用户信息*/
    public static final String UserGetUserInfo=User+"get_user_info.do";
    /**登出*/
    public static final String UserLogout=User+"logout.do";
    /**忘记密码获取密保问题*/
    public static final String UserForgetGetQuestion=User+"forget_get_question.do";
    /**提交密保问题的答案*/
    public static final String UserForgetCheckAnswer=User+"forget_check_answer.do";
    /**忘记密码时的重置密码*/
    public static final String UserForgetResetPassword=User+"forget_reset_password.do";
    /**登陆状态下的重置密码*/
    public static final String UserResetPassword=User+"reset_password.do";
    /**更新用户信息*/
    public static final String UserUpdateInfo=User+"update_information.do";
    /**获取所有的分类*/
    public static final String GetAllCategory=User+"get_all_category.do";

    /**用户管理模块*/
    public static final String UserManage=User+"manage/";
    /**添加活动*/
    public static final String ManageAdd=UserManage+"add.do";
    /**修改活动*/
    public static final String ManageAlter=UserManage+"alter.do";
    /**列出活动*/
    public static final String ManageList=UserManage+"list.do";
    /**搜索活动*/
    public static final String ManageSearch=UserManage+"search.do";
    /***/
    public static final String ManageDelete=UserManage+"delete.do";

    /***/
    public static final String Search=Server+"function/search.do";
}
