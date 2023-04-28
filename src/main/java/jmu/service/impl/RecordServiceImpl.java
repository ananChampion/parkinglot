package jmu.service.impl;

import jmu.mapper.RecordMapper;
import jmu.service.RecordService;
import jmu.vo.DayRecord;
import jmu.vo.Record;
import jmu.vo.TempRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;


    @Override
    public List<Record> queryRecord() {
        return this.recordMapper.queryRecord();
    }

    @Override
    public List<TempRecord> queryTempRecord() {
        return this.recordMapper.queryTempRecord();
    }

    @Override
    public List<Record> queryRecordById() {
        return this.recordMapper.queryRecordById();
    }

    @Override
    public List<Record> selectByCarnum(String carnum) {
        return this.recordMapper.selectByCarnum(carnum);
    }

    @Override
    public TempRecord selectTRByCarnum(String carnum) {
        return this.recordMapper.selectTRByCarnum(carnum);
    }

    @Override
    public Integer insertRecord(Record record) {
        return this.recordMapper.insertRecord(record);
    }

    @Override
    public Integer insertTRecord(TempRecord tempRecord) {
        return this.recordMapper.insertTRecord(tempRecord);
    }

    @Override
    public Integer deleteTRByCarnum(String carnum) {
        return this.recordMapper.deleteTRByCarnum(carnum);
    }

    @Override
    public Integer deleteByCarnum(String carnum) {
        return this.recordMapper.deleteByCarnum(carnum);
    }

    @Override
    public Integer deleteByRId(Integer rId) {
        return this.recordMapper.deleteByRId(rId);
    }

    @Override
    public List<TempRecord> selectBySend(Boolean tSend) {
        return this.recordMapper.selectBySend(tSend);
    }

    @Override
    public Integer updateTempRecord(TempRecord tempRecord) {
        return this.recordMapper.updateTempRecord(tempRecord);
    }

    @Override
    public List<DayRecord> selectRecordNumByDay() {
        return this.recordMapper.selectRecordNumByDay();
    }
}
