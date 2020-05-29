package com.ghz.service.impl;

import com.ghz.bean.HandsontableColumn;
import com.ghz.bean.NestedHeader;
import com.ghz.bean.mg.*;
import com.ghz.bean.repository.mg.*;
import com.ghz.except.GhzException;
import com.ghz.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private GhzDataMapper ghzDataMapper;

    @Autowired
    private BossListMapper bossListMapper;

    @Autowired
    private BossMapper bossMapper;

    @Autowired
    private KnifeDataMapper knifeDataMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Map<String, Object> getColAndAllGhzData() throws GhzException {

        Map<String,Object> res = new HashMap<>();

        Integer blId = ghzDataMapper.selectByPrimaryKey(1).getCurrentBossList();
        BossList currentBossList = bossListMapper.selectByPrimaryKey(blId);

        BossExample bossExample = new BossExample();
        BossExample.Criteria bossCriteria = bossExample.createCriteria();
        bossCriteria.andListIdEqualTo(blId);
        bossExample.setOrderByClause("ordered asc");
        List<Boss> bosses = bossMapper.selectByExample(bossExample);

        List<Integer> bossIdList = new ArrayList<>();
        for(Boss b:bosses){
            bossIdList.add(b.getId());
        }

        KnifeDataExample knifeDataExample = new KnifeDataExample();
        KnifeDataExample.Criteria knifeDataCriteria = knifeDataExample.createCriteria();
        knifeDataCriteria.andBossIdIn(bossIdList);
        List<KnifeData> knifeDataList = knifeDataMapper.selectByExample(knifeDataExample);

        List<Member> members = memberMapper.selectByExample(new MemberExample());

        List<HandsontableColumn> columns = generateQueryColumns(currentBossList,bosses);
        List<List<Object>> nestedHeaders = new ArrayList<>();

        List<Object> firstRow = new ArrayList<>();
        firstRow.add("");
        for(int i=1;i<=bosses.size();i++){
            NestedHeader header = new NestedHeader();
            header.setLabel("第"+i+"只BOSS");
            header.setColspan(currentBossList.getRound());
            firstRow.add(header);
        }
        nestedHeaders.add(firstRow);

        List<Object> secondRow = new ArrayList<>();
        for(HandsontableColumn hc:columns){
            secondRow.add(hc.getTitle());
        }
        nestedHeaders.add(secondRow);

        List<Map<String,Object>> data = generateQueryData(members,knifeDataList,bosses);

        res.put("queryData",data);
        res.put("nestedHeaders",nestedHeaders);
        res.put("columns",columns);
        return res;
    }

    private List<Map<String, Object>> generateQueryData(List<Member> members, List<KnifeData> knifeDataList, List<Boss> bosses) {

        List<Map<String, Object>> data = new ArrayList<>();
        Map<Integer,Integer> bossId2Order = new HashMap<>();
        for(Boss b:bosses){
            bossId2Order.put(b.getId(),b.getOrdered());
        }

        for(Member m:members){
            Map<String,Object> map = new HashMap<>();
            map.put("member",m.getNickName());
            map.put("totalDamage",0);
            for(KnifeData kd:knifeDataList){
                if(kd.getMemberId().equals(m.getId())){
                    Integer bossId = kd.getBossId();
                    Integer round = kd.getRound();
                    String dataTitle = "boss"+bossId2Order.get(bossId)+"Round"+round;
                    if(!map.containsKey(dataTitle)){
                        map.put(dataTitle,0);
                    }
                    map.put(dataTitle, (Integer) map.get(dataTitle) + kd.getDamage());
                    map.put("totalDamage", (Integer) map.get("totalDamage") + kd.getDamage());
                }
            }
            data.add(map);
        }

        return data;
    }

    private List<HandsontableColumn> generateQueryColumns(BossList currentBossList, List<Boss> bosses) {
        List<HandsontableColumn> columns = new ArrayList<>();

        HandsontableColumn membersColumn = new HandsontableColumn();
        membersColumn.setData("member");
        membersColumn.setTitle("<b>工会成员</b>");
        membersColumn.setReadOnly(true);
        membersColumn.setWidth(120);
        membersColumn.setType("text");
        columns.add(membersColumn);

        for(int i=1;i<=bosses.size();i++){
            for(int j=1;j<=currentBossList.getRound();j++){
                HandsontableColumn col = new HandsontableColumn();
                col.setData("boss"+i+"Round"+j);
                col.setTitle("<b>"+j+"周目"+"</b>");
                col.setType("numeric");
                col.setReadOnly(false);
                col.setWidth(120);
                columns.add(col);
            }
        }

        HandsontableColumn totalDamageCol = new HandsontableColumn();
        totalDamageCol.setData("totalDamage");
        totalDamageCol.setTitle("<b>总伤害</b>");
        totalDamageCol.setReadOnly(true);
        totalDamageCol.setWidth(120);
        totalDamageCol.setType("numeric");
        columns.add(totalDamageCol);

        return columns;
    }
}
