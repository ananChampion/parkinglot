package jmu.service;

import jmu.vo.DayRecord;
import jmu.vo.Record;
import jmu.vo.TempRecord;

import java.util.List;

public interface RecordService {
    public List<Record> queryRecord();
    public List<TempRecord> queryTempRecord();
    public List<Record> queryRecordById();
    public List<Record> selectByCarnum(String carnum);
    public TempRecord selectTRByCarnum(String carnum);
    public Integer insertRecord(Record record);
    public Integer insertTRecord(TempRecord tempRecord);
    public Integer deleteTRByCarnum(String carnum);
    public Integer deleteByCarnum(String carnum);
    public Integer deleteByRId(Integer rId);
    public List<TempRecord> selectBySend(Boolean tSend);
    public Integer updateTempRecord(TempRecord tempRecord);
    public List<DayRecord> selectRecordNumByDay();
    public List<Record> searchByCarnum(String carnum);


}
