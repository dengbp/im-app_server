package com.yr.net.app.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LicenseTaskSeq implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate createTime;

    private Long seq;

}
