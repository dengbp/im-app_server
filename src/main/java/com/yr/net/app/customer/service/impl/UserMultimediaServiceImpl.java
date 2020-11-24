package com.yr.net.app.customer.service.impl;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.customer.mapper.UserMultimediaMapper;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AddressByCoordUtil;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Slf4j
@Service
public class UserMultimediaServiceImpl extends ServiceImpl<UserMultimediaMapper, UserMultimedia> implements IUserMultimediaService {



    @Override
    public void saveFile(MultipartFile file, int type, int isFree, String price, CoordinateRequestDto coordinate,String showWord) throws AppException {
        String originName = file.getOriginalFilename();
        log.info("begin upload file[{}]",originName);
        String storeName = DateUtil.current_yyyyMMddHHmmss() + "_"+ AppUtil.getCurrentUserId() + "_"+ originName;
        log.info("保存的文件名为: {}",storeName);
        String path = "E:/fileUpload/" +storeName;
        log.info("保存文件绝对路径{}",path);
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            this.save(createUserMultimedia( file, storeName, AddressByCoordUtil.getAdd(coordinate.getLongitude(),coordinate.getLatitude()), path, isFree, price,showWord));
            log.info("upload file[{}]success",originName);
        } catch (Exception e) {
            log.error("上传失败",e);
            throw new AppException(e.getMessage());
        }
    }



    private UserMultimedia createUserMultimedia(MultipartFile file,String storeName,String addr,String path,int isFree,String price,String showWord){
        String originName = file.getOriginalFilename();
        String extName = originName.substring(originName.lastIndexOf(".") + 1);
        UserMultimedia userMultimedia = new UserMultimedia();
        userMultimedia.setUserId(AppUtil.getCurrentUserId());
        userMultimedia.setAddr(addr);
        userMultimedia.setCreatedTime(LocalDateTime.now());
        userMultimedia.setState(0);
        userMultimedia.setFileSize(file.getSize());
        userMultimedia.setFormat(extName);
        userMultimedia.setUrl(path);
        userMultimedia.setUploadTime(LocalDateTime.now());
        userMultimedia.setIsFree(isFree);
        userMultimedia.setPrice(new BigDecimal(price));
        userMultimedia.setMultimediaName(originName);
        userMultimedia.setStoreName(storeName);
        userMultimedia.setShowWord(showWord);
        return userMultimedia;
    }
}
