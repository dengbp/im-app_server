package com.yr.net.app.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_seq")
public class OrderSeq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate createTime;

    private Long seq;


}
