package com.yr.net.app.customer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户标签
 *
 * @author dengbp
 */
@Data
public class UserTagRequestDto  {


    /**
     * 标签id
     */
    @NotNull(message = "tagId is null")
    private Long tagId;

    /**
     * 标签内容（对应标签表的tag_name）
     */
    @NotNull(message = "标签内容不能为空")
    private String tagContent;

    /**
     * 标签类型 0:性格类；1：爱好类；...
     */
    @NotNull(message = "标签类型不能为空")
    private Integer tagType;

    /**
     * 状态 0:正常；1:删除
     */
    @NotNull(message = "state is null")
    private Integer state;


}
