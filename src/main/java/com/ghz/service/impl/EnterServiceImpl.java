package com.ghz.service.impl;

import com.ghz.bean.AnalysisKnifeData;
import com.ghz.bean.DistEnterBossData;
import com.ghz.bean.DistKnifeData;
import com.ghz.bean.MemberSpeakEntry;
import com.ghz.bean.mg.*;
import com.ghz.bean.repository.DistEnterBossDataMapper;
import com.ghz.bean.repository.DistKnifeDataMapper;
import com.ghz.bean.repository.mg.*;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.EnterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnterServiceImpl implements EnterService {

    @Autowired
    private DistEnterBossDataMapper distEnterBossDataMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BossMapper bossMapper;

    @Autowired
    private GhzDataMapper ghzDataMapper;

    @Autowired
    private BossListMapper bossListMapper;

    @Autowired
    private KnifeDataMapper knifeDataMapper;

    @Autowired
    private DistKnifeDataMapper distKnifeDataMapper;

    @Override
    public DistEnterBossData getBossSituationData() throws GhzException {
        DistEnterBossData res;

        try {
            res = distEnterBossDataMapper.selectCurrentBossData().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }

        return res;
    }

    @Override
    public Map<String, Object> findPageKnifeData(int page, int limit) throws GhzException {

        PageInfo pageInfo;

        try{
            PageHelper.startPage(page,limit);
            pageInfo = new PageInfo(distKnifeDataMapper.selectAllDistKnifeData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }

        Map<String,Object> res = new HashMap<>();

        res.put("count",pageInfo.getTotal());
        res.put("data",pageInfo.getList());

        return res;
    }

    @Override
    public void deleteKnifeData(Integer id) throws GhzException {
        try {
            knifeDataMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }
    }

    @Override
    public BossList getCurrentBossList() throws GhzException {

        BossList bossList;

        try {
            Integer currentBossList = ghzDataMapper.selectByPrimaryKey(1).getCurrentBossList();
            bossList = bossListMapper.selectByPrimaryKey(currentBossList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }

        return bossList;
    }

    @Override
    public List<Boss> getCurrentBosses(Integer id) throws GhzException {

        BossExample bossExample = new BossExample();
        BossExample.Criteria bossCriteria = bossExample.createCriteria();
        bossCriteria.andListIdEqualTo(id);
        bossExample.setOrderByClause("ordered asc");

        List<Boss> bosses;

        try {
            bosses = bossMapper.selectByExample(bossExample);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }

        return bosses;
    }

    @Override
    @Transactional
    public void updateBossData(Integer round, Integer currentNum, Date startDate, List<Boss> bosses) throws GhzException {

        BossList bossList = bossListMapper.selectByPrimaryKey(bosses.get(0).getListId());
        if(round <= 0) throw new GhzException(StatusCodeEnum.PARAM_EXCEPTION);
        bossList.setRound(round);
        if(currentNum <=0 || currentNum > bosses.size()) throw new GhzException(StatusCodeEnum.PARAM_EXCEPTION);
        bossList.setCurrentNum(currentNum);
        bossList.setStartDate(startDate);
        bossListMapper.updateByPrimaryKey(bossList);

        List<Integer> bossIdList = new ArrayList<>();
        for(Boss b:bosses){
            bossIdList.add(b.getId());
        }

        KnifeDataExample knifeDataExample = new KnifeDataExample();
        KnifeDataExample.Criteria knifeDataCriteria = knifeDataExample.createCriteria();
        knifeDataCriteria.andBossIdIn(bossIdList);
        List<KnifeData> knifeDataList = knifeDataMapper.selectByExample(knifeDataExample);

        Map<Integer,Integer> bossId2Ordered = new HashMap<>();
        for(Boss b:bosses){
            bossId2Ordered.put(b.getId(),b.getOrdered());
        }

        List<Integer> invalidKnifeDataIdList = new ArrayList<>();
        for(KnifeData kd:knifeDataList){
            if(kd.getRound() > round || (kd.getRound().equals(round)&&bossId2Ordered.get(kd.getBossId())>currentNum)||kd.getDate().before(startDate)){
                invalidKnifeDataIdList.add(kd.getId());
            }
        }

        if(invalidKnifeDataIdList.size()!=0){
            knifeDataExample = new KnifeDataExample();
            knifeDataCriteria = knifeDataExample.createCriteria();
            knifeDataCriteria.andIdIn(invalidKnifeDataIdList);
            knifeDataMapper.deleteByExample(knifeDataExample);
        }

        for(Boss b:bosses){
            Integer ordered = b.getOrdered();
            if(ordered < currentNum){
                b.setCurrentHp(0);
            }
            else if(ordered.equals(currentNum)){
                if(b.getCurrentHp()>b.getMaxHp())
                    b.setCurrentHp(b.getMaxHp());
            }else{
                b.setCurrentHp(b.getMaxHp());
            }
            bossMapper.updateByPrimaryKey(b);
        }

    }

    @Override
    public List<Member> getAllMembers() throws GhzException {
        List<Member> res;
        try {
            res = memberMapper.selectByExample(new MemberExample());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }
        return res;
    }

    @Override
    public void addMember(String numberId, String nickName) throws GhzException {
        Member member = new Member();
        member.setNumberId(numberId);
        member.setNickName(nickName);

        try {
            memberMapper.insertSelective(member);
        } catch (Exception e){
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_SAVE_FAIL);
        }
    }

    @Override
    public void deleteMember(int id) throws GhzException {

        try {
            memberMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_DELETE_FAIL);
        }
    }

    @Override
    @Transactional
    public void insertKnife(int memberId, int damage, Date date) throws GhzException {

        if(damage < 0){
            throw new GhzException(StatusCodeEnum.PARAM_EXCEPTION);
        }

        DistEnterBossData bossData = getBossSituationData();
        Boss boss = bossMapper.selectByPrimaryKey(bossData.getBossId());

        KnifeData knifeData = new KnifeData();

        knifeData.setMemberId(memberId);
        knifeData.setBossId(bossData.getBossId());
        knifeData.setRound(bossData.getRound());
        knifeData.setDate(date);

        //没有击杀BOSS
        if(boss.getCurrentHp() > damage){
            knifeData.setDamage(damage);
            boss.setCurrentHp(boss.getCurrentHp() - damage);
            bossMapper.updateByPrimaryKey(boss);
        }
        //击杀BOSS
        else {
            knifeData.setDamage(boss.getCurrentHp());
            GhzData ghzData = ghzDataMapper.selectByPrimaryKey(1);
            BossList bossList = bossListMapper.selectByPrimaryKey(ghzData.getCurrentBossList());

            //击杀但没有进入下一轮
            if(bossList.getTotalNum() > boss.getOrdered()){
                boss.setCurrentHp(0);
                bossMapper.updateByPrimaryKey(boss);
                bossList.setCurrentNum(bossList.getCurrentNum()+1);
                bossListMapper.updateByPrimaryKey(bossList);
            }
            //击杀且进入下一轮
            else {
                BossExample bossExample = new BossExample();
                BossExample.Criteria bossCriteria = bossExample.createCriteria();
                bossCriteria.andListIdEqualTo(bossList.getId());
                List<Boss> bosses = bossMapper.selectByExample(bossExample);
                for(Boss b:bosses){
                    b.setCurrentHp(b.getMaxHp());
                    bossMapper.updateByPrimaryKey(b);
                }
                bossList.setCurrentNum(1);
                bossList.setRound(bossList.getRound()+1);
                bossListMapper.updateByPrimaryKey(bossList);
            }
        }

        knifeDataMapper.insertSelective(knifeData);

    }

    @Override
    public Map<String, Object> knifeDataAnalysis(List<String> list) throws GhzException {

        Integer currentBossList = ghzDataMapper.selectByPrimaryKey(1).getCurrentBossList();

        Map<String,Object> res = new HashMap<>();
        DistEnterBossData bossData = getBossSituationData();

        List<AnalysisKnifeData> akds = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        List<Boss> bosses = getCurrentBosses(currentBossList);
        Map<Integer,Integer> ordered2BossId = new HashMap<>();
        List<Integer> bossIdList = new ArrayList<>();
        for(Boss b:bosses){
            bossIdList.add(b.getId());
            ordered2BossId.put(b.getOrdered(),b.getId());
        }
        KnifeDataExample knifeDataExample = new KnifeDataExample();
        knifeDataExample.createCriteria()
                .andBossIdIn(bossIdList);
        List<KnifeData> kdsDb = knifeDataMapper.selectByExample(knifeDataExample);

        List<Member> members = getAllMembers();
        Map<Integer,String> memberId2NickName = new HashMap<>();
        for(Member m:members){
            memberId2NickName.put(m.getId(),m.getNickName());
        }

        List<MemberSpeakEntry> speaks = strList2MSE(list);

//        int currentKnifeMember = 0;
        DistEnterBossData aboveBossData = new DistEnterBossData();

        for (MemberSpeakEntry mse : speaks) {
            if (((Integer) 0).equals(mse.getMemberId())) {
                //机器人
                boolean changeFlag = str2bossData(bossData, mse.getText());
//                int startIdx = mse.matches(".*#[0-9]*.*已开始挑战boss$");
                int completeIdx = mse.matches(".*#[0-9]*.*已完成挑战boss$|.*#[0-9]*.*已强行下树$");
//                if(startIdx != -1){
//                    currentKnifeMember = Integer.parseInt(mse.getText().get(startIdx).replaceAll("^.*#|[ ]*已开始挑战boss$",""));
//                }
                if (completeIdx != -1) {
                    AnalysisKnifeData akd = new AnalysisKnifeData();
                    akd.setMemberId(Integer.parseInt(mse.getText().get(completeIdx).replaceAll("^.*#|[ ]*已完成挑战boss$|[ ]*已强行下树$", "")));
                    akd.setNickName(memberId2NickName.get(akd.getMemberId()));
                    akd.setRound(aboveBossData.getRound());
                    akd.setBossId(ordered2BossId.get(aboveBossData.getCurrentNum()));
                    akd.setOrdered(aboveBossData.getCurrentNum());
                    akd.setDate(mse.getDate());
                    try {
                        akd.setDamage(bossData.getCurrentNum().equals(aboveBossData.getCurrentNum()) && bossData.getRound().equals(aboveBossData.getRound()) ? aboveBossData.getCurrentHp() - bossData.getCurrentHp() : aboveBossData.getCurrentHp());
                    } catch (NullPointerException e) {
                        throw new GhzException(480,"聊天记录不完整");
                    }
                    if (!knifeDataCollisionInList(akd, kdsDb)) {
                        akds.add(akd);
                    }
                }
                if (changeFlag) {
                    aboveBossData = new DistEnterBossData(bossData);
                }
            }
//            else{
//                //成员
//                if(mse.matches0("^申请出刀$")){
//
//                }
//                else if(mse.matches0("^完成[ ][0-9]*$|^强行下树[ ][0-9]*$")){
//
//                }
//                else if(mse.matches0("^完成[ ]击杀$")){
//
//                }
//            }
        }

        DistEnterBossData finalBossData = aboveBossData;
        res.put("analysisKnifeData",akds.stream().filter(a->
                !(a.getRound()>finalBossData.getRound()
                || a.getRound().equals(finalBossData.getRound()) && a.getOrdered()>finalBossData.getCurrentNum()
                || a.getDate().before(finalBossData.getStartDate())
        )).collect(Collectors.toList()));
        res.put("bossData",finalBossData);

        return res;
    }

    @Override
    @Transactional
    public void knifeDataAnalysisUpload(DistEnterBossData bossData, List<AnalysisKnifeData> knifeDataList) throws GhzException {

        BossList bossList = getCurrentBossList();
        List<Boss> bosses = getCurrentBosses(bossList.getId());

        bossList.setStartDate(bossData.getStartDate());
        bossList.setCurrentNum(bossData.getCurrentNum());
        bossList.setRound(bossData.getRound());

        bossListMapper.updateByPrimaryKey(bossList);

        for(Boss b:bosses){
            if(b.getOrdered() < bossData.getCurrentNum()){
                b.setCurrentHp(0);
            }
            else if(b.getOrdered().equals(bossData.getCurrentNum())){
                b.setCurrentHp(bossData.getCurrentHp());
            }
            else{
                b.setCurrentHp(b.getMaxHp());
            }
            bossMapper.updateByPrimaryKey(b);
        }

        for(AnalysisKnifeData akd:knifeDataList){
            KnifeData knifeData = new KnifeData();
            knifeData.setMemberId(akd.getMemberId());
            knifeData.setDamage(akd.getDamage());
            knifeData.setBossId(akd.getBossId());
            knifeData.setRound(akd.getRound());
            knifeData.setDate(akd.getDate());
            knifeDataMapper.insertSelective(knifeData);
        }

    }

    private boolean knifeDataCollisionInList(KnifeData target,List<KnifeData> list){
        for(KnifeData kd:list){
            if(
                    kd.getDamage().equals(target.getDamage())
                    && kd.getDate().equals(target.getDate())
                    && kd.getBossId().equals(target.getBossId())
                    && kd.getRound().equals(target.getRound())
                    && kd.getMemberId().equals(target.getMemberId())
            ){
                return true;
            }
        }
        return false;
    }

    private boolean str2bossData(DistEnterBossData bossData,List<String> list){
        for(String str:list){
            if(str.matches("^(现在|当前)[0-9]*周目，[0-9]*号boss生命值[0-9]*$")){
                String[] arr = str.replaceAll("^(现在|当前)|周目，|号boss生命值"," ").substring(1).split(" ");
                bossData.setRound(Integer.parseInt(arr[0]));
                bossData.setCurrentNum(Integer.parseInt(arr[1]));
                bossData.setCurrentHp(Integer.parseInt(arr[2]));
                return true;
            }
        }
        return false;
    }

    private List<MemberSpeakEntry> strList2MSE(List<String> list) throws GhzException {
        List<MemberSpeakEntry> res = new ArrayList<>();

        String titleTemp = null;
        boolean effectiveFlag = false;
        MemberSpeakEntry mse = null;
        for(String str:list){
            if(str.matches("^.*#.*/.*/.*:.*:.*[0-9]$")){
                if(mse != null){
                    res.add(mse);
                    mse = null;
                }
                effectiveFlag = false;
                if(str.contains("#0")){
                    effectiveFlag = true;
                    mse = new MemberSpeakEntry(str);
                }else{
                    titleTemp = str;
                }
            } else {
                if(str.matches("^申请出刀$|^完成[ ][0-9]*$|^完成[ ]击杀$|^强行下树[ ][0-9]*$")){
                    effectiveFlag = true;
                    mse = new MemberSpeakEntry(titleTemp);
                }
                if(effectiveFlag){
                    mse.getText().add(str);
                }
            }
        }

        if(mse != null){
            res.add(mse);
        }

        return res;
    }
}
