package com.ghz.bean;

import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSpeakEntry {

    private Integer memberId;
    private Date date;
    private List<String> text;

    public MemberSpeakEntry(String str) throws GhzException {
        if(str == null) throw new GhzException(StatusCodeEnum.PARAM_EXCEPTION);
        text = new ArrayList<>();
        String[] arr = str.substring(str.indexOf('#')+1).split(" ");
        memberId = Integer.parseInt(arr[0]);
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(arr[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int matches(String str){
        if(text == null) return -1;
        for(int i=0;i<text.size();i++){
            if(text.get(i).matches(str)){
                return i;
            }
        }
        return -1;
    }

    public boolean matches0(String str){
        if(memberId == 0||text == null || text.size() > 1) return false;
        return text.get(0).matches(str);
    }

    public Integer getDamage(){
        return 0;
    }
}
