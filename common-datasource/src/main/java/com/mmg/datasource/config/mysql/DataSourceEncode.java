package com.mmg.datasource.config.mysql;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.mmg.commons.config.exception.ApiException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @Auther: fan
 * @Date: 2021/6/3
 * @Description: 数据库密码解密
 */
//@Slf4j
//public class DataSourceEncode extends HikariDataSource {
//
//    private String passwordDis;
//    /**
//     * 密钥
//     */
//    private final static String PKEY = "83m2qc6u3q36hqy4";
//
//    @Override
//    public String getPassword() {
//        if (StringUtils.isNotBlank(passwordDis)) {
//            return passwordDis;
//        }
//        String encPassword = super.getPassword();
//        if (null == encPassword) {
//            return null;
//        }
//        try {
//            //  密文解密，解密方法可以修改
//            String key = HexUtil.encodeHexStr(PKEY);
//            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
//            passwordDis = aes.decryptStr(encPassword, CharsetUtil.CHARSET_UTF_8);
//            return passwordDis;
//        } catch (Exception e) {
//            log.error("数据库密码解密出错，{" + encPassword + "}");
//            throw new ApiException("数据库密码解密失败！", e);
//        }
//    }
//}
