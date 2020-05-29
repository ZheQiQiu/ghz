package com.ghz.service;

import com.ghz.bean.AnalysisKnifeData;
import com.ghz.bean.DistEnterBossData;
import com.ghz.bean.DistKnifeData;
import com.ghz.bean.mg.Boss;
import com.ghz.bean.mg.BossList;
import com.ghz.bean.mg.Member;
import com.ghz.except.GhzException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EnterService {

    DistEnterBossData getBossSituationData() throws GhzException;
    Map<String, Object> findPageKnifeData(int page,int limit) throws GhzException;
    void deleteKnifeData(Integer id) throws GhzException;
    BossList getCurrentBossList() throws GhzException;
    List<Boss> getCurrentBosses(Integer id) throws GhzException;
    void updateBossData(Integer round,Integer currentNum,Date startDate,List<Boss> bosses) throws GhzException;
    List<Member> getAllMembers() throws GhzException;
    void addMember(String numberId,String nickName) throws GhzException;
    void deleteMember(int id) throws GhzException;
    void insertKnife(int memberId, int damage, Date date) throws GhzException;
    Map<String, Object> knifeDataAnalysis(List<String> list) throws GhzException;
    void knifeDataAnalysisUpload(DistEnterBossData bossData, List<AnalysisKnifeData> knifeDataList) throws GhzException;
}
