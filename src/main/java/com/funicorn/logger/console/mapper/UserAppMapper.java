package com.funicorn.logger.console.mapper;

import com.funicorn.logger.console.entity.UserApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户与客户端关系表 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2023-03-29
 */
@Mapper
public interface UserAppMapper extends BaseMapper<UserApp> {

}
