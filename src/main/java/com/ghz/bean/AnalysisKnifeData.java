package com.ghz.bean;

import com.ghz.bean.mg.KnifeData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisKnifeData extends KnifeData {

    private Integer ordered;
    private String nickName;
}
