package com.yr.net.app.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.yr.net.app.pay.dto.PayReqDto;
import com.yr.net.app.tools.AppUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *用户充值记录
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_order")
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 支付id*/
    @TableId("order_Id")
    private Long orderId;

    /** 充值用户id*/
   @TableField("user_Id")
    private String userId;

    /** 总金额(分) */
    @TableField("total_Fee")
    private Integer totalFee;

    /** 备注 */
    private String remark;

    /** 商户订单号 */
        @TableField("out_Trade_No")
    private String outTradeNo;

        /** 交易状态，0:待定支付，1：已支付(成功)，2：交易失败 */
        private Integer status;

        public static transient Integer PAYED = 1;
        public static transient Integer PAY_FAIL = 2;

    /** 用户ip */
        @TableField("create_Ip")
    private String createIp;

    /** 创建时间 */
        @TableField("create_Time")
    private LocalDate createTime;


    /** 更新时间 */
    @TableField("update_Time")
    private LocalDate updateTime;

    /** 支付时间 */
        @TableField("pay_Time")
    private LocalDate payTime;



        public static  UserOrder createOrder(String orderNo,String ip,PayReqDto payReqDto){
            UserOrder order = new UserOrder();
            order.setCreateTime(LocalDate.now());
            order.setOutTradeNo(orderNo);
            order.setCreateIp(ip);
            order.setUserId(AppUtil.getCurrentUserId());
            order.setTotalFee(payReqDto.getTotalFee());
            return order;
        }

}
