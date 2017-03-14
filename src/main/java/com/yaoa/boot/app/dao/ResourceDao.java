package com.yaoa.boot.app.dao;

import com.yaoa.boot.app.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * RightDao
 * @author ChenJianhui
 */
@Repository
public interface ResourceDao extends BaseDao<Resource, Long> {

    /**
     * 根据接口URL查询
     * @param url
     * @return
     */
    Resource getByUrl(String url);

    /**
     * 查询指定模块的所有资源
     * @param moduleId
     * @return
     */
    List<Resource> findByModuleId(Long moduleId);

    /**
     * 查询最大的资源位置
     * @return
     */
    @Query("select max(position) from Resource")
    Integer getMaxResourcePos();

    /**
     * 查询一个资源位中最大的资源编码
     * @param position
     * @return
     */
    @Query("select max(code) from Resource where position = :position")
    Long getMaxResourceCode(@Param("position") int position);

}
