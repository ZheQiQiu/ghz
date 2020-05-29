package com.ghz.service.impl;

import com.ghz.bean.DateKnifeData;
import com.ghz.bean.mg.*;
import com.ghz.bean.repository.mg.BossMapper;
import com.ghz.bean.repository.mg.GhzDataMapper;
import com.ghz.bean.repository.mg.KnifeDataMapper;
import com.ghz.bean.repository.mg.MemberMapper;
import com.ghz.except.GhzException;
import com.ghz.service.KnifeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnifeStatisticsServiceImpl implements KnifeStatisticsService {

    @Autowired
    private KnifeDataMapper knifeDataMapper;

    @Autowired
    private BossMapper bossMapper;

    @Autowired
    private GhzDataMapper ghzDataMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<DateKnifeData> dateKnifeData(Date date) throws GhzException {

        Integer listId = ghzDataMapper.selectByPrimaryKey(1).getCurrentBossList();
        BossExample bossExample = new BossExample();
        BossExample.Criteria bossCriteria = bossExample.createCriteria();
        bossCriteria.andListIdEqualTo(listId);
        List<Boss> bosses = bossMapper.selectByExample(bossExample);

        List<Integer> bossIdList = new ArrayList<>();
        for(Boss b:bosses){
            bossIdList.add(b.getId());
        }
        KnifeDataExample knifeDataExample = new KnifeDataExample();
        KnifeDataExample.Criteria knifeDataCriteria = knifeDataExample.createCriteria();
        knifeDataCriteria.andBossIdIn(bossIdList);
        knifeDataCriteria.andDateEqualTo(date);
        List<KnifeData> knifeDataList = knifeDataMapper.selectByExample(knifeDataExample);

        List<Member> members = memberMapper.selectByExample(new MemberExample());

        Map<Integer,Integer> totalDamageMap = new HashMap<>();
        Map<Integer,Integer> countMap = new HashMap<>();

        for(KnifeData kd:knifeDataList){
            Integer mid = kd.getMemberId();

            if(!totalDamageMap.containsKey(mid)){
                totalDamageMap.put(mid,0);
            }
            if(!countMap.containsKey(mid)){
                countMap.put(mid,0);
            }

            totalDamageMap.put(mid,totalDamageMap.get(mid)+kd.getDamage());
            countMap.put(mid,countMap.get(mid)+1);
        }


        List<DateKnifeData> res = new ArrayList<>();
        for(Member m:members){
            Integer mid = m.getId();
            DateKnifeData dkd = new DateKnifeData();
            dkd.setDate(date);
            dkd.setNickName(m.getNickName());
            dkd.setCount(countMap.getOrDefault(mid, 0));
            dkd.setTotalDamage(totalDamageMap.getOrDefault(mid,0));
            res.add(dkd);
        }

        return res;
    }
}
