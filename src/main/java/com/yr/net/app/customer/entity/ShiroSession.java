package com.yr.net.app.customer.entity;

import java.sql.Blob;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShiroSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sessionId;

    private Blob sessionData;


}
