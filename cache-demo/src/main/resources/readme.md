详细源码以及使用说明参考github地址：
https://github.com/qiujiayu/AutoLoadCache.git
改地址中readme详细说明了使用方法

示例使用的GitHub地址：
https://github.com/qiujiayu/cache-example.git

访问地址GitHub：
https://github.com/qiujiayu

##########################################################3
此时Demo测试时对于查询缓存等，要保持查询参数不变，否则hash值是变化的额，缓存就会不断增加
跟踪Debug调试缓存入口类：CachePointCut即可跟踪到缓存的存储，删除与命中情况