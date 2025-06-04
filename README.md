# trading-system
简单交易系统

功能：交易记录的简单CURD
创建交易记录 - 校验金额、用户信息等
获取交易记录 - 提供简单的全部查询, 业务中可设置条件查询
删除交易记录
更新交易记录 - 更新状态

其他说明
1、数据存储基于内存，业务中使用数据库
2、简单系统将所有功能放在一个module
3、业务中一般分业务模块
    trade-application: controller层、数据库配置、统一异常处理等
    trade-service: 业务操作
    trade-api: 接口、dto、util等
    trade-manage: 数据库操作