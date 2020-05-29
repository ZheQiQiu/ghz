package com.ghz.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandsontableColumn {

    private String data;
    private String title;
    private String type;
    private Boolean readOnly;
    private Integer width;
}
