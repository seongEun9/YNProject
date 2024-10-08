package org.zerock.chain.pse.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SystemNotificationDTO {

    private Long empNo;
    private Long systemNo;
    private String systemCategory;
    private String systemTitle;
    private String systemContent;
    private LocalDate systemUploadDate;
    private boolean isRead;

}
